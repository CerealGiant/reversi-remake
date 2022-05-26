package com.example.reversi;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.MotionBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;

public class HelloController {

    private ArrayList<menuButtons> startMenuButtons;
    private ArrayList<Button> Board;
    private ArrayList<menuButtons> endGameButtons;
    private ArrayList<menuButtons> pauseButtons;
    private ArrayList<menuButtons> aiButtons;
    private reversiLogic r;
    private AnchorPane gameScreen;
    private Boolean paused = false;
    private Scene pauseScene;
    private Scene gameScene;
    private Scene aiScene;
    private View v;
    private Stage s;
    private AI_Algo algo = null;
    private ArrayList<Pair<Integer,Integer>> possible_positions = new ArrayList<>();
    private ArrayList<ArrayList<String>> board = new ArrayList<>();



    public HelloController(View v, Stage s,Scene scene) {
        this.v = v;
        this.s = s;
        setView(v,s,scene);
    }


    public void setView(View v,Stage s,Scene homeScene) {

        startMenuButtons = v.getStartMenuButtons();
        gameScreen = v.GetGameScreen();
        pauseButtons = v.GetPauseButtons();
        endGameButtons = v.getEndGameButtons();
        aiButtons = v.GetAIButtons();
        Board = v.GetBoardButtons();
        r = v.getLogic();

        pauseScene = new Scene(v.GetPauseScreen(), 800, 640);
        gameScene = new Scene(v.GetGameScreen(),800,640);
        aiScene = new Scene(v.GetAIScreen(),800,640);

        for(menuButtons b: startMenuButtons) {
            if(b.getText().equals("Start") ) {
                b.setOnAction(new StartMenuCommands.startGame(s,aiScene));
            }
        }


        board  = r.GetBoard();
        possible_positions = r.GetPositions();


        for(menuButtons b: aiButtons) {
            if(b.getText().equals("Random") ) {
                b.setOnAction(new aiScreenCommands.randomAI(s,gameScene,possible_positions,r,algo,Board));
            }else if(b.getText().equals("Max")) {
                b.setOnAction(new aiScreenCommands.mostPiecesAI(s,gameScene,possible_positions,board," B ",r,Board));
            }
        }


        for(menuButtons btn: pauseButtons) {
            if(btn.getText().equals("Resume")) {
                btn.setOnAction(new pauseScreenCommands.resume(s,gameScene));
            }
            if(btn.getText().equals("Restart")) {
                btn.setOnAction(new pauseScreenCommands.restart(v.GetGameScreen(),s,gameScene,r,Board));
            }
            if(btn.getText().equals("Main Menu")) {
                btn.setOnAction(new pauseScreenCommands.home(s,homeScene,r,Board));
            }
        }


//        for(Button b: Board) {
//            b.setOnAction(new reversiLogic.GameButtonCommands(b,r,algo));
//        }


        for(menuButtons b: endGameButtons) {
            if(b.getText().equals("Replay")) {
                b.setOnAction(new pauseScreenCommands.restart(v.GetGameScreen(),s,gameScene,r,Board));
            }else if(b.getText().equals("Main Menu")) {
                b.setOnAction(new pauseScreenCommands.home(s,homeScene,r,Board));
            }
        }


        gameScreen.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                        s.setScene(pauseScene);
                    }
            }
        });


    }


}