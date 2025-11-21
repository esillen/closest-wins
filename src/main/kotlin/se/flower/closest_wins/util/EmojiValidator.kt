package se.flower.closest_wins.util

object EmojiValidator {
	/**
	 * Validates if the input string is a valid emoji.
	 * Supports single emojis, emojis with skin tone modifiers, and complex ZWJ sequences.
	 */
	fun isValidEmoji(input: String): Boolean {
		if (input.isBlank()) return false
		val trimmed = input.trim()
		
		// Reject strings that are too long (even complex emojis rarely exceed 20 chars)
		if (trimmed.length > 20) return false
		
		// Must contain at least one emoji-related character
		var hasEmojiChar = false
		var hasNonEmojiChar = false
		
		trimmed.codePoints().forEach { codePoint ->
			if (isEmojiCodePoint(codePoint)) {
				hasEmojiChar = true
			} else if (!isEmojiModifier(codePoint)) {
				hasNonEmojiChar = true
			}
		}
		
		// Must have emoji characters and no regular text/numbers
		return hasEmojiChar && !hasNonEmojiChar
	}
	
	private fun isEmojiCodePoint(codePoint: Int): Boolean {
		return when {
			// Emoticons
			codePoint in 0x1F600..0x1F64F -> true
			// Miscellaneous Symbols and Pictographs
			codePoint in 0x1F300..0x1F5FF -> true
			// Transport and Map Symbols
			codePoint in 0x1F680..0x1F6FF -> true
			// Supplemental Symbols and Pictographs
			codePoint in 0x1F900..0x1F9FF -> true
			// Symbols and Pictographs Extended-A
			codePoint in 0x1FA70..0x1FAFF -> true
			// Miscellaneous Symbols (includes many classic emoji symbols)
			codePoint in 0x2600..0x26FF -> true
			// Dingbats (includes ✉️, ✏️, ✒️, ✂️, etc.)
			codePoint in 0x2700..0x27BF -> true
			// Enclosed Alphanumeric Supplement (includes some emoji)
			codePoint in 0x1F100..0x1F1FF -> true
			// Playing Cards and other symbols
			codePoint in 0x1F0A0..0x1F0FF -> true
			// Additional Emoticons
			codePoint in 0x1F910..0x1F96B -> true
			// Additional Transport and Map Symbols
			codePoint in 0x1F980..0x1F9E0 -> true
			// Miscellaneous Symbols and Arrows (includes ⬆️, ⬇️, ⬅️, ➡️)
			codePoint in 0x2B00..0x2BFF -> true
			// Additional symbols (✝️, ☪️, ✡️, etc.)
			codePoint in 0x2000..0x2BFF -> true
			else -> false
		}
	}
	
	private fun isEmojiModifier(codePoint: Int): Boolean {
		return when {
			// Emoji Modifiers (skin tones)
			codePoint in 0x1F3FB..0x1F3FF -> true
			// Zero Width Joiner (used in multi-part emojis)
			codePoint == 0x200D -> true
			// Variation Selectors
			codePoint == 0xFE0F || codePoint == 0xFE0E -> true
			else -> false
		}
	}
}

