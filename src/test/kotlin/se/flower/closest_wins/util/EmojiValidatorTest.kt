package se.flower.closest_wins.util

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class EmojiValidatorTest {

	@Test
	fun `should accept emojis from the smileys category`() {
		val smileys = listOf(
			"😀", "😃", "😄", "😁", "😆", "😅", "🤣", "😂", "🙂", "🙃",
			"😉", "😊", "😇", "🥰", "😍", "🤩", "😘", "😗", "😚", "😙",
			"😋", "😛", "😜", "🤪", "😝", "🤑", "🤗", "🤭", "🤫", "🤔",
			"🤐", "🤨", "😐", "😑", "😶", "😏", "😒", "🙄", "😬", "🤥"
		)
		
		smileys.forEach { emoji ->
			assertTrue(EmojiValidator.isValidEmoji(emoji), "Should accept emoji: $emoji")
		}
	}

	@Test
	fun `should accept emojis from the people category`() {
		val people = listOf(
			"👶", "👧", "🧒", "👦", "👩", "🧑", "👨", "👵", "🧓", "👴",
			"👲", "🧕", "👮", "👷", "💂", "🕵️", "👩‍⚕️", "👨‍⚕️", "👩‍🌾", "👨‍🌾",
			"👩‍🍳", "👨‍🍳", "👩‍🎓", "👨‍🎓", "👩‍🎤", "👨‍🎤", "👩‍🏫", "👨‍🏫",
			"👩‍💻", "👨‍💻", "👩‍💼", "👨‍💼", "🤵", "👸", "🤴", "🦸", "🦹"
		)
		
		people.forEach { emoji ->
			assertTrue(EmojiValidator.isValidEmoji(emoji), "Should accept emoji: $emoji")
		}
	}

	@Test
	fun `should accept emojis from the animals category`() {
		val animals = listOf(
			"🐶", "🐱", "🐭", "🐹", "🐰", "🦊", "🐻", "🐼", "🐨", "🐯",
			"🦁", "🐮", "🐷", "🐽", "🐸", "🐵", "🙈", "🙉", "🙊", "🐒",
			"🐔", "🐧", "🐦", "🐤", "🐣", "🐥", "🦆", "🦅", "🦉", "🦇",
			"🐺", "🐗", "🐴", "🦄", "🐝", "🐛", "🦋", "🐌", "🐞", "🐜"
		)
		
		animals.forEach { emoji ->
			assertTrue(EmojiValidator.isValidEmoji(emoji), "Should accept emoji: $emoji")
		}
	}

	@Test
	fun `should accept emojis from the food category`() {
		val food = listOf(
			"🍇", "🍈", "🍉", "🍊", "🍋", "🍌", "🍍", "🥭", "🍎", "🍏",
			"🍐", "🍑", "🍒", "🍓", "🥝", "🍅", "🥥", "🥑", "🍆", "🥔",
			"🥕", "🌽", "🌶️", "🥒", "🥬", "🥦", "🧄", "🧅", "🍄", "🥜",
			"🍞", "🥐", "🥖", "🥨", "🥯", "🥞", "🧇", "🧀", "🍖", "🍗",
			"🥩", "🍕", "🍔", "🍟", "🌭", "🥪", "🌮", "🌯", "🥙", "🧆"
		)
		
		food.forEach { emoji ->
			assertTrue(EmojiValidator.isValidEmoji(emoji), "Should accept emoji: $emoji")
		}
	}

	@Test
	fun `should accept emojis from the activities category`() {
		val activities = listOf(
			"⚽", "🏀", "🏈", "⚾", "🥎", "🎾", "🏐", "🏉", "🥏", "🎱",
			"🏓", "🏸", "🏒", "🏑", "🥍", "🏏", "⛳", "🏹", "🎣", "🤿",
			"🥊", "🥋", "🎽", "⛸️", "🥌", "🛷", "🛹", "🎿", "⛷️", "🏂",
			"🎯", "🎮", "🎰", "🎲", "🧩", "♟️", "🎭", "🎨", "🎪", "🎬"
		)
		
		activities.forEach { emoji ->
			assertTrue(EmojiValidator.isValidEmoji(emoji), "Should accept emoji: $emoji")
		}
	}

	@Test
	fun `should accept emojis from the objects category`() {
		val objects = listOf(
			"🎮", "🕹️", "🎰", "🎲", "🧩", "🎯", "🎱", "🔮", "🧿", "🎁",
			"🎈", "🎏", "🎀", "🎊", "🎉", "🎎", "🏮", "🎐", "🧧", "✉️",
			"📩", "📨", "📧", "💌", "📥", "📤", "📦", "🏷️", "📪", "📫",
			"📬", "📭", "📮", "📯", "📜", "📃", "📄", "📑", "📊", "📈"
		)
		
		objects.forEach { emoji ->
			assertTrue(EmojiValidator.isValidEmoji(emoji), "Should accept emoji: $emoji")
		}
	}

	@Test
	fun `should accept emojis from the symbols category`() {
		val symbols = listOf(
			"⭐", "🌟", "✨", "💫", "🔥", "💥", "💢", "💦", "💨", "💤",
			"❤️", "🧡", "💛", "💚", "💙", "💜", "🖤", "🤍", "🤎", "💔",
			"❣️", "💕", "💞", "💓", "💗", "💖", "💘", "💝", "💟", "☮️",
			"✝️", "☪️", "🕉️", "☸️", "✡️", "🔯", "🕎", "☯️", "☦️", "⚛️"
		)
		
		symbols.forEach { emoji ->
			assertTrue(EmojiValidator.isValidEmoji(emoji), "Should accept emoji: $emoji")
		}
	}

	@Test
	fun `should accept complex emojis with skin tone modifiers`() {
		val complexEmojis = listOf(
			"👋🏻", "👋🏼", "👋🏽", "👋🏾", "👋🏿",
			"👍🏻", "👍🏼", "👍🏽", "👍🏾", "👍🏿",
			"🤝🏻", "🤝🏼", "🤝🏽", "🤝🏾", "🤝🏿"
		)
		
		complexEmojis.forEach { emoji ->
			assertTrue(EmojiValidator.isValidEmoji(emoji), "Should accept emoji with skin tone: $emoji")
		}
	}

	@Test
	fun `should accept complex emojis with ZWJ sequences`() {
		val zwjEmojis = listOf(
			"👨‍👩‍👧‍👦",  // Family
			"👨‍💻",      // Man technologist
			"👩‍🚀",      // Woman astronaut
			"🧑‍🎨",      // Artist
			"👩‍⚕️",      // Woman health worker
			"🏳️‍🌈",      // Rainbow flag
			"👨‍🦰",      // Man: red hair
			"🧑‍🦱"       // Person: curly hair
		)
		
		zwjEmojis.forEach { emoji ->
			assertTrue(EmojiValidator.isValidEmoji(emoji), "Should accept ZWJ emoji: $emoji")
		}
	}

	@Test
	fun `should reject plain text`() {
		val invalidInputs = listOf(
			"hello",
			"test",
			"abc",
			"Hello World",
			"test123"
		)
		
		invalidInputs.forEach { input ->
			assertFalse(EmojiValidator.isValidEmoji(input), "Should reject plain text: $input")
		}
	}

	@Test
	fun `should reject numbers`() {
		val invalidInputs = listOf(
			"123",
			"456",
			"0",
			"999",
			"12345"
		)
		
		invalidInputs.forEach { input ->
			assertFalse(EmojiValidator.isValidEmoji(input), "Should reject numbers: $input")
		}
	}

	@Test
	fun `should reject special characters`() {
		val invalidInputs = listOf(
			"!@#$%",
			"<>",
			"[]",
			"{}",
			"()",
			"*&^%"
		)
		
		invalidInputs.forEach { input ->
			assertFalse(EmojiValidator.isValidEmoji(input), "Should reject special characters: $input")
		}
	}

	@Test
	fun `should reject empty string`() {
		assertFalse(EmojiValidator.isValidEmoji(""))
	}

	@Test
	fun `should reject blank string`() {
		assertFalse(EmojiValidator.isValidEmoji("   "))
	}

	@Test
	fun `should reject emoji mixed with text`() {
		val invalidInputs = listOf(
			"😀hello",
			"test😀",
			"😀 text",
			"a😀b"
		)
		
		invalidInputs.forEach { input ->
			assertFalse(EmojiValidator.isValidEmoji(input), "Should reject emoji mixed with text: $input")
		}
	}

	@Test
	fun `should reject excessively long strings`() {
		val longString = "😀".repeat(30)
		assertFalse(EmojiValidator.isValidEmoji(longString), "Should reject overly long strings")
	}

	@Test
	fun `should accept emojis with whitespace that gets trimmed`() {
		assertTrue(EmojiValidator.isValidEmoji("  😀  "))
		assertTrue(EmojiValidator.isValidEmoji("\n😀\n"))
		assertTrue(EmojiValidator.isValidEmoji("\t🎯\t"))
	}

	@Test
	fun `should handle variation selectors correctly`() {
		// Some emojis have variation selectors (e.g., ❤️ vs ❤)
		assertTrue(EmojiValidator.isValidEmoji("❤️"))
		assertTrue(EmojiValidator.isValidEmoji("☀️"))
		assertTrue(EmojiValidator.isValidEmoji("⭐"))
	}
}

