package players;

import game.logic.DupBoard;
import game.pieces.Piece;

import java.util.ArrayList;

/**
 * Implemented by the HumanController and the BotController classes
 */
public interface Player {

    /**
     * Used to inform a player that it is their turn.
     * @param board The current board.
     */
    void notify(DupBoard board);

    /**
     * Inform user that the move was valid, provide new board.
     * @param board
     */
    void update(DupBoard board);

    void informGameOver(boolean player1Wins, ArrayList<Piece> pieces);
}
