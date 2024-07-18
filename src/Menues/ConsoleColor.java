package Menues;

import Cards.CardColor;

public enum ConsoleColor
{
    RED("\u001B[31m"),
    BLUE("\u001B[34m"),
    RESET("\u001B[0m"),
    BLACK ("\u001B[30m"),
    GREEN ("\u001B[32m"),
    CYAN ("\u001B[36m"),
    YELLOW ("\u001B[33m");
    private final String code;
    ConsoleColor(String code) {
        this.code = code;
    }
    @Override
    public String toString()
    {
        return code;
    }
    public static void printColored(String text, ConsoleColor color)
    {
        System.out.println(color.toString() + text + ConsoleColor.RESET.toString());
    }
}
