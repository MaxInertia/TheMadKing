package game_logic.pieces;

import game_logic.Board;

/**
 *
 */
public class King extends Guard {

    public King(int x, int y) {
        super(x,y);
        type = Type.KING;
    }

    @Override
    public boolean move(int x, int y) {
        //System.out.println("King.move()");
        boolean validMove = true;

        //System.out.println("King 1 (Guard)");
        validMove = super.move(x,y); // Check the Guard movement rules (subset of King movement rules)
        if(validMove) return true;

        //System.out.println("King 2");
        // Check if the king can reach x,y by jumping over a guard
        if( (yPos == y) && (Math.abs(xPos-x) == 2) ) {
            //System.out.println("King 2 - horizontal jump");
            int intermediateX = (xPos+x)/2;
            Piece piece = Board.instance.getBoard()[intermediateX][y];
            if(piece != null && piece.getType()==Type.GUARD) {
                //System.out.println("King 2 - horizontal jump SUCCESS");
                return simpleMove(x,y);
            }

        } else if( (xPos == x) && (Math.abs(yPos-y) == 2) ) {
            //System.out.println("King 2 - vertical jump");
            int intermediateY = (yPos+y)/2;
            Piece piece = Board.instance.getBoard()[x][intermediateY];
            if(piece != null && piece.getType()==Type.GUARD) {
                //System.out.println("King 2 - vertical jump SUCCESS");
                return simpleMove(x,y);
            }
        }

        return false;
    }
}
