package madking.game.logic;

import madking.gui.Display;
import madking.players.Player;

/**
 * Created by MaxInertia on 2017-03-03.
 */
public interface Initializable {

    Initializable instance = new Game();

    void initialize(Display display, Player[] players);

}
