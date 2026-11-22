
package com.example.destination.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.destination.data.DestinationRepository

@Composable
fun HotlinesScreen(repository: DestinationRepository) {

    val hotlines = repository.getHotlines()

    var selectedId by remember {
        mutableStateOf<Int?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            "Emergency Hotlines",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Contact information for visitors. Always verify numbers with official local offices."
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(hotlines, key = { it.id }) { hotline ->

                val expanded = selectedId == hotline.id

                Card(
                    onClick = {
                        selectedId =
                            if (expanded) null else hotline.id
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.primaryContainer
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {

                        Text(
                            hotline.agency,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            hotline.contactNumber,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(hotline.description)

                        if (expanded) {

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 12.dp)
                            )

                            Text(
                                "Contact Information",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(Modifier.height(8.dp))

                            Text("Agency: ${hotline.agency}")
                            Text("Contact: ${hotline.contactNumber}")

                            Spacer(Modifier.height(8.dp))

                            Text(
                                "Verify this contact number with the official local office before relying on it.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.secondary
                            )

                            Text(
                                "Tap to close",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelLarge
                            )

                        } else {

                            Spacer(Modifier.height(8.dp))

                            Text(
                                "Tap to view details ›",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }
    }
}
