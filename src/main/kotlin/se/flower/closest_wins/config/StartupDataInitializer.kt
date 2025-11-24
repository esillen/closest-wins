package se.flower.closest_wins.config

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import se.flower.closest_wins.model.Guess
import se.flower.closest_wins.service.GameService
import se.flower.closest_wins.service.LocationService
import se.flower.closest_wins.service.PlayerService

@Component
class StartupDataInitializer(
	private val playerService: PlayerService,
	private val locationService: LocationService,
	private val gameService: GameService
) {
	@PostConstruct
	fun initializeData() {
		initializePlayers()
		val locationIds = initializeLocations()
		initializeGame(locationIds)
	}
	
	fun initializePlayers() {
		// Create 5 players with scores
		val players = listOf(
			PlayerData("Alice", "🎯", "#FF6B6B", 150, 40.7128, -74.0060), // New York
			PlayerData("Bob", "🚀", "#4ECDC4", 220, 51.5074, -0.1278),   // London
			PlayerData("Charlie", "🌟", "#FFE66D", 185, 35.6762, 139.6503), // Tokyo
			PlayerData("Diana", "🎨", "#A8E6CF", 95, null, null),      // No guess
			PlayerData("Eve", "⚡", "#FF8B94", 175, null, null)          // No guess
		)
		
		players.forEach { playerData ->
			val player = playerService.createPlayer(
				id = playerData.name.lowercase(),
				name = playerData.name,
				emoji = playerData.emoji,
				color = playerData.color,
				score = playerData.score
			)
			
			// Add guess if coordinates are provided
			if (playerData.latitude != null && playerData.longitude != null) {
				val guess = Guess(
					latitude = playerData.latitude,
					longitude = playerData.longitude
				)
				playerService.updatePlayerGuess(player.id, guess)
			}
		}
	}
	
	fun initializeLocations(): List<String> {
		val locationIds = mutableListOf<String>()
		
		// Create initial locations with iconic landmarks
		// Note: createLocation signature is (url, longitude, latitude)
		
		// Actual coords: Statue of Liberty is at lat 40.6892, long -74.0445
		val location1 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1511739001486-6bfe10ce785f?w=800",
			latitude = 40.6892,
			longitude = -74.0445,
			description = "Statue of Liberty, New York"
		)
		locationIds.add(location1.id)
		
		// Actual coords: Eiffel Tower is at lat 48.8584, long 2.2945
		val location2 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?w=800",
			latitude = 48.8584,
			longitude = 2.2945,
			description = "Eiffel Tower, Paris"
		)
		locationIds.add(location2.id)
		
		// Actual coords: Mount Fuji is at lat 35.3606, long 138.7274
		val location3 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1513407030348-c983a97b98d8?w=800",
			latitude = 35.3606,
			longitude = 138.7274,
			description = "Mount Fuji, Japan"
		)
		locationIds.add(location3.id)
		
		// Actual coords: Sydney Opera House is at lat -33.8568, long 151.2153
		val location4 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?w=800",
			latitude = -33.8568,
			longitude = 151.2153,
			description = "Sydney Opera House, Australia"
		)
		locationIds.add(location4.id)
		
		// Actual coords: Christ the Redeemer is at lat -22.9519, long -43.2105
		val location5 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1529260830199-42c24126f198?w=800",
			latitude = -22.9519,
			longitude = -43.2105,
			description = "Christ the Redeemer, Brazil"
		)
		locationIds.add(location5.id)
		
		// Actual coords: Colosseum is at lat 41.8902, long 12.4964
		val location6 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1568322445389-f64ac2515020?w=800",
			latitude = 41.8902,
			longitude = 12.4964,
			description = "Colosseum, Rome"
		)
		locationIds.add(location6.id)
		
		// Actual coords: Big Ben is at lat 51.5007, long -0.1246
		val location7 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1513635269975-59663e0ac1ad?w=800",
			latitude = 51.5007,
			longitude = -0.1246,
			description = "Big Ben, London"
		)
		locationIds.add(location7.id)
		
		// Actual coords: Taj Mahal is at lat 27.1751, long 78.0421
		val location8 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1564507592333-c60657eea523?w=800",
			latitude = 27.1751,
			longitude = 78.0421,
			description = "Taj Mahal, India"
		)
		locationIds.add(location8.id)
		
		// Actual coords: Great Wall of China is at lat 40.4319, long 116.5704
		val location9 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1508804185872-d7badad00f7d?w=800",
			latitude = 40.4319,
			longitude = 116.5704,
			description = "Great Wall of China"
		)
		locationIds.add(location9.id)
		
		// Actual coords: Machu Picchu is at lat -13.1631, long -72.5450
		val location10 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1587595431973-160d0d94add1?w=800",
			latitude = -13.1631,
			longitude = -72.5450,
			description = "Machu Picchu, Peru"
		)
		locationIds.add(location10.id)
		
		// Actual coords: Pyramids of Giza is at lat 29.9792, long 31.1342
		val location11 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1503177119275-0aa32b3a9368?w=800",
			latitude = 29.9792,
			longitude = 31.1342,
			description = "Pyramids of Giza, Egypt"
		)
		locationIds.add(location11.id)
		
		// Actual coords: Golden Gate Bridge is at lat 37.8199, long -122.4783
		val location12 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1501594907352-04cda38ebc29?w=800",
			latitude = 37.8199,
			longitude = -122.4783,
			description = "Golden Gate Bridge, San Francisco"
		)
		locationIds.add(location12.id)
		
		// Actual coords: Sagrada Familia is at lat 41.4036, long 2.1744
		val location13 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1583422409516-2895a77efded?w=800",
			latitude = 41.4036,
			longitude = 2.1744,
			description = "Sagrada Familia, Barcelona"
		)
		locationIds.add(location13.id)
		
		// Actual coords: Petra is at lat 30.3285, long 35.4444
		val location14 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1579606032821-4e6161c81bd3?w=800",
			latitude = 30.3285,
			longitude = 35.4444,
			description = "Petra, Jordan"
		)
		locationIds.add(location14.id)
		
		// Actual coords: Burj Khalifa is at lat 25.1972, long 55.2744
		val location15 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1512453979798-5ea266f8880c?w=800",
			latitude = 25.1972,
			longitude = 55.2744,
			description = "Burj Khalifa, Dubai"
		)
		locationIds.add(location15.id)
		
		// Actual coords: Stonehenge is at lat 51.1789, long -1.8262
		val location16 = locationService.createLocation(
			url = "https://images.unsplash.com/photo-1599833975787-5c143f373c30?w=800",
			latitude = 51.1789,
			longitude = -1.8262,
			description = "Stonehenge, United Kingdom"
		)
		locationIds.add(location16.id)
		
		return locationIds
	}
	
	fun initializeGame(locationIds: List<String>) {
		// Create a game with the first 4 locations
		if (locationIds.size >= 4) {
			gameService.createGameWithLocations(locationIds.take(4))
		}
	}
	
	private data class PlayerData(
		val name: String,
		val emoji: String,
		val color: String,
		val score: Int,
		val latitude: Double?,
		val longitude: Double?
	)
}
