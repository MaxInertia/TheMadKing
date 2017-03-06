package madking.players.bot.utility_functions;

import madking.game.logic.DupBoard;
import madking.game.pieces.Type;

/**
 * UtilityFunction that uses the following value function assuming MAX controls Dragons:
 * value = (5 * DragonCount) - (3 * GuardCount) + (Number of Dragons attacking King) - (King Row Number)
 *
 * If MAX controls Humans:
 * value = -value;
 */
public class Discrete implements UtilityFunction {
    @Override
    public float valueOf(DupBoard board) {
        if(board==null) return 0;

        int dragons = 0;
        int guards = 0;

        for(int r=0; r<5; r++) {
            for(int c=0; c<5; c++) {
                if(board.getCells()[r][c]!=null){
                    if(board.getCells()[r][c].getType().equals(Type.GUARD)) {
                        guards++;
                    } else if(board.getCells()[r][c].getType().equals(Type.DRAGON)) {
                        dragons++;
                    }
                }
            }
        }

        return guards*5 - dragons*3;
    }
}
