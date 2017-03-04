package util;

import madking.game.pieces.Piece;
import madking.game.pieces.Type;

/**
 * Contains methods that aid in testing classes within the program in various packages.
 */
public class TestUtilities {

    public static void printBoard(Piece[][] board) {
        System.out.println();
        for(int x=0; x<5; x++) {
            System.out.print(""+x+"  ");
            for(int y=0; y<5; y++) {
                Piece piece = board[x][y];
                if(piece==null) {
                    System.out.print("-");
                } else if(piece.getType() == Type.DRAGON) {
                    System.out.print("D");
                } else if(piece.getType() == Type.GUARD) {
                    System.out.print("G");
                } else if(piece.getType() == Type.KING) {
                    System.out.print("K");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("   0 1 2 3 4\n");
    }

}
