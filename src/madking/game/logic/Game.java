package madking.game.logic;

import madking.game.Constants;
import madking.game.history.History;
import madking.game.pieces.Type;
import madking.gui.Display;
import madking.players.Player;
import madking.game.history.Move;
import madking.game.pieces.Piece;

import static madking.game.Constants.COLUMN_ROW_COUNT;


/**
 *
 */
public class Game implements Updateable, Initializable{

    private boolean player1Wins;
    private Player[] players;
    private Team currentTurn;
    private Board board;

    private History history;
    Display display;

    Game() {
        currentTurn = Team.MAN;
        board = new Board(true);
        history = new History();
        players = new Player[2];
    }

    @Override
    public void initialize(Display display, Player[] players) {
        this.players = players;
        this.display = display;
        display.updateDisplay(board.getDupe());
        players[0].notify(board.getDupe());
        //players[0].update(new DupBoard(board));
        //players[1].update(new DupBoard(board));
    }

    @Override
    public boolean submitMove(Move move) {
        int row = move.getInitialCell().getRow();
        int column = move.getInitialCell().getColumn();
        int newRow = move.getFinalCell().getRow();
        int newColumn = move.getFinalCell().getColumn();

        if(board.cells[row][column]==null) return false;

        boolean retVal = false;
        Type pieceType = board.cells[row][column].getType();
        if(currentTurn==Team.MAN) {
            if(pieceType == Type.GUARD || pieceType==Type.KING) {
                retVal = Movement.checkIfValid(board.getCells(), row, column, newRow, newColumn);
            }
        } else {
            if(pieceType == Type.DRAGON) {
                retVal = Movement.checkIfValid(board.getCells(), row, column, newRow, newColumn);
            }
        }
        System.out.println("retval is "+retVal);

        if(retVal) {
            board.performMove(row, column, newRow, newColumn);
            history.addMove(row, column, newRow, newColumn);
            switchTurns();
            return true;
        } else {
            System.out.println("------------- AN INVALID MOVE WAS ATTEMPTED ---------");
            System.out.println("I: ("+row+","+column+")\nF: ("+newRow+","+newColumn+")");
        }
        return false;
    }

    /**
     * Switches the turn to the other team.
     */
    private void switchTurns() {
        assert display!=null;
        display.updateDisplay(board.getDupe());

        if(isGameOver()) {
            players[0].informGameOver( player1Wins, board.getPieces() );
            return;
        }

        if(currentTurn==Team.MAN) {
            currentTurn = Team.BEAST;
            assert players[1]!=null;
            players[1].notify( new DupBoard(board) );
        } else {
            currentTurn = Team.MAN;
            assert players[0]!=null;
            players[0].notify( new DupBoard(board) );
        }
    }

    public Board getBoard() {
        return board;
    }

    private boolean isGameOver(){
        //TODO: If there are less than 3 Dragons, check if they are cornered
        return checkKingReachedEnd() || checkNoDragonsLeft() || checkIfKingSurrounded();
    }

    private boolean checkKingReachedEnd() {
        for(int c=0; c<COLUMN_ROW_COUNT; c++) {
            Piece piece = board.cells[COLUMN_ROW_COUNT-1][c];
            if(piece!=null && piece.getType().equals(Type.KING)) {
                System.out.println("King found at "+c);
                player1Wins = true;
                return true;
            }
        }
        return false;
    }

    private boolean checkNoDragonsLeft() {
        for(int c=0; c<COLUMN_ROW_COUNT; c++) {
            for(int r=0; r<COLUMN_ROW_COUNT; r++) {
                Piece piece = board.cells[r][c];
                if(piece!=null && !piece.isHuman()) return false;
            }
        }
        System.out.println("No Dragons left!");
        player1Wins = true;
        return true;
    }

    private boolean checkIfKingSurrounded() {
        for(int c=0; c<COLUMN_ROW_COUNT; c++) {
            for(int r=0; r<COLUMN_ROW_COUNT; r++) {
                Piece piece = board.cells[r][c];
                if(piece!=null && piece.getType().equals(Type.KING)) {
                    int surrounding = 0;
                    int[] primary_cells = board.getAdjacentCells(r,c);

                    for(int r2=0, c2=1; r2<8; r2+=2, c2+=2) {

                        // Wall limiting king motion
                        if( primary_cells[r2]==-1 ) {
                            surrounding++;
                            continue;
                        }

                        Piece piece2 = board.getCells()[ primary_cells[r2] ][ primary_cells[c2] ];

                        // Blocked by Piece
                        if( piece2!=null) {
                            int[] secondary_cells = board.getAdjacentCells(primary_cells[r2], primary_cells[c2]);

                            // Blocked by Dragon
                            if (piece2.getType().equals(Type.DRAGON)) {

                                // Check if Guard adjacent to that Dragon
                                for (int r3 = 0, c3 = 1; r3 < 8; r3 += 2, c3 += 2) {
                                    if (secondary_cells[r3] == -1) continue; // It is a wall, check next direction
                                    Piece piece3 = board.getCells()[secondary_cells[r3]][secondary_cells[c3]];
                                    if (piece3 != null && piece3.getType().equals(Type.GUARD)) return false; // King has a way out
                                }
                                surrounding++;
                            } else if (piece2.getType().equals(Type.GUARD)) {
                                int jumpR = 0;
                                int jumpC = 0;
                                if( (r-r2) != 0 ) jumpR = r - 2*(r-r2);
                                if( (c-c2) != 0 ) jumpC = c - 2*(c-c2);
                                if(jumpR>=0 && jumpC>=0 && jumpR< Constants.COLUMN_ROW_COUNT && jumpC<Constants.COLUMN_ROW_COUNT) {
                                    Piece piece3 = board.getCells()[jumpR][jumpC];
                                    if(piece3!=null) surrounding++;
                                } else {
                                    surrounding++;
                                }
                            }
                        }

                    }

                    if (surrounding==4) {
                        System.out.println("King surrounded!");
                        player1Wins = false;
                        return true;
                    }

                } // eoIF - isKing?
            } // eoF -r
        } //eoF - c
        return false;
    }

}
