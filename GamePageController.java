package com.example.snakegame;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

public class GamePageController {
    @FXML
    Text number;
    @FXML
    Text playerTurn;
    @FXML
    ImageView player1;
    @FXML
    ImageView player2;

    double redoX;
    double redoY;

    HashMap<Pair<Double,Double>, Pair<Double,Double>> snakeLadderCoordinates;
    int turn = 1;
    Stack<Pair<Double, Double>> st1 = new Stack<>();
    Stack<Pair<Double, Double>> st2 = new Stack<>();
    double initX;
    double initY;
    int randomnumber;

    @FXML
    TextField p1Value;

    @FXML
    public void toss() throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        Random r = new Random();
        int rNum = r.nextInt(2);


        if(rNum %2 == 0){
            alert.setContentText("player 1 won the toss");
            start(1);
            alert.showAndWait();
        }
        else{
                alert.setContentText("player 2won the toss");
                alert.showAndWait();
                start(2);
        }
    }

    public void start(int num) throws IOException
    {
        if(num == 1){
            turn = 1;
            playerTurn.setText(" player 1 turn");
        }else{
            turn = 2;
            playerTurn.setText(" player 2 turn");
        }
    }
    @FXML
    public void rollDice() throws IOException
    {
        getladderCoordinates();
        Random random = new Random();
        randomnumber = random.nextInt(6) + 1;;
        number.setText(randomnumber + "");

        if(turn==1)
        {
            double moveX = player1.getTranslateX();
            double moveY = player1.getTranslateY();

            st1.push(new Pair<>(moveX, moveY));
            Pair<Double, Double> p = getCoordinates(moveX,moveY,randomnumber);

            player1.setTranslateX(p.getKey());
            player1.setTranslateY(p.getValue());



            if(snakeLadderCoordinates.containsKey(p))
            {
                player1.setTranslateX(snakeLadderCoordinates.get(p).getKey());
                player1.setTranslateY(snakeLadderCoordinates.get(p).getValue());

                st1.push(p);

                // to give the alert if the player climbed the ladder/ the snake hissed the player
                if(p.getValue() > snakeLadderCoordinates.get(p).getValue()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("yayyy!!!!! you climbed the ladder");
                    alert.show();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Ooops!! Snake hissed you, keep going");
                    alert.show();
                }
            }
            checkWin(player1.getTranslateX(), player1.getTranslateY());
        }
        else
        {
            double moveX = player2.getTranslateX();
            double moveY = player2.getTranslateY();
            st2.push(new Pair<>(moveX, moveY));
            Pair<Double, Double> p = getCoordinates(moveX,moveY,randomnumber);

            player2.setTranslateX(p.getKey());
            player2.setTranslateY(p.getValue());

            if(snakeLadderCoordinates.containsKey(p))
            {
                player2.setTranslateX(snakeLadderCoordinates.get(p).getKey());
                player2.setTranslateY(snakeLadderCoordinates.get(p).getValue());

                st2.push(p);
                if(p.getValue() > snakeLadderCoordinates.get(p).getValue()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("yayyy!!!!! you climbed the ladder");
                    alert.show();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Ooops!! Snake hissed you, keep going");
                    alert.show();
                }
            }

            checkWin(player2.getTranslateX(), player2.getTranslateY());
        }
        if(randomnumber != 6)
        {
            if(turn == 1)
            {
                playerTurn.setText("player 1 turn");
                turn = 2;
            }
            else
            {
                playerTurn.setText("player 2 turn");
                turn = 1;
            }
        }
    }
    Pair<Double,Double> getCoordinates(double moveX, double moveY, int randomnumber) throws IOException
    {
        double X = moveX;
        double Y = moveY;

        if(moveY % 100 == 0)
        {
            moveX += randomnumber * 50;

            if (moveX > 500) {
                moveX = 500 * 2 - moveX + 50;
                moveY -= 50;
            }

        }
        else
        {
            moveX -= randomnumber * 50;

            if(moveX < 50){
                if(moveY == -450){
                    return new Pair<>(X, Y);
                }
                moveX = -1*(moveX - 50);
                moveY -= 50;
            }
        }
        return new Pair<>(moveX, moveY);
    }

    void checkWin(double X, double Y) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Result");
        if(X==50.0 && Y==-450.0)
        {
            if(turn==1) {
                alert.setContentText("player1 is the Winner!!");
                alert.showAndWait();
            }else{
                alert.setContentText("player2 is the Winner!!");
                alert.showAndWait();
            }

            SnakeGame game = new SnakeGame();
            HelloApplication.root.getChildren().setAll(game.root);
        }

    }
    public void reStart() throws IOException
    {
        player1.setTranslateX(initX);
        player1.setTranslateY(initY);

        player2.setTranslateX(initX);
        player2.setTranslateY(initY);

        turn = 1;
        playerTurn.setText("player 1 turn");
    }
    public void undo() throws IOException
    {
        if(turn == 1 && randomnumber == 6)
        {
            redoX = player1.getTranslateX();
            redoY = player1.getTranslateY();
            player1.setTranslateX(st1.peek().getKey());
            player1.setTranslateY(st1.peek().getValue());
        }
        else if(turn == 2)
        {
            redoX = player1.getTranslateX();
            redoY = player1.getTranslateY();
            player1.setTranslateX(st1.peek().getKey());
            player1.setTranslateY(st1.peek().getValue());
            turn = 1;
        }
        else if(turn == 1)
        {
            redoX = player2.getTranslateX();
            redoY = player2.getTranslateY();
            player2.setTranslateX(st2.peek().getKey());
            player2.setTranslateY(st2.peek().getValue());
            turn = 2;
        }
        else if(turn == 2 && randomnumber == 6)
        {
            redoX = player2.getTranslateX();
            redoY = player2.getTranslateY();
            player2.setTranslateX(st2.peek().getKey());
            player2.setTranslateY(st2.peek().getValue());
        }
    }
    public void redo() throws IOException
    {
        if(turn==1) {
            player1.setTranslateX(redoX);
            player1.setTranslateY(redoY);
        } else {
            player2.setTranslateX(redoX);
            player2.setTranslateY(redoY);
        }
    }
    void getladderCoordinates() throws IOException
    {
        snakeLadderCoordinates = new HashMap<>();

        snakeLadderCoordinates.put(new Pair<>(50.0,0.0), new Pair<>(150.0, -150.0));
        snakeLadderCoordinates.put(new Pair<>(200.0,0.0), new Pair<>(350.0, -50.0));
        snakeLadderCoordinates.put(new Pair<>(450.0,0.0), new Pair<>(500.0, -150.0));
        snakeLadderCoordinates.put(new Pair<>(200.0,-50.0), new Pair<>(350.0, 0.0));
        snakeLadderCoordinates.put(new Pair<>(50.0,-100.0), new Pair<>(100.0, -200.0));
        snakeLadderCoordinates.put(new Pair<>(400.0,-100.0), new Pair<>(200.0, -400.0));
        snakeLadderCoordinates.put(new Pair<>(500.0,-250.0), new Pair<>(350.0, -300.0));
        snakeLadderCoordinates.put(new Pair<>(100.0,-300.0), new Pair<>(100.0, -50.0));
        snakeLadderCoordinates.put(new Pair<>(200.0,-300.0), new Pair<>(50.0, -250.0));
        snakeLadderCoordinates.put(new Pair<>(500.0,-350.0), new Pair<>(500.0, -450.0));
        snakeLadderCoordinates.put(new Pair<>(50.0,-350.0), new Pair<>(50.0, -450.0));
        snakeLadderCoordinates.put(new Pair<>(350.0,-400.0), new Pair<>(200.0, -100.0));
        snakeLadderCoordinates.put(new Pair<>(400.0,-450.0), new Pair<>(400.0, -350.0));
        snakeLadderCoordinates.put(new Pair<>(300.0,-450.0), new Pair<>(300.0, -350.0));
        snakeLadderCoordinates.put(new Pair<>(150.0,-450.0), new Pair<>(100.0, -350.0));

    }
}
