package se.flower.closest_wins.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class GameTickService(
	private val gameService: GameService
) {
	
	@Scheduled(fixedRate = 1000) // Run every 1 second
	fun tick() {
		gameService.tick()
	}
}

