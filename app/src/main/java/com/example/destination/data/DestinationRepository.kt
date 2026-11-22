package com.example.destination.data

import com.example.destination.models.*

interface DestinationRepository {

    fun getDestinations(): List<Destination>

    fun getBusinesses(): List<LocalBusiness>

    fun getHotlines(): List<EmergencyHotline>

    fun getItineraries(): List<Itinerary>

    fun getDestinationById(id: Int): Destination?
}