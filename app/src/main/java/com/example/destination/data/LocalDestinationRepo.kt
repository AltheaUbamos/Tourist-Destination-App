package com.example.destination.data

import com.example.destination.models.*

class LocalDestinationRepository : DestinationRepository {

    override fun getDestinations(): List<Destination> {
        return TouristData.destinations
    }

    override fun getBusinesses(): List<LocalBusiness> {
        return TouristData.businesses
    }

    override fun getHotlines(): List<EmergencyHotline> {
        return TouristData.hotlines
    }

    override fun getItineraries(): List<Itinerary> {
        return TouristData.itineraries
    }

    override fun getDestinationById(id: Int): Destination? {
        return TouristData.destinations.find {
            it.id == id
        }
    }
}