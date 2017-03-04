package madking;

import madking.game.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import madking.gui.HumanController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(HumanController.class.getResource("game.fxml"));
        primaryStage.setTitle("The Mad King");

        int length = Constants.CELL_WIDTH*Constants.COLUMN_ROW_COUNT + 2*Constants.BOARD_PADDING;

        primaryStage.setScene(new Scene(
                root,
                length,
                length));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
