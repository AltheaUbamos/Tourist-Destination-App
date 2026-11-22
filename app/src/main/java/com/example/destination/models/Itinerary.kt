package com.example.destination.models

data class Itinerary(
    val id: Int,
    val title: String,
    val destination: String,
    val schedule: String,
    val activity: String,
    val touristName: String = "Visitor",
    val date: String = "2025-05-01",
    val status: String = "Saved"
)
