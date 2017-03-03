package players.human;

import game.logic.DupBoard;
import game.logic.Game;
import game.logic.Updateable;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import players.Player;

import java.net.URL;
import java.util.ResourceBundle;

import static players.human.utilities.Constants.BOARD_PADDING;
import static players.human.utilities.Constants.CELL_WIDTH;
import static players.human.utilities.Constants.COLUMN_ROW_COUNT;

public class HumanController implements Initializable, Player {

    Updateable game;
    ViewModel gvm;

    private MousePressed mPressed;
    private MouseReleased mReleased;

    @FXML
    Canvas canvas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mPressed = new MousePressed();
        mReleased = new MouseReleased();
        canvas.setOnMousePressed(mPressed);
        canvas.setOnMouseReleased(mReleased);

        Game temp = new Game(this);
        gvm = new ViewModel(canvas, temp, new DupBoard(temp.getBoard()));
        game =  temp;
    }

    @Override
    public void notify(DupBoard board) {
        System.out.println("It is now your turn!"); //TODO: Replace this with a graphical alert informing player of turn.
    }

    @Override
    public void update(DupBoard board) {
        gvm.updateBoard(board);
    }

    public class MousePressed implements EventHandler<MouseEvent> {

        int row = -1;
        int column = -1;

        @Override
        public void handle(MouseEvent event) {
            row = (int) ((event.getY() - BOARD_PADDING) / CELL_WIDTH);
            column = (int) ((event.getX() - BOARD_PADDING) / CELL_WIDTH);
            System.out.println("Press: ("+row+','+column+")");

            ViewModel.redraw(row,column);
        }
    }

    public class MouseReleased implements EventHandler<MouseEvent> {

        private int row = -1;
        private int column = -1;

        @Override
        public void handle(MouseEvent event) {
            row = (int) ((event.getY() - BOARD_PADDING) / CELL_WIDTH);
            column = (int) ((event.getX() - BOARD_PADDING) / CELL_WIDTH);
            System.out.println("Release: ("+row+','+column+")");

            if(mPressed.row != -1 && mPressed.column != -1) {
                if(row>=0 && row<COLUMN_ROW_COUNT && column>=0 && column<COLUMN_ROW_COUNT) {
                    ViewModel.move(mPressed.row, mPressed.column, row, column);
                }
            }

            mPressed.row = -1;
            mPressed.column = -1;
            row = -1;
            column = -1;
        }
    }
}
