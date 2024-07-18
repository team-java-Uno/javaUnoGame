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
                    System.out.println("please enter only numbers that are in the required Range");
                }
            }
            else
            {
                System.out.println("please enter only numbers that are in the required Range");
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
                    System.out.println("please enter only a valid answer");
                }
            }
            else
            {
                System.out.println("Please enter only letters");
                scanner.next();
            }
        }
        return userInput;
    }
}