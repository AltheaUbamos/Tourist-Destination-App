
package com.example.destination.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import com.example.destination.data.ProfileStorage
import com.example.destination.data.UserProfile

@Composable
fun ProfileScreen(
    onBack: () -> Unit
) {

    val context = LocalContext.current

    val savedProfile = remember {
        ProfileStorage.getProfile(context)
    }

    var name by remember {
        mutableStateOf(savedProfile.name)
    }

    var email by remember {
        mutableStateOf(savedProfile.email)
    }

    var preference by remember {
        mutableStateOf(savedProfile.preference)
    }

    var message by remember {
        mutableStateOf(false)
    }

    val preferences = listOf(
        "Beach",
        "Mountain",
        "Island",
        "Adventure",
        "Eco-tourism",
        "Historical Places"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                "My Profile",
                style = MaterialTheme.typography.headlineMedium
            )

            TextButton(onClick = onBack) {
                Text("Back")
            }
        }

        Text(
            "Personal Information",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                message = false
            },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                message = false
            },
            label = { Text("Email Address") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Text(
            "Preferred Destination",
            style = MaterialTheme.typography.titleMedium
        )

        preferences.forEach { option ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment =
                    androidx.compose.ui.Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = preference == option,
                    onClick = {
                        preference = option
                        message = false
                    }
                )

                Text(option)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {

                ProfileStorage.saveProfile(
                    context,
                    UserProfile(
                        name = name.trim(),
                        email = email.trim(),
                        preference = preference
                    )
                )

                message = true
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = name.isNotBlank()
        ) {
            Text("Save Profile")
        }

        if (message) {
            Text(
                "Profile successfully saved!",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}