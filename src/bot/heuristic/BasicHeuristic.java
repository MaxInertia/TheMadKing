package bot.heuristic;

import bot.heuristic.Heuristic;
import game.pieces.Piece;

/**
 * Heuristic that uses the following value function assuming MAX controls Dragons:
 * value = (5 * DragonCount) - (3 * GuardCount) + (Number of Dragons attacking King) - (King Row Number)
 *
 * If MAX controls Humans:
 * value = -value;
 */
public class BasicHeuristic implements Heuristic {
    @Override
    public float valueOf(Piece[][] board) {
        if(board==null) return 0;
        return 0;
    }
}
