package views.game_view;

import game.logic.Game;
import game.pieces.Piece;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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

    Image kingImage;
    Image guardImage;
    Image dragonImage;

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
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(
                BOARD_PADDING,
                BOARD_PADDING,
                COLUMN_ROW_COUNT*CELL_WIDTH,
                COLUMN_ROW_COUNT*CELL_WIDTH
        );
    }

    void drawCells() {
        for(int multiplier=0; multiplier<=COLUMN_ROW_COUNT; multiplier++) {
            gc.setStroke(Color.BLACK);
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
                Image image = null;

                if (cells[row][column].getType().equals(Piece.Type.KING)) {
                    letter = "K";
                    image = kingImage;
                } else if (cells[row][column].getType().equals(Piece.Type.GUARD)) {
                    letter = "G";
                    image = guardImage;
                } else if (cells[row][column].getType().equals(Piece.Type.DRAGON)) {
                    letter = "D";
                    image = dragonImage;
                }

                //gc.strokeText(
                //        letter,
                //        (column * CELL_WIDTH) + BOARD_PADDING + CELL_WIDTH / 2,
                //        (row * CELL_WIDTH) + BOARD_PADDING + CELL_WIDTH / 2
                //);
                gc.drawImage(
                        image,
                        (column * CELL_WIDTH) + BOARD_PADDING,
                        (row * CELL_WIDTH) + BOARD_PADDING
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

    void loadImages() {
        String[] imgPathNames = new String[3];
        if(CELL_WIDTH<=64) {
            imgPathNames[0] = "resources/old-king_64.png";
            imgPathNames[1] = "resources/barbute_64.png";
            imgPathNames[2] = "resources/alien-stare_64.png";
        } else if (CELL_WIDTH > 64){
            imgPathNames[0] = "resources/old-king_128.png";
            imgPathNames[1] = "resources/barbute_128.png";
            imgPathNames[2] = "resources/alien-stare_128.png";
        }

        kingImage = new Image(
                getClass().getResourceAsStream(imgPathNames[0]),
                CELL_WIDTH,
                CELL_WIDTH,
                false,
                false
        );
        guardImage = new Image(
                getClass().getResourceAsStream(imgPathNames[1]),
                CELL_WIDTH,
                CELL_WIDTH,
                false,
                false
        );
        dragonImage = new Image(
                getClass().getResourceAsStream(imgPathNames[2]),
                CELL_WIDTH,
                CELL_WIDTH,
                false,
                true
        );
    }
}
