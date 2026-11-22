
package com.example.destination.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.destination.data.LocalDestinationRepository
import com.example.destination.screens.*

private data class DashboardItem(
    val title: String,
    val icon: String,
    val route: String
)

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val repository = LocalDestinationRepository()

    Scaffold { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(innerPadding)
        ) {

            // DASHBOARD
            composable("dashboard") {

                DashboardScreen(
                    onItemClick = { route ->
                        navController.navigate(route)
                    }
                )
            }

            // HOME
            composable("home") {

                HomeScreen(
                    repository = repository,

                    onExploreClick = {
                        navController.navigate("destinations")
                    },

                    onDestinationClick = {
                        navController.navigate(
                            "destination_details/${it.id}"
                        )
                    }
                )
            }

            // DESTINATIONS
            composable("destinations") {

                DestinationScreen(
                    destinations = repository.getDestinations(),

                    onDestinationClick = {
                        navController.navigate(
                            "destination_details/${it.id}"
                        )
                    }
                )
            }

            // NEARBY
            composable("nearby") {
                NearbyPlacesScreen()
            }

            // ITINERARY
            composable("itinerary") {
                TouristPlansScreen()
            }

            // FAVORITES
            composable("favorites") {

                FavoritesScreen(
                    destinations = repository.getDestinations(),

                    onBack = {
                        navController.popBackStack()
                    },

                    onDestinationClick = { destination ->
                        navController.navigate(
                            "destination_details/${destination.id}"
                        )
                    }
                )
            }

            // BUDGET PLANNER
            composable("budget") {

                BudgetPlannerScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // BUSINESSES
            composable("businesses") {
                BusinessesScreen(repository)
            }

            // HOTLINES
            composable("hotlines") {
                HotlinesScreen(repository)
            }

            // PROFILE
            composable("profile") {

                ProfileScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            // DESTINATION DETAILS WITH MAP
            composable(
                route = "destination_details/{destinationId}",

                arguments = listOf(
                    navArgument("destinationId") {
                        type = NavType.IntType
                    }
                )

            ) { entry ->

                val id = entry.arguments?.getInt("destinationId")

                val destination = id?.let {
                    repository.getDestinationById(it)
                }

                if (destination != null) {

                    DestinationDetailsScreen(
                        destination
                    ) {
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}


// DASHBOARD SCREEN
@Composable
fun DashboardScreen(
    onItemClick: (String) -> Unit
) {

    val menuItems = listOf(

        DashboardItem("Home", "⌂", "home"),

        DashboardItem("Destinations", "📍", "destinations"),

        DashboardItem("Nearby", "🧭", "nearby"),

        DashboardItem("Itinerary", "📅", "itinerary"),

        DashboardItem("Favorites", "♡", "favorites"),

        DashboardItem("Budget", "₱", "budget"),

        DashboardItem("Businesses", "🏪", "businesses"),

        DashboardItem("Hotlines", "☎", "hotlines"),

        DashboardItem("Profile", "♙", "profile")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Tourist Guide App",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "EXPLORE NASUGBU AND LIAN",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(menuItems) { item ->

                Card(
                    onClick = {
                        onItemClick(item.route)
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(105.dp),

                    shape = RoundedCornerShape(16.dp),

                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.surface
                    ),

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),

                        horizontalAlignment = Alignment.CenterHorizontally,

                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = item.icon,
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}
