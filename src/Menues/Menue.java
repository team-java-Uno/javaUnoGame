package Menues;
import Games.Game;
import java.util.Scanner;

public abstract class Menue
{
    private Game game;
    protected Scanner scanner = new Scanner(System.in);

    public Menue(Game game)
    {
        this.game = game;
    }
}
