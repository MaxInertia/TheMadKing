package madking.players.bot.search;

import com.sun.istack.internal.NotNull;
import madking.game.history.Move;
import madking.game.logic.DupBoard;
import madking.players.bot._util.BoardCheckSum;
import madking.players.bot.heuristic.Heuristic;

import java.util.Date;

import static java.lang.Math.abs;

/**
 * Created by MaxInertia on 2017-03-03.
 */
public abstract class SearchMethod {

    public enum Type {
        MiniMax, AlphaBeta
    }

    static BoardCheckSum bCS;
    static boolean deBugging = false;

    Heuristic heuristic;
    boolean isMax;
    int depthLimit;

    Move chosenMove;

    // -------------------------------------------------------------------------

    public static class Factory {
        public static SearchMethod generateInstance(int playerNumber, Type searchType, int depthLimit, Heuristic heuristic) {
            boolean isMax = false;
            if(playerNumber==1) {
                isMax = true;
            }

            if(searchType.equals(Type.MiniMax)) return new MiniMax(isMax, depthLimit, heuristic);
            if(searchType.equals(Type.AlphaBeta)) return new AlphaBeta(isMax, depthLimit, heuristic);
            return null;
        }
    }

    // -------------------------------------------------------------------------

    abstract float search(DupBoard possibleBoard, int depth, boolean isMax);

    // -------------------------------------------------------------------------

    /**
     * Determines the move that the madking.players.bot will make.
     *
     * pre-Conditions: board is not null.
     * post-Conditions: none.
     *
     * @return The bots move choice.
     */
    public Move chooseMove(@NotNull DupBoard board, SearchMethod searchMethod) {
        bCS = new BoardCheckSum();

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;

        searchMethod.search(board,0,isMax);

        elapsedTime = (new Date()).getTime() - startTime;
        System.out.println("[MiniMax]\tThe search took "+(elapsedTime/1000f)+" seconds.");

        return searchMethod.chosenMove;
    }

    // -------------------------------------------------------------------------

    /**
     * Generates the list of moves the player can make, stored inside instances of the edge class.
     *
     * pre-Conditions: none.
     * post-Conditions: none.
     *
    // * @param isMax True if MAX, false if MIN.
     * @return list of moves that contain each possible move at this turn.
     */ // TODO: Use validMoveChecker from Game or Board class here. This causes much more computing than necessary.


