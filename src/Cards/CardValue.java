package Cards;

public enum CardValue
{
    ZERO(0), ONE(1), TWO(2), THREE(3),
    FOUR(4), FIVE(5), SIX(6),
    SEVEN(7), EIGHT(8), NINE(9),
    SKIP(20), REVERSE(20), DRAW_TWO(20),
    WILD(50), DRAW_FOUR(50);
    protected int cardPoints;
    CardValue(int cardPoints)
    {
        this.cardPoints = cardPoints;
    }
    public int GetCardPoints()
    {
        return cardPoints;
    }
}
