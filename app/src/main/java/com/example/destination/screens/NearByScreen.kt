
package com.example.destination.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun NearbyPlacesScreen() {

    val context = LocalContext.current

    var area by remember {
        mutableStateOf("Nasugbu, Batangas")
    }

    var customSearch by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }

    fun searchMaps(category: String) {

        val place = area.trim().ifBlank { "near me" }

        val query = if (place.equals("near me", true)) {
            "$category near me"
        } else {
            "$category near $place"
        }

        val url =
            "https://www.google.com/maps/search/?api=1&query=${Uri.encode(query)}"

        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )

        try {
            context.startActivity(intent)
            message = "Opening Google Maps..."
        } catch (_: ActivityNotFoundException) {
            message = "Unable to open Google Maps. Check your browser."
        }
    }

    val categories = listOf(
        "Hospitals",
        "Barangay Halls",
        "Health Centers",
        "Police Stations",
        "Fire Stations",
        "Pharmacies",
        "Tourist Information Offices",
        "Hotels",
        "Restaurants"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            "Nearby Places",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            "Find important establishments and services near your selected location."
        )

        OutlinedTextField(
            value = area,
            onValueChange = { area = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter town or barangay") },
            placeholder = {
                Text("Example: Lian, Batangas")
            },
            singleLine = true
        )

        OutlinedButton(
            onClick = {
                area = "near me"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search Near My Location")
        }

        Text(
            "Quick Searches",
            style = MaterialTheme.typography.titleLarge
        )

        categories.chunked(2).forEach { pair ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                pair.forEach { category ->

                    OutlinedButton(
                        onClick = {
                            searchMaps(category)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(category)
                    }
                }

                if (pair.size == 1) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }

        HorizontalDivider()

        Text(
            "Search Other Establishments",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = customSearch,
            onValueChange = { customSearch = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Enter establishment")
            },
            placeholder = {
                Text("Example: ATM, clinic, gas station")
            },
            singleLine = true
        )

        Button(
            onClick = {
                if (customSearch.isNotBlank()) {
                    searchMaps(customSearch.trim())
                }
            },
            enabled = customSearch.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search on Google Maps")
        }

        if (message.isNotBlank()) {
            Text(
                message,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            "Note: Search results depend on Google Maps data and your internet connection. Check contact details and operating hours before visiting.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
