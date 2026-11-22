

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
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

// Predefined coordinates for tourist destinations
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

    // Match each destination with its coordinates
    val locations = remember(destinations) {
        destinations.mapNotNull { destination ->
            destinationCoordinates[destination.name]?.let { coordinate ->
                destination to coordinate
            }
        }
    }

    // Initial map camera position
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(14.0550, 120.6650),
            10f
        )
    }

    // Zoom into selected destination
    LaunchedEffect(selectedDestination) {
        val selected = locations.firstOrNull {
            it.first.id == selectedDestination?.id
        }

        if (selected != null) {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    selected.second,
                    15f
                )
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Explore Map")
                },
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

            // Google Map
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

                // Display destination markers
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

            // Selected destination information
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
                            text = destination.name,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = destination.location
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Button(
                            onClick = {
                                val mapUri = Uri.parse(
                                    destination.mapUrl
                                )

                                context.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        mapUri
                                    )
                                )
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

            // Number of displayed destinations
            Text(
                text = "${locations.size} of ${destinations.size} destinations displayed",
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
