package com.example.reversi;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class pauseScreenCommands {

    public static class resume implements ICommand, EventHandler<ActionEvent> {


        private Stage s;
        private Scene destScene;


        public resume(Stage s, Scene dest) {
            this.s = s;
            destScene = dest;
        }

        @Override
        public void execute() {
            System.out.println("WORKING");
            s.setScene(destScene);
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            execute();
        }
    }
    public static class restart implements ICommand, EventHandler<ActionEvent> {

        private AnchorPane destScreen;
        private Stage s;
        private Scene destScene;
        private reversiLogic r;
        private ArrayList<Button> board = new ArrayList<>();


        public restart(AnchorPane d, Stage s, Scene dest, reversiLogic r, ArrayList<Button> b) {
            destScreen = d;
            this.s = s;
            destScene = dest;
            this.r = r;
            this.board = b;
        }

        @Override
        public void execute() {

            String white_player = "-fx-background-color: transparent; \n"  +
                    "-fx-background-image: url('file:src/main/java/com/example/reversi/ButtonResources/player_white.png');";
            String black_player = "-fx-background-color: transparent; \n"  +
                    "-fx-background-image: url('file:src/main/java/com/example/reversi/ButtonResources/player_black.png');";
            String No_Player = "-fx-background-color: transparent";

            r.restartGame();

            r.showMoves();
            r.updatePieceCount();


            r.updateAll();

            s.setScene(destScene);
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            execute();
        }
    }
    public static class home implements ICommand, EventHandler<ActionEvent> {


        Stage s;
        private Scene destScene;
        private reversiLogic r;
        private ArrayList<Button> board = new ArrayList<>();

        public home(Stage s,Scene d,reversiLogic r,ArrayList<Button> board) {
            this.s = s;
            destScene = d;
            this.r = r;
            this.board = board;
        }

        @Override
        public void execute() {
            String white_player = "-fx-background-color: transparent; \n"  +
                    "-fx-background-image: url('file:src/main/java/com/example/reversi/ButtonResources/player_white.png');";
            String black_player = "-fx-background-color: transparent; \n"  +
                    "-fx-background-image: url('file:src/main/java/com/example/reversi/ButtonResources/player_black.png');";
            String No_Player = "-fx-background-color: transparent";

            r.restartGame();

            r.showMoves();
            r.updatePieceCount();


            r.updateAll();

            s.setScene(destScene);
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            execute();
        }
    }

}
