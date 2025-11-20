package se.flower.closest_wins.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import se.flower.closest_wins.model.GameState

@Service
class GameTickService(
	private val gameService: GameService,
	private val gameBroadcastService: GameBroadcastService
) {
	
	@Scheduled(fixedRate = 1000) // Run every 1 second
	fun tick() {
		gameService.tick()
		
		val currentGame = gameService.getCurrentGame()
		
		// Always broadcast during active game states for smooth updates
		if (currentGame.state == GameState.COUNTING_DOWN || currentGame.state == GameState.PLAYING) {
			gameBroadcastService.broadcastGameUpdate()
		}
	}
}

