package bot;

import com.sun.istack.internal.NotNull;
import game.history.Move;
import game.logic.Movement;
import game.pieces.Piece;
import org.junit.Test;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * @author Dorian Thiessen
 */
public class MiniMax {

    private Piece board[][];
    private Heuristic heuristic;
    private int depthLimit;

    public MiniMax(int depthLimit, Heuristic heuristic) {
        this.heuristic = heuristic;
        this.depthLimit = depthLimit;
    }

    /**
     * Determines the move that the bot will make.
     *
     * pre-Conditions: none.
     * post-Conditions: none.
     *
     * @return The bots move choice.
     */
    public Move chooseMove(@NotNull Piece.Type[][] board) {
        return null;
    }

    /**
     * MiniMax implementation.
     *
     * pre-Conditions:
     * * depth >= 0
     * * depth == 0 if edge == null
     * post-Conditions: none
     *
     * @param edge The edge containing the move being evaluated. The edge is effectively an edge on the search tree
     *             which is traversed.
     * @param depth The depth of the edge that the edge leads to.
     * @param player True if MAX, false if MIN. When calling this method from chooseMove() use false.
     * @return The heuristic value of the board that results from the move contained in edge.
     */
    private double search(Edge edge, int depth, boolean player) {
        if(edge != null) applyMove(edge);

        if(depth == depthLimit) {
            double h = heuristic.valueOf(board);
            undoMove(edge);
            return h;
        }

        final double[] bestValue = new double[1];

        // MAX TURN
        if(player) {
            bestValue[0] = Integer.MIN_VALUE;
            ArrayList<Edge> deltas = generateEdges(player);
            deltas.forEach(d -> {
                double v = search(d, depth+1, false);
                bestValue[0] = max(v, bestValue[0]);
            });
        }

        // MIN TURN
        else {
            bestValue[0] = Integer.MAX_VALUE;
            ArrayList<Edge> deltas = generateEdges(player);
            deltas.forEach(d -> {
                double v = search(d, depth+1, true);
                bestValue[0] = min(v, bestValue[0]);
            });
        }

        return bestValue[0];
    }

    /**
     * Uses the move stored in the edge to modify the board.
     *
     * pre-Condition: edge != null
     * post-Condition: The coordinates of any pieces that changed type due to the move will be stored in the edge.
     *
     * @param edge The edge containing the move to apply to the board.
     */
    private void applyMove(@NotNull Edge edge) {
        //TODO: Modify 'board' with move stored in edge. Keep track of the coordinates of any pieces that changed type, store those in edge.
        int row = edge.getMove().getInitialCell().getRow();
        int col = edge.getMove().getInitialCell().getColumn();
        int rowFinal = edge.getMove().getFinalCell().getRow();
        int colFinal = edge.getMove().getFinalCell().getColumn();

        Movement.setBoard(board);
    }

    /**
     * Uses the data stored in the edge to undo the move it contains.
     *
     * pre-Conditions: edge != null
     * post-Condition: The board is in the state it was previous to calling applyMove(edge).
     *
     * @param edge The edge containing the move to undo.
     */
    private void undoMove(@NotNull Edge edge) {

    }

    /**
     * Generates the list of moves the player can make, stored inside instances of the edge class.
     *
     * pre-Conditions: none.
     * post-Conditions: none.
     *
     * @param player True if MAX, false if MIN.
     * @return list of edges that contain each possible move at this turn.
     */
    private @NotNull ArrayList<Edge> generateEdges(boolean player) {
        if(player) {
            //TODO: Generate moves MAX's game pieces can make
        } else {
            //TODO: Generate moves MIN's game pieces can make
        }
        return null;
    }

    /**
     * Dragon moves, Guard surrounded by three Dragons is converted
     */
    @Test
    public void testApplyMove_1() {

    }
    @Test
    public void testUndoMove_1() {

    }

    /**
     * Guard moves into three Dragons, converts into Dragon.
     */
    @Test
    public void testApplyMove_2() {

    }
    @Test
    public void testUndoMove_2() {

    }

    /**
     * Guard captures Dragon
     */
    @Test
    public void testApplyMove_3() {

    }
    @Test
    public void testUndoMove_3() {

    }

    /**
     * Move piece, no changed pieces.
     */
    @Test
    public void testApplyMove_4() {

    }
    @Test
    public void testUndoMove_4() {

    }

}
