package Test;

import java.util.ArrayList;
import Cards.CardColor;
import Cards.CardValue;
import Games.Game;
import Players.Player;

public class GameTest {

    @Test
    public void testDoubleCard() {
        Game game = new Game();
        Player player = new Player(6969, "Kyte", false);
        player.GetPlayerHand().add(new UniTestUnoCards(CardColor.RED, CardValue.ONE));
        player.GetPlayerHand().add(new UniTestUnoCards(CardColor.RED, CardValue.ONE));

        game.playerList = new ArrayList<>();
        game.playerList.add(player);
        game.currentPlayerIndex = 0;

        game.DoubleCard(new UniTestUnoCards(CardColor.RED, CardValue.ONE));

        assertTrue(player.GetPlayerHand().isEmpty());
    }
}
