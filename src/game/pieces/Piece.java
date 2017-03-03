package game.pieces;

import static game.pieces.Type.GUARD;
import static game.pieces.Type.KING;

/**
 * This class is used in the game board to keep track of piece locations, as well as to store stats.
 */
public class Piece {
    private static int pieceCount = 0;

    Type type;

    /**
     * The number of times this has moved.
     */
    int movements;

    /**
     * The number of pieces this has captured.
     */
    int captures;

    public String id;

    public Piece(Type type) {
        this.type =type;
        pieceCount++;
        id = type.toString()+pieceCount;
        System.out.println("Piece created with ID "+id);
    }

    /**
     * Constructor used to clone a piece. Only used for testing.
     * @param piece
     */
    public Piece(Piece piece) {
        type = piece.type;
        id = piece.type.toString()+piece.id;
    }

    /**
     * Change the type of this piece to 'newType'
     * pre-Condition: none
     * post-Condition: this.getType() == newType
     * @param newType The type the piece will be changed to
     */
    public void changeType(Type newType) {
        this.type = newType;
    }

    public boolean isHuman() {
        return KING.equals(type) || GUARD.equals(type);
    }

    public Type getType() {
        return type;
    }
}
