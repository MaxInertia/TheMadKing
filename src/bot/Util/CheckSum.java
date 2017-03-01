package bot.Util;

import game.history.Move;
import game.pieces.Piece;

/**
 * Interface to be implemented by the BoardCheckSum class.
 */
public interface CheckSum {

    /**
     * Calculates the checksum for the provided board.
     * @param board The game board.
     * @return The game boards check sum.
     */
    public String getCheckSum(Piece[][] board);

    /**
     * Determine the move that was applied to board 1 (specified by the checkSum) that resulted in board 2 (specified by
     * a 2D array of Pieces).
     * @param checkSum The checksum of the initial board.
     * @param board The board after a move was applied to it.
     * @return The move applied to the inital board that produced 'board'.
     */
    public Move calculateMove(String checkSum, Piece[][] board);

}
