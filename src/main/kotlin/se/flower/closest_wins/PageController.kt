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
	private val adminService: AdminService,
	private val gameService: se.flower.closest_wins.service.GameService
) {

	@GetMapping("/")
	fun index(session: HttpSession, model: Model): String {
		val game = gameService.getCurrentGame()
		val players = playerService.getAllPlayers()
		
		// Calculate game stats
		val totalLocations = game.pastLocations.size + 
			(if (game.currentLocation != null) 1 else 0) + 
			game.upcomingLocations.size
		val currentLocationNumber = game.pastLocations.size + 
			(if (game.currentLocation != null) 1 else 0)
		
		model.addAttribute("game", game)
		model.addAttribute("playerCount", players.size)
		model.addAttribute("totalLocations", totalLocations)
		model.addAttribute("currentLocationNumber", currentLocationNumber)
		
		// Add session information
		model.addAttribute("sessionPlayer", sessionService.getCurrentPlayer(session, playerService))
		model.addAttribute("sessionIsAdmin", sessionService.isAdmin(session))
		
		return "index"
	}

	@GetMapping("/play")
	fun play(session: HttpSession, model: Model): String {
		val currentPlayer = sessionService.getCurrentPlayer(session, playerService)
		model.addAttribute("currentPlayer", currentPlayer)
		model.addAttribute("isAdmin", sessionService.isAdmin(session))
		
		// Add session information
		model.addAttribute("sessionPlayer", currentPlayer)
		model.addAttribute("sessionIsAdmin", sessionService.isAdmin(session))
		return "play"
	}

	@GetMapping("/spectate")
	fun spectate(session: HttpSession, model: Model): String {
		model.addAttribute("isAdmin", sessionService.isAdmin(session))
		
		// Add session information
		model.addAttribute("sessionPlayer", sessionService.getCurrentPlayer(session, playerService))
		model.addAttribute("sessionIsAdmin", sessionService.isAdmin(session))
		return "spectate"
	}

	@GetMapping("/join")
	fun join(session: HttpSession, model: Model): String {
		model.addAttribute("isAdmin", sessionService.isAdmin(session))
		
		// Add session information
		model.addAttribute("sessionPlayer", sessionService.getCurrentPlayer(session, playerService))
		model.addAttribute("sessionIsAdmin", sessionService.isAdmin(session))
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
		
		// Add session information
		model.addAttribute("sessionPlayer", sessionService.getCurrentPlayer(session, playerService))
		model.addAttribute("sessionIsAdmin", isAdmin)
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
		
		// Add session information
		model.addAttribute("sessionPlayer", sessionService.getCurrentPlayer(session, playerService))
		model.addAttribute("sessionIsAdmin", true)
		return "locations"
	}

	@GetMapping("/create-game")
	fun createGame(session: HttpSession, model: Model, redirectAttributes: RedirectAttributes): String {
		if (!sessionService.isAdmin(session)) {
			redirectAttributes.addFlashAttribute("error", "Admin access required")
			return "redirect:/admin"
		}
		
		// Add session information
		model.addAttribute("sessionPlayer", sessionService.getCurrentPlayer(session, playerService))
		model.addAttribute("sessionIsAdmin", true)
		return "create-game"
	}

	@GetMapping("/players")
	fun players(session: HttpSession, model: Model, redirectAttributes: RedirectAttributes): String {
		if (!sessionService.isAdmin(session)) {
			redirectAttributes.addFlashAttribute("error", "Admin access required")
			return "redirect:/admin"
		}
		model.addAttribute("players", playerService.getAllPlayers())
		model.addAttribute("isAdmin", true)
		
		// Add session information
		model.addAttribute("sessionPlayer", sessionService.getCurrentPlayer(session, playerService))
		model.addAttribute("sessionIsAdmin", true)
		return "players"
	}

	@PostMapping("/players/assume")
	fun assumePlayer(
		@RequestParam playerId: String,
		session: HttpSession,
		redirectAttributes: RedirectAttributes
	): String {
		if (!sessionService.isAdmin(session)) {
			redirectAttributes.addFlashAttribute("error", "Admin access required")
			return "redirect:/admin"
		}
		
		val player = playerService.getPlayer(playerId)
		if (player == null) {
			redirectAttributes.addFlashAttribute("error", "Player not found")
			return "redirect:/players"
		}
		
		sessionService.setPlayerId(session, playerId)
		redirectAttributes.addFlashAttribute("success", "You are now playing as ${player.name}")
		return "redirect:/players"
	}
	
	@PostMapping("/players/delete")
	fun deletePlayer(
		@RequestParam playerId: String,
		session: HttpSession,
		redirectAttributes: RedirectAttributes
	): String {
		if (!sessionService.isAdmin(session)) {
			redirectAttributes.addFlashAttribute("error", "Admin access required")
			return "redirect:/admin"
		}
		
		val player = playerService.getPlayer(playerId)
		if (player == null) {
			redirectAttributes.addFlashAttribute("error", "Player not found")
			return "redirect:/players"
		}
		
		playerService.removePlayer(playerId)
		redirectAttributes.addFlashAttribute("success", "Player ${player.name} has been deleted")
		return "redirect:/players"
	}
}

