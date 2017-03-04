package madking.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static madking.game.Constants.BOARD_PADDING;
import static madking.game.Constants.CELL_WIDTH;
import static madking.game.Constants.COLUMN_ROW_COUNT;

public class HumanController implements Initializable {

    ViewModel viewModel;

    private MousePressed mPressed;
    private MouseReleased mReleased;

    @FXML
    Canvas canvas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mPressed = new MousePressed();
        mReleased = new MouseReleased();
        canvas.setWidth(520);
        canvas.setHeight(520);
        setupGame();
    }

    private void setupGame() {
        viewModel = new ViewModel(canvas);
        canvas.setOnMousePressed(mPressed);
        canvas.setOnMouseReleased(mReleased);
    }

    private class MousePressed implements EventHandler<MouseEvent> {
        int row = -1;
        int column = -1;

        @Override
        public void handle(MouseEvent event) {
            row = (int) ((event.getY() - BOARD_PADDING) / CELL_WIDTH);
            column = (int) ((event.getX() - BOARD_PADDING) / CELL_WIDTH);

            viewModel.redraw(row,column);
        }
    }

    private class MouseReleased implements EventHandler<MouseEvent> {
        private int row = -1;
        private int column = -1;

        @Override
        public void handle(MouseEvent event) {
            row = (int) ((event.getY() - BOARD_PADDING) / CELL_WIDTH);
            column = (int) ((event.getX() - BOARD_PADDING) / CELL_WIDTH);

            if(mPressed.row != -1 && mPressed.column != -1) {
                if(row>=0 && row<COLUMN_ROW_COUNT && column>=0 && column<COLUMN_ROW_COUNT) {
                    viewModel.inputMove(mPressed.row, mPressed.column, row, column);
                }
            }
            //TODO: ADD CALL TO viewModel.redraw(); ??
            //viewModel.redraw();

            mPressed.row = -1;
            mPressed.column = -1;
            row = -1;
            column = -1;
        }
    }
}
