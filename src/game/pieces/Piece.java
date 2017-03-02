package game.pieces;

import java.util.Objects;

import static game.pieces.Piece.Type.GUARD;
import static game.pieces.Piece.Type.KING;

/**
 * The abstract class which is extended by all game pieces. The pieces are classes so they can keep track of their own
 * statistics such as movements.
 */
public class Piece {

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

    public Piece(Type type) {
        this.type =type;
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
