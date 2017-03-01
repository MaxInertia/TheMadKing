package bot;

import game.history.Move;
import game.pieces.Piece;

/**
 *
 */
public class Node {
    double value;
    Move move;
    Piece.Type[][] board;

    Node(Piece.Type[][] board) {
        this.board = board;
    }
}
