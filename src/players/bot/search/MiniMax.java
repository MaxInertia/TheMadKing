package players.bot.search;

import game.history.Move;
import game.logic.DupBoard;
import players.bot.heuristic.Heuristic;

import java.util.ArrayList;

import static java.lang.Math.*;

/**
 * @author Dorian Thiessen
 */
public class MiniMax extends SearchMethod {

    MiniMax(int depthLimit, Heuristic heuristic) {
        this.heuristic = heuristic;
        this.depthLimit = depthLimit;
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
     * @param isMax True if MAX, false if MIN. When calling this method from chooseMove() use false.
     * @return The heuristic value of the board at the depth limit with the highest value.
     */
    float search(final DupBoard possibleBoard, int depth, boolean isMax) {
        depthPrint(depth, "Depth: "+depth+"\nCheckSum: "+bCS.getCheckSum(possibleBoard.getCells()));
         if(depth == depthLimit) {
            assert possibleBoard != null;
            return heuristic.valueOf(possibleBoard.getCells());
        }

        Move bestMove = null;
        float bestValue;

        if(isMax) {
            bestValue = -1000f;
            ArrayList<Move> possibleMoves = generateMoves(possibleBoard,true);
            for(int i=0; i<possibleMoves.size(); i++) {
                Move aMove = possibleMoves.get(i);
                float val = search(
                        possibleBoard.clonePlusMove(aMove),
                        depth+1,
                        false
                );
                if(bestValue < val) {
                    bestMove = aMove;
                    bestValue = val;
                }
            }

        } else {
            bestValue = 1000f;
            ArrayList<Move> possibleMoves = generateMoves(possibleBoard, false);
            for (int i = 0; i < possibleMoves.size(); i++) {
                Move aMove = possibleMoves.get(i);
                float val = search(
                        possibleBoard.clonePlusMove(aMove),
                        depth + 1,
                        false
                );
                if (bestValue > val) {
                    bestMove = aMove;
                    bestValue = val;
                }
            }
        }

        if(depth==0) chosenMove = bestMove;
        return bestValue;
    }

}
