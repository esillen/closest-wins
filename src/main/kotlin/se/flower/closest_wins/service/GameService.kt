package se.flower.closest_wins.service

import org.springframework.stereotype.Service
import se.flower.closest_wins.model.Game
import se.flower.closest_wins.model.GameState
import se.flower.closest_wins.model.Location
import java.util.concurrent.atomic.AtomicReference

@Service
class GameService(
	private val playerService: PlayerService,
	private val locationService: LocationService
) {
	private val currentGame = AtomicReference<Game>(Game(players = emptyList()))

	fun getCurrentGame(): Game {
		val players = playerService.getAllPlayers()
		val game = currentGame.get()
		return game.copy(players = players)
	}

	fun createGameWithLocations(locationIds: List<String>): Game {
		// Fetch the actual location objects
		val locations = locationIds.mapNotNull { locationService.getLocation(it) }
		
		if (locations.isEmpty()) {
			throw IllegalArgumentException("No valid locations provided")
		}
		
		// Create a new game with the first location as current and rest as upcoming
		val newGame = Game(
			players = playerService.getAllPlayers(),
			currentLocation = null,
			upcomingLocations = locations,
			pastLocations = emptyList(),
			state = GameState.WAITING,
			roundSecondsLeft = 0
		)
		
		currentGame.set(newGame)
		return newGame
	}

	fun resetGame() {
		// Clear all players to reset the game
		playerService.getAllPlayers().forEach { player ->
			playerService.removePlayer(player.id)
		}
		
		// Reset game state
		currentGame.set(Game(players = emptyList()))
	}
}

