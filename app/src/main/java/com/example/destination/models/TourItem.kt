package com.example.destination.models

interface TourItem {
    val id: Int
    val displayTitle: String
    val displaySubtitle: String
    val displayDescription: String
    val categoryBadge: String?
}