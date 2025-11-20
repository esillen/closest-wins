package se.flower.closest_wins.config

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import se.flower.closest_wins.model.Guess
import se.flower.closest_wins.service.PlayerService

@Component
class StartupDataInitializer(
	private val playerService: PlayerService
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
	
	private data class PlayerData(
		val name: String,
		val emoji: String,
		val color: String,
		val score: Int,
		val latitude: Double?,
		val longitude: Double?
	)
}

