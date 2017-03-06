package madking.players.bot.utility_functions;

import madking.game.logic.DupBoard;
import madking.game.pieces.Type;

/**
 *
 */
public class Weighted implements UtilityFunction {
    @Override
    public float valueOf(DupBoard board) {
        float value = 0;

        for(int r=0; r<5; r++) {
            for(int c=0; c<5; c++) {
                if(board.getCells()[r][c]!=null){
                    if(board.getCells()[r][c].getType().equals(Type.GUARD)) {
                        value += 5;
                    } else if(board.getCells()[r][c].getType().equals(Type.DRAGON)) {
                        value -= 3;
                    } else if(board.getCells()[r][c].getType().equals(Type.KING)) {
                        value += Math.pow(2,r);
                    }
                }
            }
        }
        return value;
    }
}
