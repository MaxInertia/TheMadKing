package game_logic;

import game_logic.pieces.Dragon;
import game_logic.pieces.Guard;
import game_logic.pieces.King;
import game_logic.pieces.Piece;

/**
 *
 */
public class Board {

    public static Board instance;

    /** boardMatrix[x][y] to reference the cell in column x, row y.
     *
     * The position definitions and initial board position:
     *
     *     4    - - K - -
     *     3    - G G G -
     *  y  2    - - - - -
     *     1    D D D D D
     *     0    - - - - -
     *
     *          0 1 2 3 4
     *              x
     */
    Piece[][] boardMatrix;

    public Board() {
        boardMatrix = new Piece[5][5];
        boardMatrix[2][4] = new King(2,4);
        boardMatrix[1][3] = new Guard(1,3);
        boardMatrix[2][3] = new Guard(2,3);
        boardMatrix[3][3] = new Guard(3,3);
        boardMatrix[0][1] = new Dragon(0,1);
        boardMatrix[1][1] = new Dragon(1,1);
        boardMatrix[2][1] = new Dragon(2,1);
        boardMatrix[3][1] = new Dragon(3,1);
        boardMatrix[4][1] = new Dragon(4,1);
    }

    /**
     * Move piece at location (x0,y0) to (x,y)
     * @param x0 initial x-coordinate / column
     * @param y0 initial y-coordinate / row
     * @param x new x-coordinate / column
     * @param y new y-coordinate / row
     * @return true if the move was valid, otherwise false
     */
    boolean move(int x0, int y0, int x, int y) {
        return boardMatrix[x0][y0].move(x,y);
    }

    /**
     * Checks if there is a piece at column x, row y.
     * @param x x-coordinate / column of location
     * @param y y-coordinate / row of location
     * @return True if the location contains a piece, false if it does not.
     */
    public boolean pieceAt(int x, int y) {
        return boardMatrix[x][y]!=null;
    }

    /**
     * Returns the type of piece at a given location,
     * or returns null if no piece is at the specified location.
     * @param x x-coordinate of location / column
     * @param y y-coordinate of location / row
     * @return Piece.Type if a piece is at (x,y), else null
     */
    public Piece.Type pieceTypeAtLocation(int x, int y) {
        if(boardMatrix[x][y] == null) return null;
        return boardMatrix[x][y].getType();
    }

    /**
     * Retrieve the 2D array of Pieces that represents the gameboard.
     * @return the 2D gameboard array.
     */
    public Piece[][] getBoard() {
        return boardMatrix;
    }

}
