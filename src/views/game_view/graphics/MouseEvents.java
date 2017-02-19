package views.game_view.graphics;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

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
        @Override
        public void handle(MouseEvent event) {

        }
    }

    public class MouseReleased implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {

        }
    }

}
