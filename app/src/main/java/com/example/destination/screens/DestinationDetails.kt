
package com.example.destination.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.destination.models.Destination
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.CameraPosition
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

private val aliases = mapOf(
    "Mt. Batulao" to "Mount Batulao",
    "Mt Batulao" to "Mount Batulao",
    "Mt. Talamitam" to "Mount Talamitam",
    "Mt Talamitam" to "Mount Talamitam"
)

private fun normalizeName(name: String): String {
    return name.lowercase().filter { it.isLetterOrDigit() }
}

@Composable
fun DestinationDetailsScreen(
    destination: Destination,
    onBackClick: () -> Unit
) {

    val context = LocalContext.current

    val coords = remember(destination.name) {

        val canonicalName =
            aliases.entries.firstOrNull {
                it.key.equals(destination.name, ignoreCase = true)
            }?.value ?: destination.name

        destinationCoordinates.entries.firstOrNull {
            normalizeName(it.key) == normalizeName(canonicalName)
        }?.value
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // DESTINATION IMAGE
        AsyncImage(
            model = destination.imageUrl,
            contentDescription = destination.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // DESTINATION NAME
        Text(
            text = destination.name,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = destination.location,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // CATEGORY
        Text(
            text = "Category",
            style = MaterialTheme.typography.titleMedium
        )

        Text(destination.category)

        Spacer(modifier = Modifier.height(16.dp))

        // DESCRIPTION
        Text(
            text = "About this Place",
            style = MaterialTheme.typography.titleMedium
        )

        Text(destination.description)

        Spacer(modifier = Modifier.height(16.dp))

        // BUDGET
        Text(
            text = "Estimated Budget",
            style = MaterialTheme.typography.titleMedium
        )

        Text(destination.estimatedBudget)

        Spacer(modifier = Modifier.height(16.dp))

        // REMINDER
        Text(
            text = "Important Reminder",
            style = MaterialTheme.typography.titleMedium
        )

        Text(destination.reminder)

        Spacer(modifier = Modifier.height(20.dp))

        // MAP TITLE
        Text(
            text = "Location Map",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        // EMBEDDED MAP OF SELECTED DESTINATION
        if (coords != null) {

            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(
                    coords,
                    14f
                )
            }

            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),

                cameraPositionState = cameraPositionState,

                uiSettings = MapUiSettings(
                    zoomControlsEnabled = true,
                    compassEnabled = true,
                    mapToolbarEnabled = true
                )
            ) {

                Marker(
                    state = rememberMarkerState(
                        position = coords
                    ),
                    title = destination.name,
                    snippet = destination.location
                )
            }

        } else {

            Text(
                text = "Embedded map coordinates are unavailable for this destination. Use Get Directions to view it on Google Maps."
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // GOOGLE MAPS DIRECTIONS
        Button(
            onClick = {

                val mapUri = if (destination.mapUrl.isNotBlank()) {

                    Uri.parse(destination.mapUrl)

                } else {

                    Uri.parse(
                        "https://www.google.com/maps/search/?api=1&query=" +
                                Uri.encode(
                                    "${destination.name}, ${destination.location}"
                                )
                    )
                }

                val intent = Intent(
                    Intent.ACTION_VIEW,
                    mapUri
                )

                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Directions")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // BACK BUTTON
        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Destinations")
        }
    }
}