package se.flower.closest_wins.model

data class Player(
    val id: String,
    val name: String,
    val emoji: String,
    val color: String,
    val currentGuess: Guess? = null,
    val score: Int = 0
)

