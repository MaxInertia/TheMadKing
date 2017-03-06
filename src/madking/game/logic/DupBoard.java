package madking.game.logic;

import madking.game.history.Move;
import madking.game.pieces.Piece;
import madking.game.pieces.Type;
import madking.game.Constants;

import java.util.ArrayList;

/**
 * Class used by Players to access board state.
 *
 * Bot uses this class to assist move search via cloning.
 */
public class DupBoard extends Board {

    private DupBoard() {}

    public DupBoard(Board original) {
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

    // ---------- OPERATIONS ----------

    public ArrayList<Move> generateMoves(DupBoard board, boolean isMax) {
        ArrayList<Move> moves = new ArrayList<>();

        if (isMax) {
            int humanCount=0;
            for (int r = 0; r < Constants.COLUMN_ROW_COUNT; r++) { // -- Always 25 iterations
                for (int c = 0; c < Constants.COLUMN_ROW_COUNT; c++) { // -- Always 25 iterations
                    if ((board.getCells()[r][c] != null) && (board.getCells()[r][c].isHuman())) {
                        int[] adjCells = getAdjacentCells(r,c);
                        for(int r2=0, c2=1; r2<8; r2+=2, c2+=2) {

                            if( adjCells[r2] != -1 && Movement.checkIfValid(board.getCells(), r, c, adjCells[r2], adjCells[c2]) ) {
                                moves.add(new Move(r, c, adjCells[r2], adjCells[c2]));
                            }

                            if(board.getCells()[r][c].getType().equals(Type.KING)) {
                                int jumpR = r - 2*(r-adjCells[r2]);
                                int jumpC = c - 2*(c-adjCells[c2]);
                                if(jumpR>=0 && jumpC>=0 && jumpR<Constants.COLUMN_ROW_COUNT && jumpC<Constants.COLUMN_ROW_COUNT) {
                                    if(Movement.checkIfValid(board.getCells(), r, c, jumpR, jumpC)) moves.add(new Move(r, c, jumpR, jumpC));
                                }
                            }
                        }
                        if(++humanCount == Constants.INITIAL_GUARD_COUNT+1) return moves;
                    }
                }
            }

        }

        else {
            int dragonCount=0;
            for (int r = 0; r < Constants.COLUMN_ROW_COUNT; r++) { // -- Always 25 iterations
                for (int c = 0; c < Constants.COLUMN_ROW_COUNT; c++) { // -- Always 25 iterations
                    if ((board.getCells()[r][c] != null) && (!board.getCells()[r][c].isHuman())) {
                        int[] adjCells = getAdjacentCells(r,c);
                        int[] diagCells = getDiagonalCells(r,c);

                        for(int r2=0, c2=1; r2<8; r2+=2, c2+=2) {
                            if( adjCells[r2] != -1 && Movement.checkIfValid(board.getCells(), r, c, adjCells[r2], adjCells[c2]) ) {
                                moves.add(new Move(r, c, adjCells[r2], adjCells[c2]));
                            }
                            if( diagCells[r2] != -1 && Movement.checkIfValid(board.getCells(), r, c, diagCells[r2], diagCells[c2]) ) {
                                moves.add(new Move(r, c, diagCells[r2], diagCells[c2]));
                            }
                        } //eoL-3

                        if(++dragonCount == Constants.INITIAL_DRAGON_COUNT+Constants.INITIAL_GUARD_COUNT) return moves;
                    }
                } //eoL-2
            } //eoL-1
        }
        return moves;
    }



    // ---------- FOR TESTING ----------

    public static DupBoard generateEmptyInstance() {
        return new DupBoard();
    }
}
