package game.logic;

import game.history.Move;
import game.pieces.Piece;
import game.pieces.Type;
import players.human.utilities.Constants;

/**
 * Class used by Players to access board state.
 *
 * Bot uses this class to assist move search via cloning.
 */
public class DupBoard extends Board {

    public DupBoard(Board original) {
        super.gameOver = false;
        cloneCells(original);
    }

    private void cloneCells(Board original) {
        for(int r=0; r<Constants.COLUMN_ROW_COUNT; r++) {
            for(int c=0; c<Constants.COLUMN_ROW_COUNT; c++) {
                Piece p = original.cells[r][c];
                if(p==null) continue;
                if(p.getType().equals(Type.DRAGON)) {
                    super.cells[r][c] = new Piece(Type.DRAGON);
                }

                else if(p.getType().equals(Type.GUARD)) {
                    super.cells[r][c] = new Piece(Type.GUARD);
                }

                else if(p.getType().equals(Type.KING)) {
                    super.cells[r][c] = new Piece(Type.KING);
                }
            }
        }
    }

    @Override
    public DupBoard clone() {
        return new DupBoard(this);
    }

    public DupBoard clonePlusMove(Move move) {
        DupBoard clone = this.clone();
        clone.performMove(
                move.getInitialCell().getRow(),
                move.getInitialCell().getColumn(),
                move.getFinalCell().getRow(),
                move.getFinalCell().getColumn()
        );
        return clone;
    }
}
