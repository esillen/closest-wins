package se.flower.closest_wins

import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import se.flower.closest_wins.service.AdminService
import se.flower.closest_wins.service.PlayerService
import se.flower.closest_wins.service.SessionService
import se.flower.closest_wins.util.EmojiValidator

@Controller
class PageController(
	private val playerService: PlayerService,
	private val sessionService: SessionService,
	private val adminService: AdminService
) {

	@GetMapping("/")
	fun index(): String {
		return "redirect:/play"
	}

	@GetMapping("/play")
	fun play(session: HttpSession, model: Model): String {
		val currentPlayer = sessionService.getCurrentPlayer(session, playerService)
		model.addAttribute("currentPlayer", currentPlayer)
		return "play"
	}

	@GetMapping("/spectate")
	fun spectate(): String {
		return "spectate"
	}

	@GetMapping("/join")
	fun join(): String {
		return "join"
	}

	@PostMapping("/join")
	fun joinPlayer(
		@RequestParam name: String,
		@RequestParam emoji: String,
		@RequestParam color: String,
		session: HttpSession,
		redirectAttributes: RedirectAttributes
	): String {
		if (name.isBlank() || emoji.isBlank() || color.isBlank()) {
			redirectAttributes.addFlashAttribute("error", "All fields are required")
			return "redirect:/join"
		}

		if (!EmojiValidator.isValidEmoji(emoji)) {
			redirectAttributes.addFlashAttribute("error", "Please select a valid emoji")
			return "redirect:/join"
		}

		val player = playerService.createPlayer(name, emoji, color)
		sessionService.setPlayerId(session, player.id)
		
		return "redirect:/play"
	}

	@GetMapping("/admin")
	fun admin(session: HttpSession, model: Model): String {
		val isAdmin = sessionService.isAdmin(session)
		model.addAttribute("isAdmin", isAdmin)
		return "admin"
	}

	@PostMapping("/admin/login")
	fun adminLogin(
		@RequestParam password: String,
		session: HttpSession,
		redirectAttributes: RedirectAttributes
	): String {
		if (adminService.validatePassword(password)) {
			sessionService.setAdminSession(session)
			redirectAttributes.addFlashAttribute("success", "Admin login successful")
		} else {
			redirectAttributes.addFlashAttribute("error", "Invalid password")
		}
		return "redirect:/admin"
	}

	@GetMapping("/admin/locations")
	fun adminLocations(session: HttpSession, model: Model, redirectAttributes: RedirectAttributes): String {
		if (!sessionService.isAdmin(session)) {
			redirectAttributes.addFlashAttribute("error", "Admin access required")
			return "redirect:/admin"
		}
		return "locations"
	}

	@GetMapping("/create-game")
	fun createGame(session: HttpSession, redirectAttributes: RedirectAttributes): String {
		if (!sessionService.isAdmin(session)) {
			redirectAttributes.addFlashAttribute("error", "Admin access required")
			return "redirect:/admin"
		}
		return "create-game"
	}

	@GetMapping("/players")
	fun players(model: Model): String {
		model.addAttribute("players", playerService.getAllPlayers())
		return "players"
	}

	@PostMapping("/players/assume")
	fun assumePlayer(
		@RequestParam playerId: String,
		session: HttpSession,
		redirectAttributes: RedirectAttributes
	): String {
		val player = playerService.getPlayer(playerId)
		if (player == null) {
			redirectAttributes.addFlashAttribute("error", "Player not found")
			return "redirect:/players"
		}
		
		sessionService.setPlayerId(session, playerId)
		redirectAttributes.addFlashAttribute("success", "You are now playing as ${player.name}")
		return "redirect:/players"
	}
}

