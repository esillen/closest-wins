package se.flower.closest_wins.service

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class GameBroadcastService(
	private val messagingTemplate: SimpMessagingTemplate,
	private val gameService: GameService
) {
	
	fun broadcastGameUpdate() {
		val game = gameService.getCurrentGame()
		messagingTemplate.convertAndSend("/topic/game", game)
	}
}

