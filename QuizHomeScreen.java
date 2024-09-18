package com.example.natureexplorationapp;

import javafx.animation.StrokeTransition;
import javafx.application.Application;
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

import java.net.URL;

import static com.example.natureexplorationapp.Setting.AppSettings.modeOn;


/**
 * This class represents the Quiz Home Screen of the Nature Exploration App.
 * It provides a user interface where users can select the difficulty level
 * for quizzes (Easy, Medium, Hard). Each difficulty level button triggers a
 * respective quiz screen upon selection. The class includes background music
 * playback, styled buttons with stroke animations, and a home button for
 * navigation. The UI layout uses JavaFX components such as BorderPane, StackPane,
 * and HBox for organizing elements. Images for buttons and background are loaded
 * from resources, enhancing the visual appeal. The class extends JavaFX Application
 * and manages scene setup and stage configuration to display the quiz home screen
 * with a specified size and background image.
 */

public class QuizHomeScreen extends Application {

    @Override
    public void start(Stage stage) {


        //Using URL class,Media and MediaPlayer and connecting audio
        URL resource = getClass().getResource("/audio/quizhome.mp3");
        String musicFile = resource.toString();
        Media sound = new Media(musicFile);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        if (resource != null && Setting.AppSettings.musicOn) {
            mediaPlayer.play();
        }

        //here creating 3 buttons easy, medium and hard and setting up font
        Button button1 = new Button("Easy");
        button1.setFont(Font.font("Impact", 35));

        Button button2 = new Button("Medium");
        button2.setFont(Font.font("Impact", 35));

        Button button3 = new Button("Hard");
        button3.setFont(Font.font("Impact", 35));


        // Using inline CSS to set up the styles of buttons
        String buttonStyle =  "-fx-background-color: linear-gradient(#438a25, #095900); " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 25px; " +
                "-fx-font-weight: bold;" +
                "-fx-font-family: 'font3.ttf';";
        button1.setStyle(buttonStyle);
        button2.setStyle(buttonStyle);
        button3.setStyle(buttonStyle);

        // Applying same width to all buttons
        button1.setMinWidth(300);
        button2.setMinWidth(300);
        button3.setMinWidth(300);

        // Setting up the Event Handler for each button
        button1.setOnAction(e -> {
            QuizEasy quizEasy = new QuizEasy();
            quizEasy.start(stage);
        });

        button2.setOnAction(e -> {
            QuizMedium quizMedium = new QuizMedium();
            quizMedium.start(stage);
        });

        button3.setOnAction(e -> {
            QuizHard quizHard = new QuizHard();
            quizHard.start(stage);
        });

        //  A rectangle border for each button
        Rectangle border1 = createBorderRectangle(button1);
        Rectangle border2 = createBorderRectangle(button2);
        Rectangle border3 = createBorderRectangle(button3);

        // Calling createStrokeTransition method on all borders
        createStrokeTransition(border1).play();
        createStrokeTransition(border2).play();
        createStrokeTransition(border3).play();


        // Sub layout StackPane to put buttons on respective borders
        StackPane stackPane1 = new StackPane(border1, button1);
        StackPane stackPane2 = new StackPane(border2, button2);
        StackPane stackPane3 = new StackPane(border3, button3);

        // using HBox to put all 3 buttons horizontally
        HBox buttonBox = new HBox(100, stackPane1, stackPane2, stackPane3);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setTranslateY(200);
        buttonBox.setTranslateX(200);

        // Using Image class and ImageView for home button and then putting this image in HBox and
        // this HBox will go in the top of borderpane
        Image home = new Image(getClass().getResourceAsStream("/projectImages/home1.png"));
        ImageView homeImageView = new ImageView(home);
        homeImageView.setFitHeight(80);
        homeImageView.setFitWidth(80);

        Image back_image = new Image(getClass().getResourceAsStream("/projectImages/back.png"));
        ImageView back_imageView = new ImageView(back_image);
        back_imageView.setFitHeight(80);
        back_imageView.setFitWidth(80);

        HBox topRightBox = new HBox(1640, back_imageView,homeImageView);
        topRightBox.setAlignment(Pos.TOP_CENTER);
        //topRightBox.setPadding(new Insets(10));

        //Event handler for home button
        homeImageView.setOnMouseClicked(e->{
            // Stop background music
            mediaPlayer.stop();

            // Navigate back to the Welcome Screen
            PlayPage playPage = new PlayPage();
            try {
                playPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        back_imageView.setOnMouseClicked(e->{
            // Stop background music
            mediaPlayer.stop();

            // Navigate back to the Welcome Screen
            PlayPage playPage = new PlayPage();
            try {
                playPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //  Border pane to hold button hBox and home HBox
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(buttonBox);
        borderPane.setTop(topRightBox);

        // Using background Class to put Image as Background
        Image background_image = new Image(getClass().getResourceAsStream("/projectImages/quizHomeScreen.jpeg"));
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(background_image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        // Main Layout - Stack pane to hold everything
        StackPane stackPane = new StackPane();
        stackPane.setBackground(background);
        stackPane.getChildren().addAll( borderPane);

        if(modeOn){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.5); // Adjust brightness for gray effect
            stackPane.setEffect(colorAdjust);
        }

        // Creating Scene
        Scene scene = new Scene(stackPane, 1800, 1012.5);
        stage.setTitle("Quiz Home Screen");
        stage.setScene(scene);
        stage.show();
    }


    //TODO All Methods

    //createBorderRectangle method which will create rectangle
    private Rectangle createBorderRectangle(Button button) {
        Rectangle border = new Rectangle(301, 56);
        border.setFill(null);                                    //give null value to the fill of rectangle
        border.setStroke(Color.rgb(91, 29, 116)); //giving stroke value
        border.setStrokeWidth(6);                                //setting up the width of stroke
        return border;                                           //return rectangle with having above styles
    }

    //createStrokeTransition method that will apply stroke animation to the rectangle shape
    private StrokeTransition createStrokeTransition(Rectangle border) {
        StrokeTransition strokeTransition = new StrokeTransition();
        strokeTransition.setDuration(Duration.seconds(2));
        strokeTransition.setCycleCount(StrokeTransition.INDEFINITE);
        strokeTransition.setAutoReverse(true);
        strokeTransition.setShape(border);
        strokeTransition.setFromValue(Color.rgb(49,66,93));
        strokeTransition.setToValue(Color.rgb(226,252,255));
        return strokeTransition;
    }

    //main method
    public static void main(String[] args) {
        launch(args);
    }
}
