package se.flower.closest_wins.service

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import se.flower.closest_wins.model.Game

@Service
class GameBroadcastService(
	private val messagingTemplate: SimpMessagingTemplate
) {
	
	fun broadcastGameUpdate(game: Game) {
		messagingTemplate.convertAndSend("/topic/game", game)
	}
}

