package com.example.reversi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        View v = new View(stage);
        Scene homeScene = new Scene(v.getStartMenu(),800,640);
        HelloController h = new HelloController(v,stage,homeScene);
        stage.setScene(homeScene);
        stage.setTitle("Reversi");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}