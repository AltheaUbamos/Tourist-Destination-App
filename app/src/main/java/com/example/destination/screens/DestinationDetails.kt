
package com.example.destination.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.destination.models.Destination

@Composable
fun DestinationDetailsScreen(
    destination: Destination,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        AsyncImage(
            model = destination.imageUrl,
            contentDescription = destination.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = destination.name,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = destination.location,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Category",
            style = MaterialTheme.typography.titleMedium
        )
        Text(text = destination.category)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "About this Place",
            style = MaterialTheme.typography.titleMedium
        )
        Text(text = destination.description)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Estimated Budget",
            style = MaterialTheme.typography.titleMedium
        )
        Text(text = destination.estimatedBudget)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Important Reminder",
            style = MaterialTheme.typography.titleMedium
        )
        Text(text = destination.reminder)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Destinations")
        }
    }
}
