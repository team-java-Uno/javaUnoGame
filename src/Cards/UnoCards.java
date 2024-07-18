package Cards;

import Games.HasPoints;

public abstract class UnoCards implements HasPoints
{
    private CardColor cardColor;
    private CardValue cardValue;
    public int points;

    public UnoCards(CardColor cardColor, CardValue cardValue)
    {
        this.cardColor = cardColor;
        this.cardValue = cardValue;
    }

    public CardColor GetColor() {
        return cardColor;
    }

    public CardValue GetValue()
    {
        return cardValue;
    }
    public int GetPointValue()
    {
        this.points = cardValue.GetCardPoints();
        return points;
    }
    @Override
    public int PointValue(int points)
    {
        points = this.points;
        return  points;
    }
}