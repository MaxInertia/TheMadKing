package views.game_view;

import game.logic.Game;
import game.pieces.Piece;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static views.game_view.Constants.BOARD_PADDING;
import static views.game_view.Constants.CELL_WIDTH;
import static views.game_view.Constants.COLUMN_ROW_COUNT;

/**
 *
 */
public abstract class Graphics {
    GraphicsContext gc;

    void highlightCell(int row, int column) {
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(
                BOARD_PADDING + column*CELL_WIDTH,
                BOARD_PADDING + row*CELL_WIDTH,
                CELL_WIDTH,
                CELL_WIDTH
        );
    }

    void drawBackground() {
        gc.setFill(Color.ANTIQUEWHITE);
        gc.fillRect(
                BOARD_PADDING,
                BOARD_PADDING,
                COLUMN_ROW_COUNT*CELL_WIDTH,
                COLUMN_ROW_COUNT*CELL_WIDTH
        );
    }

    void drawCells() {
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

    void drawPieces(Game game) {
        Piece[][] cells = game.getBoard();
        for(int row=0; row<COLUMN_ROW_COUNT; row++) {
            for (int column = 0; column < COLUMN_ROW_COUNT; column++) {
                if(cells[row][column]==null) continue;
                String letter = "-";

                if (cells[row][column].getType().equals(Piece.Type.KING)) {
                    letter = "K";
                } else if (cells[row][column].getType().equals(Piece.Type.GUARD)) {
                    letter = "G";
                } else if (cells[row][column].getType().equals(Piece.Type.DRAGON)) {
                    letter = "D";
                }

                gc.strokeText(
                        letter,
                        (column * CELL_WIDTH) + BOARD_PADDING + CELL_WIDTH / 2,
                        (row * CELL_WIDTH) + BOARD_PADDING + CELL_WIDTH / 2
                );
            }
        }
    }

    void drawGameOver(String winnerString) {
        gc.setFont(new Font(24));
        gc.setStroke(Color.RED);
        gc.setFill(Color.RED);
        gc.strokeText(
                winnerString,
                CELL_WIDTH*COLUMN_ROW_COUNT/8,
                CELL_WIDTH*COLUMN_ROW_COUNT/2
                );
    }
}