    /*
    ArrayList<Move> generateMoves1(DupBoard board, boolean isMax) {
        ArrayList<Move> moves = new ArrayList<>();

        if (isMax) {
            // Generate moves MAXs madking.game pieces can make
            for (int r = 0; r < Constants.COLUMN_ROW_COUNT; r++) { // -- Always 25 iterations
                for (int c = 0; c < Constants.COLUMN_ROW_COUNT; c++) { // -- Always 25 iterations
                    if ((board.getCells()[r][c] != null) && (board.getCells()[r][c].isHuman())) {
                        int[] primary_cells = getAdjacentCells(r, c);

                        for (int i1 = 0, j1 = 1; i1 < 8; i1 = i1 + 2, j1 = j1 + 2) { // -- At most 4 iterations
                            if (primary_cells[i1] == -1) continue; // CASE 0: At edge of board!
                            Piece primary_Piece = board.getCells()[primary_cells[i1]][primary_cells[j1]];

                            // CASE 1: If piece is null, cell is empty and can be moved into.
                            if (primary_Piece == null) {
                                moves.add(new Move(r, c, primary_cells[i1], primary_cells[j1]));
                            }

                            // CASE 2: If I'm a King, check if I can jump a Guard.
                            else if (board.getCells()[r][c].getType().equals(Type.KING)) {
                                Piece potentialGuard = board.getCells()[primary_cells[i1]][primary_cells[j1]]; //TODO: Tries Jumping over Dragons... Fix?
                                if(potentialGuard!=null && potentialGuard.getType().equals(Type.GUARD)) {
                                    int rJump = 2 * abs(r - primary_cells[i1]) + r;
                                    int cJump = 2 * abs(c - primary_cells[j1]) + c;
                                    if (rJump >= 0 && rJump < Constants.COLUMN_ROW_COUNT && cJump >= 0 && cJump < Constants.COLUMN_ROW_COUNT && board.getCells()[rJump][cJump] == null) {
                                        // Make sure King is not moving into group of three Dragons
                                        int[] secondary_cells = getAdjacentCells(rJump, cJump);
                                        int surroundingDragons = 0;
                                        for (int i2 = 0, j2 = 1; i2 < 8; i2 = i2 + 2, j2 = j2 + 2) {
                                            if (secondary_cells[i2] == -1) continue;
                                            Piece secondary_Piece = board.getCells()[secondary_cells[i2]][secondary_cells[j2]];
                                            if (secondary_Piece != null && !secondary_Piece.isHuman())
                                                surroundingDragons++;
                                        }
                                        if (surroundingDragons < 3) moves.add(new Move(r, c, rJump, cJump));
                                    }
                                }
                            }

                            // CASE 3: If piece is a Dragon, check if it can be captured.
                            else if (primary_Piece.getType() == Type.DRAGON) { // -- At most 4 iterations
                                int[] secondary_cells = getAdjacentCells(primary_cells[i1], primary_cells[j1]);
                                int assistanceAvailable = 0;

                                for (int i2 = 0, j2 = 1; i2 < 8; i2 = i2 + 2, j2 = j2 + 2) {
                                    if (secondary_cells[i2] == -1) continue;
                                    Piece secondary_Piece = board.getCells()[secondary_cells[i2]][secondary_cells[j2]];
                                    if (secondary_Piece != null && (secondary_Piece.getType().equals(Type.GUARD) || secondary_Piece.getType().equals(Type.KING))) {
                                        assistanceAvailable++;
                                        if (assistanceAvailable == 2) break;
                                    }
                                } //eol-2-secondary
                                if (assistanceAvailable == 2) {
                                    moves.add(new Move(r, c, primary_cells[i1], primary_cells[j1]));
                                }
                            }
                        } //eol-1-primary
                    }
                } //eol-c
            } // eol-r
        } else {
            // Generate moves MINs madking.game pieces can make
            for (int r = 0; r < Constants.COLUMN_ROW_COUNT; r++) { // -- Always 25 iterations
                for (int c = 0; c < Constants.COLUMN_ROW_COUNT; c++) { // -- Always 25 iterations
                    if (board.getCells()[r][c] != null && !board.getCells()[r][c].isHuman()) {
                        int[] adjacentCells = getAdjacentCells(r, c);
                        int[] diagonalCells = getDiagonalCells(r, c);

                        for (int i1 = 0, j1 = 1; i1 < 8; i1 = i1 + 2, j1 = j1 + 2) { // -- At most 4 iterations
                            if ((adjacentCells[i1] != -1) && (board.getCells()[adjacentCells[i1]][adjacentCells[j1]] == null)) {
                                moves.add(new Move(r, c, adjacentCells[i1], adjacentCells[j1]));
                            }
                            if ((diagonalCells[i1] != -1) && (board.getCells()[diagonalCells[i1]][diagonalCells[j1]] == null)) {
                                moves.add(new Move(r, c, diagonalCells[i1], diagonalCells[j1]));
                            }
                        } // eol-1
                    } // eol-c
                } // eol-r

            }
        }
        return moves;
    }
    */
    // -------------------------------------------------------------------------

    static void depthPrint(int depth, String message) {
        if(!deBugging) return;
        String tabs = "";
        for(int i=0; i<depth; i++) {
            tabs += '\t';
        }
        System.out.println(tabs+message);
    }
    private static void print(String message) {
        if(deBugging) System.out.println(message);
    }
}
