package com.example.snakegame;

import javafx.css.Stylesheet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class SnakeGameController {

    @FXML
    public void play(MouseEvent event) throws IOException
    {
        AnchorPane start;
        start = FXMLLoader.load(getClass().getResource("GamePage.fxml"));

        HelloApplication.root.getChildren().setAll(start);
    }
}
