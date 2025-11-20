package se.flower.closest_wins.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import se.flower.closest_wins.model.Game

@Service
class AdminService(
	private val gameService: GameService,
	private val playerService: PlayerService,
	@Value("\${admin.password}") private val adminPassword: String
) {
	fun validatePassword(password: String): Boolean {
		return password == adminPassword
	}

	fun getGameStatistics(): GameStatistics {
		val players = playerService.getAllPlayers()
		val playersWithGuesses = players.count { it.currentGuess != null }
		val playersWithoutGuesses = players.size - playersWithGuesses
		
		return GameStatistics(
			totalPlayers = players.size,
			playersWithGuesses = playersWithGuesses,
			playersWithoutGuesses = playersWithoutGuesses,
			game = gameService.getCurrentGame()
		)
	}

	fun resetGame() {
		gameService.resetGame()
	}

	fun clearAllGuesses() {
		playerService.getAllPlayers().forEach { player ->
			if (player.currentGuess != null) {
				playerService.clearPlayerGuess(player.id)
			}
		}
	}

	fun removePlayer(playerId: String): Boolean {
		return playerService.removePlayer(playerId)
	}

	data class GameStatistics(
		val totalPlayers: Int,
		val playersWithGuesses: Int,
		val playersWithoutGuesses: Int,
		val game: Game
	)
}

