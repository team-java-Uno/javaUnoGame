package Cards;

public enum CardColor
{
    RED("\u001B[31m"),
    BLUE("\u001B[34m"),
    YELLOW ("\u001B[33m"),
    GREEN ("\u001B[32m"),
    BLACK ("\u001B[30m");
    protected String cardColor;
    protected String RESET = "\u001B[0m";
    CardColor(String cardColor)
    {
        this.cardColor = cardColor;
    }
    public String GetCardColor()
    {
        return cardColor;
    }
    public String GetColoredString(String text)
    {
        return cardColor + text + RESET;
    }
}

