package players.human;

import game.history.History;
import game.history.Move;
import game.logic.DupBoard;
import game.logic.Game;
import game.logic.Updateable;
import javafx.scene.canvas.Canvas;

/**
 *
 */
public class ViewModel extends Graphics {

    private static ViewModel instance;
    History history;
    Canvas canvas;
    Updateable updater;
    DupBoard board;
    
     ViewModel(Canvas canvas, Updateable updater, DupBoard board) {
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        history = new History();
        this.updater = updater;
        this.board = board;
        loadImages();
        instance = this;

        drawBackground();
        drawCells();
        drawPieces(instance.board.getCells());
    }

    public void updateBoard(DupBoard board) {
         this.board = board;
         redraw();
    }

    public static void redraw() {
        instance.drawBackground();
        instance.drawCells();
        instance.drawPieces(instance.board.getCells());
    }

    public static void redraw(int row, int column) {
        instance.drawBackground();
        instance.highlightCell(row, column);
        instance.drawCells();
        instance.drawPieces(instance.board.getCells());
    }

    public static void move(int row, int column, int newRow, int newColumn) {
        boolean validMode = instance.updater.update(new Move(row, column, newRow, newColumn));
        if(validMode) {
            instance.history.addMove(row, column, newRow, newColumn);
            redraw();
        } else {
            //TODO: Display error message to Player
        }
        /*
        if(instance.board.getCells().gameOver()) {
            if(instance.board.getCells().getActiveTeam().equals(Game.Team.BEAST)) {
                instance.drawGameOver("The Mad King Wins!");
            } else {
                instance.drawGameOver("The Dragons Win!");
            }
        }*/
    }

}
