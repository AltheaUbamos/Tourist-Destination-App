
package com.example.destination.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.destination.models.Destination
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

private val destinationCoordinates = mapOf(
    "Fortune Island" to LatLng(14.0637, 120.6210),
    "Calayo Beach" to LatLng(14.1530, 120.5900),
    "Mount Batulao" to LatLng(14.0200, 120.7950),
    "Matabungkay Beach" to LatLng(13.9570, 120.6300),
    "San Diego Beach" to LatLng(13.9700, 120.6500),
    "Lian Town Church" to LatLng(14.0340, 120.6500),
    "Caleruega Church" to LatLng(14.0940, 120.8580),
    "Mount Talamitam" to LatLng(14.0410, 120.8190),
    "Pulo Island" to LatLng(14.0800, 120.5800),
    "Bituin Cove" to LatLng(14.1450, 120.5700),
    "Layag-Layag Beach" to LatLng(14.1500, 120.5650),
    "Wawa Pier" to LatLng(14.0730, 120.6250),
    "Matuod Beach" to LatLng(13.9700, 120.6350),
    "Talim Beach" to LatLng(13.9650, 120.6400),
    "Bataguas Beach" to LatLng(13.9750, 120.6450),
    "Kay Reyna Eco-Tourism" to LatLng(13.9900, 120.6750),
    "Ligtasin Cove Eco-Tours" to LatLng(13.9800, 120.6650)
)

// Normalize names so extra spaces, punctuation and capitalization
// won't prevent coordinate matching.
private fun normalizeDestinationName(name: String): String {
    return name.lowercase().filter { it.isLetterOrDigit() }
}

// Alternate destination names
private val destinationAliases = mapOf(
    "Mt. Batulao" to "Mount Batulao",
    "Mt Batulao" to "Mount Batulao",
    "Mt. Talamitam" to "Mount Talamitam",
    "Mt Talamitam" to "Mount Talamitam"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreMapScreen(
    destinations: List<Destination>,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    var selectedDestination by remember {
        mutableStateOf<Destination?>(null)
    }

    // Create a normalized coordinate lookup
    val coordinateLookup = remember {
        destinationCoordinates.entries.associate {
            normalizeDestinationName(it.key) to it.value
        }
    }

    val aliasLookup = remember {
        destinationAliases.mapKeys {
            normalizeDestinationName(it.key)
        }.mapValues {
            normalizeDestinationName(it.value)
        }
    }

    // Match destinations with coordinates, even if names vary slightly
    val locations = remember(destinations) {
        destinations.mapNotNull { destination ->
            val normalized = normalizeDestinationName(destination.name)
            val canonicalName = aliasLookup[normalized] ?: normalized

            coordinateLookup[canonicalName]?.let { coordinate ->
                destination to coordinate
            }
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(14.0550, 120.6650),
            10f
        )
    }

    // Zoom when a destination marker is selected
    LaunchedEffect(selectedDestination) {
        val selected = locations.firstOrNull {
            it.first.id == selectedDestination?.id
        }

        selected?.let {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    it.second,
                    15f
                )
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explore Map") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Text(
                text = "Explore Tourist Destinations",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(12.dp)
            )

            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),

                cameraPositionState = cameraPositionState,

                properties = MapProperties(
                    isBuildingEnabled = true
                ),

                uiSettings = MapUiSettings(
                    zoomControlsEnabled = true,
                    zoomGesturesEnabled = true,
                    scrollGesturesEnabled = true,
                    compassEnabled = true,
                    mapToolbarEnabled = true
                )
            ) {

                locations.forEach { (destination, position) ->

                    key(destination.id) {
                        Marker(
                            state = rememberMarkerState(
                                position = position
                            ),
                            title = destination.name,
                            snippet = destination.location,
                            onClick = {
                                selectedDestination = destination
                                true
                            }
                        )
                    }
                }
            }

            // Display selected destination
            selectedDestination?.let { destination ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            destination.name,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(destination.location)

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                val uri = if (destination.mapUrl.isNotBlank()) {
                                    Uri.parse(destination.mapUrl)
                                } else {
                                    Uri.parse(
                                        "https://www.google.com/maps/search/?api=1&query=" +
                                                Uri.encode(destination.name)
                                    )
                                }

                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    uri
                                )

                                context.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Get Directions")
                        }

                        TextButton(
                            onClick = {
                                selectedDestination = null
                            }
                        ) {
                            Text("Close")
                        }
                    }
                }
            }

            // Feedback when no destinations have matching coordinates
            if (locations.isEmpty()) {
                Text(
                    text = if (destinations.isEmpty()) {
                        "No destinations available."
                    } else {
                        "No destination markers found. Check destination names."
                    },
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Text(
                text = "${locations.size} of ${destinations.size} destinations displayed",
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
