package Games;

import Cards.Card;
import Cards.CardColor;
import Cards.CardValue;
import Cards.UnoCards;
import Menues.ConsoleColor;
import Players.Player;

import java.io.*;

public class SaveGame {
    // Method to save the entire game state to a file
    public void saveGameState(Game game, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            SaveGameStatus(game, writer);

            SaveDeckState(game, writer);

            SaveCardsPlayed(writer);

            SavePlayersHands(game, writer);

            ConsoleColor.printColored("Game state saved to " + fileName, ConsoleColor.CYAN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void SavePlayersHands(Game game, BufferedWriter writer) throws IOException {
        // Save player hands
        for (Player player : game.playerList) {
            writer.write("Player," + player.GetName() + "," + player.playerPoints + "\n");
            for (UnoCards card : player.GetPlayerHand()) {
                writer.write("Hand," + player.GetName() + "," + card.GetColor() + "," + card.GetValue() + "\n");
            }
        }
    }

    private static void SaveCardsPlayed(BufferedWriter writer) throws IOException {
        // Save played cards
        for (UnoCards card : Game.playedCards) {
            writer.write("Played," + card.GetColor() + "," + card.GetValue() + "\n");
        }
    }

    private static void SaveDeckState(Game game, BufferedWriter writer) throws IOException {
        // Save deck state
        for (UnoCards card : game.unoCardDeck.getCardList()) {
            writer.write("Deck," + card.GetColor() + "," + card.GetValue() + "\n");
        }
    }

    private static void SaveGameStatus(Game game, BufferedWriter writer) throws IOException {
        // Save game status
        writer.write(game.reverseDirection + "," + game.currentPlayerIndex + "," + game.currentCardColor + "," + game.currentCardValue + "\n");
    }

    public static Game loadGameState(String fileName) {
        Game loadGame = new Game();
        loadGame.initGameComponents();
        loadGame.playerList.clear();
        loadGame.unoCardDeck.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                switch (parts[0]) {
                    case "Deck":
                        loadGame.unoCardDeck.addCard(new Card(CardColor.valueOf(parts[1]), CardValue.valueOf(parts[2])));
                        break;
                    case "Played":
                        Game.playedCards.add(new Card(CardColor.valueOf(parts[1]), CardValue.valueOf(parts[2])));
                        break;
                    case "Player":
                        Player player = new Player(loadGame.playerList.size(), parts[1],false);
                        player.playerPoints = Integer.parseInt(parts[2]);
                        loadGame.playerList.add(player);
                        break;
                    case "Hand":
                        for (Player p : loadGame.playerList) {
                            if (p.GetName().equals(parts[1])) {
                                p.GetPlayerHand().add(new Card(CardColor.valueOf(parts[2]), CardValue.valueOf(parts[3])));
                                break;
                            }
                        }
                        break;
                    default:
                        loadGame.reverseDirection = Boolean.parseBoolean(parts[0]);
                        loadGame.currentPlayerIndex = Integer.parseInt(parts[1]);
                        loadGame.currentCardColor = CardColor.valueOf(parts[2]);
                        loadGame.currentCardValue = CardValue.valueOf(parts[3]);
                        break;
                }
            }
            ConsoleColor.printColored("Game state loaded from " + fileName,ConsoleColor.CYAN);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadGame;
    }
}
