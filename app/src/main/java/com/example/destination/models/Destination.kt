package com.example.destination.models

data class Destination(
    val id: Int,
    val name: String,
    val location: String,
    val description: String,
    val category: String,
    val imageUrl: String,
    val estimatedBudget: String,
    val reminder: String,
    val mapUrl: String
)
