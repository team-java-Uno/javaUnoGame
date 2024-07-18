package Menues;

import java.util.List;

public class InputMenue extends Menue
{
    public InputMenue()
    {
        super();
    }
    public int CheckUserInput(int minSize, int maxSize)
    {
        int userInput = 0;
        boolean isCorrect = false;
        while (!isCorrect)
        {
            if(scanner.hasNextInt())
            {
                userInput = scanner.nextInt();
                if ((userInput <= maxSize) && (userInput >= minSize))
                {
                    isCorrect = true;
                }
                else
                {
                    ConsoleColor.printColored("please enter only numbers that are in the required Range",ConsoleColor.CYAN);
                }
            }
            else
            {
                ConsoleColor.printColored("please enter only numbers that are in the required Range",ConsoleColor.CYAN);
                scanner.next();
            }
        }
        return userInput;
    }
    public String CheckStringInput(List<String> RightInputList)
    {
        String userInput = "";
        boolean isCorrect = false;
        while (!isCorrect)
        {
            if (scanner.hasNext())
            {
                userInput = scanner.next();
                if (RightInputList.contains(userInput.toUpperCase()))
                {
                    isCorrect = true;
                }
                else
                {
                    ConsoleColor.printColored("please enter only a valid answer",ConsoleColor.CYAN);
                }
            }
            else
            {
                ConsoleColor.printColored("Please enter only letters",ConsoleColor.CYAN);
                scanner.next();
            }
        }
        return userInput;
    }
}