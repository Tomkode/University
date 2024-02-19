package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(GUI.class.getResource("sample.fxml"));
        Parent root = loader.load();
        SelectController controller = loader.getController();
        Scene scene = new Scene(root, 1000, 720, Color.PINK);

        primaryStage.setTitle("Program Selection");
        primaryStage.setScene(scene);
        primaryStage.show();

        FXMLLoader loader2 = new FXMLLoader(GUI.class.getResource("details.fxml"));
        Parent root2 = loader2.load();
        MainController maincontroller = loader2.getController();
        controller.setMainController(maincontroller);
        Scene scene2 = new Scene(root2, 1000, 720, Color.PINK);
        Stage detailsStage = new Stage();

        detailsStage.setTitle("Program Details");
        detailsStage.setScene(scene2);
        detailsStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
