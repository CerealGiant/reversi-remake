package com.example.reversi;

import javafx.scene.Scene;

public interface Game {
    public void gameLoop();
    public void playerTurn();
    public void aiPlayerTurn();
    public void checkGameStatus();
    public void endGameMessage();

}
