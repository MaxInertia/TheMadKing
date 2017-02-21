package game;

import game.logic.Game;
import game.pieces.Piece;
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
        assertTrue(game.turnMove(0,2,2,2));
    }

    @Test
    void kingJump_blockedByDragon() {
        assertTrue(game.turnMove(1,1,1,0));
        game.printBoard();
        assertTrue(game.turnMove(3,2,2,2));
        game.printBoard();
        assertFalse(game.turnMove(0,2,2,2));
    }

    @Test
    void kingLateral() {
        assertTrue(game.turnMove(0,2,0,3));
    }

    @Test
    void kingJump_DragonLateral() {
        assertTrue(game.turnMove(0,2,2,2));
        game.printBoard();
        assertTrue(game.turnMove(3,0,2,0));
    }

    @Test
    void guardLateral() {
        assertTrue(game.turnMove(1,1,1,0));
    }

    @Test
    void guardLateral_DragonDiagonal() {
        assertTrue(game.turnMove(1,1,1,0));
        game.printBoard();
        assertTrue(game.turnMove(3,0,2,1));
    }

    @Test
    void kingJump_DragonLateral_KingJump() {
        assertTrue(game.turnMove(0,2,2,2));
        game.printBoard();
        assertTrue(game.turnMove(3,0,2,1));
        game.printBoard();
        assertTrue(game.turnMove(2,2,0,2));
    }

    @Test
    void kingKillDragon() {
        assertTrue(game.turnMove(0,2,2,2));
        game.printBoard();
        assertTrue(game.turnMove(3, 1, 2, 1));
        game.printBoard();
        assertTrue(game.turnMove(2, 2, 2, 1));
    }

    @Test
    void guardKillDragon() {
        assertTrue(game.turnMove(0,2,2,2));
        game.printBoard();
        assertTrue(game.turnMove(3, 1, 2, 1));
        game.printBoard();
        assertTrue(game.turnMove(1, 1, 2, 1));
    }

    @Test
    void dragonConvertGuard() {
        assertTrue(game.turnMove(1,2,2,2));
        game.printBoard();
        assertTrue(game.turnMove(3,1,2,1));
        game.printBoard();
        assertTrue(game.turnMove(0,2,0,3));
        game.printBoard();
        assertTrue(game.turnMove(3,3,2,3));

        assertEquals(Piece.Type.DRAGON, game.getBoard()[2][2].getType());
    }
}