package game.pieces;

/**
 * The abstract class which is extended by all game pieces. The pieces are classes so they can keep track of their own
 * statistics such as movements.
 */
public abstract class Piece {

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

    public Type getType() {
        return type;
    }
}
