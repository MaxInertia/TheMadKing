package madking.players.bot.utility_functions;

import madking.game.logic.DupBoard;

/**
 * Any utility_functions must implement this interface and implement the valueOf() method.
 */
public interface UtilityFunction {

    /**
     * Determine the value of the provided madking.game board.
     *
     * pre-Condition: none.
     * post-Condition: none.
     *
     * @param board The madking.game board.
     * @return The value of the madking.game board as evaluated by this utility_functions.
     */
    public float valueOf(DupBoard board);

}
