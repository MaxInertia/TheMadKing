package game.logic;

import game.pieces.Piece;
import game.pieces.Type;

import java.util.ArrayList;

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

    private static ArrayList<Piece> pieceList;

    private final Piece king = new Piece(Type.KING);
    private final Piece guard1 = new Piece(Type.GUARD);
    private final Piece guard2 = new Piece(Type.GUARD);
    private final Piece guard3 = new Piece(Type.GUARD);
    private final Piece dragon1 = new Piece(Type.DRAGON);
    private final Piece dragon2 = new Piece(Type.DRAGON);
    private final Piece dragon3 = new Piece(Type.DRAGON);
    private final Piece dragon4 = new Piece(Type.DRAGON);
    private final Piece dragon5 = new Piece(Type.DRAGON);

    Board(boolean tof) {
        cells = new Piece[5][5];
        cells[0][2] = king;
        cells[1][1] = guard1;
        cells[1][2] = guard2;
        cells[1][3] = guard3;
        cells[3][0] = dragon1;
        cells[3][1] = dragon2;
        cells[3][2] = dragon3;
        cells[3][3] = dragon4;
        cells[3][4] = dragon5;

        pieceList = new ArrayList<>();
        pieceList.add(cells[0][2]);
        pieceList.add(cells[1][1]);
        pieceList.add(cells[1][2]);
        pieceList.add(cells[1][3]);
        pieceList.add(cells[3][0]);
        pieceList.add(cells[3][1]);
        pieceList.add(cells[3][2]);
        pieceList.add(cells[3][3]);
        pieceList.add(cells[3][4]);
    }

    Board() {
        cells = new Piece[5][5];
    }

    /**
     * Retrieve the 2D array of Pieces that represents the gameboard.
     * @return the 2D gameboard array.
     */
    public Piece[][] getCells() {
        return cells;
    }

    public ArrayList<Piece> getPieces() {
        return pieceList;
    }

    public void performMove(int row, int column, int newRow, int newColumn) {
        cells[row][column].moved();
        if(cells[newRow][newColumn] != null) cells[newRow][newColumn].gotKill();
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
                        cells[r][c].changeType(Type.DRAGON);
                    }
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
                        //gameOver = true;
                    }
                }
            }
        }
    }
}
