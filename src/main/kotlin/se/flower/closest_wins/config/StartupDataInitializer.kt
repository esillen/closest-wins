package se.flower.closest_wins.config

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import se.flower.closest_wins.model.Guess
import se.flower.closest_wins.service.GameService
import se.flower.closest_wins.service.LocationService
import se.flower.closest_wins.service.PlayerService

@Component
class StartupDataInitializer(
	private val playerService: PlayerService,
	private val locationService: LocationService,
	private val gameService: GameService
) {
	@PostConstruct
	fun initializeData() {
		initializePlayers()
		val locationIds = initializeLocations()
		initializeGame(locationIds)
	}
	
	fun initializePlayers() {
		// Create 5 players with scores
		val players = listOf(
			PlayerData("Alice", "🎯", "#FF6B6B", 150, 40.7128, -74.0060), // New York
			PlayerData("Bob", "🚀", "#4ECDC4", 220, 51.5074, -0.1278),   // London
			PlayerData("Charlie", "🌟", "#FFE66D", 185, 35.6762, 139.6503), // Tokyo
			PlayerData("Diana", "🎨", "#A8E6CF", 95, null, null),      // No guess
			PlayerData("Eve", "⚡", "#FF8B94", 175, null, null)          // No guess
		)
		
		players.forEach { playerData ->
			val player = playerService.createPlayer(
				id = playerData.name.lowercase(),
				name = playerData.name,
				emoji = playerData.emoji,
				color = playerData.color,
				score = playerData.score
			)
			
			// Add guess if coordinates are provided
			if (playerData.latitude != null && playerData.longitude != null) {
				val guess = Guess(
					latitude = playerData.latitude,
					longitude = playerData.longitude
				)
				playerService.updatePlayerGuess(player.id, guess)
			}
		}
	}
	
	fun initializeLocations(): List<String> {
		val locationIds = mutableListOf<String>()
		
		// Create initial locations with iconic landmarks
		// Note: createLocation signature is (url, longitude, latitude)
		
		// Actual coords: Statue of Liberty is at lat 40.6892, long -74.0445
		val location1 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1511739001486-6bfe10ce785f?w=800",
			longitude = -74.0445,
			latitude = 40.6892
		) // Statue of Liberty, New York
		locationIds.add(location1.id)
		
		// Actual coords: Eiffel Tower is at lat 48.8584, long 2.2945
		val location2 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?w=800",
			longitude = 2.2945,
			latitude = 48.8584
		) // Eiffel Tower, Paris
		locationIds.add(location2.id)
		
		// Actual coords: Mount Fuji is at lat 35.3606, long 138.7274
		val location3 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1513407030348-c983a97b98d8?w=800",
			longitude = 138.7274,
			latitude = 35.3606
		) // Mount Fuji, Japan
		locationIds.add(location3.id)
		
		// Actual coords: Sydney Opera House is at lat -33.8568, long 151.2153
		val location4 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?w=800",
			longitude = 151.2153,
			latitude = -33.8568
		) // Sydney Opera House, Australia
		locationIds.add(location4.id)
		
		// Actual coords: Christ the Redeemer is at lat -22.9519, long -43.2105
		val location5 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1529260830199-42c24126f198?w=800",
			longitude = -43.2105,
			latitude = -22.9519
		) // Christ the Redeemer, Brazil
		locationIds.add(location5.id)
		
		// Actual coords: Colosseum is at lat 41.8902, long 12.4964
		val location6 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1568322445389-f64ac2515020?w=800",
			longitude = 12.4964,
			latitude = 41.8902
		) // Colosseum, Rome
		locationIds.add(location6.id)
		
		return locationIds
	}
	
	fun initializeGame(locationIds: List<String>) {
		// Create a game with the first 4 locations
		if (locationIds.size >= 4) {
			gameService.createGameWithLocations(locationIds.take(4))
		}
	}
	
	private data class PlayerData(
		val name: String,
		val emoji: String,
		val color: String,
		val score: Int,
		val latitude: Double?,
		val longitude: Double?
	)
}
