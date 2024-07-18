package Games;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Unitests {

    @Test
    public void testCheckPlayerPoints() {
        Game game = new Game();
        // Assuming Player class has a constructor that accepts points as parameter
        Player player1 = new Player(400, "UNITESTER 1");
        Player player2 = new Player(600, "UNITESTER 2");
        game.PlayerList.add(player1);
        game.PlayerList.add(player2);

        boolean result = game.CheckPlayerPoints();
        assertTrue(result, "Expected true as one player has points >= 500");
    }
}