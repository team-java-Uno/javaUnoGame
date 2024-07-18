package Games;
import Cards.*;
import Menues.ConsoleColor;
import Menues.InputMenue;
import Menues.StartMenue;
import Players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public static void main(String[] args)
    {
        if (Game.UserLoadLastGame())
        {
            Game loadGame = Game.loadGame(fileName);
            loadGame.isLoadedGame = true;
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
    private static final String fileName = "gameState.txt";
    private boolean isLoadedGame = false;

    private void Initializer()
    {
        initGameComponents();
        initNewGame();
    }
    //initializer--------------------------------------------------------------------------------
    public void initGameComponents() {
        playerList = new ArrayList<>();
        unoCardDeck = new CardDeck();
        this.startMenue = new StartMenue(playerList);
        this.inputMenue = new InputMenue();
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
        return answer.equalsIgnoreCase("yes");
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
        if(!isLoadedGame)
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

                if(currentPlayer.hasPlayableCard( this))
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

                    if (isValidPlay(drawnCard))
                    {
                        ConsoleColor.printColored("The Drawn Card is Playable Player "+currentPlayer.GetName()+" played Drawn Card",ConsoleColor.CYAN);
                        PlayApplyCard(currentPlayer, drawnCard);
                    }

                }

                if (currentPlayer.GetPlayerHand().isEmpty())
                {
                    SetPlayerPoints(currentPlayer);
                    isGameRunning = false;
                    ConsoleColor.printColored(currentPlayer.GetName() + " has won the game!",ConsoleColor.CYAN);
                } else
                {
                    currentPlayerIndex = getNextPlayerIndex();
                }
                if (currentPlayer.GetPlayerHand().size() == 1)
                {
                    ConsoleColor.printColored(currentPlayer.GetName()+" says UNO last Card", ConsoleColor.CYAN);
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
                if (currentPlayer.hasPlayableCard(this))
                {
                    for (int i = 0; i < currentPlayer.GetPlayerHand().size(); i++) {
                        UnoCards card = currentPlayer.GetPlayerHand().get(i);
                        if (isValidPlay(card))
                        {
                            currentPlayer.PlayCard(card);
                            ConsoleColor.printColored(currentPlayer.GetName()+" played "+printColoredCard(card.GetColor(), card.GetValue())+"\n",ConsoleColor.CYAN );
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
                    ConsoleColor.printColored(currentPlayer.GetName()+" has no playable card. Drawing a card...\n", ConsoleColor.CYAN);
                    currentPlayer.PlayerDrawCard(unoCardDeck);
                }
                if (currentPlayer.GetPlayerHand().size() == 1)
                {
                    ConsoleColor.printColored(currentPlayer.GetName()+" says UNO last Card\n", ConsoleColor.CYAN);
                }
                if (currentPlayer.GetPlayerHand().isEmpty())
                {
                    SetPlayerPoints(currentPlayer);
                    ConsoleColor.printColored(currentPlayer.GetName()+ " has won the game!",ConsoleColor.CYAN);
                }
                currentPlayerIndex = getNextPlayerIndex();
            }
        }
    }

    private boolean ChooseCard(Player currentPlayer)
    {
        if(currentPlayer.hasPlayableCard( this)) {
            ConsoleColor.printColored("Enter the index of the card you want to play: 0-"+ (currentPlayer.GetPlayerHand().size() -1 ), ConsoleColor.CYAN);
            int cardIndex = inputMenue.CheckUserInput(0, currentPlayer.GetPlayerHand().size() - 1);
            UnoCards cards = currentPlayer.GetPlayerHand().get(cardIndex);

            if (isValidPlay(cards))
                PlayApplyCard(currentPlayer, cards);
            else {
                ConsoleColor.printColored("Invalid card. Try again.", ConsoleColor.CYAN);
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
                ConsoleColor.printColored("AI chose the color: "+ currentCardColor+"\n", ConsoleColor.CYAN);
            }
            else
            {
                ConsoleColor.printColored("Choose a color: "+ConsoleColor.RED+"RED, "+ConsoleColor.YELLOW+"YELLOW, "+ConsoleColor.GREEN+"GREEN, "+ConsoleColor.BLUE+"BLUE",ConsoleColor.CYAN);
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
    public boolean isValidPlay(UnoCards cards)
    {
        return cards.GetColor() == currentCardColor ||
                cards.GetValue() == currentCardValue ||
                cards.GetColor() == CardColor.BLACK;

    }
    private void PassCardsClockwise() {
        if (playerList.isEmpty()) return;
        List<UnoCards> firstPlayerHand = new ArrayList<>(playerList.get(0).GetPlayerHand());
        for (int i = 0; i < playerList.size() - 1; i++) {
            playerList.get(i).SetPlayerHand(new ArrayList<>(playerList.get(i + 1).GetPlayerHand()));
        }
        playerList.get(playerList.size() - 1).SetPlayerHand(firstPlayerHand);
        ConsoleColor.printColored("All players passed their hands clockwise.",ConsoleColor.CYAN);
    }
    private void SwapHandWithAnotherPlayer() {
        Player currentPlayer = playerList.get(currentPlayerIndex);
        if (currentPlayer.isAI) {
            Random random = new Random();
            int chosenPlayerIndex;
            do {
                chosenPlayerIndex = random.nextInt(playerList.size());
            } while (chosenPlayerIndex == currentPlayerIndex);
            Player chosenPlayer = playerList.get(chosenPlayerIndex);
            List<UnoCards> tempHand = currentPlayer.GetPlayerHand();
            currentPlayer.SetPlayerHand(chosenPlayer.GetPlayerHand());
            chosenPlayer.SetPlayerHand(tempHand);
            ConsoleColor.printColored(currentPlayer.GetName()+" swapped hands with "+chosenPlayer.GetName()+"\n",ConsoleColor.CYAN);
        } else {
            ConsoleColor.printColored("Choose a player to swap hands with:",ConsoleColor.CYAN);

            // Print player options except the current player
            for (int i = 0; i < playerList.size(); i++) {
                if (i != currentPlayerIndex) {
                    ConsoleColor.printColored(i+": "+playerList.get(i).GetName()+"\n",ConsoleColor.CYAN);
                }
            }
            int chosenPlayerIndex = inputMenue.CheckUserInput(0, playerList.size() - 1);

            while (chosenPlayerIndex == currentPlayerIndex) {
                ConsoleColor.printColored("You cannot swap hands with yourself. Choose another player:",ConsoleColor.CYAN);
                chosenPlayerIndex = inputMenue.CheckUserInput(0, playerList.size() - 1);
            }

            Player chosenPlayer = playerList.get(chosenPlayerIndex);
            List<UnoCards> tempHand = new ArrayList<>(currentPlayer.GetPlayerHand());
            currentPlayer.SetPlayerHand(chosenPlayer.GetPlayerHand());
            chosenPlayer.SetPlayerHand(tempHand);

            ConsoleColor.printColored(currentPlayer.GetName()+" swapped hands with "+chosenPlayer.GetName()+".\n",ConsoleColor.CYAN);
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
                ConsoleColor.printColored("Card was Double on Player Hand Player played Double "+printColoredCard(cards.GetColor(), cards.GetValue()),ConsoleColor.CYAN);
                currentplayer.PlayCard(doubleCard);
            }
        }
    }
    public void saveGame(String fileName) {
        SaveGame saveGame = new SaveGame();
        saveGame.saveGameState(this, fileName);
    }
    public static Game loadGame(String fileName) {
        return SaveGame.loadGameState(fileName);
    }
    public static String printColoredCard(CardColor currentCardColor, CardValue currentCardValue)
    {
        return currentCardColor.GetColoredString(currentCardColor.name())+"_"+currentCardColor.GetColoredString(currentCardValue.name());
    }
}
