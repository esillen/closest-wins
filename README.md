# Closest Wins

A real-time multiplayer geolocation guessing game built with Kotlin, Spring Boot, and Leaflet.js.

## Overview

Closest Wins is a game where players compete to guess locations on a world map. The player whose guess is closest to the actual location wins!

## Features

- 🗺️ **Interactive World Map** - Click anywhere on the map to place your guess
- 👥 **Multiplayer Support** - Multiple players can join and compete
- 📍 **Real-time Updates** - See other players' guesses update live in spectator mode
- 🎨 **Player Customization** - Each player has their own emoji, name, and color
- 👀 **Spectator Mode** - Watch all players' guesses in real-time
- 🔐 **Admin Panel** - Password-protected admin controls for game management
- 🎯 **Location-based Gameplay** - Support for multiple rounds with different locations

## Tech Stack

- **Backend**: Kotlin + Spring Boot 4.0
- **Frontend**: Thymeleaf + Leaflet.js
- **Data**: In-memory storage with thread-safe collections
- **Real-time**: Polling-based updates (2-second intervals)

## Getting Started

### Prerequisites

- Java 17 or higher
- Gradle (included via wrapper)

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd closest-wins
```

2. Run the application:
```bash
./gradlew bootRun
```

3. Open your browser and navigate to:
```
http://localhost:8080
```

### Hot Reload (Development)

For automatic recompilation and restart on code changes:

```bash
./gradlew bootRun --continuous
```

Changes to Kotlin/Java files will trigger automatic restart, and template changes will reload immediately.

## Usage

### Joining the Game

1. Navigate to `/join` or click "Join" in the navigation
2. Enter your name
3. Select an emoji from the picker
4. Choose your color
5. Click "Join"

### Playing

1. After joining, you'll be redirected to `/play`
2. Click anywhere on the world map to place your guess
3. Your guess marker shows your emoji and color
4. Drag the marker to update your guess
5. Your guess is saved automatically

### Spectating

1. Navigate to `/spectate` to watch all players
2. The map shows all player guesses in real-time
3. Markers are color-coded by player
4. Click markers to see player names and coordinates

### Admin Access

1. Navigate to `/admin`
2. Enter the admin password (default: `admin123`)
3. Access admin features:
   - View game statistics
   - Reset the entire game
   - Clear all player guesses
   - Remove individual players

## Configuration

Edit `src/main/resources/application.properties` to configure:

```properties
# Application name
spring.application.name=closest-wins

# Hot reload settings
spring.devtools.restart.enabled=true
spring.devtools.livereload.enabled=true
spring.thymeleaf.cache=false

# Admin password (change this!)
admin.password=admin123
```

## API Endpoints

### Player Management
- `POST /api/players` - Create a new player
- `GET /api/players` - Get all players
- `GET /api/players/{id}` - Get a specific player
- `GET /api/players/current` - Get current player from session
- `PUT /api/players/{id}/guess` - Update player's guess
- `DELETE /api/players/{id}/guess` - Clear player's guess
- `DELETE /api/players/{id}` - Remove a player

### Game Management
- `GET /api/game` - Get current game state
- `POST /api/game/reset` - Reset the game
- `POST /api/game/actions/guess` - Submit a guess (requires player session)

### Admin (Password Protected)
- `POST /api/admin/login` - Login as admin
- `GET /api/admin/stats` - Get game statistics
- `POST /api/admin/reset-game` - Reset entire game
- `POST /api/admin/clear-guesses` - Clear all guesses
- `DELETE /api/admin/players/{playerId}` - Remove a player

## Project Structure

```
src/main/kotlin/se/flower/closest_wins/
├── config/
│   └── StartupDataInitializer.kt      # Initial test data
├── controller/
│   ├── AdminController.kt              # Admin API endpoints
│   ├── GameActionController.kt         # Game action endpoints
│   ├── GameController.kt               # Game state endpoints
│   └── PlayerController.kt             # Player management endpoints
├── model/
│   ├── Game.kt                         # Game state model
│   ├── GameState.kt                    # Game state enum
│   ├── Guess.kt                        # Player guess model
│   ├── Location.kt                     # Location model
│   └── Player.kt                       # Player model
├── service/
│   ├── AdminService.kt                 # Admin business logic
│   ├── GameActionService.kt            # Game actions
│   ├── GameService.kt                  # Game state management
│   ├── PlayerService.kt                # Player management
│   └── SessionService.kt               # Session management
├── util/
│   └── EmojiValidator.kt               # Emoji validation
├── PageController.kt                   # Web page routes
└── ClosestWinsApplication.kt           # Main application

src/main/resources/
├── templates/
│   ├── admin.html                      # Admin panel
│   ├── join.html                       # Join page
│   ├── play.html                       # Play page
│   ├── players.html                    # Players list
│   └── spectate.html                   # Spectator view
└── application.properties              # Configuration
```

## Models

### Player
- `id`: Unique identifier (UUID)
- `name`: Player name
- `emoji`: Player emoji
- `color`: Player color (hex)
- `currentGuess`: Current guess (latitude, longitude)

### Game
- `players`: List of players
- `currentLocation`: Current location to guess
- `upcomingLocations`: Queue of upcoming locations
- `pastLocations`: History of previous locations
- `state`: Game state (PLAYING, FINISHED)
- `roundSecondsLeft`: Time remaining in current round

### Location
- `url`: Image URL of the location
- `longitude`: Location longitude
- `latitude`: Location latitude

## Development Notes

### Test Data

The application initializes with 5 test players on startup:
- Alice (🎯) - New York
- Bob (🚀) - London
- Charlie (🌟) - Tokyo
- Diana (🎨) - No guess
- Eve (⚡) - No guess

### Session Management

- Player sessions are stored in HTTP sessions
- Admin sessions are separate from player sessions
- Sessions persist until browser close or timeout

### Map Implementation

- Uses Leaflet.js for interactive maps
- OpenStreetMap tiles
- Custom markers with player emojis and colors
- Longitude normalized to -180 to 180 range
- No world wrapping to prevent duplicate coordinates

## Building for Production

```bash
./gradlew build
```

The built JAR will be in `build/libs/closest-wins-0.0.1-SNAPSHOT.jar`

Run it with:
```bash
java -jar build/libs/closest-wins-0.0.1-SNAPSHOT.jar
```

## License

This project is for educational purposes.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

