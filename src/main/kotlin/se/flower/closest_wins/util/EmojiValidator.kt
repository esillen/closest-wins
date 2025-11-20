package se.flower.closest_wins.util

object EmojiValidator {
	fun isValidEmoji(input: String): Boolean {
		if (input.isBlank()) return false
		val trimmed = input.trim()
		
		// Check if it's a reasonable length (emojis can be 1-4 code points typically)
		if (trimmed.length > 10) return false
		
		// Check if all characters are emoji-related Unicode symbols
		// This includes emoji, symbols, and modifiers
		val hasEmojiChar = trimmed.any { char ->
			val codePoint = char.code
			// Check various emoji ranges
			codePoint in 0x1F300..0x1F9FF || // Miscellaneous Symbols and Pictographs
			codePoint in 0x1F600..0x1F64F || // Emoticons
			codePoint in 0x1F680..0x1F6FF || // Transport and Map Symbols
			codePoint in 0x2600..0x26FF ||   // Miscellaneous Symbols
			codePoint in 0x2700..0x27BF ||   // Dingbats
			codePoint in 0x1F900..0x1F9FF || // Supplemental Symbols and Pictographs
			codePoint in 0x1F1E0..0x1F1FF || // Regional Indicator Symbols
			codePoint in 0x1F3FB..0x1F3FF || // Emoji Modifiers (skin tones)
			codePoint == 0x200D ||           // Zero Width Joiner
			codePoint == 0xFE0F ||           // Variation Selector-16
			codePoint == 0xFE0E              // Variation Selector-15
		}
		
		return hasEmojiChar && trimmed.length <= 10
	}
}

