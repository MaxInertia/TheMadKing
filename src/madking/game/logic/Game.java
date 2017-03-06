package madking.game.logic;

import madking.game.Constants;
import madking.game.history.History;
import madking.game.pieces.Type;
import madking.gui.Display;
import madking.players.Player;
import madking.game.history.Move;
import madking.game.pieces.Piece;
import org.junit.Test;

import static madking.game.Constants.COLUMN_ROW_COUNT;
import static org.junit.Assert.assertFalse;


/**
 *
 */
public class Game implements Updateable, Initializable{

    public boolean player1Wins;
    private Player[] players;
    private Team currentTurn;
    private Board board;

    private History history;
    Display display;

    Game() {
        currentTurn = Team.BEAST;
        board = new Board(true);
        history = new History();
        players = new Player[2];
    }

    @Override
    public void initialize(Display display, Player[] players) {
        this.players = players;
        this.display = display;
        display.updateDisplay(board.getDupe());
        players[1].notify(board.getDupe());
        //players[0].update(new DupBoard(board));
        //players[1].update(new DupBoard(board));
    }

    @Override
    public boolean submitMove(Move move) {
        int row = move.getInitialCell().getRow();
        int column = move.getInitialCell().getColumn();
        int newRow = move.getFinalCell().getRow();
        int newColumn = move.getFinalCell().getColumn();

        if(board.cells[row][column] == null) return false;

        boolean retVal = false;
        Type pieceType = board.cells[row][column].getType();
        if(currentTurn == Team.MAN) {
            if(pieceType == Type.GUARD || pieceType == Type.KING) {
                retVal = Movement.checkIfValid(board.getCells(), row, column, newRow, newColumn);
            }
        } else {
            if(pieceType == Type.DRAGON) {
                retVal = Movement.checkIfValid(board.getCells(), row, column, newRow, newColumn);
            }
        }

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

        if(board.isGameOver()) {
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



    class TestHook {
        public void setBoard(Board newBoard) {
            board = newBoard;
        }

        //public boolean callCheckIfKingSurrounded() {
            //return board.checkIfKingSurrounded();
        //}
    }

}
