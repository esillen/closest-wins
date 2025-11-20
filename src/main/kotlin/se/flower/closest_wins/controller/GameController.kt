package se.flower.closest_wins.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import se.flower.closest_wins.service.GameService

@RestController
@RequestMapping("/api/game")
class GameController(
	private val gameService: GameService
) {

	@GetMapping
	fun getGame(): ResponseEntity<se.flower.closest_wins.model.Game> {
		return ResponseEntity.ok(gameService.getCurrentGame())
	}

	@PostMapping("/reset")
	fun resetGame(): ResponseEntity<Map<String, String>> {
		gameService.resetGame()
		return ResponseEntity.ok(mapOf("message" to "Game reset"))
	}
}

