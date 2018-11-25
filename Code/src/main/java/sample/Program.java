package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Program extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start method is vital to initializing key components such as our Stage (Window)
     * Also one of the first methods called in the program
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Maze Generator");
        primaryStage.setScene(new Scene(root, 450, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


}
