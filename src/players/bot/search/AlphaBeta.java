package players.bot.search;

import game.history.Move;
import game.logic.DupBoard;
import players.bot.heuristic.Heuristic;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 *
 */
public class AlphaBeta extends SearchMethod {

    AlphaBeta(int depthLimit, Heuristic heuristic) {
        this.heuristic = heuristic;
        this.depthLimit = depthLimit;
    }

    float search(final DupBoard possibleBoard, int depth, boolean isMax) {
        return alphaBeta(possibleBoard, depth, -1000, 1000, isMax);
    }

    /**
     * MiniMax implementation.
     *
     * pre-Conditions:
     * * depth >= 0
     * * depth == 0 if edge == null
     * post-Conditions: The chosen move is stored in 'bestMove'
     *
     * @param possibleBoard The board being evaluated.
     * @param depth The depth of the edge that the edge leads to.
     * @param alpha
     * @param beta
     * @param isMax True if MAX, false if MIN. When calling this method from chooseMove() use false.
     * @return The heuristic value of the board at the depth limit with the highest value.
     */
    float alphaBeta(final DupBoard possibleBoard, int depth, float alpha, float beta, boolean isMax) {
        depthPrint(depth, "Depth: "+depth);
        depthPrint(depth,"CheckSum: "+bCS.getCheckSum(possibleBoard.getCells()));
        if(depth == depthLimit) {
            return heuristic.valueOf(possibleBoard.getCells());
        }

        Move currentBestMove = null;
        if(isMax) {
            ArrayList<Move> possibleMoves = generateMoves(possibleBoard,true);
            for(int i=0; i<possibleMoves.size(); i++) {
                Move aMove = possibleMoves.get(i);
                float score = alphaBeta(
                        possibleBoard.clonePlusMove(aMove),
                        depth+1,
                        alpha,
                        beta,
                        false);
                if(score >= beta) return beta; // Fail hard beta-cutoff
                if(score > alpha) {
                    currentBestMove = aMove;
                    alpha = score; // Beta acts like max in MiniMax
                }
            }
            if(depth==0) chosenMove = currentBestMove;
            return alpha;

        } else {
            ArrayList<Move> possibleMoves = generateMoves(possibleBoard,false);
            for(int i=0; i<possibleMoves.size(); i++) {
                Move aMove = possibleMoves.get(i);
                float score = alphaBeta(
                        possibleBoard.clonePlusMove(aMove),
                        depth+1,
                        alpha,
                        beta,
                        false);
                if(score <= alpha) return alpha; // Fail hard beta-cutoff
                if(score < beta) {
                    currentBestMove = aMove;
                    beta = score; // Beta acts like min in MiniMax
                }
            }
            if(depth==0) chosenMove = currentBestMove;
            return beta;
        }
    }

}
