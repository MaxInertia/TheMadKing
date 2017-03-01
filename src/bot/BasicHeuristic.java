package bot;

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
    public double valueOf(Piece.Type[][] board) {
        return 0;
    }
}
