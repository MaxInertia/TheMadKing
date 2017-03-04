package madking.players.bot.heuristic;

import madking.game.logic.DupBoard;

/**
 * Any heuristic must implement this interface and implement the valueOf() method.
 */
public interface Heuristic {

    /**
     * Determine the value of the provided madking.game board.
     *
     * pre-Condition: none.
     * post-Condition: none.
     *
     * @param board The madking.game board.
     * @return The value of the madking.game board as evaluated by this heuristic.
     */
    public float valueOf(DupBoard board);

}
