package com.example.natureexplorationapp;

import javafx.animation.StrokeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import java.io.IOException;

import static com.example.natureexplorationapp.Setting.AppSettings.modeOn;

/**
 * This class represents the Topic Menu Page of the Nature Exploration App.
 * It displays buttons for various learning topics such as Flowers, Seasons, Birds, Trees, Fruits, and Animals.
 * Each button transitions to a specific screen related to the selected topic.
 */
public class TopicMenuPage extends Application {

    private void stopBackgroundMusic() {
        if (mediaPlayer != null && Setting.AppSettings.musicOn) {
            mediaPlayer.stop();
        }
    }

    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) throws IOException {

        if (Setting.AppSettings.musicOn) {
            String musicPath = getClass().getResource("/Project_Images/music.mp3").toString();
            Media sound = new Media(musicPath);
            mediaPlayer = new MediaPlayer(sound);

            // Handle end of media to restart playback
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.seek(Duration.ZERO); // Restart from the beginning
                mediaPlayer.play(); // Resume playback
            });

            // Play the media
            mediaPlayer.play();
        }


        //TODO back button, home and setting
        ImageView backButtonImageView = new ImageView(new Image(getClass().getResourceAsStream("/Project_Images/undo.png")));
        backButtonImageView.setFitWidth(70);
        backButtonImageView.setFitHeight(70);

        ImageView homeButtonImageView = new ImageView(new Image(getClass().getResourceAsStream("/Project_Images/home.png")));
        homeButtonImageView.setFitWidth(70);
        homeButtonImageView.setFitHeight(70);

        ImageView settingButtonImageView = new ImageView(new Image(getClass().getResourceAsStream("/Project_Images/settings.png")));
        settingButtonImageView.setFitWidth(70);
        settingButtonImageView.setFitHeight(70);

        //TODO buttons

        Button flower_Button = new Button("Flowers");
        Button season_Button = new Button("Seasons");
        Button bird_Button = new Button("Birds");
        Button tree_Button = new Button("Trees");
        Button fruit_Button = new Button("Fruits");
        Button animal_Button = new Button("Animals");

        Font button_font = Font.loadFont(getClass().getResourceAsStream("/Project_Images/font3.ttf"), 120);

        String buttonStyle = "-fx-background-color: linear-gradient(#438a25, #fbfbe3); " +
                "-fx-text-fill: brown; " +
                "-fx-font-size: 25px; " +
                "-fx-font-weight: bold;" +
                "-fx-font-family: 'font3.ttf';";

        flower_Button.setStyle(buttonStyle);
        animal_Button.setStyle(buttonStyle);
        bird_Button.setStyle(buttonStyle);
        fruit_Button.setStyle(buttonStyle);
        season_Button.setStyle(buttonStyle);
        tree_Button.setStyle(buttonStyle);

        //animal_Button.setFont(button_font);

        //all buttons have the same width
        animal_Button.setMinWidth(300);
        bird_Button.setMinWidth(300);
        season_Button.setMinWidth(300);
        fruit_Button.setMinWidth(300);
        tree_Button.setMinWidth(300);
        flower_Button.setMinWidth(300);

        // Create a rectangle border for each button
        Rectangle border1 = createBorderRectangle(animal_Button);
        Rectangle border2 = createBorderRectangle(season_Button);
        Rectangle border3 = createBorderRectangle(bird_Button);
        Rectangle border4 = createBorderRectangle(tree_Button);
        Rectangle border5 = createBorderRectangle(fruit_Button);
        Rectangle border6 = createBorderRectangle(flower_Button);

        // Create a stroke transition for each border
        createStrokeTransition(border1).play();
        createStrokeTransition(border2).play();
        createStrokeTransition(border3).play();
        createStrokeTransition(border4).play();
        createStrokeTransition(border5).play();
        createStrokeTransition(border6).play();

        // Stack pane to hold buttons and borders
        StackPane stackPane1 = new StackPane(border1, animal_Button);
        StackPane stackPane2 = new StackPane(border2, flower_Button);
        StackPane stackPane3 = new StackPane(border3, season_Button);
        StackPane stackPane4 = new StackPane(border4, bird_Button);
        StackPane stackPane5 = new StackPane(border5, tree_Button);
        StackPane stackPane6 = new StackPane(border6, fruit_Button);

        //TODO label
        Label welcome_label = new Label("Let's Dive In!");
        Font label_font = Font.loadFont(getClass().getResourceAsStream("/Project_Images/font3.ttf"), 150);
        welcome_label.setFont(label_font);
        welcome_label.setTextFill(Color.BROWN);

        //background image
        Image background_image = new Image(getClass().getResourceAsStream("/Project_Images/background.jpg"));
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(background_image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        //TODO sub layout
        HBox first_hbox = new HBox(60);
        first_hbox.getChildren().addAll(stackPane1, stackPane2);
        first_hbox.setAlignment(Pos.CENTER);

        HBox second_hbox = new HBox(60);
        second_hbox.getChildren().addAll(stackPane3, stackPane4);
        second_hbox.setAlignment(Pos.CENTER);

        HBox third_hbox = new HBox(60);
        third_hbox.getChildren().addAll(stackPane5, stackPane6);
        third_hbox.setAlignment(Pos.CENTER);

        VBox final_vbox = new VBox(50);
        final_vbox.getChildren().addAll(welcome_label, first_hbox, second_hbox, third_hbox);
        final_vbox.setAlignment(Pos.CENTER);

        // HBox for home and setting buttons
        HBox home_setting = new HBox(10, settingButtonImageView, homeButtonImageView);
        home_setting.setAlignment(Pos.TOP_RIGHT);
        home_setting.setPadding(new Insets(10));

        // HBox for back button
        HBox back_box = new HBox(1550,backButtonImageView, home_setting);
        back_box.setAlignment(Pos.TOP_LEFT);
        back_box.setPadding(new Insets(10));

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(back_box);
        borderPane.setCenter(final_vbox);

        // Main StackPane
        StackPane stackPane = new StackPane(borderPane);
        stackPane.setBackground(background);

        if(modeOn){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.5); // Adjust brightness for gray effect
            stackPane.setEffect(colorAdjust);
        }

        homeButtonImageView.setOnMouseClicked(event -> {
            // Stop background music
            if(mediaPlayer!=null){
                mediaPlayer.stop();
            }


            // Navigate back to the Welcome Screen
            PlayPage playPage = new PlayPage();
            try {
                playPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        backButtonImageView.setOnMouseClicked(e -> {
            // Stop background music
            if(mediaPlayer!= null){
                mediaPlayer.stop();
            }

            // Navigate back to the Welcome Screen
            PlayPage playPage = new PlayPage();
            try {
                playPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        settingButtonImageView.setOnMouseClicked(event -> {
            if(mediaPlayer!=null){
                mediaPlayer.stop();
            }


            Setting setting1 = new Setting();
            try {
                setting1.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        //TODO scene part
        Scene scene = new Scene(stackPane, 1800, 1012.5);
        stage.setScene(scene);
        stage.setTitle("Learning Topics");

        animal_Button.setOnAction(e -> {
            stopBackgroundMusic();
            try {
                AnimalScreen animalScreen = new AnimalScreen();
                animalScreen.start(stage);
            } catch (Exception ex) {
                System.out.println("Event handler is not running");
            }
        });

        flower_Button.setOnAction(e -> {
            stopBackgroundMusic();
            try {
                FlowerScreen flowerScreen = new FlowerScreen();
                flowerScreen.start(stage);
            } catch (Exception ex) {
                System.out.println("Event handler is not running");
            }
        });

        season_Button.setOnAction(e -> {
            stopBackgroundMusic();
            try {
                SeasonScreen seasonScreen = new SeasonScreen();
                seasonScreen.start(stage);
            } catch (Exception ex) {
                System.out.println("Event handler is not running");
            }
        });

        bird_Button.setOnAction(e -> {
            stopBackgroundMusic();
            try {
                BirdScreen birdScreen = new BirdScreen();
                birdScreen.start(stage);
            } catch (Exception ex) {
                System.out.println("Event handler is not running");
            }
        });

        tree_Button.setOnAction(e -> {
            stopBackgroundMusic();
            try {
                TreeScreen treeScreen = new TreeScreen();
                treeScreen.start(stage);
            } catch (Exception ex) {
                System.out.println("Event handler is not running");
            }
        });

        fruit_Button.setOnAction(e -> {
            stopBackgroundMusic();
            try {
                FruitScreen fruitScreen = new FruitScreen();
                fruitScreen.start(stage);
            } catch (Exception ex) {
                System.out.println("Event handler is not running");
            }
        });

        stage.show();
    }

    //TODO border method
    private Rectangle createBorderRectangle(Button button) {
        Rectangle border = new Rectangle(301, 56);
        border.setFill(null); // No fill, just stroke
        border.setStroke(Color.rgb(91, 29, 116)); // Initial stroke color
        border.setStrokeWidth(6); //
        return border;
    }

    //TODO Animation method
    private StrokeTransition createStrokeTransition(Rectangle border) {
        StrokeTransition strokeTransition = new StrokeTransition();
        strokeTransition.setDuration(Duration.seconds(3)); // Duration of the transition
        strokeTransition.setShape(border); // Shape to apply the transition to
        strokeTransition.setFromValue(Color.rgb(91, 29, 116)); // Initial stroke color
        strokeTransition.setToValue(Color.rgb(253, 253, 150)); // Final stroke color
        strokeTransition.setCycleCount(StrokeTransition.INDEFINITE); // Repeat indefinitely
        strokeTransition.setAutoReverse(true); // Reverse the transition direction
        return strokeTransition;
    }

    public static void main(String[] args) {
        launch();
    }
}
