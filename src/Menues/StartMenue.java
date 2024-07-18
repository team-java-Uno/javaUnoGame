package Menues;
import Players.AI;
import Players.Player;


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
        System.out.println("Please enter how many players are playing.");
        System.out.println("Remember that at least 2 Players are needed to Play and that no more than 10 Players can play at the same time");
        System.out.print("Number of Players: \n");
        while (isSelecting)
        {
            if (scanner.hasNextInt())
            {
                int playerAmount = scanner.nextInt();
                if ((playerAmount <= 10) && (playerAmount > 1))
                {
                    for (int i = 1; i <= playerAmount; i++)
                    {
                        System.out.println("Players.Player: " + i + ". Choose your Name :");
                        String playerName = scanner.next();
                        PlayerList.add(new Player(i , playerName, false));
                    }
                    isSelecting = false;
                }
                else
                {
                    System.out.println("please enter only Numbers in range 2-10");
                }
            }
            else
            {
                System.out.println("Please enter only Numbers");
                scanner.next();
            }
        }
        return PlayerList;
    }
    public List<Player> initAI(InputMenue inputMenue)
    {
        System.out.println("How many AI Enemies?");
        int amountAI = inputMenue.CheckUserInput(0, 3);

        for (int i = 1; i <= amountAI; i++)
        {
            PlayerList.add(new AI(PlayerList.size()+i, "AI"+i, true));
        }
        return PlayerList;
    }

}

