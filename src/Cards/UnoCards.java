package Cards;

public abstract class UnoCards {
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
}