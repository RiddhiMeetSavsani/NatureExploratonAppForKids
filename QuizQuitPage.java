package com.example.natureexplorationapp;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

import static com.example.natureexplorationapp.Setting.AppSettings.modeOn;


/**
 * This class represents the Quiz Quit Page of the Nature Exploration App.
 * It displays the quiz results including score and number of questions attempted.
 * Users can navigate back to the home screen or review their quiz performance.
 */

public class QuizQuitPage extends Application {

    private MediaPlayer mediaPlayer1; // MediaPlayer for first audio
    private MediaPlayer mediaPlayer2; // MediaPlayer for second audio
    public static int score = 0;  // Static variable to store quiz score
    public static int questionCount = 0; // Static variable to store number of questions attempted

    @Override
    public void start(Stage stage) {


        // Images
        Image monkeyImageClass = new Image(getClass().getResourceAsStream("/projectImages/monkey.png"));
        ImageView monkeyImageView = new ImageView(monkeyImageClass);
        monkeyImageView.setFitWidth(370);
        monkeyImageView.setFitHeight(310);
        monkeyImageView.setTranslateX(15);
        monkeyImageView.setTranslateY(-200);

        Image homeImageClass = new Image(getClass().getResourceAsStream("/projectImages/home1.png"));
        ImageView homeImageView = new ImageView(homeImageClass);
        homeImageView.setFitHeight(80);
        homeImageView.setFitWidth(80);

        Image backImage = new Image(getClass().getResourceAsStream("/projectImages/back.png"));
        ImageView backImageView = new ImageView(backImage);
        backImageView.setFitWidth(80);
        backImageView.setFitHeight(80);

        // Home button functionality
        HBox homeBox = new HBox();
        homeBox.getChildren().addAll(homeImageView);

        homeImageView.setOnMouseClicked(e -> {
            // Stop background music
            mediaPlayer1.stop();
            mediaPlayer2.stop();

            // Navigate back to the Welcome Screen
            PlayPage playPage = new PlayPage();
            try {
                playPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Back button functionality
        backImageView.setOnMouseClicked(e -> {
            stopAudio(); // Stop audio when navigating away
            QuizHomeScreen quizHomeScreen = new QuizHomeScreen();
            quizHomeScreen.start(stage);
        });

        Image borderImageClass1 = new Image(getClass().getResourceAsStream("/projectImages/scoreBoard.jpg"));
        ImageView borderImageView1 = new ImageView(borderImageClass1);
        borderImageView1.setFitHeight(200);
        borderImageView1.setFitWidth(200);

        Image borderImageClass2 = new Image(getClass().getResourceAsStream("/projectImages/scoreBoard.jpg"));
        ImageView borderImageView2 = new ImageView(borderImageClass2);
        borderImageView2.setFitHeight(200);
        borderImageView2.setFitWidth(200);

        // Score and Attempt Labels, Fonts, and Texts
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/font3.ttf"), 50); // Specify the size here

        Text result = new Text("Result");
        result.setFont(customFont); // Apply the custom font
        result.setFill(Color.BROWN);
        result.setTranslateX(-20);
        result.setTranslateY(-170);

        Label scoreLabel = new Label("Your Score : ");
        scoreLabel.setFont(Font.font("impact", FontWeight.BOLD, 40));
        scoreLabel.setTextFill(Color.BROWN);
        scoreLabel.setTranslateY(90);

        Text scoreText = new Text();
        scoreText.setFont(Font.font("Impact", FontWeight.BOLD, 40));
        scoreText.setFill(Color.BROWN);
        scoreText.setText(" " +score + "/5");
        scoreText.setTranslateX(-135);
        scoreText.setTranslateY(90);

        Label attemptLabel = new Label("Attempted Question : ");
        attemptLabel.setFont(Font.font("impact", FontWeight.BOLD, 40));
        attemptLabel.setTextFill(Color.BROWN);
        attemptLabel.setTranslateY(90);

        Text attemptText = new Text();
        attemptText.setFont(Font.font("Impact", FontWeight.BOLD, 40));
        attemptText.setFill(Color.BROWN);
        attemptText.setText(" " + questionCount + "/5");
        attemptText.setTranslateX(-135);
        attemptText.setTranslateY(90);

        // Layouts for Score and Attempt Labels
        StackPane subStackPane = new StackPane();
        subStackPane.getChildren().addAll(monkeyImageView, result);

        // Animation transitions for the Result text
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), subStackPane);
        translateTransition.setFromY(-400);
        translateTransition.setToY(0);
        translateTransition.setCycleCount(1);

        TranslateTransition bounceBack = new TranslateTransition(Duration.seconds(0.5), subStackPane);
        bounceBack.setFromY(0);
        bounceBack.setToY(-30);
        bounceBack.setAutoReverse(true);
        bounceBack.setCycleCount(TranslateTransition.INDEFINITE);

        SequentialTransition sequentialTransition = new SequentialTransition(translateTransition, bounceBack);
        sequentialTransition.play();

        // Combine scoreLabel and scoreHBox
        HBox scoreHBox = new HBox();
        scoreHBox.getChildren().addAll(scoreLabel, borderImageView1, scoreText);

        // Combine attemptLabel and attemptHBox
        HBox attemptHBox = new HBox();
        attemptHBox.getChildren().addAll(attemptLabel, borderImageView2, attemptText);

        HBox reviewHBox = new HBox();
        reviewHBox.getChildren().addAll(scoreHBox, attemptHBox);

        // Main Layout - StackPane
        Image backgroundImage = new Image(getClass().getResourceAsStream("/projectImages/QuizQuitScreen.jpg"));
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImg);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(subStackPane, reviewHBox, homeImageView, backImageView);
        StackPane.setAlignment(homeImageView, Pos.TOP_RIGHT);
        StackPane.setAlignment(backImageView, Pos.TOP_LEFT);
        StackPane.setMargin(reviewHBox, new Insets(400, 0, 0, 380)); // top, right, bottom, left

        if(modeOn){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.5); // Adjust brightness for gray effect
            stackPane.setEffect(colorAdjust);
        }

        stackPane.setBackground(background);

        // Scene
        Scene scene = new Scene(stackPane, 1800, 1012.5);
        stage.setScene(scene);
        stage.setTitle("Quiz Quit Page");
        stage.show();

        // Play the audio
        playAudio();
    }

    /**
     * Method to play the result audio files.
     * Plays two consecutive audio files when the page is loaded.
     */
    private void playAudio() {
        URL resource1 = getClass().getResource("/audio/result.mp3");
        String musicFile1 = resource1.toString();
        Media sound1 = new Media(musicFile1);
        mediaPlayer1 = new MediaPlayer(sound1);

        URL resource2 = getClass().getResource("/audio/audioAns1.mp3");
        String musicFile2 = resource2.toString();
        Media sound2 = new Media(musicFile2);
        mediaPlayer2 = new MediaPlayer(sound2);

        // Play the first audio
        mediaPlayer1.play();

        // When the first audio ends, play the second one
        mediaPlayer1.setOnEndOfMedia(() -> mediaPlayer2.play());
    }


    /**
     * Method to stop all currently playing audio files.
     * Stops both MediaPlayer instances if they are currently playing.
     */
    private void stopAudio() {
        if (mediaPlayer1 != null) {
            mediaPlayer1.stop();
        }
        if (mediaPlayer2 != null) {
            mediaPlayer2.stop();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
