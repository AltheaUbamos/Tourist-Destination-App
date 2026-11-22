
package com.example.destination.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.destination.data.FavoritesStorage
import com.example.destination.models.Destination

@Composable
fun FavoritesScreen(
    destinations: List<Destination>,
    onBack: () -> Unit,
    onDestinationClick: (Destination) -> Unit
) {
    val context = LocalContext.current

    var favoriteIds by remember {
        mutableStateOf(FavoritesStorage.getFavoriteIds(context))
    }

    val favoriteDestinations = destinations.filter {
        it.id in favoriteIds
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "My Favorites",
                style = MaterialTheme.typography.headlineMedium
            )

            TextButton(onClick = onBack) {
                Text("Back")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "${favoriteDestinations.size} saved destinations"
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (favoriteDestinations.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Column(
                    horizontalAlignment =
                        androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    Text(
                        "♡",
                        style = MaterialTheme.typography.displayLarge
                    )

                    Text(
                        "No Favorites Yet",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        "Save your favorite destinations from the Destinations page."
                    )
                }
            }

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    favoriteDestinations,
                    key = { it.id }
                ) { destination ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {

                        Column {

                            AsyncImage(
                                model = destination.imageUrl,
                                contentDescription = destination.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(170.dp),
                                contentScale = ContentScale.Crop
                            )

                            Column(
                                modifier = Modifier.padding(14.dp)
                            ) {

                                Text(
                                    destination.name,
                                    style = MaterialTheme.typography.titleLarge
                                )

                                Text(destination.location)

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )

                                Row(
                                    horizontalArrangement =
                                        Arrangement.spacedBy(8.dp)
                                ) {

                                    Button(
                                        onClick = {
                                            onDestinationClick(destination)
                                        }
                                    ) {
                                        Text("View Details")
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            FavoritesStorage.removeFavorite(
                                                context,
                                                destination.id
                                            )

                                            favoriteIds =
                                                FavoritesStorage.getFavoriteIds(
                                                    context
                                                )
                                        }
                                    ) {
                                        Text("Remove")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
