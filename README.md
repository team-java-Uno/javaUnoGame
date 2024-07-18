# SEPE Uno Game

## Überblick

Das **SEPE Uno Game** ist eine modifizierte Variante des klassischen Uno-Spiels. Es bietet Spielern die Möglichkeit, gegen KI-Gegner oder andere Spieler anzutreten.

## Installation

Für die Einrichtung des Spiels folge bitte den Anweisungen in der [Installation](installation).

## Nutzung

Um das Projekt zu verstehen kannst du dir: [Nutzung](usage) durchlesen.

---

# English:

# SEPE Uno Game

## Overview

The **SEPE Uno Game** is a modified version of the classic Uno game. It allows players to compete against AI opponents or other players.

## Installation

To set up the game, please follow the instructions in [Installation](installation).

## Usage

To understand the basics of the code structure redirect to: [Usage](usage).

---

Under MIT-License
# German
### Anleitung zum Setup und Ausführen des UNO-Spiels

1. **Repository klonen:**
   Öffne die Konsole und führe den folgenden Befehl aus, um das Repository zu klonen:

Repo link:[is here](https://github.com/team-java-Uno/javaUnoGame.git)
   ```bash
   git clone https://github.com/team-java-Uno/javaUnoGame.git 
   cd javaUnoGame
   ```

2. **Interpreter auswählen:**
   Stelle sicher, dass du eine Java-Entwicklungsumgebung (JDK) installiert hast.

3. **Spiel ausführen:**
   Öffne die Konsole im Projektverzeichnis und führe den folgenden Befehl aus:
   ```bash
   javac Games/Game.java  # Kompiliert das Spiel
   java Games.Game        # Führt das Spiel aus
   ```

4. **Spiel starten:**
   - Wenn das Spiel läuft, wirst du nach dem Laden des letzten Spiels gefragt.
   - Gib "yes" ein, um das letzte gespeicherte Spiel zu laden, oder "no", um ein neues Spiel zu starten.

5. **Spielen:**
   - Folge den Anweisungen im Spiel, um Karten zu spielen und die Runden zu absolvieren.
   - Die Spielregeln basieren auf dem UNO-Kartenspiel.

6. **Spiel speichern:**
   Das Spiel wird automatisch gespeichert, wenn notwendig. Der Zustand wird in der Datei "gameState.txt" gespeichert.

7. **Beenden:**
   Um das Spiel zu beenden, schließe einfach das Konsolenfenster oder drücke "Strg + C" in der Konsole.

---
# Englisch
### Guide to Setup and Run the UNO Game

1. **Clone the repository:**
   Open your terminal and execute the following command to clone the repository:

Repo link:[is here](https://github.com/team-java-Uno/javaUnoGame.git)
   ```bash
   git clone https://github.com/team-java-Uno/javaUnoGame.git 
   cd javaUnoGame
   ```

2. **Choose your interpreter:**
   Make sure you have a Java Development Kit (JDK) installed.

3. **Compile and run the game:**
   Open your terminal in the project directory and execute the following commands:
   ```bash
   javac Games/Game.java  # Compile the game
   java Games.Game        # Run the game
   ```

4. **Starting the game:**
   - When the game starts, you will be asked if you want to load the last saved game.
   - Type "yes" to load the last saved game or "no" to start a new game.

5. **Playing the game:**
   - Follow the in-game instructions to play cards and progress through rounds.
   - The game rules are based on the UNO card game.

6. **Saving the game:**
   The game state is automatically saved as needed. The state is stored in the file "gameState.txt".

7. **Exiting the game:**
   To exit the game, simply close the console window or press "Ctrl + C" in the terminal.
# SEPE Uno Game - Usage

# German:

Die Nutzung des **SEPE Uno Game** erfordert ein grundlegendes Verständnis der Klassen und ihrer Funktionen, die eng miteinander verknüpft sind, um das Spiel zu steuern und zu verwalten.

---

### `UnoCards`

- **Wichtigkeit**: Die Klasse `UnoCards` repräsentiert die Uno-Karten im Spiel. Jede Karte hat eine Farbe, einen Wert und eine Punktzahl. Diese Eigenschaften sind entscheidend für das Spielgeschehen, da sie bestimmen, welche Karten ein Spieler spielen kann und wie Punkte berechnet werden.
  
---

### `CardDeck`

- **Wichtigkeit**: Das `CardDeck` ist die zentrale Struktur, die alle Uno-Karten enthält. Das Deck wird initialisiert, gemischt und verwendet, um Karten an Spieler zu verteilen. Ein korrekt funktionierendes Deck ist essentiell für das reibungslose Spielen des Uno-Spiels.

---

### `Player`

- **Wichtigkeit**: Die Klasse `Player` repräsentiert die Spieler im Spiel. Jeder Spieler hat eine Hand mit Uno-Karten, die er spielen kann. Spieler können Karten ziehen, Karten spielen und ihre Hand verwalten. Diese Klasse ist kritisch für die Interaktionen zwischen den Spielern und dem Spiel selbst.

---

### `Game`

- **Wichtigkeit**: Die Klasse `Game` orchestriert das gesamte Spielgeschehen. Sie verwaltet die Spielerliste, das Kartendeck, den Spielverlauf und alle Spielregeln. Ohne diese Klasse wäre das Spiel nicht in der Lage, seine verschiedenen Phasen wie das Ziehen von Karten, das Spielen von Karten und das Überprüfen der Spielregeln zu koordinieren.

---

### Erweiterungsmöglichkeiten:

- **Neue Spielregeln hinzufügen**: Der nächste Entwickler könnte neue Spielregeln implementieren, indem er die Methoden in der `Game`-Klasse erweitert oder überschreibt, die für die Spielregeln verantwortlich sind. Zum Beispiel könnte eine neue Regel hinzugefügt werden, die besagt, dass Spieler eine bestimmte Anzahl von Karten ziehen müssen, wenn sie eine bestimmte Karte spielen.

- **AI-Verbesserungen**: Die `AI`-Klasse kann erweitert werden, um die KI der computergesteuerten Spieler intelligenter zu machen. Dies könnte durch Hinzufügen neuer Strategien geschehen, die basierend auf dem aktuellen Spielzustand entscheiden, welche Karte gespielt werden soll.

---

# English:

Using **SEPE Uno Game** requires a fundamental understanding of the classes and their functions, which are closely interconnected to control and manage the game.

---

### `UnoCards`

- **Importance**: The `UnoCards` class represents the Uno cards in the game. Each card has a color, a value, and a point value. These properties are crucial for gameplay as they determine which cards a player can play and how points are calculated.

---

### `CardDeck`

- **Importance**: The `CardDeck` is the central structure that holds all Uno cards. The deck is initialized, shuffled, and used to deal cards to players. A properly functioning deck is essential for smooth gameplay of the Uno game.

---

### `Player`

- **Importance**: The `Player` class represents the players in the game. Each player has a hand of Uno cards that they can play. Players can draw cards, play cards, and manage their hand. This class is critical for the interactions between players and the game itself.

---

### `Game`

- **Importance**: The `Game` class orchestrates the entire gameplay. It manages the player list, the card deck, the game flow, and all game rules. Without this class, the game would not be able to coordinate its various phases such as drawing cards, playing cards, and checking game rules.

---

### Expansion Opportunities:

- **Adding New Game Rules**: The next developer could implement new game rules by extending or overriding methods in the `Game` class responsible for game rules. For example, a new rule could be added stating that players must draw a certain number of cards when playing a specific card.

- **Improving AI**: The `AI` class can be expanded to make the AI of computer-controlled players smarter. This could be done by adding new strategies that decide which card to play based on the current game state.
