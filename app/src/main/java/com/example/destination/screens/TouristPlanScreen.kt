
package com.example.destination.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.destination.data.TravelPlanStorage
import com.example.destination.models.Itinerary

@Composable
fun TouristPlansScreen() {
    val context = LocalContext.current

    var touristName by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var schedule by remember { mutableStateOf("") }
    var activity by remember { mutableStateOf("") }

    var plans by remember {
        mutableStateOf(TravelPlanStorage.getPlans(context))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "My Travel Plans",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = touristName,
            onValueChange = { touristName = it },
            label = { Text("Tourist Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Plan Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("Destination") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Preferred Date") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = schedule,
            onValueChange = { schedule = it },
            label = { Text("Schedule / Time") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = activity,
            onValueChange = { activity = it },
            label = { Text("Activities") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                if (
                    touristName.isNotBlank() &&
                    title.isNotBlank() &&
                    destination.isNotBlank() &&
                    date.isNotBlank() &&
                    schedule.isNotBlank() &&
                    activity.isNotBlank()
                ) {
                    val newPlan = Itinerary(
                        id = (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),
                        touristName = touristName,
                        title = title,
                        destination = destination,
                        date = date,
                        schedule = schedule,
                        activity = activity
                    )

                    TravelPlanStorage.savePlan(context, newPlan)
                    plans = TravelPlanStorage.getPlans(context)

                    title = ""
                    destination = ""
                    date = ""
                    schedule = ""
                    activity = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Plan")
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "Saved Tourist Plans",
            style = MaterialTheme.typography.titleLarge
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(plans, key = { it.id }) { plan ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            plan.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text("Tourist: ${plan.touristName}")
                        Text("Destination: ${plan.destination}")
                        Text("Date: ${plan.date}")
                        Text("Schedule: ${plan.schedule}")
                        Text("Activities: ${plan.activity}")

                        Text(
                            "Status: ${plan.status}",
                            color = if (plan.status == "Cancelled")
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.primary
                        )

                        if (plan.status == "Saved") {
                            TextButton(
                                onClick = {
                                    TravelPlanStorage.cancelPlan(
                                        context, plan.id
                                    )
                                    plans = TravelPlanStorage.getPlans(context)
                                }
                            ) {
                                Text("Cancel Plan")
                            }
                        }
                    }
                }
            }
        }
    }
}
