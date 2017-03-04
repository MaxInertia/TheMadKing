package madking.players.bot;

import madking.game.logic.DupBoard;
import org.junit.Before;
import org.junit.Test;
import madking.players.bot.heuristic.DiscreteHeuristic;
import madking.players.bot.search.SearchMethod;

/**
 * Created by MaxInertia on 2017-03-03
 */
public class SearchTest {

    private SearchMethod searchMethod;
    private int depthLimit = 3;
    private DupBoard board;

    // DupBoard - added private constructor with no paramters
    // DupBoard - added public static method 'generateEmptyInstance()'

    @Before
    public void setup() {
        searchMethod = SearchMethod.Factory.generateInstance(
                2,
                SearchMethod.Type.AlphaBeta,
                depthLimit,
                new DiscreteHeuristic()
        );
        board = DupBoard.generateEmptyInstance();
    }

    @Test
    public void testSearch_1() {

    }

}
