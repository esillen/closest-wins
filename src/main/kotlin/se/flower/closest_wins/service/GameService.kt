package se.flower.closest_wins.service

import org.springframework.stereotype.Service
import se.flower.closest_wins.model.Game
import se.flower.closest_wins.model.GameSettings
import se.flower.closest_wins.model.GameState
import se.flower.closest_wins.model.Location
import se.flower.closest_wins.util.DistanceCalculator
import java.util.concurrent.atomic.AtomicReference

@Service
class GameService(
	private val playerService: PlayerService,
	private val locationService: LocationService,
	private val gameBroadcastService: GameBroadcastService
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
		
		// Create a new game with all locations as upcoming
		val newGame = Game(
			players = playerService.getAllPlayers(),
			currentLocation = null,
			upcomingLocations = locations,
			pastLocations = emptyList(),
			state = GameState.WAITING,
			roundSecondsLeft = 0,
			countdownSecondsLeft = 0,
			settings = GameSettings(
				countdownDurationSeconds = 5,
				roundDurationSeconds = 10
			)
		)
		
		currentGame.set(newGame)
		return getCurrentGame()
	}

	fun startNextLocation(): Game {
		val game = currentGame.get()
		
		if (game.state != GameState.WAITING) {
			throw IllegalStateException("Can only start next location when in WAITING state")
		}
		
		if (game.upcomingLocations.isEmpty()) {
			throw IllegalStateException("No upcoming locations available")
		}

		// Clear all player guesses when starting a new location
		playerService.getAllPlayers().forEach { player ->
			playerService.clearPlayerGuess(player.id)
		}

		// Move current location to past
		val newPastLocations = if (game.currentLocation != null) {
			game.pastLocations + game.currentLocation
		} else {
			game.pastLocations
		}
		
		// Transition to COUNTING_DOWN state (location is hidden during countdown)
		val newGame = game.copy(
			state = GameState.COUNTING_DOWN,
			currentLocation = null,
			countdownSecondsLeft = game.settings.countdownDurationSeconds,
			pastLocations = newPastLocations
		)
		
		currentGame.set(newGame)
		val updatedGame = getCurrentGame()
		gameBroadcastService.broadcastGameUpdate(updatedGame)
		return updatedGame
	}

	fun tick() {
		val game = currentGame.get()
		
		when (game.state) {
			GameState.COUNTING_DOWN -> {
				if (game.countdownSecondsLeft > 0) {
					// Countdown
					val newGame = game.copy(countdownSecondsLeft = game.countdownSecondsLeft - 1)
					currentGame.set(newGame)
				} else {
					// Transition to PLAYING
					transitionToPlaying()
				}
			}
			GameState.PLAYING -> {
				if (game.roundSecondsLeft > 0) {
					// Countdown
					val newGame = game.copy(roundSecondsLeft = game.roundSecondsLeft - 1)
					currentGame.set(newGame)
				} else {
					// Transition to WAITING
					transitionToWaiting()
				}
			}
			GameState.WAITING -> {
				// No action needed
			}
		}
	}

	private fun transitionToPlaying() {
		val game = currentGame.get()
		
		if (game.upcomingLocations.isEmpty()) {
			// No more locations, stay in waiting
			currentGame.set(game.copy(state = GameState.WAITING, countdownSecondsLeft = 0))
			return
		}
		
		// Move first upcoming location to current
		val nextLocation = game.upcomingLocations.first()
		val newUpcoming = game.upcomingLocations.drop(1)
		
		val newGame = game.copy(
			state = GameState.PLAYING,
			currentLocation = nextLocation,
			upcomingLocations = newUpcoming,
			roundSecondsLeft = game.settings.roundDurationSeconds,
			countdownSecondsLeft = 0
		)
		
		currentGame.set(newGame)
	}

	private fun transitionToWaiting() {
		val game = currentGame.get()
		
		// Calculate and award scores if there's a current location
		if (game.currentLocation != null) {
			calculateAndAwardScores(game.currentLocation)
		}
		
		val newGame = game.copy(
			state = GameState.WAITING,
		)
		
		currentGame.set(newGame)
	}
	
	private fun calculateAndAwardScores(location: Location) {
		val players = playerService.getAllPlayers()
		
		// Get players with guesses and calculate their distances
		val playersWithDistances = players
			.filter { it.currentGuess != null }
			.map { player ->
				val guess = player.currentGuess!!
				val distance = DistanceCalculator.calculateDistance(
					guess.latitude, guess.longitude,
					location.latitude, location.longitude
				)
				Pair(player, distance)
			}
			.sortedBy { it.second } // Sort by distance, closest first
		
		// Award points based on ranking
		val totalPlayers = playersWithDistances.size
		playersWithDistances.forEachIndexed { index, (player, _) ->
			val pointsEarned = totalPlayers - index
			playerService.updatePlayerScore(player.id, pointsEarned)
		}
		
		// Players without guesses get 0 points
		players
			.filter { it.currentGuess == null }
			.forEach { player ->
				playerService.updatePlayerScore(player.id, 0)
			}
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
