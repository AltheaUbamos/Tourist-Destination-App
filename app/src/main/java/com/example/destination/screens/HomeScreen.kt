package com.example.destination.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.destination.data.DestinationRepository
import com.example.destination.models.Destination

@Composable
fun HomeScreen(
    repository: DestinationRepository,
    onExploreClick: () -> Unit,
    onDestinationClick: (Destination) -> Unit
) {

    val popular = repository.getDestinations().take(6)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Text(
                "DISCOVER BATANGAS",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(Modifier.height(6.dp))

            Text(
                "Your next adventure starts here.",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Explore beaches, islands, mountains, and local attractions in Nasugbu and Lian.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(14.dp))

            Button(
                onClick = onExploreClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Explore All Destinations")
            }
        }

        item {
            Text(
                "Popular Destinations",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    popular,
                    key = { it.id }
                ) { place ->

                    Card(
                        onClick = {
                            onDestinationClick(place)
                        },
                        modifier = Modifier
                            .width(260.dp)
                            .height(205.dp),
                        shape = RoundedCornerShape(18.dp),
                        elevation = CardDefaults.cardElevation(3.dp)
                    ) {

                        Column(
                            Modifier.padding(16.dp)
                        ) {

                            Text(
                                place.category.uppercase(),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                place.name,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.height(6.dp))

                            Text(place.location)

                            Spacer(Modifier.height(8.dp))

                            Text(
                                place.description,
                                maxLines = 3,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(Modifier.height(6.dp))

                            Text(
                                "View details ›",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        item {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {

                Column(
                    Modifier.padding(18.dp)
                ) {

                    Text(
                        "Plan Your Next Adventure",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "Create your own itinerary, check helpful contact information, and discover local businesses."
                    )
                }
            }
        }
    }
}