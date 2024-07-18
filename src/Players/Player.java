package Players;

import Cards.CardColor;
import Cards.CardValue;
import Cards.UnoCards;
import Games.CardDeck;
import Games.Game;
import Games.HasPoints;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player implements HasPoints
{
    private int PlayerID;
    private final String Name;
    public int playerPoints = 0;
    public static List<Player> PlayerList;
    private List<UnoCards> PlayerHand;

    public boolean isAI;

    public Player (int PlayerId, String name, boolean isAI)
    {
        this.PlayerID = PlayerId;
        this.Name = name;
        this.PlayerHand = new ArrayList<>();
        this.isAI = isAI;
    }
    public CardColor chooseRandomColor()
    {
        CardColor[] colors = CardColor.values();
        Random random = new Random();
        return colors[random.nextInt(colors.length - 2)]; // Exclude BLACK from the choices
    }
    public String GetName()
    {
        return Name;
    }
    public int GetID()
    {
        return PlayerID;
    }
    public List<UnoCards> GetPlayerHand()
    {
        return PlayerHand;
    }
    public void PrintPlayerHand()
    {
        System.out.print("Your Hand: ");
        for (int i = 0; i < PlayerHand.size(); i++)
        {
            System.out.printf("%d.%s_%s, ", i , PlayerHand.get(i).GetColor(), PlayerHand.get(i).GetValue());
        }
    }
    public void PlayerDrawCard(CardDeck deck)
    {
        PlayerHand.add(deck.DrawCard());
    }
    public void PlayCard(UnoCards cards)
    {
        Game.playedCards.add(cards);
        PlayerHand.remove(cards);
    }
    public boolean hasPlayableCard(Game currentGame, CardColor currentColor, CardValue currentValue)
    {
        for ( UnoCards cards : PlayerHand)
        {
            if (currentGame.isValidPlay(cards, currentColor, currentValue))
            {
                return true;
            }
        }
        return false;
    }
    public void SetPlayerHand(List<UnoCards> newHand)
    {
        this.PlayerHand = newHand;
    }
    @Override
    public int PointValue(int points)
    {
        points = playerPoints;
        return points;
    }
}


