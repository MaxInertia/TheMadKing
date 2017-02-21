package views.game_view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import static views.game_view.Constants.BOARD_PADDING;
import static views.game_view.Constants.CELL_WIDTH;
import static views.game_view.Constants.COLUMN_ROW_COUNT;

/**
 *
 */
public class MouseEvents {

    MousePressed mPressed;
    MouseReleased mReleased;

    public MouseEvents() {
        mPressed = new MousePressed();
        mReleased = new MouseReleased();
    }

    public MousePressed getMousePressListener() {
        return mPressed;
    }

    public MouseReleased getMouseReleaseListener() {
        return mReleased;
    }

    public class MousePressed implements EventHandler<MouseEvent> {

        int row = -1;
        int column = -1;

        @Override
        public void handle(MouseEvent event) {
            //System.out.println(event.getEventType());
            row = (int) ((event.getY() - BOARD_PADDING) / CELL_WIDTH);
            column = (int) ((event.getX() - BOARD_PADDING) / CELL_WIDTH);
            System.out.println("Press: ("+row+','+column+")");

            GameViewModel.redraw(row,column);
        }
    }

    public class MouseReleased implements EventHandler<MouseEvent> {

        private int row = -1;
        private int column = -1;

        @Override
        public void handle(MouseEvent event) {
            //System.out.println(event.getEventType());
            row = (int) ((event.getY() - BOARD_PADDING) / CELL_WIDTH);
            column = (int) ((event.getX() - BOARD_PADDING) / CELL_WIDTH);
            System.out.println("Release: ("+row+','+column+")");

            if(mPressed.row != -1 && mPressed.column != -1) {
                if(row>=0 && row<COLUMN_ROW_COUNT && column>=0 && column<COLUMN_ROW_COUNT) {
                    GameViewModel.move(mPressed.row, mPressed.column, row, column);
                }
            }

            mPressed.row = -1;
            mPressed.column = -1;
            row = -1;
            column = -1;
        }
    }

}
