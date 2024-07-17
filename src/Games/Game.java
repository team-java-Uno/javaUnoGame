package Games;
import Cards.*;
import Menues.InputMenue;
import Menues.StartMenue;

import java.util.ArrayList;
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
        reverseDirection = false;
        currentPlayerIndex = 0;

    }
    private void GameLoop()
    {
        int roundIndex = 0;
        while(!CheckPlayerPoints())
        {
            roundIndex++;
            System.out.println("Starting Game.....");
            RoundLoop(roundIndex);
            PlayerList.get(0).points = 500;
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
            if (player.points >= 500)
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
        System.out.printf("Starting Card %s_%s \n", firstCard.GetColor(), firstCard.GetValue());

        boolean isGameRunning = true;
        while(isGameRunning)
        {
            Player currentPlayer = PlayerList.get(currentPlayerIndex);
            System.out.printf("%s's turn. \n", currentPlayer);
            System.out.printf("Current card is: %s_%s ", currentCardColor, currentCardValue);
            currentPlayer.PrintPlayerHand();
            System.out.println("\n");

            if(currentPlayer.hasPlayableCard( this, currentCardColor, currentCardValue))
            {
                System.out.printf("Enter the index of the card you want to play: 0-%d", currentPlayer.GetPlayerHand().size() -1);
                int cardIndex = inputMenue.CheckUserInput(0, currentPlayer.GetPlayerHand().size()-1);
                UnoCards cards = currentPlayer.GetPlayerHand().get(cardIndex);

                if (isValidPlay(cards, currentCardColor, currentCardValue))
                {
                    currentPlayer.PlayCard(cards);
                    currentCardColor = cards.GetColor();
                    currentCardValue = cards.GetValue();
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
            }

            isGameRunning = false;
        }
    }
    private UnoCards InitStartingCard()
    {
        UnoCards firstCard = UnoCardDeck.DrawCard();

        while(firstCard.GetColor() == CardColor.BLACK)
        {
            UnoCardDeck.AddCard(firstCard);
            UnoCardDeck.ShuffleCardDeck();
            firstCard = UnoCardDeck.DrawCard();
        }
        Game.PlayedCards.add(firstCard);
        currentCardColor = firstCard.GetColor();
        currentCardValue = firstCard.GetValue();
        return firstCard;
    }
    public boolean isValidPlay(UnoCards cards, CardColor Color, CardValue Value)
    {
        return cards.GetColor() == currentCardColor ||
                cards.GetValue() == currentCardValue ||
                cards.GetColor() == CardColor.BLACK;

    }
    private void ApplyCardEffect(UnoCards cards)
    {
        switch (cards.GetValue())
        {
            case REVERSE:
                reverseDirection = !reverseDirection;
                break;
            case SKIP:
                currentPlayerIndex = getNextPlayerIndex();
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
}
