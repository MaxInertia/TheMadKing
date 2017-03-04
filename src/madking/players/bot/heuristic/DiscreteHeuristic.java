package madking.players.bot.heuristic;

import madking.game.logic.DupBoard;
import madking.game.pieces.Type;
import madking.game.Constants;

/**
 * Created by MaxInertia on 2017-03-03.
 */
public class DiscreteHeuristic implements Heuristic {

    private final int BASE_GUARD_VALUE = 1;
    private final int BASE_DRAGON_VALUE = 1;

    @Override
    public float valueOf(DupBoard board) {
        float value = 0;

        for(int r=0; r<5; r++) {
            for(int c=0; c<5; c++) {

                if(board.getCells()[r][c]!=null){

                    if(board.getCells()[r][c].getType().equals(Type.GUARD)) {
                        value += BASE_GUARD_VALUE*r;
                    }

                    else if(board.getCells()[r][c].getType().equals(Type.DRAGON)) {
                        value -= BASE_GUARD_VALUE*((Constants.COLUMN_ROW_COUNT-1)-r);
                    }

                    else if(board.getCells()[r][c].getType().equals(Type.KING)) {
                        value += Math.pow(2,r);
                    }
                }
            }
        }
        return value;
    }
}
