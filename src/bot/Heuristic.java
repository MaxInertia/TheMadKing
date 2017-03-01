package bot;

import game.pieces.Piece;

/**
 * Any heuristic must implement this interface and implement the valueOf() method.
 */
public interface Heuristic {

    /**
     * Determine the value of the provided game board.
     *
     * pre-Condition: none.
     * post-Condition: none.
     *
     * @param board The game board.
     * @return The value of the game board as evaluated by this heuristic.
     */
    public double valueOf(Piece[][] board);

}
