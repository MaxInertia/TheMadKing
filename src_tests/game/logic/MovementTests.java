package game.logic;

import game.pieces.Type;
import org.junit.Before;
import org.junit.Test;
import util.TestUtilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 *
 */
public class MovementTests {

 //   Game game;
    Board board;

    @Before
    public void before() {
        board = new Board();
        TestUtilities.printBoard(board.getCells());
    }

    @Test
    public void kingJump() {
        assertTrue(Movement.checkIfValid(board.getCells(),0,2,2,2));
    }

    @Test
    public void kingJump_blockedByDragon() {
        assertTrue(Movement.checkIfValid(board.getCells(),1,1,1,0));
        board.performMove(1,1,1,0);
        TestUtilities.printBoard(board.getCells());

        assertTrue(Movement.checkIfValid(board.getCells(),3,2,2,2));
        board.performMove(3,2,2,2);
        TestUtilities.printBoard(board.getCells());

        assertFalse(Movement.checkIfValid(board.getCells(),0,2,2,2));
        board.performMove(0,2,2,2);
        TestUtilities.printBoard(board.getCells());
    }

    @Test
    public void kingLateral() {
        assertTrue(Movement.checkIfValid(board.getCells(),0,2,0,3));
    }

    @Test
    public void kingJump_DragonLateral() {
        assertTrue(Movement.checkIfValid(board.getCells(),0,2,2,2));
        board.performMove(0,2,2,2);
        TestUtilities.printBoard(board.getCells());

        assertTrue(Movement.checkIfValid(board.getCells(),3,0,2,0));
        board.performMove(3,0,2,0);
        TestUtilities.printBoard(board.getCells());
    }

    @Test
    public void guardLateral() {
        assertTrue(Movement.checkIfValid(board.getCells(),1,1,1,0));
    }

    @Test
    public void guardLateral_DragonDiagonal() {
        assertTrue(Movement.checkIfValid(board.getCells(),1,1,1,0));
        board.performMove(1,1,1,1);
        TestUtilities.printBoard(board.getCells());

        assertTrue(Movement.checkIfValid(board.getCells(),3,0,2,1));
        board.performMove(0,2,2,2);
        TestUtilities.printBoard(board.getCells());
    }

    @Test
    public void kingJump_DragonLateral_KingJump() {
        assertTrue(Movement.checkIfValid(board.getCells(),0,2,2,2));
        board.performMove(0,2,2,2);
        TestUtilities.printBoard(board.getCells());

        assertTrue(Movement.checkIfValid(board.getCells(),3,0,2,1));
        board.performMove(3,0,2,1);
        TestUtilities.printBoard(board.getCells());

        assertTrue(Movement.checkIfValid(board.getCells(),2,2,0,2));
        board.performMove(2,2,0,2);
        TestUtilities.printBoard(board.getCells());
    }

    @Test
    public void kingKillDragon() {
        assertTrue(Movement.checkIfValid(board.getCells(),0,2,2,2));
        board.performMove(0,2,2,2);
        TestUtilities.printBoard(board.getCells());

        assertTrue(Movement.checkIfValid(board.getCells(),3, 1, 2, 1));
        board.performMove(3, 1, 2, 1);
        TestUtilities.printBoard(board.getCells());

        assertTrue(Movement.checkIfValid(board.getCells(),2, 2, 2, 1));
        board.performMove(2, 2, 2, 1);
        TestUtilities.printBoard(board.getCells());
    }

    @Test
    public void guardKillDragon() {
        assertTrue(Movement.checkIfValid(board.getCells(),0,2,2,2));
        board.performMove(0,2,2,2);
        TestUtilities.printBoard(board.getCells());

        assertTrue(Movement.checkIfValid(board.getCells(),3, 1, 2, 1));
        board.performMove(3,1,2,1);
        TestUtilities.printBoard(board.getCells());

        assertTrue(Movement.checkIfValid(board.getCells(),1, 1, 2, 1));
        board.performMove(1,1,2,1);
        TestUtilities.printBoard(board.getCells());

    }

    @Test
    public void dragonConvertGuard() {
        assertTrue(Movement.checkIfValid(board.getCells(),1,2,2,2));
        board.performMove(1,2,2,2);
        TestUtilities.printBoard(board.getCells());

        assertTrue(Movement.checkIfValid(board.getCells(),3,1,2,1));
        board.performMove(3,1,2,1);
        TestUtilities.printBoard(board.getCells());

        assertTrue(Movement.checkIfValid(board.getCells(),0,2,0,3));
        board.performMove(0,2,0,3);
        TestUtilities.printBoard(board.getCells());

        assertTrue(Movement.checkIfValid(board.getCells(),3,3,2,3));
        board.performMove(3,3,2,3);
        TestUtilities.printBoard(board.getCells());

        assertEquals(Type.DRAGON, board.getCells()[2][2].getType());
    }
}