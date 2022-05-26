package com.example.reversi;

import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class View implements gameObserver,Game{

    private static final int SCREEN_HEIGHT = 640;
    private static final int SCREEN_WIDTH = 800;
    private ArrayList<menuButtons> startMenuButtons = new ArrayList<>();
    private ArrayList<menuButtons> endGameButtons = new ArrayList<>();
    private ArrayList<Button> BoardButtons = new ArrayList<>();
    private ArrayList<menuButtons> pauseButtons = new ArrayList<>();
    private ArrayList<menuButtons> AIButtons = new ArrayList<>();
    private String white_player = "-fx-background-color: transparent; \n"  +
            "-fx-background-image: url('file:src/main/java/com/example/reversi/ButtonResources/player_white.png');";
    private String black_player = "-fx-background-color: transparent; \n"  +
            "-fx-background-image: url('file:src/main/java/com/example/reversi/ButtonResources/player_black.png');";

    private String No_Player = "-fx-background-color: transparent";



    private ImageView playerimg;
    private ImageView playerimg2;
    private Text p1;
    private Text p2;
    private Text pause_text;
    private Text no_of_white;
    private Text no_of_black;
    private Text Winner = new Text();



    private reversiLogic r;

    private AnchorPane startMenu;
    private AnchorPane gameScreen;
    private AnchorPane endScreen;
    private AnchorPane pauseScreen;
    private AnchorPane chooseAIScreen;

    private Stage s;
    private Scene scene;
    private ImageView winId1;
    private ImageView winId2;

    public View(Stage s) {
        startMenu = new AnchorPane();
        gameScreen = new AnchorPane();
        endScreen = new AnchorPane();
        pauseScreen = new AnchorPane();
        chooseAIScreen = new AnchorPane();
        scene = new Scene(endScreen,SCREEN_WIDTH,SCREEN_HEIGHT);
        r = new reversiLogic();
        r.addObserver(this);
        createStartMenu();
        CreateAIScreen();
        CreateGameScreen();
        CreateEndScreen();
        CreatePauseScreen();
        addLogo();
        setBackground();
        p1.setEffect(new DropShadow());
        playerimg.setEffect(new DropShadow());
        this.s = s;
    }



    private void setBackground() {
        String IMAGE_PATH = "src/main/java/com/example/reversi/ButtonResources/startBackground.jpg";
        Image i = null;
        try {
            i = new Image(new FileInputStream(IMAGE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BackgroundImage b = new BackgroundImage(i, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        Background background = new Background(b);
        startMenu.setBackground(background);
    }

    private void addLogo() {
        Image i = null;
        try {
            i = new Image(new FileInputStream("src/main/java/com/example/reversi/ButtonResources/logo.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView v = new ImageView(i);
        v.setLayoutX(480);
        v.setLayoutY(40);
        startMenu.getChildren().add(v);
    }

    private void createStartMenu() {

        int ButtonGap = 100;

        menuButtons Start = new menuButtons("Start");
        menuButtons stats = new menuButtons("Stats");
        menuButtons help = new menuButtons("Help");

        Start.setLayoutX(100);
        Start.setLayoutY(150);

        stats.setLayoutX(100);
        stats.setLayoutY(150+ButtonGap);

        help.setLayoutX(100);
        help.setLayoutY(150+2*ButtonGap);


        startMenu.getChildren().addAll(Start,stats,help);
        startMenuButtons.add(Start);
        startMenuButtons.add(stats);
        startMenuButtons.add(help);
    }

    private void CreateAIScreen() {

        String FONT_PATH = "src/main/java/com/example/reversi/ButtonResources/kenvector_future.ttf";

        menuButtons random = new menuButtons("Random");
        menuButtons mostPieces = new menuButtons("Max");
        AIButtons.add(random);
        AIButtons.add(mostPieces);


        Text t1 = new Text("CHOOSE AI");
        try {
            t1.setFont(Font.loadFont(new FileInputStream(FONT_PATH),25));
        } catch (FileNotFoundException e) {
            t1.setFont(Font.loadFont("Verdana",25));
        }

        t1.setLayoutX(250);
        t1.setLayoutY(100);

        random.setLayoutX(270);
        random.setLayoutY(100+100);

        mostPieces.setLayoutX(270);
        mostPieces.setLayoutY(100+100+100);


        chooseAIScreen.getChildren().addAll(t1,random,mostPieces);
    }

    private void CreateGameScreen() {
        String IMAGE_PATH = "src/main/java/com/example/reversi/ButtonResources/Board.png";
        String FONT_PATH = "src/main/java/com/example/reversi/ButtonResources/kenvector_future.ttf";
        String WINNING_INDICATOR_PATH = "src/main/java/com/example/reversi/ButtonResources/winning_indicator.png";

        Image i = null;
        try {
            i = new Image(new FileInputStream(IMAGE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView v = new ImageView(i);
        v.setLayoutX(125);
        v.setLayoutY(40);

        Image winningIndicator1 = null;
        try {
            winningIndicator1 = new Image(new FileInputStream(WINNING_INDICATOR_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        Image player = null;
        try {
            player = new Image(new FileInputStream("src/main/java/com/example/reversi/ButtonResources/playerimg.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Image player2 = null;
        try {
            player2 = new Image(new FileInputStream("src/main/java/com/example/reversi/ButtonResources/player2img.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        p1 = new Text("WHITE");
        p2 = new Text("BLACK");




        try {
            p1.setFont(Font.loadFont(new FileInputStream(FONT_PATH),12));
        } catch (FileNotFoundException e) {
            p1.setFont(Font.loadFont("Verdana",12));
        }




        try {
            p2.setFont(Font.loadFont(new FileInputStream(FONT_PATH),12));
        } catch (FileNotFoundException e) {
            p2.setFont(Font.loadFont("Verdana",12));
        }

        no_of_black = new Text("2");
        no_of_white = new Text("2");

        try {
            no_of_black.setFont(Font.loadFont(new FileInputStream(FONT_PATH),20));
        } catch (FileNotFoundException e) {
            no_of_black.setFont(Font.loadFont("Verdana",20));
        }

        try {
            no_of_white.setFont(Font.loadFont(new FileInputStream(FONT_PATH),20));
        } catch (FileNotFoundException e) {
            no_of_white.setFont(Font.loadFont("Verdana",20));
        }



        playerimg = new ImageView(player);
        playerimg.setLayoutX(20);
        playerimg.setLayoutY(100);
        p1.setLayoutX(30);
        p1.setLayoutY(100+80);
        no_of_white.setLayoutX(40);
        no_of_white.setLayoutY(180 + 40);
        winId1 = new ImageView(winningIndicator1);
        winId1.setLayoutX(30);
        winId1.setLayoutY(60);

        playerimg2 = new ImageView(player2);
        playerimg2.setLayoutX(695);
        playerimg2.setLayoutY(90);
        p2.setLayoutX(705);
        p2.setLayoutY(90+80);
        no_of_black.setLayoutX(715);
        no_of_black.setLayoutY(170 + 40);
        winId2 = new ImageView(winningIndicator1);
        winId2.setLayoutX(715);
        winId2.setLayoutY(50);


        gameScreen.getChildren().add(v);
        gameScreen.getChildren().addAll(playerimg,p1,playerimg2,p2,no_of_white,no_of_black,winId1,winId2);
        updateWinIndicator();

        //Adding and setting up the gameButtons
        int count = 0;
        for(int k = 0;k<8;k++) {
            for(int j=0;j<8;j++) {
                Button b = new Button();
                b.setPadding(new Insets(26,29,26,28));
                b.setLayoutX(125+70*j);
                b.setLayoutY(40+69*k);
                b.setUserData(count);
                count++;
                gameScreen.getChildren().add(b);
                if(k == 3 && j == 3) b.setStyle(white_player);
                else if(k == 3 && j == 4) b.setStyle(black_player);
                else if(k == 4 && j == 3) b.setStyle(black_player);
                else if(k == 4 && j == 4) b.setStyle(white_player);
                else b.setStyle(No_Player);

                BoardButtons.add(b);
            }
        }

        //Adding Background
        gameScreen.setStyle("-fx-background-image: url('file:src/main/java/com/example/reversi/ButtonResources/game_background.jpg')");

        //Called here because want to see possible moves the moment game is started
        r.showMoves();
        updateCurrentBoard(r.GetBoard());

    }

    private void CreateEndScreen() {
        endScreen.setStyle("-fx-background-color:#6699FF");
        try {
            Winner.setFont(Font.loadFont(new FileInputStream("src/main/java/com/example/reversi/ButtonResources/kenvector_future.ttf"),40));
        } catch (FileNotFoundException e) {
            Winner.setFont(Font.loadFont("Verdana",40));
        }

        Winner.setLayoutX(170);
        Winner.setLayoutY(50);
        menuButtons Replay = new menuButtons("Replay");
        menuButtons Main_Menu = new menuButtons("Main Menu");

        Replay.setLayoutX(280);
        Replay.setLayoutY(150);

        Main_Menu.setLayoutX(280);
        Main_Menu.setLayoutY(250);




        endScreen.getChildren().addAll(Winner,Replay,Main_Menu);
        endGameButtons.add(Replay);
        endGameButtons.add(Main_Menu);

    }

    private void CreatePauseScreen() {

        String FONT_PATH = "src/main/java/com/example/reversi/ButtonResources/kenvector_future.ttf";

        pause_text = new Text("GAME PAUSED");
        pause_text.setLayoutX(250);
        pause_text.setLayoutY(100);

        try {
            pause_text.setFont(Font.loadFont(new FileInputStream(FONT_PATH),25));
        } catch (FileNotFoundException e) {
            pause_text.setFont(Font.loadFont("Verdana",25));
        }

        pauseScreen.getChildren().add(pause_text);

        menuButtons resume = new menuButtons("Resume");
        menuButtons restart = new menuButtons("Restart");
        menuButtons home = new menuButtons("Main Menu");

        resume.setLayoutX(270);
        resume.setLayoutY(100+100);

        restart.setLayoutX(270);
        restart.setLayoutY(100+100+100);

        home.setLayoutX(270);
        home.setLayoutY(100+100+100+100);

        pauseScreen.getChildren().addAll(resume,restart,home);

        pauseButtons.add(resume);
        pauseButtons.add(restart);
        pauseButtons.add(home);


    }




    public AnchorPane getStartMenu() {
        return startMenu;
    }
    public AnchorPane GetGameScreen() {
        return gameScreen;
    }
    public AnchorPane GetPauseScreen() {return pauseScreen;}
    public AnchorPane GetAIScreen() {return chooseAIScreen;}

    public int getHeight() {return SCREEN_HEIGHT;}
    public int getWidth(){return SCREEN_WIDTH;}

    public reversiLogic getLogic() {return r;}



    public ArrayList<menuButtons> getStartMenuButtons() {return startMenuButtons;}
    public ArrayList<menuButtons> getEndGameButtons() {return endGameButtons;}
    public ArrayList<Button> GetBoardButtons() {return BoardButtons;}
    public ArrayList<menuButtons> GetPauseButtons() {return pauseButtons;}
    public ArrayList<menuButtons> GetAIButtons() {return AIButtons;}


    public Text GetPauseText() {return pause_text;}

    private void updateCurrentBoard(ArrayList<ArrayList<String>> board) {
        for(int i =0;i<8;i++) {
            for(int j=0;j<8;j++) {
                int position = ((i*8) + j);
                if(board.get(i).get(j).equals(" W ")) {
                        BoardButtons.get(position).setStyle(white_player);
                }else if(board.get(i).get(j).equals(" B ")) {
                        BoardButtons.get(position).setStyle(black_player);
                }else if(board.get(i).get(j).equals(" * ")) {
                        BoardButtons.get(position).setStyle("-fx-background-image: url('file:src/main/java/com/example/reversi/ButtonResources/place_piece.png'); \n"
                                + "-fx-background-color:transparent; \n"
                                + "-fx-background-repeat: no-repeat;");
                }else {
                    BoardButtons.get(position).setStyle(No_Player);
                }
            }
        }
    }

    private void updateWinIndicator() {
        if(r.getWhites() > r.getBlacks() ) {
            winId2.setVisible(false);
            winId1.setVisible(true);
        }else if(r.getBlacks() > r.getWhites() ) {
            winId2.setVisible(true);
            winId1.setVisible(false);
        }else {
            winId1.setVisible(false);
            winId2.setVisible(false);
        }
    }


    @Override
    public void update() {
        checkGameStatus();
        gameLoop();

    }



    @Override
    public void gameLoop() {
        //Setting the text that shows how many pieces are white and black
        no_of_white.setText(r.getWhites() + "");
        no_of_black.setText(r.getBlacks() + "");
        System.out.println("[WHITE]" + r.getWhites());


        ArrayList<ArrayList<String>> board;
        board = r.GetBoard();

        //Checking with the board in reversiLogic and updating current board accordingly
        updateCurrentBoard(board);


        //Updating the star above the player(to show who has more pieces)
        updateWinIndicator();


        //Displaying who is the current player using drop shadows.
        if(r.getCurrent_player().equals(" B ") ) {
            playerimg2.setEffect(new DropShadow());
            p2.setEffect(new DropShadow());
            p1.setEffect(null);
            playerimg.setEffect(null);
        }else {
            playerimg2.setEffect(null);
            p2.setEffect(null);
            p1.setEffect(new DropShadow());
            playerimg.setEffect(new DropShadow());
        }
    }

    @Override
    public void playerTurn() {

    }

    @Override
    public void aiPlayerTurn() {

    }

    @Override
    public void checkGameStatus() {
        //Checking if the player has won or lost
        if(r.checkStatus() == 2) {
            Winner.setText("PLAYER WHITE WINS");
            System.out.println("White");
            s.setScene(scene);
        }else if(r.checkStatus() == 1) {
            Winner.setText("PLAYER BLACK WINS");
            System.out.println("Black");
            s.setScene(scene);
        }
    }

    @Override
    public void endGameMessage() {

    }
}
