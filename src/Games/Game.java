package Games;
import Cards.*;
import Menues.StartMenue;

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

    private void Initializer()
    {
        PlayerList = new ArrayList<>();
        UnoCardDeck = new CardDeck(this);
        StartMenue startMenue = new StartMenue(this, PlayerList);
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
        System.out.printf("Start Round %d \n", roundIndex);
        System.out.println("Dealing Card.....");

        boolean isGameRunning = true;
        while(isGameRunning)
        {
            DealPlayerHand();
            isGameRunning = false;
        }
    }
    private void InitStartingCard()
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

    }
    public boolean isValidPlay( UnoCards cards, CardColor Color,CardValue Value)
    {

        return true;
    }
}
