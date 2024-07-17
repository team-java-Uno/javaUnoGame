package Cards;

import Games.HasPoints;

public abstract class UnoCards implements HasPoints {
    private CardColor cardColor;
    private CardValue cardValue;

    public UnoCards(CardColor cardColor, CardValue cardValue) {
        this.cardColor = cardColor;
        this.cardValue = cardValue;
    }

    public CardColor GetColor() {
        return cardColor;
    }

    public CardValue GetValue() {
        return cardValue;
    }

    @Override
    public void PointValue(int points){
        points = cardValue.GetCardPoints();
    }
}