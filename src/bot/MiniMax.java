package bot;

import game.history.Move;
import game.pieces.Piece;

import java.util.ArrayList;

/**
 *
 */
public class MiniMax {

    public Move search(Piece.Type[][] board) {
        Node root = new Node(board);
        minimax(root, 1 or 0, true);
    }

    public ArrayList<Integer> minimax(Piece.Type[][] board, ) {

    }
}
