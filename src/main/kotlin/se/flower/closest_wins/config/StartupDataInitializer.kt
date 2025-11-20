package se.flower.closest_wins.config

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import se.flower.closest_wins.model.Guess
import se.flower.closest_wins.service.LocationService
import se.flower.closest_wins.service.PlayerService

@Component
class StartupDataInitializer(
	private val playerService: PlayerService,
	private val locationService: LocationService
) {
	@PostConstruct
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
	
	@PostConstruct
	fun initializeLocations() {
		// Create initial locations with iconic landmarks
		locationService.createLocation(
			url = "https://images.unsplash.com/photo-1511739001486-6bfe10ce785f?w=800",
			longitude = -74.0445,
			latitude = 40.6892
		) // Statue of Liberty, New York
		
		locationService.createLocation(
			url = "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?w=800",
			longitude = 2.2945,
			latitude = 48.8584
		) // Eiffel Tower, Paris
		
		locationService.createLocation(
			url = "https://images.unsplash.com/photo-1513407030348-c983a97b98d8?w=800",
			longitude = 139.8107,
			latitude = 35.7101
		) // Mount Fuji, Japan
		
		locationService.createLocation(
			url = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?w=800",
			longitude = 138.2529,
			latitude = -34.9285
		) // Sydney Opera House, Australia
		
		locationService.createLocation(
			url = "https://images.unsplash.com/photo-1529260830199-42c24126f198?w=800",
			longitude = -43.2105,
			latitude = -22.9519
		) // Christ the Redeemer, Brazil
		
		locationService.createLocation(
			url = "https://images.unsplash.com/photo-1568322445389-f64ac2515020?w=800",
			longitude = 12.4964,
			latitude = 41.8902
		) // Colosseum, Rome
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

