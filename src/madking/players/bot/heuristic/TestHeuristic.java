package madking.players.bot.heuristic;

import madking.game.logic.DupBoard;
import madking.game.pieces.Type;
import madking.game.Constants;

/**
 * Created by MaxInertia on 2017-03-03.
 */
public class TestHeuristic implements Heuristic {

	private final int BASE_KING_VALUE = 10;
	private final int BASE_GUARD_VALUE = 1000;
	private final int BASE_DRAGON_VALUE = 100;


    @Override
    public float valueOf(DupBoard board) {      
        // First determine the types of the pieces on the board, and how many there are.
        float value = 0;

        for(int r=0; r<5; r++) {
            for(int c=0; c<5; c++) {
                if(board.getCells()[r][c]!=null){
                    if(board.getCells()[r][c].getType().equals(Type.GUARD)) {
                        if(board.getCells()[r][c].getType().equals(Type.GUARD)) {
                        	int[] cells = board.getDiagonalCells(r, c);
                        	//System.out.print(cells[0]);
                        	//System.out.print(" "+cells[1]);
                        	if (board.getCells()[cells[0]][cells[1]] != null) {
                        		if (board.getCells()[cells[0]][cells[1]].getType() != null) {
                        			value += Math.pow(2*BASE_GUARD_VALUE,r);
                        		}
                        		//System.out.println(" "+board.getCells()[cells[0]][cells[1]].getType());
                        	} else {
                    			//System.out.println("");
                    		}
                        	
                        } else {
                        	value += BASE_GUARD_VALUE*r;
                        }
                    }

                    else if(board.getCells()[r][c].getType().equals(Type.DRAGON)) {
                        	value -= BASE_GUARD_VALUE*((Constants.COLUMN_ROW_COUNT-1)-r);
                    }
                    else if(board.getCells()[r][c].getType().equals(Type.KING)) {
                        value += Math.pow(2,r*BASE_KING_VALUE);
                    }
                }
            }
        }
        return value;
    }
}
