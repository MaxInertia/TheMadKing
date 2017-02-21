package views.game_view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    GameViewModel gvm;

    @FXML
    Canvas canvas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MouseEvents mouseEvents = new MouseEvents();
        canvas.setOnMousePressed(mouseEvents.getMousePressListener());
        canvas.setOnMouseReleased(mouseEvents.getMouseReleaseListener());

        gvm = new GameViewModel(canvas);
    }

}
