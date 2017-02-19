package game_logic;

import game_logic.pieces.Piece;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class GameTest {

    Game game;

    @BeforeEach
    void before() {
        game = new Game();
        game.printBoard();
    }

    @AfterEach
    void afterEach() {
        game.printBoard();
    }

    @Test
    void kingJump() {
        assertTrue(game.turnMove(2,4,2,2));
    }

    @Test
    void kingLateral() {
        assertTrue(game.turnMove(2,4,1,4));
    }

    @Test
    void kingJump_DragonLateral() {
        assertTrue(game.turnMove(2,4,2,2));
        game.printBoard();
        assertTrue(game.turnMove(1,1,1,2));
    }

    @Test
    void guardLateral() {
        assertTrue(game.turnMove(2,3,2,2));
    }

    @Test
    void guardLateral_DragonDiagonal() {
        assertTrue(game.turnMove(2,3,2,2));
        game.printBoard();
        assertTrue(game.turnMove(2,1,3,2));
    }

    @Test
    void kingJump_DragonLateral_KingJump() {
        assertTrue(game.turnMove(2,4,2,2));
        game.printBoard();
        assertTrue(game.turnMove(1,1,1,2));
        game.printBoard();
        assertTrue(game.turnMove(2,2,2,4));
    }

    @Test
    void kingkillDragon() {
        assertTrue(game.turnMove(2, 4, 2, 2));
        game.printBoard();
        assertTrue(game.turnMove(1, 1, 1, 2));
        game.printBoard();
        assertTrue(game.turnMove(2, 2, 1, 2));
    }

    @Test
    void guardkillDragon() {
        assertTrue(game.turnMove(2, 4, 2, 2));
        game.printBoard();
        assertTrue(game.turnMove(1, 1, 1, 2));
        game.printBoard();
        assertTrue(game.turnMove(1, 3, 1, 2));
    }

    @Test
    void dragonConvertGuard() {
        assertTrue(game.turnMove(2,3,2,2));
        game.printBoard();
        assertTrue(game.turnMove(1,1,1,2));
        game.printBoard();
        assertTrue(game.turnMove(3,3,3,4));
        game.printBoard();
        assertTrue(game.turnMove(3,1,3,2));

        assertEquals(Piece.Type.DRAGON, Board.instance.getBoard()[2][2].getType());
    }
}