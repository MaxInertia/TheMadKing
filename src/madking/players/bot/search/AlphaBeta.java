package madking.players.bot.search;

import madking.game.history.Move;
import madking.game.logic.DupBoard;
import madking.players.bot.utility_functions.UtilityFunction;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 *
 */
public class AlphaBeta extends SearchMethod {

    int int_nodes;
    int leaf_nodes;

    AlphaBeta(boolean isMax, int depthLimit, UtilityFunction utilityFunction) {
        this.utilityFunction = utilityFunction;
        this.depthLimit = depthLimit;
        this.isMax = isMax;
        int_nodes = 0;
        leaf_nodes = 0;
    }

    float search(final DupBoard possibleBoard, int depth, boolean isMax) {
        float bestVal;

        float alpha = -100000;
        float beta = 100000;
        if(isMax) {

            ArrayList<Move> moves = possibleBoard.generateMoves(possibleBoard, true);
            System.out.println("# There are "+moves.size()+" possible moves available for Humans");
            for (Move aMove : moves) {
                float moveVal = alphaBeta(possibleBoard.clonePlusMove(aMove), 2, -100000, 100000, false);
                if (moveVal > alpha) {
                    alpha = moveVal;
                    chosenMove = aMove;
                }
            }
            bestVal = alpha;
        } else {
            ArrayList<Move> moves = possibleBoard.generateMoves(possibleBoard, false);
            System.out.println("# There are "+moves.size()+" possible moves available for Dragons");
            for (Move aMove : moves) {
                float moveVal = alphaBeta(possibleBoard.clonePlusMove(aMove), 2, -100000, 100000, true);
                if (moveVal < beta) {
                    beta = moveVal;
                    chosenMove = aMove;
                }
            }
            bestVal = beta;
        }
        System.out.println("\tIntermediate nodes: "+int_nodes);
        System.out.println("\tLeaf nodes: "+leaf_nodes);
        System.out.println("\tUtilityFunction: "+bestVal);
        leaf_nodes = 0;
        int_nodes = 0;

        try{
            if(chosenMove==null) {
                throw new NullPointerException();
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        }

        return bestVal;
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
     * @return The utility_functions value of the board at the depth limit with the highest value.
     */
    float alphaBeta(final DupBoard possibleBoard, int depth, float alpha, float beta, boolean isMax) {
        //depthPrint(depth, "Depth: "+depth);
        //depthPrint(depth,"CheckSum: "+bCS.getCheckSum(possibleBoard.getCells()));
        if(depth == depthLimit) {
            leaf_nodes++;
            float h = utilityFunction.valueOf(possibleBoard);
            //System.out.println("Leaf node "+leaf_nodes+ " utility_functions = "+h);
            return h;
        }
        int_nodes++;
        //if(int_nodes%1000==0) System.out.println("Currently at "+int_nodes+" nodes.");

        if(isMax) {
            //Move betaMove = null;
            ArrayList<Move> possibleMoves = possibleBoard.generateMoves(possibleBoard,true);
            //System.out.println("There are "+possibleMoves.size()+" possible moves available for Humans");
            for(int i=0; i<possibleMoves.size(); i++) {
                Move aMove = possibleMoves.get(i);
                float score = alphaBeta(
                        possibleBoard.clonePlusMove(aMove),
                        depth+1,
                        alpha,
                        beta,
                        false
                );
                if(score >= beta) {
                    return beta; // Fail hard beta-cutoff
                }
                if(score > alpha) {
                    //betaMove = aMove;
                    alpha = score; // Beta acts like max in MiniMax
                }
            }
            //if(depth==0) chosenMove = betaMove;
            return alpha;

        } else {
            //Move alphaMove = null;
            ArrayList<Move> possibleMoves = possibleBoard.generateMoves(possibleBoard,false);
            //System.out.println("There are "+possibleMoves.size()+" possible moves available for Dragons");
            for(int i=0; i<possibleMoves.size(); i++) {
                Move aMove = possibleMoves.get(i);
                float score = alphaBeta(
                        possibleBoard.clonePlusMove(aMove),
                        depth+1,
                        alpha,
                        beta,
                        false
                );
                if(score <= alpha) {
                    return alpha; // Fail hard alpha-cutoff
                }
                if(score < beta) {
                    //alphaMove = aMove;
                    beta = score; // Alpha acts like min in MiniMax
                }
            }
            //if(depth==0) chosenMove = alphaMove;
            return beta;
        }
    }

    float alphaBeta2(DupBoard possibleBoard, int depth, float alpha, float beta, boolean isMax) {
        if( depthLimit==depth) {
            leaf_nodes++;
            return -utilityFunction.valueOf(possibleBoard);
        }

        ArrayList<Move> possibleMoves = possibleBoard.generateMoves(possibleBoard,isMax);
        for(Move aMove: possibleMoves) {
            float score = -alphaBeta(
                    possibleBoard.clonePlusMove(aMove),
                    depth+1,
                    -beta,
                    -alpha,
                    !isMax
            );
            if(score >= beta) {
                return beta; // Fail hard alpha-cutoff
            }
            if(score > alpha) {
                alpha = score; // Alpha acts like min in MiniMax
            }
        }
        return alpha;
    }

}
