package com.example.snakegame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Group root;
    @Override
    public void start(Stage stage) throws IOException {
        root = new Group();
        SnakeGame obj = new SnakeGame();
        root.getChildren().add(obj.root);
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("Snake&Ladder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}