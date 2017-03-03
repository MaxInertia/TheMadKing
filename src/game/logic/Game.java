package game.logic;

import game.pieces.Type;
import players.bot.BotController;
import players.Player;
import game.history.Move;
import game.pieces.Piece;

import static players.human.utilities.Constants.COLUMN_ROW_COUNT;


/**
 *
 */
public class Game implements Updateable{

    private Player[] players;

    Board board;

    private boolean player1Wins;

    private Team currentTurn;

    public Game(Player humanPlayer1, Player humanPlayer2) {
        basicSetup();
        players[0] = humanPlayer1;
        players[1] = humanPlayer2;
    }

    public Game(Player humanPlayer) {
        basicSetup();
        players[0] = humanPlayer;
        players[1] = new BotController(this);
    }

    public Game() {
        basicSetup();
    }

    /**
     * Only called by the Constructor. Initializes fields common to all Constructors.
     */
    private void basicSetup() {
        currentTurn = Team.MAN;
        board = new Board(true);
        players = new Player[2];
    }

    @Override
    public boolean update(Move move) {
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
            switchTurns();
            return true;
        } else {
            System.out.println("------------- AN INVALID MOVE WAS ATTEMPTED ---------");
            System.out.println("I: ("+row+","+column+")\nF: ("+newRow+","+newColumn+")");
        }
        return false;
    }

    /**
     * Returns the team who's turn it currently is.
     */
    public Team getActiveTeam() {
        return currentTurn;
    }

    /**
     * Switches the turn to the other team.
     */
    private void switchTurns() {
        if(isGameOver()) players[0].informGameOver( player1Wins, board.getPieces() );

        if(currentTurn==Team.MAN) {
            players[0].update(new DupBoard(board));
            currentTurn = Team.BEAST;
            assert players[1] != null;
            players[1].notify( new DupBoard(board) );
        } else {
            players[1].update(new DupBoard(board));
            currentTurn = Team.MAN;
            assert players[0] != null;
            players[0].notify( new DupBoard(board) );
        }
    }

    public Board getBoard() {
        return board;
    }

    public boolean isGameOver(){
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
                    int[] primary_cells = getAdjacentCells(r,c);

                    for(int r2=0, c2=1; r2<8; r2+=2, c2+=2) {

                        // Wall limiting king motion
                        if( primary_cells[r2]==-1 ) {
                            surrounding++;
                            continue;
                        }

                        Piece piece2 = board.getCells()[ primary_cells[r2] ][ primary_cells[c2] ];

                        // Dragon on that side of the King
                        if( piece2!=null && piece2.getType().equals(Type.DRAGON)) {
                            int[] secondary_cells = getAdjacentCells(primary_cells[r2], primary_cells[c2]);

                            // Check if Guard adjacent to that Dragon
                            for(int r3=0, c3=1; r3<8; r3+=2, c3+=2) {
                                if( secondary_cells[r3] == -1 ) continue; // It is a wall, check next direction
                                Piece piece3 = board.getCells()[ secondary_cells[r3] ][ secondary_cells[c3] ];
                                if(piece3!=null && piece3.getType()==Type.GUARD) return false; // King has a way out
                            }
                            surrounding++;
                        }

                        else {
                            return false;
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

    private static int[] getAdjacentCells(int r, int c) {
        int[] cells = new int[8];
        if(c+1<5) { cells[0] = r; cells[1] = c+1; }
        else cells[0] = -1;
        if(r-1>=0) { cells[2] = r-1; cells[3] = c; }
        else cells[2] = -1;
        if(c-1>=0) { cells[4] = r; cells[5] = c-1; }
        else cells[4] = -1;
        if(r+1<5) { cells[6] = r+1; cells[7] = c; }
        else cells[6] = -1;
        return cells;
    }
}
