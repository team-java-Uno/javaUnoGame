package Menues;
import Players.AI;
import Players.Player;


import java.util.InputMismatchException;
import java.util.List;


public class StartMenue extends Menue
{
    private final List<Player> PlayerList;
    public StartMenue(List<Player> playerList)
    {
        super();
        this.PlayerList = playerList;
    }
    public List<Player> initPlayer()
    {
        boolean isSelecting = true;
        ConsoleColor.printColored("Please enter how many players are playing.",ConsoleColor.CYAN);
        ConsoleColor.printColored("Remember that at least 1 Players is needed to Play and that no more than 10 Players can play at the same time",ConsoleColor.CYAN);
        ConsoleColor.printColored("Number of Players: \n",ConsoleColor.CYAN);
        while (isSelecting)
        {
            if (scanner.hasNextInt())
            {
                int playerAmount = scanner.nextInt();
                if ((playerAmount <= 10) && (playerAmount > 0))
                {
                    for (int i = 1; i <= playerAmount; i++)
                    {
                        ConsoleColor.printColored("Players.Player: " + i + ". Choose your Name :",ConsoleColor.CYAN);
                        String playerName = scanner.next();
                        PlayerList.add(new Player(i , playerName, false));
                    }
                    isSelecting = false;
                }
                if (playerAmount == 1)
                {
                    PlayerList.add(new AI(PlayerList.size()+1, "AI"+1, true));
                }
                else
                {
                   ConsoleColor.printColored("please enter only Numbers in range 2-10",ConsoleColor.CYAN);
                }
            }
            else
            {
                ConsoleColor.printColored("Please enter only Numbers",ConsoleColor.CYAN);
                scanner.next();
            }
        }
        return PlayerList;
    }
    public List<Player> initAI(InputMenue inputMenue)
    {
        try
        {
            ConsoleColor.printColored("How many AI Enemies?", ConsoleColor.CYAN);
            int amountAI = inputMenue.CheckUserInput(0, 3);

            for (int i = 1; i <= amountAI; i++)
            {
                PlayerList.add(new AI(PlayerList.size() + i, "AI" + i, true));
            }
        }
        catch (InputMismatchException e)
        {
            ConsoleColor.printColored("Invalid input. Please enter a number between 0 and 3.", ConsoleColor.RED);
        }
        catch (Exception e)
        {
            ConsoleColor.printColored("An unexpected error occurred: " + e.getMessage(), ConsoleColor.RED);
        }

        return PlayerList;
    }


}

