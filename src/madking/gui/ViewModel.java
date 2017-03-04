package madking.gui;

import madking.game.GameInitializer;
import madking.game.logic.DupBoard;
import javafx.scene.canvas.Canvas;
import madking.players.human.HumanPlayer;

/**
 *
 */
public class ViewModel extends Graphics implements Display {

    private static ViewModel instance;
    private DupBoard board;

    private HumanPlayer player1;
    private HumanPlayer player2;
    private int playerTurn;

     ViewModel(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
        loadImages();
        playerTurn = 1;
        instance = this;

        GameInitializer.Bundle startupBundle = GameInitializer.initialize(
                (Display) this,
                false,
                true);
        player1 = startupBundle.getPlayer1();
        player2 = startupBundle.getPlayer2();
        //board = startupBundle.getInitialGameBoard();
        //redraw();
    }

    boolean inputMove(int r, int c, int rF, int cF) {
         if(playerTurn == 1 && player1!=null) {
             if(player1.move(r, c, rF, cF)) {
                 playerTurn = 2;
                 return true;
             }
         } else if(playerTurn == 2 && player2!=null) {
             if(player2.move(r, c, rF, cF)) {
                 playerTurn = 1;
                 return true;
             }
         }
         return false;
    }

    public static void updateBoard(DupBoard board) {
         instance.board = board;
         if(instance.playerTurn==1) instance.playerTurn = 2;
         else instance.playerTurn = 1;
         redraw();
    }

    private static void redraw() {
        System.out.println("redraw() called!");
        instance.drawBackground();
        instance.drawCells();
        instance.drawPieces(instance.board.getCells());
    }

    void redraw(int row, int column) {
        drawBackground();
        highlightCell(row, column);
        drawCells();
        drawPieces(board.getCells());
    }

    private void setupPlayers(HumanPlayer player1, HumanPlayer player2) {
        // TODO: Player selection
    }

    @Override
    public void updateDisplay(DupBoard board) {
        this.board = board;
        redraw();
    }
}
