package game.logic;

import game.pieces.Piece;
import game.pieces.Type;

import static players.human.utilities.Constants.COLUMN_ROW_COUNT;

/**
 *
 */
class Board {

    /** boardMatrix[y][x] to reference the cell in row y, column x.
     *
     * The position definitions and initial board position:
     *
     *     0    - - K - -
     *     1    - G G G -
     *  y  2    - - - - -
     *     3    D D D D D
     *     4    - - - - -
     *
     *          0 1 2 3 4
     *              x
     */
    Piece[][] cells;

    boolean gameOver;

    Board(boolean tof) {
        cells = new Piece[5][5];
        cells[0][2] = new Piece(Type.KING);
        cells[1][1] = new Piece(Type.GUARD);
        cells[1][2] = new Piece(Type.GUARD);
        cells[1][3] = new Piece(Type.GUARD);
        cells[3][0] = new Piece(Type.DRAGON);
        cells[3][1] = new Piece(Type.DRAGON);
        cells[3][2] = new Piece(Type.DRAGON);
        cells[3][3] = new Piece(Type.DRAGON);
        cells[3][4] = new Piece(Type.DRAGON);
        gameOver = false;
    }

    Board() {
        cells = new Piece[5][5];
        gameOver = false;
    }

    /**
     * Retrieve the 2D array of Pieces that represents the gameboard.
     * @return the 2D gameboard array.
     */
    public Piece[][] getCells() {
        return cells;
    }

    public void performMove(int row, int column, int newRow, int newColumn) {
        cells[newRow][newColumn] = cells[row][column];
        cells[row][column] = null;
        checkForConversions();
    }

    /**
     * O(n) where,
     * n: Number of pieces on the board. <= 9
     */
    private void checkForConversions() {

        for(int r=0; r<COLUMN_ROW_COUNT; r++) {
            for(int c=0; c<COLUMN_ROW_COUNT; c++) {
                Piece p = cells[r][c];
                if(p == null) continue;

                if (p.getType().equals(Type.GUARD)) {
                    int surroundingDragons = 0;
                    Piece piece;

                    if ((c - 1) >= 0) {
                        piece = cells[r][c - 1];
                        if (piece != null && piece.getType().equals(Type.DRAGON)) surroundingDragons++;
                    }
                    if ((r- 1) >= 0) {
                        piece = cells[r- 1][c];
                        if (piece != null && piece.getType().equals(Type.DRAGON)) surroundingDragons++;
                    }
                    if ((c + 1) < COLUMN_ROW_COUNT) {
                        piece = cells[r][c + 1];
                        if (piece != null && piece.getType().equals(Type.DRAGON)) surroundingDragons++;
                    }
                    if ((r+ 1) < COLUMN_ROW_COUNT) {
                        piece = cells[r+ 1][c];
                        if (piece != null && piece.getType().equals(Type.DRAGON)) surroundingDragons++;
                    }

                    if (surroundingDragons >= 3) {
                        System.out.println("Guard at (" + c + "," + r+ ") turned into a Dragon!");
                        //Piece dragon = new Piece(Type.DRAGON);
                        //cells[r][c] = dragon;
                        cells[r][c].changeType(Type.DRAGON);
                    }
                    surroundingDragons = 0;
                }

                if(p.getType().equals(Type.KING)) {
                    int blockedDirections = 0;
                    Piece piece;

                    if ((c - 1) >= 0) {
                        piece = cells[r][c - 1];
                        if (piece != null) {
                            if (piece.getType().equals(Type.DRAGON)) {
                                blockedDirections++;
                            } else if (piece.getType().equals(Type.GUARD) && (c - 2) >= COLUMN_ROW_COUNT && cells[r][c-2] != null) {
                                blockedDirections++;
                            }
                        }
                    } else blockedDirections++;

                    if ((r- 1) >= 0) {
                        piece = cells[r- 1][c];
                        if (piece != null) {
                            if (piece.getType().equals(Type.DRAGON)) {
                                blockedDirections++;
                            } else if (piece.getType().equals(Type.GUARD) && (r - 2) >= COLUMN_ROW_COUNT && cells[r-2][c] != null) {
                                blockedDirections++;
                            }
                        }
                    } else blockedDirections++;

                    if ((c + 1) < COLUMN_ROW_COUNT) {
                        piece = cells[r][c+1];
                        if (piece != null) {
                            if (piece.getType().equals(Type.DRAGON)) {
                                blockedDirections++;
                            } else if (piece.getType().equals(Type.GUARD) && (c + 2) < COLUMN_ROW_COUNT && cells[r][c+2] != null) {
                                blockedDirections++;
                            }
                        }
                    } else blockedDirections++;

                    if ((r+ 1) < COLUMN_ROW_COUNT) {
                        piece = cells[r+1][c];
                        if (piece != null) {
                            if (piece.getType().equals(Type.DRAGON)) {
                                blockedDirections++;
                            } else if (piece.getType().equals(Type.GUARD) && (r + 2) < COLUMN_ROW_COUNT && cells[r+2][c] != null) {
                                blockedDirections++;
                            }
                        }
                    } else blockedDirections++;

                    if(blockedDirections == 4) {
                        gameOver = true;
                    }
                }
            }
        }

        for(int c=0; c<COLUMN_ROW_COUNT; c++) {
            Piece piece = cells[COLUMN_ROW_COUNT-1][c];
            if(piece!=null && piece.getType().equals(Type.KING)) {
                gameOver = true;
            }
        }
    }
}
