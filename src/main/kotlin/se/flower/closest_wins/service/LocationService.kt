package se.flower.closest_wins.service

import org.springframework.stereotype.Service
import se.flower.closest_wins.model.Location
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Service
class LocationService {
    private val locations = ConcurrentHashMap<String, Location>()

    fun createLocation(url: String, latitude: Double, longitude: Double, description: String): Location {
        val id = UUID.randomUUID().toString()
        val location = Location(id = id, url = url, latitude = latitude, longitude = longitude, description = description)
        locations[id] = location
        return location
    }

    fun getLocation(id: String): Location? {
        return locations[id]
    }

    fun getAllLocations(): List<Location> {
        return locations.values.toList()
    }

    fun updateLocation(id: String, url: String, latitude: Double, longitude: Double, description: String): Location? {
        val existingLocation = locations[id] ?: return null
        val updatedLocation = Location(id = id, url = url, latitude = latitude, longitude = longitude, description = description)
        locations[id] = updatedLocation
        return updatedLocation
    }

    fun deleteLocation(id: String): Boolean {
        return locations.remove(id) != null
    }
}

