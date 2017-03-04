package madking.players;

import madking.game.history.Move;
import madking.game.logic.DupBoard;
import madking.game.pieces.Piece;

import java.util.ArrayList;

/**
 * Implemented by the HumanController and the BotPlayer classes
 */
public interface Player {

    /**
     * Used to inform a player that it is their turn.
     * @param board The current board.
     */
    void notify(DupBoard board);

    void informGameOver(boolean player1Wins, ArrayList<Piece> pieces);

}
