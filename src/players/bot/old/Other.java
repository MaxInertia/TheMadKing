package players.bot.old;

import static org.junit.Assert.assertTrue;

/**
 * Created by MaxInertia on 2017-03-02.
 */
public class Other {

    /**
     * Uses the move stored in the edge to modify the board.
     *
     * pre-Condition: edge != null
     * post-Condition: The coordinates of any pieces that changed type due to the move will be stored in the edge.
     *
     * @param edge The edge containing the move to apply to the board.
     */
    /*private static void applyMove(@NotNull Edge edge, @NotNull Piece[][] board) {
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
        board[row][col] = null;

        // Check for Conversions
        for(int r = 0; r< Constants.COLUMN_ROW_COUNT; r++) {
            for(int c=0; c<Constants.COLUMN_ROW_COUNT; c++) {
                if(board[r][c]!=null && board[r][c].getType().equals(Type.GUARD)) {
                    int numberSurrounding = 0;

                    // Check how many dragons surround that guard
                    if(r-1 >= 0) {
                        Piece piece =board[r-1][c];
                        //if(piece!=null && (piece.getType()== Type.KING || piece.getType()== Type.GUARD) ) {
                        if(piece!=null && (piece.getType()== Type.DRAGON) ) {
                            numberSurrounding++;
                        }
                    }
                    if(r+1 <= 4) {
                        Piece piece =board[r+1][c];
                        //if(piece!=null && (piece.getType()== Type.KING || piece.getType()== Type.GUARD) ) {
                        if(piece!=null && (piece.getType()== Type.DRAGON) ) {
                            numberSurrounding++;
                        }
                    }
                    if(c-1 >= 0) {
                        Piece piece =board[r][c-1];
                        //if(piece!=null && (piece.getType()== Type.KING || piece.getType()== Type.GUARD) ) {
                        if(piece!=null && (piece.getType()== Type.DRAGON) ) {
                            numberSurrounding++;
                        }
                    }
                    if(c+1 <= 4) {
                        Piece piece =board[r][c+1];
                        //if(piece!=null && (piece.getType() == Type.KING || piece.getType()== Type.GUARD) ) {
                        if(piece!=null && (piece.getType()== Type.DRAGON) ) {
                            numberSurrounding++;
                        }
                    }

                    // If surrounding dragons >= 3, convert dragon to guard
                    if(numberSurrounding>=3) {
                        board[r][c].changeType(Type.DRAGON);
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
    /*private static void undoMove(@NotNull Edge edge, @NotNull Piece[][] board) {
        System.out.println("[MiniMax.java]\t -----UNDO STARTED-----");
        // Get the first 4 values in the delta.
        int row = edge.getMove().getInitialCell().getRow();
        int col = edge.getMove().getInitialCell().getColumn();
        System.out.println("[MiniMax.java]\t"+"Initial position = ("+row+","+col+")");
        int rowFinal = edge.getMove().getFinalCell().getRow();
        int colFinal = edge.getMove().getFinalCell().getColumn();
        System.out.println("[MiniMax.java]\t"+"Final position = ("+rowFinal+","+colFinal+")");

        // First check to see if any pieces changed types (1 or more).
        // A dragon could have been removed, etc.

        //Piece piece = board[colFinal][rowFinal];
        //Piece initpiece = board[col][row];

        ArrayList<Integer> changes = edge.getChange();
        int[] adj = getAdjacentCells(rowFinal, colFinal);

        if(changes!=null) {
            System.out.println("Change count: "+changes.size());
            for(int i=0; i<1; i++) {
                System.out.println(i+": "+changes.get(i));
            }
            for (int i = 0; (i+1) < changes.size(); i += 2) {
                if (changes.get(i) == rowFinal && changes.get(i + 1) == colFinal) {
                    //if(board[rowFinal][colFinal]==null) break;
                    if (board[rowFinal][colFinal].getType().equals(Type.DRAGON)) {
                        int dragons = 0;

                        for(int r=0, c=1; c<adj.length; r+=2, c+=2) {
                            if(board[adj[r]][adj[c]]!=null && !board[adj[r]][adj[c]].isHuman()) {
                                dragons++;
                            }
                        }
                        if(dragons >= 3) {
                            board[rowFinal][colFinal].changeType(Type.GUARD);
                        }
                    }
                }
            }
        }

        board[row][col] = board[rowFinal][colFinal];
        board[rowFinal][colFinal] = null;

        if(changes==null) return;
        for(int i=0; (i+1)<changes.size(); i=i+2) {

            if(changes.get(i)==rowFinal && changes.get(i+1)==colFinal) {
                if(board[rowFinal][colFinal]!= null){
                    if(board[row][col].getType().equals(Type.DRAGON)){
                        board[rowFinal][colFinal].changeType(Type.GUARD);
                    } else {
                        board[rowFinal][colFinal].changeType(Type.DRAGON);
                    }
                } else {
                    int dragons = 0;
                    for(int r=0, c=1; c<adj.length; r+=2, c+=2) {
                        if(adj[r] == -1) continue;
                        if(board[adj[r]][adj[c]]!=null && !board[adj[r]][adj[c]].isHuman()) {
                            dragons++;
                        }
                    }
                    if(dragons < 3) {
                        System.out.println("ASDKAJSVKDUASDGSD.... Dragons: "+dragons);
                        board[rowFinal][colFinal] = new Piece(Type.DRAGON);
                    }
                }
            } else {
                Type type = board[ changes.get(i) ][ changes.get(i+1) ].getType();
                if(type.equals(Type.DRAGON)) {
                    board[ changes.get(i) ][ changes.get(i+1) ] = new Piece(Type.GUARD);
                } else {
                    board[ changes.get(i) ][ changes.get(i+1) ] = new Piece(Type.DRAGON);
                }
            }
        }
        /*
        if (finalpiece == null) {
        	System.out.println("[MiniMax.java]\t Piece type was null (final).");
        	finalpiece = new Piece (Type.TEMP);
        }
        if (initpiece == null) {
        	System.out.println("[MiniMax.java]\t Piece type was null (init).");
        	initpiece = new Piece (Type.TEMP);        	
        } 

   		System.out.println("[MiniMax.java]\t Final Piece type was "+finalpiece.getType());
   		System.out.println("[MiniMax.java]\t Init Piece type was "+initpiece.getType());
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
		        			System.out.println("[MiniMax.java]\t Guard case.");
		        		} else {
		        			// No piece was changed.
		        			System.out.println("[MiniMax.java]\t "+edge.getChange(1)[0]);
		        			System.out.println("[MiniMax.java]\t "+edge.getChange(1)[1]);
		        			
			        		board[row][col] = board[rowFinal][colFinal];
			        		board[rowFinal][colFinal] = null;
		        		}
		        		
		        	}
	        	break;
	        	case DRAGON:
		        	// Was this the same as it was initially?
		        	if (initpiece.getType() == finalpiece.getType()) {
		        		// It was the same type, move piece back to where it started.
		        		//Type tempType = finalpiece.getType();
		        		
		        		board[row][col] = board[rowFinal][colFinal];
		        		System.out.println(board[rowFinal][colFinal]);
		        		board[row][col] = new Piece (Type.DRAGON);
		        		board[rowFinal][colFinal] = null;
		        		
		        	} else {
		        		// The piece changed, the original piece was a guard.
		        		
		        		if (edge.getChange(1) == null) {
		        			System.out.println("[MiniMax.java]\t Dragon case.");
		        		} else {
		        			// No piece was changed.
			        		// Set the original piece to be a guard.
			        		board[row][col] = new Piece(Type.GUARD);
			        		
			        		board[rowFinal][colFinal] = null;
		        		}
		        		
		        	}
	        	break;
	        	case TEMP:
	        		System.out.println("[MiniMax.java]\t Temp Piece shouldn't be used.");
	        	break;
        	}
        // Move the pieces back to where they were.
       	//System.out.println("[MiniMax.java]\t Data at init "+row+"r"+col+"c"+board[row][col].getType());
       	//System.out.println("[MiniMax.java]\t Data at final "+rowFinal+"r"+colFinal+"c"+board[rowFinal][colFinal].getType());

        System.out.println("[MiniMax.java]\t -----UNDO ENDING-----");
    }

    /** ----------------------------------------------------------------------------
     * JUnit util encased in inner-class Unit_Tests.
     */
    /*public static class Unit_Tests {

        BoardCheckSum sum = new BoardCheckSum();

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
        /*private void setup_1() {
            board = new Piece[5][5];
            board[0][2] = new Piece(Type.KING);
            board[1][1] = new Piece(Type.GUARD);
            board[1][2] = new Piece(Type.GUARD);
            board[1][3] = new Piece(Type.GUARD);
            board[3][0] = new Piece(Type.DRAGON);
            board[3][1] = new Piece(Type.DRAGON);
            board[3][2] = new Piece(Type.DRAGON);
            board[3][3] = new Piece(Type.DRAGON);
            board[3][4] = new Piece(Type.DRAGON);
            constantBoard = duplicateBoard(board);
            r0 = 1; c0 = 2; rF = 2; cF = 2;
            edge = new Edge(r0,c0,rF,cF);
        }
        @Test
        public void testApplyMove_1() {
            setup_1();
            printBoard(board);
            applyMove(edge, board);
            printBoard(board);
            //compare(r0,c0,rF,cF);
        }
        //@Test
        /*public void testUndoMove_1() {
            board = new Piece[5][5];
            board[0][2] = new Piece(Type.KING);
            board[1][1] = new Piece(Type.GUARD);
            board[2][2] = new Piece(Type.GUARD);
            board[1][3] = new Piece(Type.GUARD);
            board[3][0] = new Piece(Type.DRAGON);
            board[3][1] = new Piece(Type.DRAGON);
            board[3][2] = new Piece(Type.DRAGON);
            board[3][3] = new Piece(Type.DRAGON);
            board[3][4] = new Piece(Type.DRAGON);

            constantBoard = duplicateBoard(board);
            r0 = 1; c0 = 2; rF = 2; cF = 2;
            edge = new Edge(r0,c0,rF,cF);

            printBoard(board);
            undoMove(edge, board);
            printBoard(board);
            //compare(rF,cF,r0,c0);
        }
        @Test
        public void testInverse_1() {
            setup_1();
            printBoard(board);
            applyMove(edge, board);
            printBoard(board);
            undoMove(edge,board);
            printBoard(board);
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
        /*private void setup_2() {
            board = new Piece[5][5];
            board[2][2] = new Piece(Type.GUARD);
            board[2][1] = new Piece(Type.DRAGON);
            board[3][2] = new Piece(Type.DRAGON);
            board[3][3] = new Piece(Type.DRAGON);
            constantBoard = duplicateBoard(board);
            r0 = 3; c0 = 3; rF = 2; cF = 3;
            edge = new Edge(r0,c0,rF,cF);
        }
        @Test
        public void testApplyMove_2() {
            setup_2();
            applyMove(edge, board);
            //compare(r0,c0,rF,cF);
        }
        //@Test
        public void testUndoMove_2() {
            setup_2();
            undoMove(edge, board);
            //compare(r0,c0,rF,cF);
        }
        @Test
        public void testInverse_2() {
            setup_2();
            printBoard(board);
            applyMove(edge, board);
            printBoard(board);
            undoMove(edge,board);
            printBoard(board);
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
        /*private void setup_3() {
            board = new Piece[5][5];
            board[1][2] = new Piece(Type.GUARD);
            board[2][1] = new Piece(Type.DRAGON);
            board[3][2] = new Piece(Type.DRAGON);
            board[2][3] = new Piece(Type.DRAGON);
            constantBoard = duplicateBoard(board);
            r0 = 1; c0 = 2; rF = 2; cF = 2;
            edge = new Edge(r0,c0,rF,cF);
        }
        @Test
        public void testApplyMove_3() {
            setup_3();
            applyMove(edge, board);
            //compare(r0,c0,rF,cF);
        }
        //@Test
        public void testUndoMove_3() {
            setup_3();
            undoMove(edge, board);
            //compare(r0,c0,rF,cF);
        }
        @Test
        public void testInverse_3() {
            setup_3();
            printBoard(board);
            applyMove(edge, board);
            printBoard(board);
            undoMove(edge,board);
            printBoard(board);
            assertSame(board,constantBoard);
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
        /*private void setup_4() {
            board = new Piece[5][5];
            board[1][2] = new Piece(Type.GUARD);
            board[2][1] = new Piece(Type.GUARD);
            board[2][2] = new Piece(Type.DRAGON);
            constantBoard = duplicateBoard(board);
            r0 = 1; c0 = 2; rF = 2; cF = 2;
            edge = new Edge(r0,c0,rF,cF);
        }
        @Test
        public void testApplyMove_4() {
            setup_4();
            applyMove(edge, board);
            //compare(r0,c0,rF,cF);
        }
        //@Test
        public void testUndoMove_4() {
            setup_4();
            undoMove(edge, board);
            //compare(r0,c0,rF,cF);
        }
        @Test
        public void testInverse_4() {
            setup_4();
            printBoard(board);
            applyMove(edge, board);
            printBoard(board);
            undoMove(edge,board);
            printBoard(board);
            assertSame(board,constantBoard);
        }

        // --------------------------------------------------------

        @Test
        public void testDuplicate() {
            BoardCheckSum checkSum = new BoardCheckSum();

            setup_1();
            String boardSum = checkSum.getCheckSum(board);
            String constantSum = checkSum.getCheckSum(constantBoard);
            System.out.println("boardSum_1: "+boardSum);
            System.out.println("constantSum_1: "+constantSum);
            assertTrue(boardSum.equals(constantSum));

            setup_2();
            boardSum = checkSum.getCheckSum(board);
            constantSum = checkSum.getCheckSum(constantBoard);
            System.out.println("boardSum_2: "+boardSum);
            System.out.println("constantSum_2: "+constantSum);
            assertTrue(boardSum.equals(constantSum));

            setup_3();
            boardSum = checkSum.getCheckSum(board);
            constantSum = checkSum.getCheckSum(constantBoard);
            System.out.println("boardSum_3: "+boardSum);
            System.out.println("constantSum_3: "+constantSum);
            assertTrue(boardSum.equals(constantSum));

            setup_2();
            checkSum = new BoardCheckSum();
            boardSum = checkSum.getCheckSum(board);
            constantSum = checkSum.getCheckSum(constantBoard);
            System.out.println("boardSum_4: "+boardSum);
            System.out.println("constantSum_4: "+constantSum);
            assertTrue(boardSum.equals(constantSum));
        }

        private void assertSame(Piece[][] b1, Piece[][] b2) {
            for(int row=0; row < Constants.COLUMN_ROW_COUNT; row++) {
                for(int col=0; col < Constants.COLUMN_ROW_COUNT; col++) {
                    if(b1[row][col]==null) assertTrue(b2[row][col]==null);

                    if(b1[row][col]!=null) assertTrue("r"+row+"c"+col+" not the same", b2[row][col]!=null);

                    /*if(b1[row][col]==null) assertTrue();
                    assertTrue(
                            "Unexpected different between two boards at r"+row+"c"+col,
                            (b1[row][col].getType().equals(b2[row][col].getType()))
                    );*/
              /*  }
            }
        }
    }
    */
}
