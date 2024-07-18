package Games;

import Cards.*;
import Menues.ConsoleColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck
{
    private final List<UnoCards> CardList;
    public CardDeck()
    {
        this.CardList = new ArrayList<>();
        InitCardDeck();
        ShuffleCardDeck();
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
            CardList.addAll(Game.playedCards);
            ShuffleCardDeck();
            ConsoleColor.printColored("Reshuffle all Already Played Cards",ConsoleColor.CYAN);
        }
        return CardList.remove(CardList.size() -1);
    }
    public void addCard(UnoCards card)
    {
        CardList.add(card);
    }
    public void clear() {
        CardList.clear();
    }
    public List<UnoCards> getCardList() {
        return CardList;
    }

}
