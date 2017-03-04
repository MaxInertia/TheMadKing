package madking.players.bot;

import madking.game.history.Move;
import madking.game.pieces.Piece;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import org.junit.Test;
import madking.players.Player;
import madking.game.logic.DupBoard;
import madking.game.logic.Updateable;
import madking.players.bot.heuristic.DiscreteHeuristic;
import madking.players.bot.search.SearchMethod;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 *
 */
public class BotPlayer implements Player {

    private SearchMethod searchMethod;
    private Updateable updater;
    private int depthLimit = 3;
    private int playerNumber;

    private BotPlayer(){}

    public BotPlayer(Updateable gameInstance, int playerNumber) {
        this.playerNumber = playerNumber;
        updater = gameInstance;
        searchMethod = SearchMethod.Factory.generateInstance(playerNumber, SearchMethod.Type.AlphaBeta, depthLimit, new DiscreteHeuristic());
    }

    @Override
    public void notify(DupBoard board) {
        System.out.println("Bot has been informed of it's turn.");
        SearchTask task = new SearchTask();
        task.initialBoard = board;

        Thread searchThread = new Thread(task);
        searchThread.setDaemon(true);
        searchThread.start();
    }

    @Override
    public void informGameOver(boolean player1Wins, ArrayList<Piece> pieces) {}

    class SearchTask extends Task<Move> {
        DupBoard initialBoard;

        @Override
        protected Move call() throws Exception {
            System.out.println("MiniMax started running on Thread: "+Thread.currentThread());
            return searchMethod.chooseMove(initialBoard, searchMethod);
        }

        @Override
        protected void succeeded() {
            super.succeeded();
            System.out.println("setOnSucceeded running on Thread: "+Thread.currentThread());
            updater.submitMove(getValue()); // Ends the Bots turn
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static class Unit_Tests {
        private static String jFxThread = "";
        private static String onCallThread = "";
        private static String onSucceededThread = "";

        @Test
        public void test_threadsUsed() throws InterruptedException {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    new JFXPanel(); // Initializes the JavaFx Platform

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                jFxThread = Thread.currentThread().getName();
                                BotPlayer botPlayer = new BotPlayer();
                                SearchTask s = botPlayer.new SearchTask(){
                                    @Override
                                    protected Move call() throws Exception {
                                        onCallThread = Thread.currentThread().getName();
                                        return null;
                                    }
                                };
                                s.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                    @Override
                                    public void handle(WorkerStateEvent event) {
                                        onSucceededThread = Thread.currentThread().getName();
                                    }
                                });
                                Thread botThread = new Thread(s);
                                botThread.setDaemon(true);
                                botThread.start();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }); // eoT - JFX
                }
            }); // eoT - ?

            thread.start();
            Thread.sleep(1000); // Give threads 1 second.

            assertTrue(""+jFxThread+" != "+onSucceededThread+" but they should be the same.",jFxThread.equals(onSucceededThread));
            assertTrue(""+jFxThread+" == "+onCallThread+" but shouldn't be the same.",!jFxThread.equals(onCallThread));
            System.out.println(
                    "Method\t\t| Thread\n" +
                    "------------+-------\n"+
                    "start()\t\t| javaFX\n" +
                    "call()\t\t| other\n" +
                    "onSuccess()\t| javaFX");
        }

    }
}
