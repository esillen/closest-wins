package se.flower.closest_wins.service

import org.springframework.stereotype.Service
import se.flower.closest_wins.model.Game

@Service
class GameService(
	private val playerService: PlayerService
) {
	fun getCurrentGame(): Game {
		val players = playerService.getAllPlayers()
		return Game(players = players)
	}

	fun resetGame() {
		// Clear all players to reset the game
		playerService.getAllPlayers().forEach { player ->
			playerService.removePlayer(player.id)
		}
	}
}

