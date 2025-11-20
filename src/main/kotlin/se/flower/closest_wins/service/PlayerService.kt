package se.flower.closest_wins.service

import org.springframework.stereotype.Service
import se.flower.closest_wins.model.Guess
import se.flower.closest_wins.model.Player
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Service
class PlayerService {
	private val players = ConcurrentHashMap<String, Player>()

    fun createPlayer(id: String, name: String, emoji: String, color: String, score: Int = 0): Player {
        val player = Player(id = id, name = name, emoji = emoji, color = color, score = score)
        players[id] = player
        return player
    }

    fun createPlayer(name: String, emoji: String, color: String): Player {
        val id = UUID.randomUUID().toString()
        return createPlayer(id, name, emoji, color, 0)
    }

	fun getPlayer(id: String): Player? {
		return players[id]
	}

	fun getAllPlayers(): List<Player> {
		return players.values.toList()
	}

	fun updatePlayerGuess(playerId: String, guess: Guess): Player? {
		val player = players[playerId] ?: return null
		val updatedPlayer = player.copy(
			currentGuess = guess,
			score = player.score,
			lastScore = player.lastScore
		)
		players[playerId] = updatedPlayer
		return updatedPlayer
	}

	fun clearPlayerGuess(playerId: String): Player? {
		val player = players[playerId] ?: return null
		val updatedPlayer = player.copy(
			currentGuess = null,
			score = player.score,
			lastScore = player.lastScore
		)
		players[playerId] = updatedPlayer
		return updatedPlayer
	}
	
	fun updatePlayerScore(playerId: String, pointsEarned: Int): Player? {
		val player = players[playerId] ?: return null
		val updatedPlayer = player.copy(
			score = player.score + pointsEarned,
			lastScore = pointsEarned
		)
		players[playerId] = updatedPlayer
		return updatedPlayer
	}

	fun removePlayer(id: String): Boolean {
		return players.remove(id) != null
	}

	fun updatePlayer(player: Player): Player {
		players[player.id] = player
		return player
	}
}

