package Games;

import Cards.CardColor;
import Cards.CardValue;
import Cards.UnoCards;

import java.util.ArrayList;
import java.util.List;

public class Player
{
    private int PlayerID;
    private final String Name;
    protected int points = 0;
    public static List<Player> PlayerList;
    private List<UnoCards> PlayerHand;

    public Player (int PlayerId, String name)
    {
        this.PlayerID = PlayerId;
        this.Name = name;
        this.PlayerHand = new ArrayList<>();
    }
    public String GetName()
    {
        return Name;
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
        Game.PlayedCards.add(cards);
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


}


