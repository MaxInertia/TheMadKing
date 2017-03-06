package madking.players.bot.utility_functions;

import madking.game.logic.DupBoard;
import madking.game.pieces.Piece;
import madking.game.pieces.Type;
import org.junit.Test;

/**
 * Created by MaxInertia on 2017-03-05.
 */
public class PrimarySecondary implements UtilityFunction {

    private final int GUARD_ROW_MULTIPLIER = 5;
    private final int BASE_GUARD_VALUE = 15;
    private final int HUMAN_DIAGONAL_ADDITIVE = 2;
    private final int KING_EXPONENT_BASE = 3;
    private final int KING_EXPONENT_MULTIPLIER = 5;

    private final int BASE_DRAGON_VALUE = 30;
    private final int DRAGON_ROW_MULTIPLIER = 3;
    private final int DRAGON_DIAGONAL_ADDITIVE = 5;

    @Override
    public float valueOf(DupBoard board) {
        float value = 0;

        // TODO: Come up with primary and secondary 'goals' for each type, each goal contributes to value

        for(int r=0; r<5; r++) {
            for(int c=0; c<5; c++) {
                if(board.getCells()[r][c]!=null){
                    if(board.getCells()[r][c].isHuman()) {

                        // Secondary: Diagonal to other Humans
                        int[] diagCells = board.getDiagonalCells(r, c);
                        for (int r2 = 0, c2 = 1; r2 < 8; r2 += 2, c2 += 2) {
                            if(diagCells[r2] == -1) continue;
                            Piece pieceDiag = board.getCells()[diagCells[r2]][diagCells[c2]];
                            if (pieceDiag != null && pieceDiag.isHuman()) {
                                value += HUMAN_DIAGONAL_ADDITIVE;
                            }
                        }

                        if (board.getCells()[r][c].getType().equals(Type.GUARD)) {
                            // Primary: Higher row number
                            value += BASE_GUARD_VALUE + GUARD_ROW_MULTIPLIER*r;

                        }  else if(board.getCells()[r][c].getType().equals(Type.KING)) {
                            // Primary: Higher row number
                            value += KING_EXPONENT_MULTIPLIER * Math.pow(KING_EXPONENT_BASE,r);
                        }
                    } else if (board.getCells()[r][c].getType().equals(Type.DRAGON)) {

                        // Primary: Lower row number
                        value -= (BASE_DRAGON_VALUE + DRAGON_ROW_MULTIPLIER*r); // Make Dragon favor being low on the board

                        // Secondary: Diagonal to other Dragons
                        int[] diagCells = board.getDiagonalCells(r, c);
                        for (int r2 = 0, c2 = 1; r2 < 8; r2 += 2, c2 += 2) {
                            if(diagCells[r2] == -1) continue;
                            Piece pieceDiag = board.getCells()[diagCells[r2]][diagCells[c2]];
                            if (pieceDiag != null && pieceDiag.isHuman()) {
                                value -= DRAGON_DIAGONAL_ADDITIVE; // Make Dragon favor being diagonal to other dragons
                            }
                        }
                    }


                }
            }
        }
        return value;
    }

    // ------------------

    @Test
    public void compareBoards() {
        DupBoard board = DupBoard.generateEmptyInstance();

        DupBoard board2 = DupBoard.generateEmptyInstance();

    }
}
