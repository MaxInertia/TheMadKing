package game_logic;

import game_logic.pieces.Piece;

/**
 *
 */
public class Game {

    enum Team {
        MAN, BEAST
    }

    private Team currentTurn;

    public Game() {
        Board.instance = new Board();
        currentTurn = Team.MAN;
    }

    /**
     * Returns the team whos turn it currently is.
     */
    public Team getActiveTeam() {
        return currentTurn;
    }

    /**
     * Called when a player specifies a piece and the location it is to be moved.
     *
     * If the move is valid the board will be modified accordingly, then the other team will be able to take their turn.
     * If the move is not valid, the board will not change and it will still be that players turn.
     *
     * @param x0 Piece location x-coordinate / column
     * @param y0 Piece location y-coordinate / row
     * @param x New location x-coordinate / column
     * @param y New location y-coordinate / row
     * @return True if the move was valid, false if it was not
     */
    public boolean turnMove(int x0, int y0, int x, int y) {
        boolean retVal = false;
        Piece.Type pieceType = Board.instance.getBoard()[x0][y0].getType();
        if(currentTurn==Team.MAN) {
            if(pieceType==Piece.Type.GUARD || pieceType==Piece.Type.KING) {
                retVal = Board.instance.move(x0, y0, x, y);

            }
        } else {
            if(pieceType== Piece.Type.DRAGON) {
                retVal = Board.instance.move(x0, y0, x, y);
            }
        }

        if(retVal) {
            switchTurns();
            return true;
        }
        return false;
    }

    /**
     * Switches the turn to the other team.
     */
    private void switchTurns() {
        if(currentTurn==Team.MAN) currentTurn = Team.BEAST;
        else currentTurn = Team.MAN;
    }

    public void printBoard() {
        System.out.println();
        for(int y=4; y>=0; y--) {
            System.out.print(""+y+"  ");
            for(int x=0; x<5; x++) {
                Piece piece = Board.instance.getBoard()[x][y];
                if(piece==null) {
                    System.out.print("-");
                } else if(piece.getType() == Piece.Type.DRAGON) {
                    System.out.print("D");
                } else if(piece.getType() == Piece.Type.GUARD) {
                    System.out.print("G");
                } else if(piece.getType() == Piece.Type.KING) {
                    System.out.print("K");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("   0 1 2 3 4\n");
    }


}
