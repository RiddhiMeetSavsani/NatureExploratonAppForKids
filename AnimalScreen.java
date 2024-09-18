package com.example.natureexplorationapp;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.natureexplorationapp.Setting.AppSettings.modeOn;


/**
 * This class represents the screen for displaying information about different animals.
 * It includes animations, audio playback, and navigation between different animal screens.
 */

public class AnimalScreen extends Application {

    //background music stop method
    private void stopAnimalAudio() {
        if (animalMediaPlayer != null) {
            animalMediaPlayer.stop();
        }
    }

    //list to store scenes
    private List<Scene> scenes = new ArrayList<>();
    private int currentSceneIndex = 0; //keep track of current index

    // Arrays to store animal data
    private String[] animalNames = {"Lion", "Elephant", "Tiger"};
    private String[] animalInfoTexts = {
            "I am the King of\nthe Jungle.\nI have Golden fur.\nI have powerful teeth.\nI belong to 'cat' family.\nMy roar can be \nheard from up to five\nmiles away!\nI live in groups called\nprides in Africa.",
            "I am the largest land\nanimal on Earth.\nI am known for\nmy intelligence and\nstrong social bonds.\nWe communicate with each\nother through sounds,\ngestures,and touch.",
            "I am a fierce predator\nin the Jungle.\nI have orange fur with\nblack stripes.\nWe are the largest cats in the\nworld.\nI live in various habitats,\nincluding forests,grasslands,\nand swamps."
    };

    private Hyperlink[] hyperlinks = new Hyperlink[] {
            new Hyperlink("For more Info."),
            new Hyperlink("For more Info."),
            new Hyperlink("For more Info.")
    };


    private String[] animalImagePaths = {"/Project_Images/lion.png", "/Project_Images/elephant.png", "/Project_Images/tiger.png"};
    private String[] animalAudioPaths = {"/Project_Images/lion_audio.mp3", "/Project_Images/elephant_audio.mp3", "/Project_Images/tiger_audio.mp3"};
    private double[] imageTranslateX = {-240, -280, -240};
    private double[] imageTranslateY = {20, 20, 20};
    private double[] textTranslateX = {-210, -280, -210};
    private double[] textTranslateY = {-260, -250, -260};
    private double[] infoTranslateX = {240, 280, 300};
    private double[] infoTranslateY = {-20, -20, -20};

    // Arrays to store scale transition values for each animal
    private double[] scaleXValues = {2.5, 3, 5};
    private double[] scaleYValues = {2.5, 3, 5};


    //media player
    private MediaPlayer animalMediaPlayer;

    @Override
    public void start(Stage stage) throws IOException {

        // Create scenes for each animal
        for (int i = 0; i < animalNames.length; i++) {
            scenes.add(createAnimalScene(stage, i));
        }

        // Set the initial scene
        stage.setScene(scenes.get(currentSceneIndex));
        stage.setTitle("Main Learning Page");
        stage.show();

        //play the first audio for 0 index
        playAnimalAudio(0);
    }


    //change scene method will change the scene when press next button along with that change the audio also
    private void changeScene(int increment, Stage stage) {
        // Stop current animal's audio
        if (animalMediaPlayer != null) {
            animalMediaPlayer.stop();
        }

        // Calculate the next scene index
        currentSceneIndex = (currentSceneIndex + increment + scenes.size()) % scenes.size();
        stage.setScene(scenes.get(currentSceneIndex));

        // Play audio for the new animal scene
        playAnimalAudio(currentSceneIndex);
    }


