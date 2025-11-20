package se.flower.closest_wins.model

data class Game(
	val players: List<Player> = emptyList(),
	val currentLocation: Location? = null,
	val upcomingLocations: List<Location> = emptyList(),
	val pastLocations: List<Location> = emptyList(),
	val state: GameState = GameState.PLAYING,
	val roundSecondsLeft: Int = 0,
	val settings: GameSettings = GameSettings(roundDurationSeconds = 10.0)
) {
	fun hasMoreLocations(): Boolean {
		return upcomingLocations.isNotEmpty()
	}
}

