package com.example.natureexplorationapp;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.InputStream;
import java.net.URL;

import static com.example.natureexplorationapp.Setting.AppSettings.modeOn;

/**
 * This class represents the Play Page of the Nature Exploration App.
 * It displays various buttons for learning, quizzes, and settings,
 * along with background music and animated transitions.
 */
public class PlayPage extends Application {

    // Font for the buttons and titles
    private final Font forestFont = Font.loadFont(getClass().getResourceAsStream("/font3.ttf"), 60);

    // Media and MediaPlayer for background music
    private Media sound;
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) throws Exception {

        // Load background music if musicOn is true in AppSettings
        if (Setting.AppSettings.musicOn) {
            URL musicFile = getClass().getResource("/playPageAudio.mp3");
            sound = new Media(musicFile.toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        }


        // Create a BorderPane as the root layout for the Play Page
        BorderPane pane = new BorderPane();
        if(modeOn){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.5); // Adjust brightness for gray effect
            pane.setEffect(colorAdjust);
        }

        // Setting background image
        Image backgroundImage = new Image("/BackPage.png");
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, false, false, false, true);
        BackgroundImage bgImage = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(bgImage);
        pane.setBackground(background);

        // Create buttons with images and text using helper methods
        ImageView learning = imageDisplay("/Learning.png");
        Button learningBT = buttonCreate(learning, " Do you want to learn?");

        ImageView quiz = imageDisplay("/quiz.png");
        Button quizBT = buttonCreate(quiz, "\tLets go for Quiz!!");

        ImageView setting = imageDisplay("/setting.png");
        Button settingBT = buttonCreate(setting, "\t\t Settings");

        ImageView back = imageDisplay("/projectImages/back.png");
        Button backBT = buttonCreate(back, "");

        // Animation: Translate Transition for buttons
        TranslateTransition learningTransition = new TranslateTransition(Duration.millis(700), learningBT);
        learningTransition.setFromX(-1200); // Start from X = -1200
        learningTransition.setToX(0); // Move to X = 0
        learningTransition.setCycleCount(1);
        learningTransition.setAutoReverse(true);

        TranslateTransition quizTransition = new TranslateTransition(Duration.millis(700), quizBT);
        quizTransition.setFromX(1200); // Start from X = 1200
        quizTransition.setToX(0); // Move to X = 0
        quizTransition.setCycleCount(1);
        quizTransition.setAutoReverse(true);

        TranslateTransition settingTransition = new TranslateTransition(Duration.millis(700), settingBT);
        settingTransition.setFromX(-1200); // Start from X = -1200
        settingTransition.setToX(0); // Move to X = 0
        settingTransition.setCycleCount(1);
        settingTransition.setAutoReverse(true);

        // Sequential transition for sequential animation of buttons
        SequentialTransition sequentialTransition = new SequentialTransition(learningTransition, quizTransition, settingTransition);
        sequentialTransition.play();

        // Event handlers for each button
        learningBT.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Stop background music if it's playing
                if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.stop();
                }
                TopicMenuPage topicMenuPage = new TopicMenuPage();
                try {
                    topicMenuPage.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        quizBT.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Stop background music if it's playing
                if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.stop();
                }
                QuizHomeScreen quizHomeScreen = new QuizHomeScreen();
                try {
                    quizHomeScreen.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        settingBT.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Stop background music if it's playing
                if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.stop();
                }
                Setting setting1 = new Setting();
                try {
                    setting1.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        backBT.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Stop background music if it's playing
                if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.stop();
                }
                WelcomeScreen welcomeScreen = new WelcomeScreen();
                try {
                    welcomeScreen.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // VBox to vertically align buttons in the center
        VBox buttonsAlign = new VBox(50);
        buttonsAlign.getChildren().addAll(learningBT, quizBT, settingBT);
        buttonsAlign.setAlignment(Pos.CENTER);

        // Set layout for the Play Page
        pane.setCenter(buttonsAlign);
        pane.setLeft(backBT); // Place back button on the left side

        // Create and display the scene
        Scene scene = new Scene(pane, 1800, 1012.5);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    // Method to load and display an image
    private ImageView imageDisplay(String image) {
        InputStream stream = getClass().getResourceAsStream(image);
        Image imageF = (stream != null) ? new Image(stream) : null;
        ImageView imageDisplay = new ImageView(imageF);
        imageDisplay.setFitHeight(130);
        imageDisplay.setFitWidth(120);
        return imageDisplay;
    }

    // Method to create a button with an image and text
    private Button buttonCreate(ImageView imageDisplay, String text) {
        Button buttonCreate = new Button(text);
        buttonCreate.setFont(forestFont);
        buttonCreate.setTextFill(Color.rgb(64, 41, 27));
        buttonCreate.setGraphic(imageDisplay);
        buttonCreate.setShape(new Rectangle(60, 50));
        buttonCreate.setBackground(null);
        buttonCreate.setPadding(new Insets(0));
        return buttonCreate;
    }

    // Main method to launch the JavaFX application
    public static void main(String[] args) {
        launch();
    }
}