    //this will create the whole scene
    private Scene createAnimalScene(Stage stage, int animalIndex) throws IOException {
        Image backgroundImageClass = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Project_Images/backGround.jpg")));
        ImageView backGroundImageView = new ImageView(backgroundImageClass);
        backGroundImageView.setFitHeight(770);
        backGroundImageView.setFitWidth(1470);

        //setting up the background
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(backgroundImageClass, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        // Create navigation buttons
        Image homeButtonImageClass = new Image(getClass().getResourceAsStream("/Project_Images/home.png"));
        ImageView homeButtonImageView = new ImageView(homeButtonImageClass);
        homeButtonImageView.setFitHeight(70);
        homeButtonImageView.setFitWidth(70);
        homeButtonImageView.setTranslateX(860);
        homeButtonImageView.setTranslateY(-465);

        Image backButtonImageClass = new Image(getClass().getResourceAsStream("/Project_Images/undo.png"));
        ImageView backButtonImageView = new ImageView(backButtonImageClass);
        backButtonImageView.setFitHeight(70);
        backButtonImageView.setFitWidth(70);
        backButtonImageView.setTranslateX(-860);
        backButtonImageView.setTranslateY(-465);

        //handling back button
        backButtonImageView.setOnMouseClicked(e->{
            stopAnimalAudio();
            try{
                TopicMenuPage topicMenuPage = new TopicMenuPage();
                topicMenuPage.start(stage);
            }catch(Exception exception){
                System.out.println("Event Handler is not Running.");
            }
        });

        Image leftButtonImageClass = new Image(getClass().getResourceAsStream("/Project_Images/leftArrow.png"));
        ImageView leftButtonImageView = new ImageView(leftButtonImageClass);
        leftButtonImageView.setFitHeight(90);
        leftButtonImageView.setFitWidth(90);
        leftButtonImageView.setTranslateX(-855);
        leftButtonImageView.setTranslateY(0);

        Image rightButtonImageClass = new Image(getClass().getResourceAsStream("/Project_Images/rightArrow.png"));
        ImageView rightButtonImageView = new ImageView(rightButtonImageClass);
        rightButtonImageView.setFitHeight(90);
        rightButtonImageView.setFitWidth(90);
        rightButtonImageView.setTranslateX(855);
        rightButtonImageView.setTranslateY(0);

        // Add event handlers for navigation buttons
        rightButtonImageView.setOnMouseClicked(event -> changeScene(1, stage));
        leftButtonImageView.setOnMouseClicked(event -> changeScene(-1, stage));

        // Create image container
        Image imageContainerImageClass = new Image(getClass().getResourceAsStream("/Project_Images/EditedBook.png"));
        ImageView imageContainerImageView = new ImageView(imageContainerImageClass);
        imageContainerImageView.setFitHeight(900);
        imageContainerImageView.setFitWidth(1300);
        imageContainerImageView.setTranslateY(0);

        // Create animal image and its animation
        Image animalImageClass = new Image(getClass().getResourceAsStream(animalImagePaths[animalIndex]));
        ImageView animalImageView = new ImageView(animalImageClass);
        animalImageView.setFitHeight(200);
        animalImageView.setFitWidth(200);
        animalImageView.setTranslateX(imageTranslateX[animalIndex]);
        animalImageView.setTranslateY(imageTranslateY[animalIndex]);

        //animation for animal image
        ScaleTransition scaleTransitionAnimalImage = new ScaleTransition(Duration.seconds(17), animalImageView);
        scaleTransitionAnimalImage.setByX(scaleXValues[animalIndex]);
        scaleTransitionAnimalImage.setByY(scaleYValues[animalIndex]);
        scaleTransitionAnimalImage.setAutoReverse(true);
        scaleTransitionAnimalImage.play();

        // Create animal text and its animation
        Text animalText = new Text(animalNames[animalIndex]);
        Font animalFont = Font.loadFont(getClass().getResourceAsStream("/Project_Images/font3.ttf"), 55);
        animalText.setFont(animalFont);
        animalText.setTranslateX(textTranslateX[animalIndex]);
        animalText.setTranslateY(textTranslateY[animalIndex]);
        animalText.setFill(Color.BROWN);

        RotateTransition rotateTransitionAnimalText = new RotateTransition(Duration.seconds(1), animalText);
        rotateTransitionAnimalText.setFromAngle(-2);
        rotateTransitionAnimalText.setToAngle(2);
        rotateTransitionAnimalText.setAutoReverse(true);
        rotateTransitionAnimalText.setCycleCount(RotateTransition.INDEFINITE);

        ScaleTransition scaleTransitionAnimalText = new ScaleTransition(Duration.seconds(10), animalText);
        scaleTransitionAnimalText.setByX(2.5);
        scaleTransitionAnimalText.setByY(2.5);

        ParallelTransition parallelTransitionAnimalText = new ParallelTransition(rotateTransitionAnimalText, scaleTransitionAnimalText);
        parallelTransitionAnimalText.play();

        homeButtonImageView.setOnMouseClicked(e -> {
            // Stop background music
            stopAnimalAudio();

            // Navigate back to the Welcome Screen
            PlayPage playPage = new PlayPage();
            try {
                playPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        // Create animal info text and its animation
        Text animalInfoText = new Text(animalInfoTexts[animalIndex]);
        Font animalInfoTextFont = Font.loadFont(getClass().getResourceAsStream("/Project_Images/font3.ttf"), 55);
        animalInfoText.setFont(animalInfoTextFont);
        animalInfoText.setTranslateX(infoTranslateX[animalIndex]);
        animalInfoText.setTranslateY(infoTranslateY[animalIndex]);
        animalInfoText.setFill(Color.BROWN);


        //TODO Hyperlink
        /*
        getHostServices().showDocument(url): This method is from the HostServices interface in JavaFX, which provides a way to interact with the host environment to open a specified URL
         */
        Hyperlink hyperlink = hyperlinks[animalIndex];
        hyperlink.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 40)); // Override font if needed
        hyperlink.setTextFill(Color.BROWN); // Override text color if needed
        hyperlink.setOnAction(e -> {
            // Open Wikipedia page corresponding to the animal
            switch (animalIndex) {
                case 0: // Lion
                    getHostServices().showDocument("https://en.wikipedia.org/wiki/Lion");
                    break;
                case 1: // Elephant
                    getHostServices().showDocument("https://en.wikipedia.org/wiki/Elephant");
                    break;
                case 2: // Tiger
                    getHostServices().showDocument("https://en.wikipedia.org/wiki/Tiger");
                    break;
                default:
                    break;
            }
        });
        hyperlink.setTranslateX(490);
        hyperlink.setTranslateY(330);

        String hyperLinkStyle = new String( "-fx-border-color: transparent;");
        hyperlink.setStyle(hyperLinkStyle);

        // Using stack pane as main layout
        StackPane stackPane = new StackPane();
        stackPane.setBackground(background);
        stackPane.getChildren().addAll(imageContainerImageView, animalText, animalImageView, animalInfoText, hyperlink,homeButtonImageView, backButtonImageView, leftButtonImageView, rightButtonImageView);

        if(modeOn){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.5); // Adjust brightness for gray effect
            stackPane.setEffect(colorAdjust);
        }
        return new Scene(stackPane, 1800, 1012.5);
    }


    //method to play audio for different screens
    private void playAnimalAudio(int animalIndex) {
        // Play audio for the specified animal index
        String animalAudioPath = getClass().getResource(animalAudioPaths[animalIndex]).toString();

        // Check if music should be played based on the AppSettings.musicOn variable
        if (Setting.AppSettings.musicOn) {
            Media animalAudio = new Media(animalAudioPath);
            animalMediaPlayer = new MediaPlayer(animalAudio);

            // Start playing the audio
            animalMediaPlayer.play();
        }
    }




    public static void main(String[] args) {
        launch();
    }
}
