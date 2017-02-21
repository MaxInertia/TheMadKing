package views.game_view;

import game.history.History;
import game.logic.Game;
import javafx.scene.canvas.Canvas;

/**
 *
 */
public class GameViewModel extends Graphics {

    static GameViewModel instance;
    History history;
    Canvas canvas;
    Game game;

    GameViewModel(Canvas canvas) {
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        game = new Game();
        history = new History();
        loadImages();
        instance = this;

        drawBackground();
        drawCells();
        drawPieces(instance.game);
    }

    public static void redraw() {
        instance.drawBackground();
        instance.drawCells();
        instance.drawPieces(instance.game);
    }

    public static void redraw(int row, int column) {
        instance.drawBackground();
        instance.highlightCell(row, column);
        instance.drawCells();
        instance.drawPieces(instance.game);
    }

    public static void move(int row, int column, int newRow, int newColumn) {
        boolean validMode = instance.game.turnMove(row, column, newRow, newColumn);
        if(validMode) {
            instance.history.addMove(row, column, newRow, newColumn);
            redraw();
        }
        if(instance.game.gameOver()) {
            if(instance.game.getActiveTeam().equals(Game.Team.BEAST)) {
                instance.drawGameOver("The Mad King Wins!");
            } else {
                instance.drawGameOver("The Dragons Win!");
            }
        }
    }

}
