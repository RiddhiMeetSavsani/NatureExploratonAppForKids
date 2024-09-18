package com.example.natureexplorationapp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.effect.ColorAdjust;

import java.io.IOException;
import java.net.URL;

public class Setting extends Application {
    StackPane stackPane = new StackPane();
    private boolean isGrayEffectOn = false; // Flag to track gray effect

    @Override
    public void start(Stage stage) throws IOException {

        URL musicFile = getClass().getResource("/music.mp3");
        Media sound = new Media(musicFile.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        if (Setting.AppSettings.musicOn) {
            // Play background music
            mediaPlayer.play();
        }

        // All needed nodes
        Label headingLabel = new Label("Preferences");
        headingLabel.setTranslateX(50);
        headingLabel.setTranslateY(-200);

        Label musicLabel = new Label("Music");
        Label notificationLabel = new Label("Notifications");
        Label themeLabel = new Label("Mode");
        Button onButton1 = new Button("ON");
        Button offButton1 = new Button("OFF");
        Button onButton2 = new Button("ON");
        Button offButton2 = new Button("OFF");
        Button onButton3 = new Button("ON");
        Button offButton3 = new Button("OFF");

        onButton1.setOnAction(e -> {
            AppSettings.musicOn = true;
            toggleGrayEffect(); // Toggle gray effect
            System.out.println("Music ON");
            mediaPlayer.play();
        });

        offButton1.setOnAction(e -> {
            AppSettings.musicOn = false;
            toggleGrayEffect(); // Toggle gray effect
            System.out.println("Music OFF");
            mediaPlayer.stop();
        });

        onButton2.setOnAction(e -> {
            toggleGrayEffect(); // Toggle gray effect
            System.out.println("Notifications ON");
        });

        offButton2.setOnAction(e -> {
            toggleGrayEffect(); // Toggle gray effect
            System.out.println("Notifications OFF");
        });

        onButton3.setOnAction(e -> {
            AppSettings.modeOn = true;
            toggleGrayEffect(); // Toggle gray effect
            System.out.println("Mode ON");
        });

        offButton3.setOnAction(e -> {
            AppSettings.modeOn = false;
            removeGrayEffect(); // Ensure gray effect is off when mode is off
            System.out.println("Mode OFF");
        });

        // Font style
        Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 50);
        headingLabel.setFont(font);
        musicLabel.setFont(font);
        notificationLabel.setFont(font);
        themeLabel.setFont(font);
        headingLabel.setTextFill(Color.WHITE);
        musicLabel.setTextFill(Color.WHITE);
        notificationLabel.setTextFill(Color.WHITE);
        themeLabel.setTextFill(Color.WHITE);

        // Images
        Image musicImageClass = new Image(getClass().getResourceAsStream("/Images/musical-note.png"));
        ImageView musicImageView = new ImageView(musicImageClass);
        musicImageView.setFitWidth(70);
        musicImageView.setFitHeight(70);

        Image notificationImageClass = new Image(getClass().getResourceAsStream("/Images/notification.png"));
        ImageView notificationImageView = new ImageView(notificationImageClass);
        notificationImageView.setFitWidth(70);
        notificationImageView.setFitHeight(70);

        Image themeImageClass = new Image(getClass().getResourceAsStream("/Images/dark-mode.png"));
        ImageView themeImageView = new ImageView(themeImageClass);
        themeImageView.setFitWidth(70);
        themeImageView.setFitHeight(70);

        Image homeImageClass = new Image(getClass().getResourceAsStream("/Images/home.png"));
        ImageView homeImageView = new ImageView(homeImageClass);
        homeImageView.setFitWidth(70);
        homeImageView.setFitHeight(70);

        Image backImageClass = new Image(getClass().getResourceAsStream("/Images/undo.png"));
        ImageView backImageView = new ImageView(backImageClass);
        backImageView.setFitWidth(70);
        backImageView.setFitHeight(70);

        // Background Image class
        Image backGroundImageClass = new Image(getClass().getResourceAsStream("/Images/background.jpg"));

        // Setting background image size
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);

        // Creating BackgroundImage
        BackgroundImage backgroundImage = new BackgroundImage(backGroundImageClass, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        // Sub layout
        HBox button1HBox = new HBox(30);
        button1HBox.getChildren().addAll(onButton1, offButton1);
        button1HBox.setTranslateX(160);
        button1HBox.setTranslateY(18);
        HBox musicHBox = new HBox(40);
        musicHBox.getChildren().addAll(musicImageView, musicLabel, button1HBox);

        HBox button2HBox = new HBox(30);
        button2HBox.getChildren().addAll(onButton2, offButton2);
        button2HBox.setTranslateX(0);
        button2HBox.setTranslateY(18);
        HBox notificationHBox = new HBox(40);
        notificationHBox.getChildren().addAll(notificationImageView, notificationLabel, button2HBox);

        HBox button3HBox = new HBox(30);
        button3HBox.getChildren().addAll(onButton3, offButton3);
        button3HBox.setTranslateX(175);
        button3HBox.setTranslateY(18);
        HBox themeHBox = new HBox(40);
        themeHBox.getChildren().addAll(themeImageView, themeLabel, button3HBox);

        HBox top_hBox = new HBox(1660);
        top_hBox.getChildren().addAll(backImageView, homeImageView);

        VBox mainVBox = new VBox(30);
        mainVBox.getChildren().addAll(musicHBox, notificationHBox, themeHBox);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setTranslateX(650);
        mainVBox.setTranslateY(100);

        homeImageView.setOnMouseClicked(e -> {
            mediaPlayer.stop();
            PlayPage playPage = new PlayPage();
            try {
                playPage.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        backImageView.setOnMouseClicked(e -> {
            mediaPlayer.stop();
            PlayPage playPage = new PlayPage();
            try {
                playPage.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        // Main layout stackPane
        stackPane.getChildren().addAll(top_hBox, headingLabel, mainVBox);

        // Setting background image
        Background background = new Background(backgroundImage);
        stackPane.setBackground(background);

        // Scene
        Scene scene = new Scene(stackPane, 1800, 1012.5);
        stage.setScene(scene);
        stage.setTitle("Setting Screen");
        stage.show();
    }

    // Method to apply gray color effect
    private void applyGrayEffect() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5); // Adjust brightness for gray effect
        stackPane.setEffect(colorAdjust);
        isGrayEffectOn = true;
    }

    // Method to remove gray color effect
    private void removeGrayEffect() {
        stackPane.setEffect(null); // Remove effect
        isGrayEffectOn = false;
    }

    // Method to toggle gray color effect
    private void toggleGrayEffect() {
        if (AppSettings.modeOn) {
            applyGrayEffect();
        } else {
            removeGrayEffect();
        }
    }

    public class AppSettings {
        public static boolean musicOn = true; // Default is music on
        //public static boolean zoomOn = false; // Default is zoom off
        public static boolean modeOn = false; // Default is mode off

        // Method to toggle music state
       /* public static void toggleMusic() {
            musicOn = !musicOn; // Toggle the music state
        }

        // Method to toggle zoom state
        public static void toggleZoom() {
            zoomOn = !zoomOn; // Toggle zoom state
        }*/
    }

    public static void main(String[] args) {
        launch();
    }
}
