package Games;

import Cards.*;
import Games.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck
{
    private List<UnoCards> CardList;
    private Game CurrentGame;
    public CardDeck(Game game)
    {
        this.CurrentGame = game;
        this.CardList = new ArrayList<>();
        InitCardDeck();
        PrintCards();
    }
    private void InitCardDeck()
    {
        for (CardColor colorCards : CardColor.values())
        {
            if (colorCards != CardColor.BLACK)
            {
                for (CardValue cardValue : CardValue.values())
                {
                    if ((cardValue != CardValue.WILD)  && (cardValue != CardValue.DRAW_FOUR))
                    {
                        CardList.add(new Card(colorCards, cardValue));
                        if (cardValue != CardValue.ZERO) {
                            CardList.add(new Card(colorCards, cardValue));
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 4; i++)
        {
            CardList.add(new SpecialCard(CardColor.BLACK, CardValue.WILD));
            CardList.add(new SpecialCard(CardColor.BLACK, CardValue.DRAW_FOUR));
        }
    }
    public void ShuffleCardDeck()
    {
        Collections.shuffle(CardList);
    }
    public UnoCards DrawCard()
    {
        if (CardList.isEmpty())
        {
            CardList.addAll(this.CurrentGame.PlayedCards);
            ShuffleCardDeck();
            System.out.println("Reshuffle all Already Played Cards");
        }
        return CardList.remove(CardList.size() - 1);
    }
    public void AddCard(UnoCards card)
    {
        CardList.add(card);
    }
    private void PrintCards()
    {
        for (UnoCards cards :CardList)
        {
            System.out.printf("%s_%s", cards.GetColor(), cards.GetValue());
        }
    }
}
