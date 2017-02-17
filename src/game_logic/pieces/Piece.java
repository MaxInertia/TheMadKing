package game_logic.pieces;

import game_logic.Board;

/**
 * The abstract class which is extended by all game pieces. The pieces are classes so they can keep track of their own
 * statistics such as movements.
 */
public abstract class Piece {

    public enum Type {
        KING, GUARD, DRAGON;
    };

    Type type;

    int xPos;
    int yPos;

    /**
     * The number of times this has moved.
     */
    int movements;

    /**
     * The number of pieces this has captured.
     */
    int captures;

    Piece(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    /**
     * Moves the gamepiece to the specified location.
     * @precondition The specified location is valid
     * @param x x coordinate of new location
     * @param y y coordinate of new location
     * @return True if it is a valid move, otherwise false
     */
    public boolean move(int x, int y) {
        //System.out.println("Piece.move()");
        return simpleMove(x,y);
    }

    boolean simpleMove(int x, int y) {
        Board.instance.getBoard()[x][y] = Board.instance.getBoard()[xPos][yPos];
        Board.instance.getBoard()[xPos][yPos] = null;
        xPos = x;
        yPos = y;
        movements++;
        return true;
    }

    public Type getType() {
        return type;
    }
}
