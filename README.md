# Curve Fever Multiplayer

A real-time multiplayer snake game built with Java and JavaFX, inspired by the classic Curve Fever (Achtung, die Kurve!) game. Players control snakes that leave trails, competing to be the last one standing in an arena.

## 🎮 Features

- **Real-time Multiplayer**: Play against other players in real-time over the network
- **Lobby System**: Browse online players and send game invitations
- **Smooth Gameplay**: Real-time snake movement with collision detection
- **Client-Server Architecture**: Dedicated server handling game logic and state synchronization
- **JavaFX GUI**: Modern user interface with login and lobby screens
- **Network Protocol**: Efficient TCP/UDP communication for gameplay and matchmaking

## 🏗️ Architecture

The project consists of two main components:

### Client (`clientCurveFever`)
- JavaFX-based graphical interface
- Real-time game rendering
- TCP client for game logic communication
- UDP client for lobby and matchmaking
- Controllers for login and lobby management

### Server (`ServerCurveFever`)
- Dedicated game server
- TCP server for game state management
- UDP server for player discovery and invitations
- Multi-threaded architecture for concurrent connections
- Game logic and collision detection

## 🛠️ Technologies Used

- **Java 17**: Core programming language
- **JavaFX 17.0.6**: GUI framework for the client
- **Gradle 8.0.2**: Build automation and dependency management
- **Socket Programming**: TCP and UDP for network communication
- **Multi-threading**: Concurrent game session handling

## 📋 Prerequisites

- Java Development Kit (JDK) 17 or higher
- Gradle 8.0+ (or use the included Gradle wrapper)
- Network connectivity for multiplayer functionality

## 🚀 Installation

### Clone the Repository

```bash
git clone https://github.com/koushamoeini/curve-fever-multiplayer.git
cd curve-fever-multiplayer
```

### Build the Client

```bash
cd clientCurveFever
./gradlew build
```

On Windows:
```cmd
cd clientCurveFever
gradlew.bat build
```

### Compile the Server

The server is a standard Java project. Compile it using your IDE or:

```bash
cd ServerCurveFever
javac -d out src/**/*.java src/*.java
```

## 🎯 Usage

### Starting the Server

1. Navigate to the server directory
2. Run the main class:

```bash
cd ServerCurveFever
java -cp out Main
```

The server will start listening on:
- TCP port `1234` for game logic
- UDP port `9999` for lobby/matchmaking

### Starting the Client

1. Navigate to the client directory
2. Run the application using Gradle:

```bash
cd clientCurveFever
./gradlew run
```

Or use your IDE to run the `Curve` class.

### How to Play

1. **Login**: Enter your username on the login screen
2. **Lobby**: Browse online players in the lobby
3. **Invite**: Click on a player's name to send a game invitation
4. **Accept**: Accept incoming invitations to start a match
5. **Play**: Use arrow keys to control your snake's direction
   - **↑ Up**: Move up
   - **↓ Down**: Move down
   - **← Left**: Move left
   - **→ Right**: Move right
6. **Survive**: Avoid hitting the walls, your trail, or opponent's trail

## 📁 Project Structure

```
curve-fever-multiplayer/
├── clientCurveFever/           # JavaFX Client Application
│   ├── src/main/java/
│   │   └── com/example/clientap6/
│   │       ├── Curve.java      # Main application entry
│   │       ├── client/         # Network clients (TCP/UDP)
│   │       ├── controllers/    # JavaFX controllers
│   │       ├── gui/            # Game rendering
│   │       ├── objects/        # Game objects (Snake, Body)
│   │       └── user/           # User management
│   ├── src/main/resources/     # FXML layouts
│   └── build.gradle            # Client build configuration
│
├── ServerCurveFever/           # Java Server Application
│   └── src/
│       ├── Main.java           # Server entry point
│       ├── network/            # TCP/UDP server logic
│       ├── gameData/           # Game state management
│       ├── objects/            # Game objects (Snake, Coordinate, Prize)
│       └── userData/           # User data models
│
└── README.md                   # This file
```

## 🎮 Game Mechanics

- **Snake Movement**: Snakes move continuously in the current direction
- **Direction Control**: Arrow keys change the velocity vector components
- **Trail Creation**: Snakes leave a trail of circular bodies behind them
- **Collision Detection**: Contact with trails or boundaries ends the game
- **Multiplayer Sync**: Server maintains authoritative game state
- **Real-time Updates**: Game state synchronized at ~100Hz

## 🌐 Network Protocol

### TCP (Port 1234)
- Game state synchronization
- Snake position updates
- Input command transmission
- Game session management

### UDP (Port 9999)
- Player discovery
- Lobby player list broadcasts
- Game invitations
- Quick status updates

## 🔧 Configuration

Default network settings (change in source code if needed):
- Server address: `localhost` / `127.0.0.1`
- TCP port: `1234`
- UDP port: `9999`

## 🐛 Known Issues

- Server must be started before clients can connect
- Clients connect to localhost by default (modify for LAN play)
- Game window size is fixed (1920x1000)

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**Kousha Moeini**
- Email: koushamoeini@gmail.com
- GitHub: [@koushamoeini](https://github.com/koushamoeini)

---
