package se.flower.closest_wins.service

import org.springframework.stereotype.Service
import se.flower.closest_wins.model.Guess

@Service
class GameActionService(
	private val playerService: PlayerService
) {
	fun submitGuess(playerId: String, longitude: Double, latitude: Double): Boolean {
		val player = playerService.getPlayer(playerId) ?: return false
		val guess = Guess(longitude = longitude, latitude = latitude)
		playerService.updatePlayerGuess(playerId, guess)
		return true
	}
}

