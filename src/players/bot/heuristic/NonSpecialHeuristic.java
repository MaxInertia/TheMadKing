package players.bot.heuristic;

import game.pieces.Piece;
import game.pieces.Type;

/**
 *
 */
public class NonSpecialHeuristic implements Heuristic {
    @Override
    public float valueOf(Piece[][] board) {
        float value = 0;

        for(int r=0; r<5; r++) {
            for(int c=0; c<5; c++) {
                if(board[r][c]!=null){
                    if(board[r][c].getType().equals(Type.GUARD)) {
                        value += 5;
                    } else if(board[r][c].getType().equals(Type.DRAGON)) {
                        value -= 3;
                    } else if(board[r][c].getType().equals(Type.KING)) {
                        value += Math.pow(2,r);
                    }
                }
            }
        }
        return value;
    }
}
