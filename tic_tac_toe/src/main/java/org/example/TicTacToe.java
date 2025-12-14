package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

public class TicTacToe extends Application {
    private int clickCount = 0;
    private VBox createContent() {
        Button btnReset = new Button("Reset");
        Button[][] buttonBoard = new Button[3][3];
        char[][] board = new char[3][3];
        AtomicInteger counter1 = new AtomicInteger();
        AtomicInteger counter2 = new AtomicInteger();
        String player1 = "player 1";
        String player2 = "player 2";

        btnReset.setPrefSize(200, 100);
        btnReset.setFont(new Font(25));
        btnReset.setBackground(new Background
                (new BackgroundFill(Color.ANTIQUEWHITE, null, null)));

        Label playerOne = new Label(String.valueOf(player1));
        playerOne.setFont(new Font(20));
        Label playerTwo = new Label(String.valueOf(player2));
        playerTwo.setFont(new Font(20));
        Label scoreLabel1 = new Label(String.valueOf(counter1.get()));
        scoreLabel1.setFont(new Font(20));
        Label scoreLabel2 = new Label(String.valueOf(counter2.get()));
        scoreLabel2.setFont(new Font(20));

        Button increaseScore1 = new Button();
        Button decreaseScore1 = new Button();
        Button increaseScore2 = new Button();
        Button decreaseScore2 = new Button();
        increaseScore1.setText("+");
        increaseScore1.setFont(new Font(20));
        increaseScore1.setPrefSize(40, 40);
        decreaseScore1.setPrefSize(40, 40);
        decreaseScore1.setFont(new Font(20));
        decreaseScore1.setText("-");
        increaseScore2.setText("+");
        increaseScore2.setFont(new Font(20));
        increaseScore2.setPrefSize(40, 40);
        decreaseScore2.setPrefSize(40, 40);
        decreaseScore2.setFont(new Font(20));
        decreaseScore2.setText("-");

        increaseScore1.setOnAction(e -> {
            counter1.getAndIncrement();
            scoreLabel1.setText(String.valueOf(counter1.get()));
        });
        decreaseScore1.setOnAction(e -> {
            counter1.decrementAndGet();
            scoreLabel1.setText(String.valueOf(counter1.get()));
        });
        increaseScore2.setOnAction(e -> {
            counter2.getAndIncrement();
            scoreLabel2.setText(String.valueOf(counter2.get()));
        });
        decreaseScore2.setOnAction(e -> {
            counter2.decrementAndGet();
            scoreLabel2.setText(String.valueOf(counter2.get()));
        });

        Button btnResetScore = new Button( "Reset Score");
        btnResetScore.setPrefSize(170, 50);
        btnResetScore.setFont(new Font(20));
        btnResetScore.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null)));

        btnResetScore.setOnAction(e -> {
            counter1.set(0);
            scoreLabel1.setText(String.valueOf(counter1.get()));
            counter2.set(0);
            scoreLabel2.setText(String.valueOf(counter2.get()));
        });

        HBox score1 = new HBox(10, playerOne, increaseScore1, decreaseScore1, scoreLabel1);
        score1.setAlignment(Pos.CENTER);
        HBox score2 = new HBox(10, playerTwo, increaseScore2, decreaseScore2, scoreLabel2);
        score2.setAlignment(Pos.CENTER);
        HBox resetScore = new HBox(10, btnResetScore);
        resetScore.setAlignment(Pos.CENTER);

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);

        btnReset.setOnMouseClicked(e -> {
            for (javafx.scene.Node node : gp.getChildren()) {
                if (node instanceof Button b) {
                    b.setText("");
                    b.setDisable(false);
                    b.setBackground(new Background
                            (new BackgroundFill(Color.WHEAT, null, null)));
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            board[i][j] = '\0';
                        }
                    }
                }
            }
        });

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button();
                button.setPrefSize(150, 150);
                button.setFont(new Font(45));
                button.setBackground(new Background
                        (new BackgroundFill(Color.WHEAT, null, null)));
                gp.add(button, col, row);
                buttonBoard[row][col] = button;

                int r = row;
                int c = col;

                button.setOnMouseClicked(e -> {

                    if (board[r][c] != '\0') return;

                    if (clickCount % 2 == 0) {
                        button.setText("X");
                        board[r][c] = 'X';
                    }
                    else {
                        button.setText("O");
                        board[r][c] = 'O';
                    }
                    clickCount++;

                    if(checkWin(board, buttonBoard)){
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                buttonBoard[i][j].setDisable(true);
                            }
                        }
                    };
                });
            }
        }

        gp.setHgap(5);
        gp.setVgap(5);
        VBox.setMargin(btnReset, new Insets(10, 0, 0, 0));

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(gp, btnReset, score1, score2, resetScore);
        VBox.setMargin(score1, new Insets(10));
        VBox.setMargin(score2, new Insets(10));
        VBox.setMargin(resetScore, new Insets(10));
        return root;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent(), 1000, 1000));
        stage.show();
    }

    private boolean checkWin(char[][] board, Button[][] buttonBoard) {
        for (int i = 0; i < 3; i++) {
            //row
            if (board[i][0] != '\0' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                buttonBoard[i][0].setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                buttonBoard[i][0].setDisable(true);
                buttonBoard[i][1].setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                buttonBoard[i][1].setDisable(true);
                buttonBoard[i][2].setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                buttonBoard[i][2].setDisable(true);
                return true;
            } //col
            if (board[0][i] != '\0' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                buttonBoard[0][i].setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                buttonBoard[0][i].setDisable(true);
                buttonBoard[1][i].setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                buttonBoard[1][i].setDisable(true);
                buttonBoard[2][i].setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                buttonBoard[2][i].setDisable(true);
                return true;
            }//diagonal
            if (board[0][0] != '\0' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
                buttonBoard[0][0].setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                buttonBoard[0][0].setDisable(true);
                buttonBoard[1][1].setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                buttonBoard[1][1].setDisable(true);
                buttonBoard[2][2].setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                buttonBoard[2][2].setDisable(true);
                return true;
            } //diagonal
            if (board[0][2] != '\0' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
                buttonBoard[0][2].setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                buttonBoard[0][2].setDisable(true);
                buttonBoard[1][1].setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                buttonBoard[1][1].setDisable(true);
                buttonBoard[2][0].setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                buttonBoard[2][0].setDisable(true);
                return true;
            }
        }
        return false;
    }
}
