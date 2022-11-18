package com.example.snakegame;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SnakeGame {
    public AnchorPane root;

    SnakeGame() throws IOException{
        root = FXMLLoader.load(getClass().getResource("SnakeGame.fxml"));
    }
}
