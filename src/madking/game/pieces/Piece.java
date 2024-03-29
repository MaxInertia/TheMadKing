package madking.game.pieces;

import static madking.game.pieces.Type.GUARD;
import static madking.game.pieces.Type.KING;

/**
 * This class is used in the madking.game board to keep track of piece locations, as well as to store stats.
 */
public class Piece {
    private static int pieceCount = 0;

    Type type;

    /**
     * The number of times this has moved.
     */
    int movements = 0;

    /**
     * The number of pieces this has captured.
     */
    int captures = 0;

    public String id;

    public Piece(Type type) {
        this.type =type;
        pieceCount++;
        id = type.toString()+pieceCount;
        //System.out.println("Piece created with ID "+id);
    }

    /**
     * Constructor used to clone a piece. Only used for testing.
     * @param piece
     */
    public Piece(Piece piece) {
        type = piece.type;
        id = piece.type.toString()+piece.id;
    }

    public void moved() {
        movements++;
    }

    public int getMoveCount() {
        return movements;
    }

    public void gotKill() {
        captures++;
    }

    public int getKills() {
        return captures;
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
