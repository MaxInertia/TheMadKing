package views.game_view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static views.game_view.graphics.Constants.BOARD_PADDING;
import static views.game_view.graphics.Constants.CELL_WIDTH;
import static views.game_view.graphics.Constants.COLUMN_ROW_COUNT;

/**
 *
 */
public class GameViewModel {

    Canvas canvas;
    GraphicsContext gc;

    GameViewModel(Canvas canvas) {
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        drawBoard();
    }

    void drawBoard() {
        gc.setFill(Color.ANTIQUEWHITE);
        gc.fillRect(
                BOARD_PADDING,
                BOARD_PADDING,
                COLUMN_ROW_COUNT*CELL_WIDTH,
                COLUMN_ROW_COUNT*CELL_WIDTH
        );

        for(int multiplier=0; multiplier<=COLUMN_ROW_COUNT; multiplier++) {
            gc.strokeLine(
                    BOARD_PADDING+CELL_WIDTH*multiplier,
                    BOARD_PADDING,
                    BOARD_PADDING+CELL_WIDTH*multiplier,
                    COLUMN_ROW_COUNT*CELL_WIDTH+BOARD_PADDING
            );
            gc.strokeLine(
                    BOARD_PADDING,
                    BOARD_PADDING+CELL_WIDTH*multiplier,
                    COLUMN_ROW_COUNT*CELL_WIDTH+BOARD_PADDING,
                    BOARD_PADDING+CELL_WIDTH*multiplier
            );
        }

    }

}
