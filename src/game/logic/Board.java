package game.logic;

import game.pieces.Dragon;
import game.pieces.Guard;
import game.pieces.King;
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
        cells[0][2] = new King(0,2);
        cells[1][1] = new Guard(1,1);
        cells[1][2] = new Guard(1,2);
        cells[1][3] = new Guard(1,3);
        cells[3][0] = new Dragon(3,0);
        cells[3][1] = new Dragon(3,1);
        cells[3][2] = new Dragon(3,2);
        cells[3][3] = new Dragon(3,3);
        cells[3][4] = new Dragon(3,4);
    }

    /**
     * Retrieve the 2D array of Pieces that represents the gameboard.
     * @return the 2D gameboard array.
     */
    public Piece[][] getCells() {
        return cells;
    }

}
