package game.pieces;

import static game.pieces.Piece.Type.GUARD;
import static game.pieces.Piece.Type.KING;

/**
 * The abstract class which is extended by all game pieces. The pieces are classes so they can keep track of their own
 * statistics such as movements.
 */
public class Piece {
    private static int pieceCount = 0;

    public enum Type {
        KING, GUARD, DRAGON;
    };

    Type type;

    /**
     * The number of times this has moved.
     */
    int movements;

    /**
     * The number of pieces this has captured.
     */
    int captures;

    public int id;

    public Piece(Type type) {
        this.type =type;
        pieceCount++;
        id = pieceCount;
        System.out.println("Piece created with ID "+id);
    }

    /**
     * Constructor used to clone a piece. Only used for testing.
     * @param piece
     */
    public Piece(Piece piece) {
        type = piece.type;
        id = piece.id;
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
