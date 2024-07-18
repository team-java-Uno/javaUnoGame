package Games;
import Cards.*;
import Menues.ConsoleColor;
import Menues.InputMenue;
import Menues.StartMenue;
import Players.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public static void main(String[] args)
    {
        if (Game.UserLoadLastGame())
        {
            Game loadGame = Game.loadGame(fileName);
            loadGame.isloadGame = true;
            loadGame.GameLoop();
        }
        else
        {
            Game Game_1 = new Game();
            Game_1.Initializer();
            Game_1.GameLoop();
        }

    }

    public static List<UnoCards> playedCards = new ArrayList<>();
    public List<Player> playerList;
    public CardDeck unoCardDeck;
    public boolean reverseDirection;
    public int currentPlayerIndex;
    public CardColor currentCardColor;
    public CardValue currentCardValue;
    private InputMenue inputMenue;
    private StartMenue startMenue;
    private static String fileName = "gameState.txt";
    private boolean isloadGame = false;

    private void Initializer()
    {
        initGameComponents();
        initNewGame();
    }
    //initializer--------------------------------------------------------------------------------
    public void initGameComponents() {
        playerList = new ArrayList<>();
        unoCardDeck = new CardDeck(this);
        this.startMenue = new StartMenue(this, playerList);
        this.inputMenue = new InputMenue(this);
    }

    private void initNewGame() {
        playerList = startMenue.initPlayer();
        playerList = (startMenue.initAI(inputMenue));
        reverseDirection = false;
        currentPlayerIndex = -1;

    }
    private static boolean UserLoadLastGame() {
        ConsoleColor.printColored("Do you want to load the last game? (yes/no)",ConsoleColor.CYAN);
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        if (answer.equalsIgnoreCase("yes")) {
            return true;
        }
        return false;
    }

    //initializer end--------------------------------------------------------------------------------
    private void GameLoop()
    {
        int roundIndex = 0;
        while(!CheckPlayerPoints())
        {
            roundIndex++;
            ConsoleColor.printColored("Starting Game.....",ConsoleColor.CYAN);
            RoundLoop(roundIndex);
        }
    }
    private void DealPlayerHand()
    {
        for (Player player : playerList)
        {
            for (int i = 0; i < 7; i++)
            {
                player.PlayerDrawCard(unoCardDeck);
            }
        }
    }
    public boolean CheckPlayerPoints()
    {
        for (Player player : playerList)
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
        ConsoleColor.printColored("Start Round "+roundIndex, ConsoleColor.CYAN);
        ConsoleColor.printColored("Dealing Card.....",ConsoleColor.CYAN);
        if(!isloadGame)
        {
            DealPlayerHand();
        }
        firstCard = InitStartingCard();
        currentPlayerIndex = 0;
        ConsoleColor.printColored("Starting Card is "+ printColoredCard(firstCard.GetColor(),firstCard.GetValue()),ConsoleColor.CYAN);


        boolean isGameRunning = true;
        while(isGameRunning)
        {
            Player currentPlayer = playerList.get(currentPlayerIndex);
            ConsoleColor.printColored(currentPlayer.GetName()+"'s Turn.", ConsoleColor.CYAN);
            ConsoleColor.printColored("Current card is: "+printColoredCard(currentCardColor, currentCardValue), ConsoleColor.CYAN);
            CheckAIRound();
            if (!currentPlayer.isAI)
            {
                currentPlayer.PrintPlayerHand();
                System.out.println("\n");

                if(currentPlayer.hasPlayableCard( this, currentCardColor, currentCardValue))
                {
                    if (ChooseCard(currentPlayer)) continue;
                }
                else
                {
                    ConsoleColor.printColored("No Playable card. Please enter 1 to Draw a card",ConsoleColor.CYAN);
                    inputMenue.CheckUserInput(1,1);
                    currentPlayer.PlayerDrawCard(unoCardDeck);
                    UnoCards drawnCard = currentPlayer.GetPlayerHand().get(currentPlayer.GetPlayerHand().size()-1);
                    ConsoleColor.printColored(currentPlayer.GetName()+" has Drawn the Card "+printColoredCard(drawnCard.GetColor(), drawnCard.GetValue()),ConsoleColor.CYAN);

                    System.out.printf("Player %s has drawn the card %s_%s ", currentPlayer.GetName(), drawnCard.GetColor(), drawnCard.GetValue());
                    if (isValidPlay(drawnCard, currentCardColor, currentCardValue))
                    {
                        ConsoleColor.printColored("The Drawn Card is Playable Player "+currentPlayer.GetName()+" played Drawn Card",ConsoleColor.CYAN);
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
            saveGame(fileName);
        }

    }
    private void CheckAIRound()
    {
        {
            Player currentPlayer = playerList.get(currentPlayerIndex);
            if (currentPlayer.isAI) {
                if (currentPlayer.hasPlayableCard(this, currentCardColor, currentCardValue))
                {
                    for (int i = 0; i < currentPlayer.GetPlayerHand().size(); i++) {
                        UnoCards card = currentPlayer.GetPlayerHand().get(i);
                        if (isValidPlay(card, currentCardColor, currentCardValue)) {
                            currentPlayer.PlayCard(card);
                            System.out.printf("Player %s played %s_%s\n",currentPlayer.GetName(), card.GetColor(), card.GetValue());
                            currentCardColor = card.GetColor();
                            currentCardValue = card.GetValue();
                            checkBlack(card);
                            ApplyCardEffect(card);
                            break;
                        }
                    }
                }
                else
                {
                    System.out.printf("%s has no playable card. Drawing a card...\n", currentPlayer.GetName());
                    currentPlayer.PlayerDrawCard(unoCardDeck);
                }
                if (currentPlayer.GetPlayerHand().size() == 1)
                {
                    System.out.printf("Player: %s says UNO last Card\n", currentPlayer.GetName());
                }
                if (currentPlayer.GetPlayerHand().isEmpty())
                {
                    SetPlayerPoints(currentPlayer);
                    System.out.println(currentPlayer.GetName() + " has won the game!");
                }
                currentPlayerIndex = getNextPlayerIndex();
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

    private void checkBlack(UnoCards cards)
    {
        if (cards.GetColor() == CardColor.BLACK)
        {
            Player currentPlayer = playerList.get(currentPlayerIndex);
            if (currentPlayer.isAI)
            {
                currentCardColor = currentPlayer.chooseRandomColor();
                System.out.printf("AI chose the color: %s\n", currentCardColor);
            }
            else
            {
                System.out.println("Choose a color: RED, YELLOW, GREEN, BLUE");
                List<String> colors = new ArrayList<>();
                colors.add("RED");
                colors.add("BLUE");
                colors.add("YELLOW");
                colors.add("GREEN");
                currentCardColor = CardColor.valueOf(inputMenue.CheckStringInput(colors).toUpperCase());
            }
        }
    }

    private UnoCards InitStartingCard()
    {
        UnoCards firstCard = null;
        boolean correctCard = false;
        while (!correctCard)
        {
            firstCard = unoCardDeck.DrawCard();
            if (firstCard.GetColor() != CardColor.BLACK)
            {
                Game.playedCards.add(firstCard);
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
        if (playerList.isEmpty()) return;

        // SPeichert die hand vom ersten spieler
        List<UnoCards> firstPlayerHand = new ArrayList<>(playerList.get(0).GetPlayerHand());

        // Reicht die hand dem anderen spiler weiter
        for (int i = 0; i < playerList.size() - 1; i++) {
            playerList.get(i).SetPlayerHand(new ArrayList<>(playerList.get(i + 1).GetPlayerHand()));
        }

        // der letzte spieler bekommt die vom ersten
        playerList.get(playerList.size() - 1).SetPlayerHand(firstPlayerHand);

        System.out.println("All players passed their hands clockwise.");
    }

    private void SwapHandWithAnotherPlayer() {
        Player currentPlayer = playerList.get(currentPlayerIndex);
        if (currentPlayer.isAI) {
            // AI chooses a random player to swap hands with
            Random random = new Random();
            int chosenPlayerIndex;
            do {
                chosenPlayerIndex = random.nextInt(playerList.size());
            } while (chosenPlayerIndex == currentPlayerIndex); // Ensure AI does not choose itself

            Player chosenPlayer = playerList.get(chosenPlayerIndex);
            List<UnoCards> tempHand = currentPlayer.GetPlayerHand();
            currentPlayer.SetPlayerHand(chosenPlayer.GetPlayerHand());
            chosenPlayer.SetPlayerHand(tempHand);

            System.out.printf("%s swapped hands with %s\n", currentPlayer.GetName(), chosenPlayer.GetName());
        } else {
            System.out.println("Choose a player to swap hands with:");

            // Print player options except the current player
            for (int i = 0; i < playerList.size(); i++) {
                if (i != currentPlayerIndex) {
                    System.out.printf("%d: %s\n", i, playerList.get(i).GetName());
                }
            }
            int chosenPlayerIndex = inputMenue.CheckUserInput(0, playerList.size() - 1);

            while (chosenPlayerIndex == currentPlayerIndex) {
                System.out.println("You cannot swap hands with yourself. Choose another player:");
                chosenPlayerIndex = inputMenue.CheckUserInput(0, playerList.size() - 1);
            }

            // Swap hands between current player and chosen player
            Player chosenPlayer = playerList.get(chosenPlayerIndex);
            List<UnoCards> tempHand = new ArrayList<>(currentPlayer.GetPlayerHand());
            currentPlayer.SetPlayerHand(chosenPlayer.GetPlayerHand());
            chosenPlayer.SetPlayerHand(tempHand);

            System.out.printf("%s swapped hands with %s.\n", currentPlayer.GetName(), chosenPlayer.GetName());
        }
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
                Player nextPlayer = playerList.get(getNextPlayerIndex());
                nextPlayer.PlayerDrawCard(unoCardDeck);
                nextPlayer.PlayerDrawCard(unoCardDeck);
                break;
            case DRAW_FOUR:
                Player nextPlayerFour = playerList.get(getNextPlayerIndex());
                nextPlayerFour.PlayerDrawCard(unoCardDeck);
                nextPlayerFour.PlayerDrawCard(unoCardDeck);
                nextPlayerFour.PlayerDrawCard(unoCardDeck);
                nextPlayerFour.PlayerDrawCard(unoCardDeck);
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
            return (currentPlayerIndex == 0) ? playerList.size() -1 : currentPlayerIndex -1;
        } else {
            return (currentPlayerIndex+ 1) % playerList.size();
        }
    }
    private void SetPlayerPoints(Player currentplayer)
    {
        for (Player player : playerList)
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
        Player currentplayer = playerList.get(currentPlayerIndex);
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
    public static Game loadGame(String fileName) {
        Game loadGame = SaveGame.loadGameState(fileName);
        return loadGame;
    }
    public String printColoredCard(CardColor currentCardColor, CardValue currentCardValue)
    {
        return currentCardColor.GetColoredString(currentCardColor.name())+"_"+currentCardColor.GetColoredString(currentCardValue.name());
    }
}
