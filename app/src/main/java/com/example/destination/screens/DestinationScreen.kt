
package com.example.destination.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.example.destination.components.SearchBar
import com.example.destination.models.Destination
import com.example.destination.data.FavoritesStorage

@Composable
fun DestinationScreen(
    destinations: List<Destination>,
    onDestinationClick: (Destination) -> Unit
) {

    var searchText by remember { mutableStateOf("") }

    val filteredDestinations = remember(searchText, destinations) {
        destinations.filter { destination ->

            val query = searchText.trim()

            query.isBlank() ||
                    destination.name.contains(query, true) ||
                    destination.location.contains(query, true) ||
                    destination.category.contains(query, true) ||
                    destination.description.contains(query, true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Text(
            text = "Explore Destinations",
            modifier = Modifier.padding(top = 16.dp, bottom = 10.dp),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        SearchBar(
            value = searchText,
            onValueChange = { searchText = it },
            hint = "Search destination, location, or category..."
        )

        if (filteredDestinations.isEmpty()) {

            Text(
                "No destinations found. Try another keyword.",
                modifier = Modifier.padding(16.dp)
            )

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    filteredDestinations,
                    key = { it.id }
                ) { destination ->

                    val context = LocalContext.current

                    var isFavorite by remember(destination.id) {
                        mutableStateOf(
                            destination.id in
                                    FavoritesStorage.getFavoriteIds(context)
                        )
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Column {

                            Box {

                                AsyncImage(
                                    model = destination.imageUrl,
                                    contentDescription = destination.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(190.dp)
                                        .clickable {
                                            onDestinationClick(destination)
                                        },
                                    contentScale = ContentScale.Crop
                                )

                                IconButton(
                                    onClick = {
                                        FavoritesStorage.toggleFavorite(
                                            context,
                                            destination.id
                                        )

                                        isFavorite = !isFavorite
                                    },
                                    modifier = Modifier
                                        .align(androidx.compose.ui.Alignment.TopEnd)
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = if (isFavorite) "♥" else "♡",
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.headlineMedium
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onDestinationClick(destination)
                                    }
                                    .padding(16.dp)
                            ) {

                                Text(
                                    destination.name,
                                    style = MaterialTheme.typography.titleLarge
                                )

                                Text(destination.location)

                                Text(
                                    destination.category,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Text(
                                    "Tap to view details",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
