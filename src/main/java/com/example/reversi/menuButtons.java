package com.example.reversi;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class menuButtons extends Button {

    private static final String FONT_PATH = "src/main/java/com/example/reversi/ButtonResources/kenvector_future.ttf";
    private static final String BUTTON_PRESSED = "-fx-background-color: transparent; \n" +
            "-fx-background-image: url('file:src/main/java/com/example/reversi/ButtonResources/red_button_pressed.png');";
    private static final String BUTTON_RELEASED = "-fx-background-color: transparent; \n" +
            "-fx-background-image: url('file:src/main/java/com/example/reversi/ButtonResources/red_button.png');";

    menuButtons(String text) {
        setFont();
        setText(text);
        setPrefWidth(190);
        setPrefHeight(49);
        buttonReleasedStyle();
        initaliseListeners();
    }

    private void setFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH),23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana",23));
        }
    }

    private void buttonPressedStyle() {
        setStyle(BUTTON_PRESSED);
        setHeight(45);
        //Looks like it is at the same position
        setLayoutY(getLayoutY() + 4);
    }

    private void buttonReleasedStyle() {
        setStyle(BUTTON_RELEASED);
        setHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    private void initaliseListeners() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) buttonPressedStyle();
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                buttonReleasedStyle();
            }
        });
    }

}
