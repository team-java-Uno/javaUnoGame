package Games;
import Cards.*;
import Menues.InputMenue;
import Menues.StartMenue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    public static void main(String[] args)
    {
        Game Game_1 = new Game();
        Game_1.Initializer();
        Game_1.GameLoop();
    }

    public static List<UnoCards> PlayedCards = new ArrayList<>();
    private List<Player> PlayerList;
    private CardDeck UnoCardDeck;
    private boolean reverseDirection;
    private int currentPlayerIndex;
    private CardColor currentCardColor;
    private CardValue currentCardValue;
    private InputMenue inputMenue;
    private StartMenue startMenue;

    private void Initializer()
    {
        PlayerList = new ArrayList<>();
        UnoCardDeck = new CardDeck(this);
        this.startMenue = new StartMenue(this, PlayerList);
        this.inputMenue = new InputMenue(this);

        PlayerList = startMenue.initPlayer();
        PlayerList = (startMenue.initAI(inputMenue));
        reverseDirection = false;
        currentPlayerIndex = -1;

    }
    private void GameLoop()
    {
        int roundIndex = 0;
        while(!CheckPlayerPoints())
        {
            roundIndex++;
            System.out.println("Starting Game.....");
            RoundLoop(roundIndex);
        }
    }
    private void DealPlayerHand()
    {
        for (Player player : PlayerList)
        {
            for (int i = 0; i < 7; i++)
            {
                player.PlayerDrawCard(UnoCardDeck);
            }
        }
    }
    private boolean CheckPlayerPoints()
    {
        for (Player player : PlayerList)
        {
            if (player.playerPoints >= 500)
            {
                return true;
            }
        }
        return false;
    }
    private void RoundLoop(int roundIndex)
    {
        UnoCards firstCard;
        System.out.printf("Start Round %d \n", roundIndex);
        System.out.println("Dealing Card.....");

        DealPlayerHand();
        firstCard = InitStartingCard();
        currentPlayerIndex = 0;
        System.out.printf("Starting Card %s_%s \n", firstCard.GetColor(), firstCard.GetValue());

        boolean isGameRunning = true;
        while(isGameRunning)
        {
            Player currentPlayer = PlayerList.get(currentPlayerIndex);
            System.out.printf("%s's turn. \n", currentPlayer.GetName());
            System.out.printf("Current card is: %s_%s \n", currentCardColor, currentCardValue);
            currentPlayer.PrintPlayerHand();
            System.out.println("\n");

            if(currentPlayer.hasPlayableCard( this, currentCardColor, currentCardValue))
            {
                if (ChooseCard(currentPlayer)) continue;
            }
            else
            {
                System.out.println("No playable card. Please enter 1 to Draw a card");
                inputMenue.CheckUserInput(1,1);
                currentPlayer.PlayerDrawCard(UnoCardDeck);
                UnoCards drawnCard = currentPlayer.GetPlayerHand().get(currentPlayer.GetPlayerHand().size()-1);
                System.out.printf("Player %s has drawn the card %s_%s ", currentPlayer.GetName(), drawnCard.GetColor(), drawnCard.GetValue());
                if (isValidPlay(drawnCard, currentCardColor, currentCardValue))
                {
                    System.out.printf("The Drawn Card is Playable Player %s played Drawn Card", currentPlayer.GetName());
                    PlayApplyCard(currentPlayer, drawnCard);
                }

            }

            if (currentPlayer.GetPlayerHand().isEmpty())
            {
                SetPlayerPoints(currentPlayer);
                isGameRunning = false;
                System.out.println(currentPlayer.GetName() + " has won the game!");
            } else
            {
                currentPlayerIndex = getNextPlayerIndex();
            }
            if (currentPlayer.GetPlayerHand().size() == 1)
            {
                System.out.printf("Player: %s says UNO last Card", currentPlayer.GetName());
            }
        }

    }

    private boolean ChooseCard(Player currentPlayer)
    {
        if(currentPlayer.hasPlayableCard( this, currentCardColor, currentCardValue)) {
            System.out.printf("Enter the index of the card you want to play: 0-%d", currentPlayer.GetPlayerHand().size() - 1);
            int cardIndex = inputMenue.CheckUserInput(0, currentPlayer.GetPlayerHand().size() - 1);
            UnoCards cards = currentPlayer.GetPlayerHand().get(cardIndex);

            if (isValidPlay(cards, currentCardColor, currentCardValue))
                PlayApplyCard(currentPlayer, cards);
            else {
                System.out.println("Invalid card. Try again.");
                return true;
            }
            return false;
        }
        return false;
    }

    private void PlayApplyCard(Player currentPlayer, UnoCards cards) {
        currentPlayer.PlayCard(cards);
        DoubleCard(cards);
        currentCardColor = cards.GetColor();
        currentCardValue = cards.GetValue();
        checkBlack(cards);
        ApplyCardEffect(cards);
    }

    private void checkBlack(UnoCards cards) {
        if (cards.GetColor() == CardColor.BLACK) {
            System.out.println("Choose a color: RED, YELLOW, GREEN, BLUE");

            List<String> colors = new ArrayList<>();
            colors.add("RED");
            colors.add("BLUE");
            colors.add("YELLOW");
            colors.add("GREEN");

            // Ensure the selected color is converted to the appropriate enum type
            currentCardColor = CardColor.valueOf(inputMenue.CheckStringInput(colors).toUpperCase());
        }
    }

    private UnoCards InitStartingCard()
    {
        UnoCards firstCard = null;
        boolean correctCard = false;
        while (!correctCard)
        {
            firstCard = UnoCardDeck.DrawCard();
            if (firstCard.GetColor() != CardColor.BLACK)
            {
                Game.PlayedCards.add(firstCard);
                currentCardColor = firstCard.GetColor();
                currentCardValue = firstCard.GetValue();
                correctCard = true;
            }
        }
        return firstCard;
    }
    public boolean isValidPlay(UnoCards cards, CardColor Color, CardValue Value)
    {
        return cards.GetColor() == currentCardColor ||
                cards.GetValue() == currentCardValue ||
                cards.GetColor() == CardColor.BLACK;

    }
    private void PassCardsClockwise() {
        if (PlayerList.isEmpty()) return;

        // SPeichert die hand vom ersten spieler
        List<UnoCards> firstPlayerHand = new ArrayList<>(PlayerList.get(0).GetPlayerHand());

        // Reicht die hand dem anderen spiler weiter
        for (int i = 0; i < PlayerList.size() - 1; i++) {
            PlayerList.get(i).SetPlayerHand(new ArrayList<>(PlayerList.get(i + 1).GetPlayerHand()));
        }

        // der letzte spieler bekommt die vom ersten
        PlayerList.get(PlayerList.size() - 1).SetPlayerHand(firstPlayerHand);

        System.out.println("All players passed their hands clockwise.");
    }

    private void SwapHandWithAnotherPlayer() {
        Player currentPlayer = PlayerList.get(currentPlayerIndex);
        System.out.println("Choose a player to swap hands with:");

        // Print player options except the current player
        for (int i = 0; i < PlayerList.size(); i++) {
            if (i != currentPlayerIndex) {
                System.out.printf("%d: %s\n", i, PlayerList.get(i).GetName());
            }
        }
        int chosenPlayerIndex = inputMenue.CheckUserInput(0, PlayerList.size() - 1);

        while (chosenPlayerIndex == currentPlayerIndex) {
            System.out.println("You cannot swap hands with yourself. Choose another player:");
            chosenPlayerIndex = inputMenue.CheckUserInput(0, PlayerList.size() - 1);
        }

        // Swap hands between current player and chosen player
        Player chosenPlayer = PlayerList.get(chosenPlayerIndex);
        List<UnoCards> tempHand = new ArrayList<>(currentPlayer.GetPlayerHand());
        currentPlayer.SetPlayerHand(chosenPlayer.GetPlayerHand());
        chosenPlayer.SetPlayerHand(tempHand);

        System.out.printf("%s swapped hands with %s.\n", currentPlayer.GetName(), chosenPlayer.GetName());
    }
    private void ApplyCardEffect(UnoCards cards)
    {
        switch (cards.GetValue())
        {
            case REVERSE:
                reverseDirection = !reverseDirection;
                break;
            case SKIP:
                this.currentPlayerIndex = getNextPlayerIndex();
                break;
            case DRAW_TWO:
                Player nextPlayer = PlayerList.get(getNextPlayerIndex());
                nextPlayer.PlayerDrawCard(UnoCardDeck);
                nextPlayer.PlayerDrawCard(UnoCardDeck);
                break;
            case DRAW_FOUR:
                Player nextPlayerFour = PlayerList.get(getNextPlayerIndex());
                nextPlayerFour.PlayerDrawCard(UnoCardDeck);
                nextPlayerFour.PlayerDrawCard(UnoCardDeck);
                nextPlayerFour.PlayerDrawCard(UnoCardDeck);
                nextPlayerFour.PlayerDrawCard(UnoCardDeck);
                break;
            case ZERO:
                PassCardsClockwise();
                break;
            case SEVEN:
                SwapHandWithAnotherPlayer();
            default:
                break;
        }
    }

    private int getNextPlayerIndex()
    {
        if (reverseDirection)
        {
            return (currentPlayerIndex == 0) ? PlayerList.size() -1 : currentPlayerIndex -1;
        } else {
            return (currentPlayerIndex+ 1) % PlayerList.size();
        }
    }
    private void SetPlayerPoints(Player currentplayer)
    {
        for (Player player : PlayerList)
        {
            if (player != currentplayer)
            {
                for( UnoCards cards : player.GetPlayerHand())
                {
                    currentplayer.playerPoints += cards.GetPointValue();
                }
            }
        }
    }
    private void DoubleCard(UnoCards cards)
    {
        Player currentplayer = PlayerList.get(currentPlayerIndex);
        for (UnoCards doubleCard : currentplayer.GetPlayerHand())
        {
            if ((doubleCard.GetColor() == cards.GetColor()) && (doubleCard.GetValue() == cards.GetValue()))
            {
                System.out.printf("Card was Double on Player Hand Player played Double %s_%s", cards.GetColor(), cards.GetValue());
                currentplayer.PlayCard(doubleCard);
            }
        }
    }
    public void saveGame(String fileName) {
        SaveGame saveGame = new SaveGame();
        saveGame.saveGameState(this, fileName);
    }
    public static void loadGame(String fileName) {
        SaveGame saveGame = new SaveGame();
        saveGame.loadGameState(fileName);
    }
}
