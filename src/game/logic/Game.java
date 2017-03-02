package game.logic;

import game.pieces.Piece;

import static views.game_view.Constants.COLUMN_ROW_COUNT;

/**
 *
 */
public class Game {

    Board board;
    private boolean endGameState;

    public enum Team {
        MAN, BEAST
    }

    private Team currentTurn;

    public Game() {
        currentTurn = Team.MAN;
        board = new Board();
        endGameState = false;
    }

    /**
     * Returns the team whos turn it currently is.
     */
    public Team getActiveTeam() {
        return currentTurn;
    }

    /**
     * Called when a player specifies a piece and the location it is to be moved.
     *
     * If the move is valid the board will be modified accordingly, then the other team will be able to take their turn.
     * If the move is not valid, the board will not change and it will still be that players turn.
     *
     * @param row Piece location y-coordinate
     * @param column Piece location x-coordinate
     * @param newRow New location y-coordinate
     * @param newColumn New location x-coordinate
     * @return True if the move was valid, false if it was not
     */
    public boolean turnMove(int row, int column, int newRow, int newColumn) {
        if(board.cells[row][column]==null) return false;


        boolean retVal = false;
        Piece.Type pieceType = board.cells[row][column].getType();
        if(currentTurn==Team.MAN) {
            if(pieceType==Piece.Type.GUARD || pieceType==Piece.Type.KING) {
                retVal = Movement.checkIfValid(board.getCells(), row, column, newRow, newColumn);
            }
        } else {
            if(pieceType== Piece.Type.DRAGON) {
                retVal = Movement.checkIfValid(board.getCells(), row, column, newRow, newColumn);
            }
        }

        System.out.println("retval is "+retVal);

        if(retVal) {
            performMove(row, column, newRow, newColumn);
            performCheck();
            switchTurns();
            return true;
        }
        return false;
    }

    void performMove(int row, int column, int newRow, int newColumn) {
        board.cells[newRow][newColumn] = board.cells[row][column];
        board.cells[row][column] = null;
    }

    /**
     * O(n) where,
     * n: Number of pieces on the board. <= 9
     */
    private void performCheck() {
        
        for(int r=0; r<COLUMN_ROW_COUNT; r++) {
            for(int c=0; c<COLUMN_ROW_COUNT; c++) {
                Piece p = board.cells[r][c];
                if(p == null) continue;

                if (p.getType().equals(Piece.Type.GUARD)) {
                    int surroundingDragons = 0;
                    Piece piece;

                    if ((c - 1) >= 0) {
                        piece = board.cells[r][c - 1];
                        if (piece != null && piece.getType().equals(Piece.Type.DRAGON)) surroundingDragons++;
                    }
                    if ((r- 1) >= 0) {
                        piece = board.cells[r- 1][c];
                        if (piece != null && piece.getType().equals(Piece.Type.DRAGON)) surroundingDragons++;
                    }
                    if ((c + 1) < COLUMN_ROW_COUNT) {
                        piece = board.cells[r][c + 1];
                        if (piece != null && piece.getType().equals(Piece.Type.DRAGON)) surroundingDragons++;
                    }
                    if ((r+ 1) < COLUMN_ROW_COUNT) {
                        piece = board.cells[r+ 1][c];
                        if (piece != null && piece.getType().equals(Piece.Type.DRAGON)) surroundingDragons++;
                    }

                    if (surroundingDragons >= 3) {
                        System.out.println("Guard at (" + c + "," + r+ ") turned into a Dragon!");
                        Piece dragon = new Piece(Piece.Type.DRAGON);
                        board.cells[r][c] = dragon;
                    }
                    surroundingDragons = 0;
                }

                if(p.getType().equals(Piece.Type.KING)) {
                    int blockedDirections = 0;
                    Piece piece;

                    if ((c - 1) >= 0) {
                        piece = board.cells[r][c - 1];
                        if (piece != null) {
                            if (piece.getType().equals(Piece.Type.DRAGON)) {
                                blockedDirections++;
                            } else if (piece.getType().equals(Piece.Type.GUARD) && (c - 2) >= COLUMN_ROW_COUNT && board.cells[r][c-2] != null) {
                                blockedDirections++;
                            }
                        }
                    } else blockedDirections++;

                    if ((r- 1) >= 0) {
                        piece = board.cells[r- 1][c];
                        if (piece != null) {
                            if (piece.getType().equals(Piece.Type.DRAGON)) {
                                blockedDirections++;
                            } else if (piece.getType().equals(Piece.Type.GUARD) && (r - 2) >= COLUMN_ROW_COUNT && board.cells[r-2][c] != null) {
                                blockedDirections++;
                            }
                        }
                    } else blockedDirections++;

                    if ((c + 1) < COLUMN_ROW_COUNT) {
                        piece = board.cells[r][c+1];
                        if (piece != null) {
                            if (piece.getType().equals(Piece.Type.DRAGON)) {
                                blockedDirections++;
                            } else if (piece.getType().equals(Piece.Type.GUARD) && (c + 2) < COLUMN_ROW_COUNT && board.cells[r][c+2] != null) {
                                blockedDirections++;
                            }
                        }
                    } else blockedDirections++;

                    if ((r+ 1) < COLUMN_ROW_COUNT) {
                        piece = board.cells[r+1][c];
                        if (piece != null) {
                            if (piece.getType().equals(Piece.Type.DRAGON)) {
                                blockedDirections++;
                            } else if (piece.getType().equals(Piece.Type.GUARD) && (r + 2) < COLUMN_ROW_COUNT && board.cells[r+2][c] != null) {
                                blockedDirections++;
                            }
                        }
                    } else blockedDirections++;

                    if(blockedDirections == 4) {
                        endGameState = true;
                    }
                }
            }
        }

        for(int c=0; c<COLUMN_ROW_COUNT; c++) {
            Piece piece = board.cells[COLUMN_ROW_COUNT-1][c];
            if(piece!=null && piece.getType().equals(Piece.Type.KING)) {
                endGameState = true;
            }
        }
    }

    /**
     * Switches the turn to the other team.
     */
    private void switchTurns() {
        if(currentTurn==Team.MAN) currentTurn = Team.BEAST;
        else currentTurn = Team.MAN;
        System.out.println("It is now "+currentTurn.toString()+ "'s turn");
    }

    public Piece[][] getBoard() {
        return board.cells;
    }

    public boolean gameOver(){
        return endGameState;
    }
    
    public void printBoard() {
        System.out.println();
        for(int x=0; x<5; x++) {
            System.out.print(""+x+"  ");
            for(int y=0; y<5; y++) {
                Piece piece = board.cells[x][y];
                if(piece==null) {
                    System.out.print("-");
                } else if(piece.getType() == Piece.Type.DRAGON) {
                    System.out.print("D");
                } else if(piece.getType() == Piece.Type.GUARD) {
                    System.out.print("G");
                } else if(piece.getType() == Piece.Type.KING) {
                    System.out.print("K");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("   0 1 2 3 4\n");
    }
}
