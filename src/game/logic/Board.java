package game.logic;

import game.pieces.Piece;

/**
 *
 */
public class Board {

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

    Board() {
        cells = new Piece[5][5];
        cells[0][2] = new Piece(Piece.Type.KING);
        cells[1][1] = new Piece(Piece.Type.GUARD);
        cells[1][2] = new Piece(Piece.Type.GUARD);
        cells[1][3] = new Piece(Piece.Type.GUARD);
        cells[3][0] = new Piece(Piece.Type.DRAGON);
        cells[3][1] = new Piece(Piece.Type.DRAGON);
        cells[3][2] = new Piece(Piece.Type.DRAGON);
        cells[3][3] = new Piece(Piece.Type.DRAGON);
        cells[3][4] = new Piece(Piece.Type.DRAGON);
    }

    /**
     * Retrieve the 2D array of Pieces that represents the gameboard.
     * @return the 2D gameboard array.
     */
    public Piece[][] getCells() {
        return cells;
    }

}
