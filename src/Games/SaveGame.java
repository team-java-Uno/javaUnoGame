package Games;

import Cards.Card;
import Cards.CardColor;
import Cards.CardValue;
import Cards.UnoCards;

import java.io.*;
import java.util.List;

public class SaveGame {

    private List<UnoCards> PlayerHand;

    // Method to save the entire game state to a file
    public void saveGameState(Game game, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            SaveGameStatus(game, writer);

            SaveDeckState(game, writer);

            SaveCardsPlayed(writer);

            SavePlayersHands(game, writer);

            System.out.println("Game state saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void SavePlayersHands(Game game, BufferedWriter writer) throws IOException {
        // Save player hands
        for (Player player : game.PlayerList) {
            writer.write("Player," + player.GetName() + "," + player.playerPoints + "\n");
            for (UnoCards card : player.GetPlayerHand()) {
                writer.write("Hand," + player.GetName() + "," + card.GetColor() + "," + card.GetValue() + "\n");
            }
        }
    }

    private static void SaveCardsPlayed(BufferedWriter writer) throws IOException {
        // Save played cards
        for (UnoCards card : Game.PlayedCards) {
            writer.write("Played," + card.GetColor() + "," + card.GetValue() + "\n");
        }
    }

    private static void SaveDeckState(Game game, BufferedWriter writer) throws IOException {
        // Save deck state
        for (UnoCards card : game.UnoCardDeck.getCardList()) {
            writer.write("Deck," + card.GetColor() + "," + card.GetValue() + "\n");
        }
    }

    private static void SaveGameStatus(Game game, BufferedWriter writer) throws IOException {
        // Save game status
        writer.write(game.reverseDirection + "," + game.currentPlayerIndex + "," + game.currentCardColor + "," + game.currentCardValue + "\n");
    }

    // Method to load the entire game state from a file
    public static void loadGameState(String fileName) {
        Game game = new Game();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            game.UnoCardDeck.clear();
            Game.PlayedCards.clear();
            game.PlayerList.clear();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                switch (parts[0]) {
                    case "Deck":
                        game.UnoCardDeck.addCard(new Card(CardColor.valueOf(parts[1]), CardValue.valueOf(parts[2])));
                        break;
                    case "Played":
                        Game.PlayedCards.add(new Card(CardColor.valueOf(parts[1]), CardValue.valueOf(parts[2])));
                        break;
                    case "Player":
                        Player player = new Player(game.PlayerList.size(), parts[1]);
                        player.playerPoints = Integer.parseInt(parts[2]);
                        game.PlayerList.add(player);
                        break;
                    case "Hand":
                        for (Player p : game.PlayerList) {
                            if (p.GetName().equals(parts[1])) {
                                p.GetPlayerHand().add(new Card(CardColor.valueOf(parts[2]), CardValue.valueOf(parts[3])));
                                break;
                            }
                        }
                        break;
                    default:
                        game.reverseDirection = Boolean.parseBoolean(parts[0]);
                        game.currentPlayerIndex = Integer.parseInt(parts[1]);
                        game.currentCardColor = CardColor.valueOf(parts[2]);
                        game.currentCardValue = CardValue.valueOf(parts[3]);
                        break;
                }
            }
            System.out.println("Game state loaded from " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
