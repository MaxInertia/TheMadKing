package game_logic.pieces;

import game_logic.Board;

/**
 *
 */
public class Dragon extends Piece {

    public Dragon(int x, int y) {
        super(x,y);
        type = Type.DRAGON;
    }

    @Override
    public boolean move(int x, int y) {
        // Check if location is adjacent or kitty-corner from current position
        if( Board.instance.getBoard()[x][y] == null && (Math.abs(xPos-x) <= 1) && (Math.abs(yPos-y) <= 1) ) {
            return super.move(x,y);

        } else {
            return false;
        }
    }

}
