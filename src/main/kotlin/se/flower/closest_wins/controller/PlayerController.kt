package se.flower.closest_wins.controller

import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import se.flower.closest_wins.model.Guess
import se.flower.closest_wins.model.Player
import se.flower.closest_wins.service.PlayerService
import se.flower.closest_wins.service.SessionService

@RestController
@RequestMapping("/api/players")
class PlayerController(
	private val playerService: PlayerService,
	private val sessionService: SessionService
) {

	@PostMapping
	fun createPlayer(@RequestBody request: CreatePlayerRequest): ResponseEntity<Player> {
		val player = if (request.id != null) {
			playerService.createPlayer(
				id = request.id,
				name = request.name,
				emoji = request.emoji,
				color = request.color
			)
		} else {
			playerService.createPlayer(
				name = request.name,
				emoji = request.emoji,
				color = request.color
			)
		}
		return ResponseEntity.ok(player)
	}

	@GetMapping("/{id}")
	fun getPlayer(@PathVariable id: String): ResponseEntity<Player> {
		val player = playerService.getPlayer(id)
		return if (player != null) {
			ResponseEntity.ok(player)
		} else {
			ResponseEntity.notFound().build()
		}
	}

	@GetMapping
	fun getAllPlayers(): ResponseEntity<List<Player>> {
		return ResponseEntity.ok(playerService.getAllPlayers())
	}

	@GetMapping("/current")
	fun getCurrentPlayer(session: HttpSession): ResponseEntity<Player> {
		val player = sessionService.getCurrentPlayer(session, playerService)
		return if (player != null) {
			ResponseEntity.ok(player)
		} else {
			ResponseEntity.notFound().build()
		}
	}

	@PutMapping("/{id}/guess")
	fun updatePlayerGuess(
		@PathVariable id: String,
		@RequestBody guess: Guess
	): ResponseEntity<Player> {
		val updatedPlayer = playerService.updatePlayerGuess(id, guess)
		return if (updatedPlayer != null) {
			ResponseEntity.ok(updatedPlayer)
		} else {
			ResponseEntity.notFound().build()
		}
	}

	@DeleteMapping("/{id}/guess")
	fun clearPlayerGuess(@PathVariable id: String): ResponseEntity<Player> {
		val updatedPlayer = playerService.clearPlayerGuess(id)
		return if (updatedPlayer != null) {
			ResponseEntity.ok(updatedPlayer)
		} else {
			ResponseEntity.notFound().build()
		}
	}

	@DeleteMapping("/{id}")
	fun removePlayer(@PathVariable id: String): ResponseEntity<Map<String, String>> {
		val success = playerService.removePlayer(id)
		return if (success) {
			ResponseEntity.ok(mapOf("message" to "Player removed"))
		} else {
			ResponseEntity.notFound().build()
		}
	}

	data class CreatePlayerRequest(
		val id: String? = null,
		val name: String,
		val emoji: String,
		val color: String
	)
}

