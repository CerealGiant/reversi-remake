package com.example.reversi;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StartMenuCommands {

    public static class startGame implements ICommand, EventHandler<ActionEvent> {


        Stage s;
        private Scene destScene;

        public startGame(Stage s,Scene dest) {
            this.s = s;
            destScene = dest;
        }

        @Override
        public void execute() {
            s.setScene(destScene);
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            execute();
        }
    }

}
