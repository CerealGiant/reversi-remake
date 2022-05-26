package com.example.reversi;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;

public class aiScreenCommands {

    public static class randomAI implements ICommand, EventHandler<ActionEvent> {

        private Stage s;
        private Scene destScene;
        private AI_Algo algo = null;
        private ArrayList<Pair<Integer,Integer>> possible_positions = new ArrayList<>();
        private reversiLogic r;
        private ArrayList<Button> Board = new ArrayList<>();


        public randomAI(Stage s,Scene dest,ArrayList<Pair<Integer,Integer>> positions,reversiLogic r,AI_Algo algo,ArrayList<Button> Board) {
            this.s = s;
            destScene = dest;
            possible_positions = positions;
            this.r = r;
            this.algo = algo;
            this.Board = Board;
        }

        @Override
        public void execute() {
            System.out.println("CLIKCED HEREE");
            algo = new randomAlgorithm(possible_positions);
            r.setAlgo(algo);
            s.setScene(destScene);
            for(Button b: Board) {
                b.setOnAction(new reversiLogic.GameButtonCommands(b,r,algo));
            }
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            execute();
        }
    }

    public static class mostPiecesAI implements ICommand, EventHandler<ActionEvent> {

        private Stage s;
        private Scene destScene;
        private AI_Algo algo = null;
        private ArrayList<Pair<Integer,Integer>> possible_positions = new ArrayList<>();
        private ArrayList<ArrayList<String>> board;
        private String player;
        private reversiLogic r;
        private ArrayList<Button> Board = new ArrayList<>();


        public mostPiecesAI(Stage s,Scene dest,ArrayList<Pair<Integer,Integer>> positions,ArrayList<ArrayList<String>> board, String player, reversiLogic r,ArrayList<Button> Board) {
            this.s = s;
            destScene = dest;
            possible_positions = positions;
            this.board = board;
            this.player = player;
            this.r = r;
            this.Board = Board;
        }


        @Override
        public void execute() {
            algo = new mostPiecesAlgorithm(possible_positions,board,player);
            r.setAlgo(algo);
            s.setScene(destScene);
            for(Button b: Board) {
                b.setOnAction(new reversiLogic.GameButtonCommands(b,r,algo));
            }
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            execute();
        }
    }
}
