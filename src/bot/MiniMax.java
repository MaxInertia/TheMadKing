package bot;

import bot.heuristic.Heuristic;
import com.sun.istack.internal.NotNull;
import game.history.Move;
import game.pieces.Dragon;
import game.pieces.Guard;
import game.pieces.King;
import game.pieces.Piece;
import org.junit.Test;
import views.game_view.Constants;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.junit.Assert.assertTrue;

/**
 * @author Dorian Thiessen
 */
public class MiniMax {

    private Piece board[][];
    private Heuristic heuristic;
    private int depthLimit;

    MiniMax(int depthLimit, Heuristic heuristic) {
        this.heuristic = heuristic;
        this.depthLimit = depthLimit;
    }

    /**
     * Determines the move that the bot will make.
     *
     * pre-Conditions: board is not null.
     * post-Conditions: none.
     *
     * @return The bots move choice.
     */
    Move chooseMove(@NotNull Piece[][] board) {
        this.board = board;

        Edge bestEdge = search(null,0,true);
        return bestEdge.getMove();
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
     * @param isMax True if MAX, false if MIN. When calling this method from chooseMove() use false.
     * @return The heuristic value of the board that results from the move contained in edge.
     */
    private Edge search(Edge edge, int depth, boolean isMax) {
        if(edge != null) applyMove(edge, board);

        if(depth == depthLimit) {
            assert edge != null;
            edge.setvalue( heuristic.valueOf(board) );
            undoMove(edge, board);
            return edge;
        }

        final Edge[] bestEdge = new Edge[1];

        // MAX TURN
        if(isMax) {
            bestEdge[0].setvalue(Float.MIN_VALUE);
            ArrayList<Edge> edges = generateEdges(isMax);
            edges.forEach(e -> {
                Edge maxEdge = search(e, depth+1, false);
                if(max(maxEdge.value, bestEdge[0].value) == maxEdge.value) {
                    bestEdge[0] = maxEdge;
                }
            });
        }

        // MIN TURN
        else {
            bestEdge[0].setvalue(Float.MIN_VALUE);
            ArrayList<Edge> edges = generateEdges(isMax);
            edges.forEach(e -> {
                Edge minEdge = search(e, depth+1, false);
                if(min(minEdge.value, bestEdge[0].value) == minEdge.value) {
                    bestEdge[0] = minEdge;
                }
            });
        }

        return bestEdge[0];
    }

    /**
     * Uses the move stored in the edge to modify the board.
     *
     * pre-Condition: edge != null
     * post-Condition: The coordinates of any pieces that changed type due to the move will be stored in the edge.
     *
     * @param edge The edge containing the move to apply to the board.
     */
    private static void applyMove(@NotNull Edge edge, @NotNull Piece[][] board) {
        //TODO: Modify 'board' with move stored in edge. Keep track of the coordinates of any pieces that changed type, store those in edge.
        int row = edge.getMove().getInitialCell().getRow();
        int col = edge.getMove().getInitialCell().getColumn();
        int rowFinal = edge.getMove().getFinalCell().getRow();
        int colFinal = edge.getMove().getFinalCell().getColumn();



        // Use Movement.checkIfValid(...) to determine if a given move is valid.
    }

    /**
     * Uses the data stored in the edge to undo the move it contains.
     *
     * pre-Conditions: edge != null
     * post-Condition: The board is in the state it was previous to calling applyMove(edge).
     *
     * @param edge The edge containing the move to undo.
     */
    private static void undoMove(@NotNull Edge edge, @NotNull Piece[][] board) {
    	// Get the first 4 values in the delta.
        int row = edge.getMove().getInitialCell().getRow();
        int col = edge.getMove().getInitialCell().getColumn();
        int rowFinal = edge.getMove().getFinalCell().getRow();
        int colFinal = edge.getMove().getFinalCell().getColumn();
        
        // First check to see if any pieces changed types (1 or more).
        // A dragon could have been removed, etc.
        
        Piece finalpiece = board[colFinal][rowFinal];
        Piece initpiece = board[colFinal][rowFinal];
        
        switch (finalpiece.getType()) {
	        case KING:
	        	// Was this the same as it was initially?
	        	if (initpiece.getType() == finalpiece.getType()) {
	        		
	        	} else {
	        		// The piece changed.
	        	}
	        	break;
	        case GUARD:
	        	
	        	break;
	        	
	        case DRAGON:
	        
	        break;
        }
        
        // Move the pieces back to where they were.
        
    }

    /**
     * Generates the list of moves the player can make, stored inside instances of the edge class.
     *
     * pre-Conditions: none.
     * post-Conditions: none.
     *
     * @param isMax True if MAX, false if MIN.
     * @return list of edges that contain each possible move at this turn.
     */
    private @NotNull ArrayList<Edge> generateEdges(boolean isMax) {
        if(isMax) {
            //TODO: Generate moves MAX's game pieces can make
        } else {
            //TODO: Generate moves MIN's game pieces can make
        }
        return null;
    }

    /** ----------------------------------------------------------------------------
     * JUnit tests encased in inner-class Unit_Tests.
     */
    public static class Unit_Tests {

        private Piece[][] constantBoard;
        private Piece[][] board;
        int r0, c0;
        int rF, cF;
        Edge edge;

        /** --------------------------------------
         * Test Case 1: Move piece, no changed pieces.
         *
         *     0    - - K - -           - - K - -
         *     1    - G G G -           - G - G -
         *  R  2    - - - - -    -->    - - G - -
         *     3    D D D D D           D D D D D
         *     4    - - - - -           - - - - -
         *
         *          0 1 2 3 4           0 1 2 3 4
         *              C                   C
         */
        private void setup_1() {
            board = new Piece[5][5];
            board[0][2] = new King();
            board[1][1] = new Guard();
            board[1][2] = new Guard();
            board[1][3] = new Guard();
            board[3][0] = new Dragon();
            board[3][1] = new Dragon();
            board[3][2] = new Dragon();
            board[3][3] = new Dragon();
            board[3][4] = new Dragon();
            constantBoard = duplicateBoard(board);
            r0 = 1; c0 = 2; rF = 2; cF = 2;
            edge = new Edge(r0,c0,rF,cF);
        }
        @Test
        public void testApplyMove_1() {
            setup_1();
            applyMove(edge, board);
            compare(r0,c0,rF,cF);
        }
        @Test
        public void testUndoMove_1() {
            setup_1();
            undoMove(edge, board);
            compare(r0,c0,rF,cF);
        }
        @Test
        public void testInverse_1() {
            setup_1();
            applyMove(edge, board);
            undoMove(edge,board);
            assertSame(board,constantBoard);
        }

        /** --------------------------------------
         * Test Case 2: Dragon moves, Guard surrounded by three Dragons is converted.
         *
         *     0    - - - - -           - - - - -
         *     1    - - - - -           - - - - -
         *  R  2    - D G - -    -->    - D D D -
         *     3    - - D D -           - - D - -
         *     4    - - - - -           - - - - -
         *
         *          0 1 2 3 4           0 1 2 3 4
         *              C                   C
         */
        private void setup_2() {
            board = new Piece[5][5];
            board[2][2] = new Guard();
            board[2][1] = new Dragon();
            board[3][2] = new Dragon();
            board[3][3] = new Dragon();
            constantBoard = duplicateBoard(board);
            r0 = 3; c0 = 3; rF = 2; cF = 3;
            edge = new Edge(r0,c0,rF,cF);
        }
        @Test
        public void testApplyMove_2() {
            setup_2();
            applyMove(edge, board);
            compare(r0,c0,rF,cF);
        }
        @Test
        public void testUndoMove_2() {
            setup_2();
            undoMove(edge, board);
            compare(r0,c0,rF,cF);
        }
        @Test
        public void testInverse_2() {
            setup_2();
            applyMove(edge, board);
            undoMove(edge,board);
            assertSame(board,constantBoard);
        }

        /** --------------------------------------
         * Test Case 3: Guard moves into three Dragons, converts into Dragon.
         *
         *     0    - - - - -           - - - - -
         *     1    - - G - -           - - - - -
         *  R  2    - D - D -    -->    - D D D -
         *     3    - - D - -           - - D - -
         *     4    - - - - -           - - - - -
         *
         *          0 1 2 3 4           0 1 2 3 4
         *              C                   C
         */
        private void setup_3() {
            board = new Piece[5][5];
            board[1][2] = new Guard();
            board[2][1] = new Dragon();
            board[3][2] = new Dragon();
            board[2][3] = new Dragon();
            constantBoard = duplicateBoard(board);
            r0 = 3; c0 = 3; rF = 2; cF = 3;
            edge = new Edge(r0,c0,rF,cF);
        }
        @Test
        public void testApplyMove_3() {
            setup_3();
            applyMove(edge, board);
            compare(r0,c0,rF,cF);
        }
        @Test
        public void testUndoMove_3() {
            setup_3();
            undoMove(edge, board);
            compare(r0,c0,rF,cF);
        }
        @Test
        public void testInverse_3() {
            setup_3();
            applyMove(edge,board);
            undoMove(edge, board);
            compare(r0,c0,rF,cF);
        }

        /** --------------------------------------
         * Test Case 4: Guard captures Dragon.
         *
         *     0    - - - - -           - - - - -
         *     1    - - G - -           - - - - -
         *  R  2    - G D - -    -->    - G G - -
         *     3    - - - - -           - - - - -
         *     4    - - - - -           - - - - -
         *
         *          0 1 2 3 4           0 1 2 3 4
         *              C                   C
         */
        private void setup_4() {
            board = new Piece[5][5];
            board[1][2] = new Guard();
            board[2][1] = new Dragon();
            board[3][2] = new Dragon();
            board[2][3] = new Dragon();
            constantBoard = duplicateBoard(board);
            r0 = 1; c0 = 2; rF = 2; cF = 2;
            edge = new Edge(r0,c0,rF,cF);
        }
        @Test
        public void testApplyMove_4() {
            setup_4();
            applyMove(edge, board);
            compare(r0,c0,rF,cF);
        }
        @Test
        public void testUndoMove_4() {
            setup_4();
            undoMove(edge, board);
            compare(r0,c0,rF,cF);
        }
        @Test
        public void testInverse_4() {
            setup_4();
            applyMove(edge, board);
            undoMove(edge, board);
            compare(r0,c0,rF,cF);
        }

        // --------------------------------------------------------

        private Piece[][] duplicateBoard(Piece[][] board) {
            Piece[][] duplicate = new Piece[Constants.COLUMN_ROW_COUNT][Constants.COLUMN_ROW_COUNT];
            for(int row=0; row < Constants.COLUMN_ROW_COUNT; row++) {
                for(int col=0; col < Constants.COLUMN_ROW_COUNT; col++) {
                    if(board[row][col]==null) continue;
                    switch(board[row][col].getType()) {
                        case DRAGON:
                            duplicate[row][col] = new Dragon(row,col);
                            break;
                        case GUARD:
                            duplicate[row][col] = new Guard(row,col);
                            break;
                        case KING:
                            duplicate[row][col] = new King(row,col);
                            break;
                    }
                }
            }
            return duplicate;
        }

        private void compare(int r0, int c0, int rF, int cF) {
            for(int row=0; row < Constants.COLUMN_ROW_COUNT; row++) {
                for(int col=0; col < Constants.COLUMN_ROW_COUNT; col++) {
                    if( (row==r0 && col==c0) || (row==rF && col==cF) ) {
                        assertTrue(
                                "Expected change was not observed at r"+row+"c"+col,
                                ( board[row][col]==null && constantBoard[row][col]!=null )
                                        || ( board[row][col]!=null && constantBoard[row][col]==null )
                        );
                    } else {
                        assertTrue(
                                "An unexpected change has occurred at r"+row+"c"+col,
                                board[row][col]==null && constantBoard[row][col]==null ||
                                        board[row][col].getType().equals(constantBoard[row][col].getType())
                        );
                    }
                }
            }
        }

        private void assertSame(Piece[][] b1, Piece[][] b2) {
            for(int row=0; row < Constants.COLUMN_ROW_COUNT; row++) {
                for(int col=0; col < Constants.COLUMN_ROW_COUNT; col++) {
                    assertTrue(
                            "Unexpected different between two boards at r"+row+"c"+col,
                            (b1[row][col]==null && b2[row][col]==null) ||
                                    ( (b1[row][col]!=null && b2[row][col]!=null) &&
                                    (b1[row][col].getType().equals(b2[row][col].getType())) )
                    );
                }
            }
        }
    }

}
