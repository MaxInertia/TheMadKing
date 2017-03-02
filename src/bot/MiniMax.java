package bot;

import bot.heuristic.Heuristic;
import com.sun.istack.internal.NotNull;
import game.history.Move;
import game.pieces.Piece;
import game.pieces.Piece.Type;

import org.junit.Test;
import views.game_view.Constants;

import java.util.ArrayList;

import static java.lang.Math.abs;
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

        if(board[rowFinal][colFinal]!=null) {
            edge.addChange(rowFinal,colFinal);
        }

        // Perform move
        board[rowFinal][colFinal] = board[row][col];

        // Check for Conversions
        for(int r=0; r<Constants.COLUMN_ROW_COUNT; r++) {
            for(int c=0; c<Constants.COLUMN_ROW_COUNT; c++) {
                if(board[r][c]!=null && board[r][c].getType().equals(Piece.Type.GUARD)) {
                    int numberSurrounding = 0;

                    // Check how many dragons surround that guard
                    if(r-1 >= 0) {
                        Piece piece =board[r-1][c];
                        if(piece!=null && (piece.getType()== Piece.Type.KING || piece.getType()== Piece.Type.GUARD) ) {
                            numberSurrounding++;
                        }
                    }
                    if(r+1 <= 4) {
                        Piece piece =board[r+1][c];
                        if(piece!=null && (piece.getType()== Piece.Type.KING || piece.getType()== Piece.Type.GUARD) ) {
                            numberSurrounding++;
                        }
                    }
                    if(c-1 >= 0) {
                        Piece piece =board[r][c-1];
                        if(piece!=null && (piece.getType()== Piece.Type.KING || piece.getType()== Piece.Type.GUARD) ) {
                            numberSurrounding++;
                        }
                    }
                    if(c+1 <= 4) {
                        Piece piece =board[r][c+1];
                        if(piece!=null && (piece.getType() == Piece.Type.KING || piece.getType()== Piece.Type.GUARD) ) {
                            numberSurrounding++;
                        }
                    }

                    // If surrounding dragons >= 3, convert dragon to guard
                    if(numberSurrounding>=3) {
                        board[r][c] = new Piece(Piece.Type.DRAGON);
                        edge.addChange(r,c);
                    }
                }
            }
        }
        
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
        
        // Remvoe the last to integers from the delta list.
        edge.removedelta();
        
        if (finalpiece.getType() == null) {
        	System.out.println("[MiniMax.java]\t Piece type was null.");
        } else {
        	switch (finalpiece.getType()) {
        		case KING:
		        	// Was this the same as it was initially?
		        	if (initpiece.getType() == finalpiece.getType()) {
		        		// It was the same type.
		        		board[row][col] = board[rowFinal][colFinal];
		        		board[rowFinal][colFinal] = null;
		        		
		        	} else {
		        		// The piece changed. This should never happen.
		        		//System.out.println("[MiniMax.java]\t King piece changed type (somehow?)");
		        		// The piece changed, was originally a dragon.
		        		
		        		if (edge.getChange(1) == null) {
		        			
		        		} else {
			        		board[row][col] = board[rowFinal][colFinal];
			        		board[rowFinal][colFinal].changeType(Type.DRAGON);
		        		}
		        		
		        	}
	        	break;
	        	case GUARD:
		        	// Was this the same as it was initially?
		        	if (initpiece.getType() == finalpiece.getType()) {
		        		// It was the same type.
		        		
		        		board[row][col] = board[rowFinal][colFinal];
		        		board[rowFinal][colFinal] = null;
		        		
		        	} else {
		        		// The piece changed, was originally a dragon.
		        		
		        		if (edge.getChange(1) == null) {
		        			
		        		} else {
			        		board[row][col] = board[rowFinal][colFinal];
			        		board[rowFinal][colFinal].changeType(Type.DRAGON);
		        		}
		        		
		        	}
	        	break;
	        	case DRAGON:
		        	// Was this the same as it was initially?
		        	if (initpiece.getType() == finalpiece.getType()) {
		        		// It was the same type, move piece back to where it started.
		        		//Type tempType = finalpiece.getType();
		        		
		        		board[row][col] = board[rowFinal][colFinal];
		        		board[row][col].changeType(Type.DRAGON);
		        		board[rowFinal][colFinal] = null;
		        		
		        	} else {
		        		// The piece changed, the original piece was a guard.
		        		board[row][col] = board[rowFinal][colFinal];
		        		board[rowFinal][colFinal] = null;	
		        		
		        		// Set the original piece to be a guard.
		        		board[row][col].changeType(Type.GUARD);
		        		
		        	}
	        	break;
        	}
        }
        
        
        
        // Move the pieces back to where they were.
        
    }

    public int[] getAdjacentCells(int r, int c) {
        int[] adjCells = new int[8];

        if(c+1 < 5) { adjCells[0] = r; adjCells[1] = c+1; }
        else adjCells[0] = -1;
        if(r-1 > 0) { adjCells[2] = r-1; adjCells[3] = c; }
        else adjCells[2] = -1;
        if(c-1 > 0) { adjCells[4] = r; adjCells[5] = c-1; }
        else adjCells[4] = -1;
        if(r+1 < 5) { adjCells[6] = r+1; adjCells[7] = c; }
        else adjCells[6] = -1;

        return adjCells;
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
        ArrayList<Edge> edges = new ArrayList<>();
        
        if(isMax) {
            //TODO: Generate moves MAX's game pieces can make
            for(int r=0; r<Constants.COLUMN_ROW_COUNT; r++) { // -- Always 25 iterations
                for(int c=0; c<Constants.COLUMN_ROW_COUNT; c++) { // -- Always 25 iterations

                    // TEAM HUMAN
                    if( board[r][c]!=null && (board[r][c].isHuman()) ) {
                        int[] primary_AdjCells = getAdjacentCells(r,c);

                        for(int i1 = 0, j1 = 1; i1<8; i1=i1+2, j1=j1+2) { // -- At most 4 iterations
                            if(primary_AdjCells[i1] == -1) continue; // CASE 0: At edge of board!
                            Piece primary_Piece = board[ primary_AdjCells[i1] ][ primary_AdjCells[j1] ];

                            // CASE 1: If piece is null, cell is empty and can be moved into.
                            if(primary_Piece==null) {
                                edges.add(new Edge(r, c, primary_AdjCells[i1], primary_AdjCells[j1]));
                            }

                            // CASE 2: If I'm a King, check if I can jump a Guard.
                            else if(board[r][c].getType().equals(Piece.Type.KING)) {
                                int rJump = 2*abs(r - primary_AdjCells[i1]) + r;
                                int cJump = 2*abs(c - primary_AdjCells[j1]) + c;
                                if(rJump>=0 && rJump<Constants.COLUMN_ROW_COUNT && cJump>=0 && cJump<Constants.COLUMN_ROW_COUNT && board[rJump][cJump]==null) {
                                    // Make sure King is not moving into group of three Dragons
                                    int[] secondary_AdjCells = getAdjacentCells(rJump, cJump);
                                    int surroundingDragons = 0;
                                    for(int i2=0, j2=1; i2<8; i2=i2+2, j2=j2+2) {
                                        Piece secondary_Piece = board[secondary_AdjCells[i2]][secondary_AdjCells[j2]];
                                        if(secondary_Piece!=null && !secondary_Piece.isHuman()) surroundingDragons++;
                                    }
                                    if(surroundingDragons<3) edges.add(new Edge(r, c, rJump, cJump));
                                }
                            }

                            // CASE 3: If piece is a Dragon, check if it can be captured.
                            else if(primary_Piece.getType()== Piece.Type.DRAGON) { // -- At most 4 iterations
                                int[] secondary_AdjCells = getAdjacentCells(primary_AdjCells[i1],primary_AdjCells[j1]);
                                int assistanceAvailable = 0;
                                for(int i2=0, j2=1; i2<8; i2=i2+2, j2=j2+2) {
                                    if(secondary_AdjCells[i2] == -1) continue;
                                    Piece secondary_Piece = board[secondary_AdjCells[i2]][secondary_AdjCells[j2]];
                                    if( secondary_Piece!=null && (secondary_Piece.getType().equals(Piece.Type.GUARD) || secondary_Piece.getType().equals(Piece.Type.KING)) ) {
                                        assistanceAvailable++;
                                        if(assistanceAvailable == 2) break;
                                    }
                                } //eol-2-secondary
                                if(assistanceAvailable == 2) {
                                    edges.add(new Edge(r, c, primary_AdjCells[i1], primary_AdjCells[j1]));
                                }
                            }
                        } //eol-1-primary
                        
                    }
                    
                    // TEAM DRAGON
                    else if (board[r][c] != null){
                        /*int[] primary_AdjCells = getSurroundingCells(r,c);
                        for(int i1 = 0, j1 = 1; i1<8; i1=i1+2, j1=j1+2) { // -- At most 4 iterations
                            if (primary_AdjCells[i1] == -1) continue; // CASE 0: At edge of board!
                        }*/
                    }
                    
                } //eol-c
            } // eol-r
            
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
            board[0][2] = new Piece(Piece.Type.KING);
            board[1][1] = new Piece(Piece.Type.GUARD);
            board[1][2] = new Piece(Piece.Type.GUARD);
            board[1][3] = new Piece(Piece.Type.GUARD);
            board[3][0] = new Piece(Piece.Type.DRAGON);
            board[3][1] = new Piece(Piece.Type.DRAGON);
            board[3][2] = new Piece(Piece.Type.DRAGON);
            board[3][3] = new Piece(Piece.Type.DRAGON);
            board[3][4] = new Piece(Piece.Type.DRAGON);
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
            board[2][2] = new Piece(Piece.Type.GUARD);
            board[2][1] = new Piece(Piece.Type.DRAGON);
            board[3][2] = new Piece(Piece.Type.DRAGON);
            board[3][3] = new Piece(Piece.Type.DRAGON);
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
            board[1][2] = new Piece(Piece.Type.GUARD);
            board[2][1] = new Piece(Piece.Type.DRAGON);
            board[3][2] = new Piece(Piece.Type.DRAGON);
            board[2][3] = new Piece(Piece.Type.DRAGON);
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
            board[1][2] = new Piece(Piece.Type.GUARD);
            board[2][1] = new Piece(Piece.Type.DRAGON);
            board[3][2] = new Piece(Piece.Type.DRAGON);
            board[2][3] = new Piece(Piece.Type.DRAGON);
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
                            duplicate[row][col] = new Piece(Piece.Type.DRAGON);
                            break;
                        case GUARD:
                            duplicate[row][col] = new Piece(Piece.Type.GUARD);;
                            break;
                        case KING:
                            duplicate[row][col] = new Piece(Piece.Type.KING);
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
