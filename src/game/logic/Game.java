package game.logic;

import game.pieces.Type;
import players.bot.BotController;
import players.Player;
import game.history.Move;
import game.pieces.Piece;


/**
 *
 */
public class Game implements Updateable{

    private Player[] players;

    Board board;

    private boolean endGameState;

    public enum Team {
        MAN, BEAST
    }

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
        endGameState = false;
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
        // TODO: Check for end of game state (No Dragons left)

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

    public boolean gameOver(){
        return endGameState;
    }
}
