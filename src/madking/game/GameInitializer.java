package madking.game;

import madking.game.logic.DupBoard;
import madking.game.logic.Game;
import madking.game.logic.Initializable;
import madking.game.logic.Updateable;
import madking.gui.Display;
import madking.players.Player;
import madking.players.bot.BotPlayer;
import madking.players.human.HumanPlayer;

/**
 * Created by MaxInertia on 2017-03-03
 */
public class GameInitializer {

    private GameInitializer(){}

    public static Bundle initialize(Display display, boolean player1_isHuman, boolean player2_isHuman) {
        GameInitializer init = new GameInitializer();
        Bundle bundle = init.new Bundle();

        Initializable game = Initializable.instance;
        Player[] players = new Player[2];

        if(player1_isHuman) {
            players[0] = new HumanPlayer((Updateable)game, 1);
            bundle.player1 = (HumanPlayer) players[0];
        } else {
            players[0] = new BotPlayer((Updateable)game, 1);
        }

        if(player2_isHuman) {
            players[1] = new HumanPlayer((Updateable)game, 2);
            bundle.player2 = (HumanPlayer) players[1];
        } else {
            players[1] = new BotPlayer((Updateable)game, 2);
        }

        Initializable.instance.initialize(display, players);
        return bundle;
    }

    public class Bundle {
        //DupBoard initialGameBoard;
        HumanPlayer player1;
        HumanPlayer player2;

        public HumanPlayer getPlayer1() {
            return this.player1;
        }

        public HumanPlayer getPlayer2() {
            return this.player2;
        }

        /*public DupBoard getInitialGameBoard() {
            return initialGameBoard;
        }*/

        Bundle() {
            player1 = null;
            player2 = null;
            //initialGameBoard = null;
        }
    }

}
