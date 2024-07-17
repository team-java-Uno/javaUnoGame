package Menues;
import Games.Game;

import java.util.Scanner;

public class InputMenue extends Menue
{
    InputMenue(Game game)
    {
        super(game);
    }
    protected int CheckUserInput(int minSize, int maxSize)
    {
        int userInput = 0;
        boolean isCorrect = false;
        while (!isCorrect)
        {
            try
            {
                userInput = scanner.nextInt();
                if ((userInput <= maxSize) && (userInput >= minSize))
                {
                    isCorrect = true;
                }
                else
                {
                    System.out.println("please enter only numbers that are in the required Range");
                }
            }
            catch (Exception e)
            {
                System.out.println("please enter only numbers that are in the required Range");
            }
        }
        return userInput;
    }
}
