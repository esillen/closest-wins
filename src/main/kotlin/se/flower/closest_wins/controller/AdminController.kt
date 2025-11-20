package se.flower.closest_wins.controller

import jakarta.servlet.http.HttpSession
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import se.flower.closest_wins.service.AdminService
import se.flower.closest_wins.service.SessionService

@RestController
@RequestMapping("/api/admin")
class AdminController(
	private val adminService: AdminService,
	private val sessionService: SessionService
) {
	@PostMapping("/login")
	fun login(
		@RequestBody request: LoginRequest,
		session: HttpSession
	): ResponseEntity<Map<String, Any>> {
		if (adminService.validatePassword(request.password)) {
			sessionService.setAdminSession(session)
			return ResponseEntity.ok(mapOf("message" to "Login successful"))
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(mapOf("error" to "Invalid password"))
		}
	}

	@GetMapping("/stats")
	fun getStatistics(session: HttpSession): ResponseEntity<Any> {
		if (!sessionService.isAdmin(session)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(mapOf("error" to "Admin access required"))
		}

		val stats = adminService.getGameStatistics()
		return ResponseEntity.ok(stats)
	}

	@PostMapping("/reset-game")
	fun resetGame(session: HttpSession): ResponseEntity<Map<String, Any>> {
		if (!sessionService.isAdmin(session)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(mapOf("error" to "Admin access required"))
		}

		adminService.resetGame()
		return ResponseEntity.ok(mapOf("message" to "Game reset successfully"))
	}

	@PostMapping("/clear-guesses")
	fun clearAllGuesses(session: HttpSession): ResponseEntity<Map<String, Any>> {
		if (!sessionService.isAdmin(session)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(mapOf("error" to "Admin access required"))
		}

		adminService.clearAllGuesses()
		return ResponseEntity.ok(mapOf("message" to "All guesses cleared"))
	}

	@DeleteMapping("/players/{playerId}")
	fun removePlayer(
		@PathVariable playerId: String,
		session: HttpSession
	): ResponseEntity<Map<String, Any>> {
		if (!sessionService.isAdmin(session)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(mapOf("error" to "Admin access required"))
		}

		val success = adminService.removePlayer(playerId)
		return if (success) {
			ResponseEntity.ok(mapOf("message" to "Player removed"))
		} else {
			ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(mapOf("error" to "Player not found"))
		}
	}

	@PostMapping("/create-game")
	fun createGame(
		@RequestBody request: CreateGameRequest,
		session: HttpSession
	): ResponseEntity<Any> {
		if (!sessionService.isAdmin(session)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(mapOf("error" to "Admin access required"))
		}

		return try {
			val game = adminService.createGameWithLocations(request.locationIds)
			ResponseEntity.ok(game)
		} catch (e: IllegalArgumentException) {
			ResponseEntity.badRequest().body(mapOf("error" to e.message))
		}
	}

	@PostMapping("/next-location")
	fun nextLocation(session: HttpSession): ResponseEntity<Any> {
		if (!sessionService.isAdmin(session)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(mapOf("error" to "Admin access required"))
		}

		return try {
			val game = adminService.startNextLocation()
			ResponseEntity.ok(game)
		} catch (e: IllegalStateException) {
			ResponseEntity.badRequest().body(mapOf("error" to e.message))
		}
	}

	data class LoginRequest(
		val password: String
	)

	data class CreateGameRequest(
		val locationIds: List<String>
	)
}

