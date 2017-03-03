package players.bot.heuristic;

import game.pieces.Type;
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

        int dragons = 0;
        int guards = 0;

        for(int r=0; r<5; r++) {
            for(int c=0; c<5; c++) {
                if(board[r][c]!=null){
                    if(board[r][c].getType().equals(Type.GUARD)) {
                        guards++;
                    } else if(board[r][c].getType().equals(Type.DRAGON)) {
                        dragons++;
                    }
                }
            }
        }

        return guards*5 - dragons*3;
    }
}
