package game.logic;

import game.history.Move;

/**
 * Created by MaxInertia on 2017-03-02.
 */
public interface Updateable {

    /**
     * Called when a player specifies a piece and the location it is to be moved.
     *
     * If the move is valid the board will be modified accordingly, then the other team will be able to take their turn.
     * If the move is not valid, the board will not change and it will still be that players turn.
     *
     * @param move The move the player has chosen to make on their turn
     * @return True if the move was valid, false if it was not
     */
    public boolean update(Move move);

}
