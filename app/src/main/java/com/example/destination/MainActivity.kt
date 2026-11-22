package com.example.destination

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.destination.navigation.AppNavigation
import com.example.destination.ui.theme.DestinationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DestinationTheme {
                AppNavigation()
            }
        }
    }
}
