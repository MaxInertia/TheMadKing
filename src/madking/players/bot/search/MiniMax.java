package madking.players.bot.search;

import madking.game.history.Move;
import madking.game.logic.DupBoard;
import madking.players.bot.utility_functions.UtilityFunction;

import java.util.ArrayList;

/**
 * @author Dorian Thiessen
 */
public class MiniMax extends SearchMethod {

    MiniMax(boolean isMax, int depthLimit, UtilityFunction utilityFunction) {
        this.utilityFunction = utilityFunction;
        this.depthLimit = depthLimit;
        this.isMax = isMax;
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
     * @return The utility_functions value of the board at the depth limit with the highest value.
     */
    float search(final DupBoard possibleBoard, int depth, boolean isMax) {
        depthPrint(depth, "Depth: "+depth+"\nCheckSum: "+bCS.getCheckSum(possibleBoard.getCells()));
         if(depth == depthLimit) {
            assert possibleBoard != null;
            return utilityFunction.valueOf(possibleBoard);
        }

        Move bestMove = null;
        float bestValue;

        if(isMax) {
            bestValue = -100000;
            ArrayList<Move> possibleMoves = possibleBoard.generateMoves(possibleBoard,true);
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
            bestValue = 100000;
            ArrayList<Move> possibleMoves = possibleBoard.generateMoves(possibleBoard, false);
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
