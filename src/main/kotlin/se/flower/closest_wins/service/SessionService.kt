package se.flower.closest_wins.service

import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Service
import se.flower.closest_wins.model.Player

@Service
class SessionService {
	companion object {
		private const val PLAYER_ID_KEY = "playerId"
		private const val ADMIN_SESSION_KEY = "isAdmin"
	}

	fun setPlayerId(session: HttpSession, playerId: String) {
		session.setAttribute(PLAYER_ID_KEY, playerId)
	}

	fun getPlayerId(session: HttpSession): String? {
		return session.getAttribute(PLAYER_ID_KEY) as? String
	}

	fun getCurrentPlayer(session: HttpSession, playerService: PlayerService): Player? {
		val playerId = getPlayerId(session) ?: return null
		return playerService.getPlayer(playerId)
	}

	fun clearPlayer(session: HttpSession) {
		session.removeAttribute(PLAYER_ID_KEY)
	}

	fun setAdminSession(session: HttpSession) {
		session.setAttribute(ADMIN_SESSION_KEY, true)
	}

	fun isAdmin(session: HttpSession): Boolean {
		return session.getAttribute(ADMIN_SESSION_KEY) as? Boolean ?: false
	}

	fun clearAdmin(session: HttpSession) {
		session.removeAttribute(ADMIN_SESSION_KEY)
	}
}

