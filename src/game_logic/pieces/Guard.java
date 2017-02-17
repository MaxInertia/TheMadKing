package game_logic.pieces;

import game_logic.Board;

/**
 *
 */
public class Guard extends Piece {

    public Guard(int x, int y) {
        super(x,y);
        type = Type.GUARD;
    }

    @Override
    public boolean move(int x, int y) {
        //System.out.println("Guard.move()");
        boolean validMove;

        // Check if location is adjacent to current position
        //System.out.println("Guard 1");
        if( (Math.abs(xPos-x) + Math.abs(yPos-y)) == 1
                && Board.instance.getBoard()[x][y] == null) {
            return super.move(x, y);
        }

        // Check if Dragon can be moved into
        //System.out.println("Guard 2");
        if(Board.instance.getBoard()[x][y] != null){
            if(Board.instance.getBoard()[x][y].getType()==Piece.Type.DRAGON) {

                int numberSurrounding = 0;

                if(x-1 >= 0) {
                    Piece piece = Board.instance.getBoard()[x-1][y];
                    if(piece!=null && (piece.getType()==Type.KING || piece.getType()==Type.GUARD) ) {
                        numberSurrounding++;
                    }
                }
                if(x+1 <= 4) {
                    Piece piece = Board.instance.getBoard()[x+1][y];
                    if(piece!=null && (piece.getType()==Type.KING || piece.getType()==Type.GUARD) ) {
                        numberSurrounding++;
                    }
                }
                if(y-1 >= 0) {
                    Piece piece = Board.instance.getBoard()[x][y-1];
                    if(piece!=null && (piece.getType()==Type.KING || piece.getType()==Type.GUARD) ) {
                        numberSurrounding++;
                    }
                }
                if(y+1 <= 4) {
                    Piece piece = Board.instance.getBoard()[x][y+1];
                    if(piece!=null && (type==Type.KING || type==Type.GUARD) ) {
                        numberSurrounding++;
                    }
                }

                if(numberSurrounding >= 2) {
                    Board.instance.getBoard()[x][y] = null;
                    validMove = super.move(x,y);
                    return validMove; // -- EXIT 2
                }
            }
        }

        return false;
    }



}
