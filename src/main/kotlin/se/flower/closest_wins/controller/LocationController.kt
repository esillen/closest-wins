package se.flower.closest_wins.controller

import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import se.flower.closest_wins.model.Location
import se.flower.closest_wins.service.LocationService
import se.flower.closest_wins.service.SessionService

@RestController
@RequestMapping("/api/locations")
class LocationController(
    private val locationService: LocationService,
    private val sessionService: SessionService
) {

    @GetMapping
    fun getAllLocations(): ResponseEntity<List<Location>> {
        return ResponseEntity.ok(locationService.getAllLocations())
    }

    @GetMapping("/{id}")
    fun getLocation(@PathVariable id: String): ResponseEntity<Location> {
        val location = locationService.getLocation(id)
        return if (location != null) {
            ResponseEntity.ok(location)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createLocation(
        @RequestBody request: LocationRequest,
        session: HttpSession
    ): ResponseEntity<Any> {
        if (!sessionService.isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(mapOf("error" to "Admin access required"))
        }

        val location = locationService.createLocation(
            url = request.url,
            latitude = request.latitude,
            longitude = request.longitude,
            description = request.description
        )
        return ResponseEntity.ok(location)
    }

    @PutMapping("/{id}")
    fun updateLocation(
        @PathVariable id: String,
        @RequestBody request: LocationRequest,
        session: HttpSession
    ): ResponseEntity<Any> {
        if (!sessionService.isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(mapOf("error" to "Admin access required"))
        }

        val location = locationService.updateLocation(
            id = id,
            url = request.url,
            latitude = request.latitude,
            longitude = request.longitude,
            description = request.description
        )
        return if (location != null) {
            ResponseEntity.ok(location)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteLocation(
        @PathVariable id: String,
        session: HttpSession
    ): ResponseEntity<Any> {
        if (!sessionService.isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(mapOf("error" to "Admin access required"))
        }

        val success = locationService.deleteLocation(id)
        return if (success) {
            ResponseEntity.ok(mapOf("message" to "Location deleted"))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    data class LocationRequest(
        val url: String,
        val latitude: Double,
        val longitude: Double,
        val description: String
    )
}

