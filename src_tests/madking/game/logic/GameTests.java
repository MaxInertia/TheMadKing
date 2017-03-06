package madking.game.logic;

import madking.game.pieces.Piece;
import madking.game.pieces.Type;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by MaxInertia on 2017-03-05.
 */
public class GameTests {

    @Test
    public void testKingSurrounded_FalsePositive() {
        Board board = new Board();
        board.cells[2][2] = new Piece(Type.KING);
        board.cells[1][2] = new Piece(Type.GUARD);
        board.cells[3][2] = new Piece(Type.GUARD);
        board.cells[2][1] = new Piece(Type.GUARD);
        board.cells[2][3] = new Piece(Type.GUARD);
        board.cells[0][0] = new Piece(Type.DRAGON);

        Game game = new Game();
        Game.TestHook testHook = game.new TestHook();

        testHook.setBoard(board);
        assertFalse(testHook.callCheckIfKingSurrounded());
    }

}
