package madking.players.human;

import madking.game.history.Move;
import madking.game.logic.DupBoard;
import madking.game.logic.Updateable;
import madking.game.pieces.Piece;
import madking.gui.ViewModel;
import madking.players.Player;

import java.util.ArrayList;

/**
 * Created by MaxInertia on 2017-03-03.
 */
public class HumanPlayer implements Player {
    private Updateable gameUpdater;
    private int playerNumber;

    public HumanPlayer(Updateable instance, int playerNumber) {
        gameUpdater = instance;
        this.playerNumber = playerNumber;
    }

    @Override
    public void notify(DupBoard board) { // Notify player that it is now their turn
        System.out.println("It is now your turn!"); //TODO: Replace this with a graphical alert informing player of turn.
        ViewModel.updateBoard(board);
    }

    @Override
    public void informGameOver(boolean player1Wins, ArrayList<Piece> pieces) {
        if(player1Wins) System.out.println("THE MAD KING WINS!");
        else System.out.println("THE DRAGONS WIN!");

        for(Piece p: pieces) {
            System.out.println(p.id + "\tMoves: "+p.getMoveCount());
        }
    }

    public boolean move(int row, int column, int newRow, int newColumn) {
        boolean validMode = gameUpdater.submitMove(new Move(row, column, newRow, newColumn));
        if(validMode) {
            System.out.println("valid move!");
            // TODO: MAY NEED TO CALL  viewModel.updateBoard(board); HERE
        } else {
            //TODO: Display error message to Player
            System.out.println("INVALID Move from Player "+playerNumber+"!");
        }
        return validMode;
    }
}
