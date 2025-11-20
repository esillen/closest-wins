package se.flower.closest_wins.controller

import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import se.flower.closest_wins.service.GameActionService
import se.flower.closest_wins.service.SessionService

@RestController
@RequestMapping("/api/game/actions")
class GameActionController(
	private val gameActionService: GameActionService,
	private val sessionService: SessionService
) {
	@PostMapping("/guess")
	fun submitGuess(
		@RequestBody request: SubmitGuessRequest,
		session: HttpSession
	): ResponseEntity<Map<String, Any>> {
		val playerId = sessionService.getPlayerId(session)
		if (playerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(mapOf("error" to "You must be logged in as a player"))
		}

		val success = gameActionService.submitGuess(
			playerId = playerId,
			longitude = request.longitude,
			latitude = request.latitude
		)

		return if (success) {
			ResponseEntity.ok(mapOf("message" to "Guess submitted successfully"))
		} else {
			ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(mapOf("error" to "Failed to submit guess"))
		}
	}

	data class SubmitGuessRequest(
		val longitude: Double,
		val latitude: Double
	)
}

