package madking.players.bot.search;

import com.sun.istack.internal.NotNull;
import madking.game.history.Move;
import madking.game.logic.DupBoard;
import madking.players.bot.other.BoardCheckSum;
import madking.players.bot.utility_functions.UtilityFunction;

import java.util.Date;

import static java.lang.Math.abs;

/**
 * Created by MaxInertia on 2017-03-03.
 */
public abstract class SearchMethod {

    public enum Type {
        MiniMax, AlphaBeta
    }

    static BoardCheckSum bCS;
    static boolean deBugging = false;

    UtilityFunction utilityFunction;
    boolean isMax;
    int depthLimit;

    Move chosenMove;

    // -------------------------------------------------------------------------

    public static class Factory {
        public static SearchMethod generateInstance(int playerNumber, Type searchType, int depthLimit, UtilityFunction utilityFunction) {
            boolean isMax = false;
            if(playerNumber==1) {
                isMax = true;
            }

            if(searchType.equals(Type.MiniMax)) return new MiniMax(isMax, depthLimit, utilityFunction);
            if(searchType.equals(Type.AlphaBeta)) return new AlphaBeta(isMax, depthLimit, utilityFunction);
            return null;
        }
    }

    // -------------------------------------------------------------------------

    abstract float search(DupBoard possibleBoard, int depth, boolean isMax);

    // -------------------------------------------------------------------------

    /**
     * Determines the move that the madking.players.bot will make.
     *
     * pre-Conditions: board is not null.
     * post-Conditions: none.
     *
     * @return The bots move choice.
     */
    public Move chooseMove(@NotNull DupBoard board, SearchMethod searchMethod) {
        bCS = new BoardCheckSum();

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;

        searchMethod.search(board,0,isMax);

        elapsedTime = (new Date()).getTime() - startTime;
        System.out.println("[MiniMax]\tThe search took "+(elapsedTime/1000f)+" seconds.");

        return searchMethod.chosenMove;
    }

    // -------------------------------------------------------------------------

    static void depthPrint(int depth, String message) {
        if(!deBugging) return;
        String tabs = "";
        for(int i=0; i<depth; i++) {
            tabs += '\t';
        }
        System.out.println(tabs+message);
    }

    private static void print(String message) {
        if(deBugging) System.out.println(message);
    }

}
