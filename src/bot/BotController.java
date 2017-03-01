package bot;

import game.history.Move;
import game.pieces.Piece;

/**
 *
 */
public class BotController {

    MiniMax miniMax;

    public void notifyOfTurn(Piece.Type[][] board) {
        if(miniMax == null) {
            miniMax = new MiniMax(3, new BasicHeuristic());
        }
        Move choice = miniMax.chooseMove(board);

        System.out.println(
                "("+ choice.getInitialCell().getRow() +","+ choice.getInitialCell().getColumn() +")"
                +"("+ choice.getFinalCell().getRow() +","+ choice.getFinalCell().getColumn() +")"
        );
    }

}
