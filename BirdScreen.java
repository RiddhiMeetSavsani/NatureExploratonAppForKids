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

public class BirdScreen extends Application {

    private void stopAnimalAudio() {
        if (animalMediaPlayer != null) {
            animalMediaPlayer.stop();
        }
    }

    private List<Scene> scenes = new ArrayList<>();
    private int currentSceneIndex = 0;

    // Arrays to store animal data
    private String[] animalNames = {"Parrot", "Owl", "Eagle"};
    private String[] animalInfoTexts = {
            "I am colorful bird\nwith bright feathers that\ncan be green,blue,\nred,yellow.\nI am known for my\nability to mimic sounds\nand human speech.\nI eat variety of\nfoods,including seeds,\nnuts,fruits.",
            "I have large,round\neyes and a flat face.\nI have excellent vision\nand hearing,which\nhelp me to hunt at night.\nWe are mostly active\nat night.\nWe are known for\nour distinctive hooting\ncalls.",
            "I am the large bird\nof pray with powerful\nbeaks and talons.\nWe are carnivorous\nand primarily eat fish,\nsmall mammals,and\nother birds.\nI am the national\nbird of United States."
    };

    private Hyperlink[] hyperlinks = new Hyperlink[] {
            new Hyperlink("For more Info."),
            new Hyperlink("For more Info."),
            new Hyperlink("For more Info.")
    };
    private String[] animalImagePaths = {"/Project_Images/parrot.png", "/Project_Images/owl.png", "/Project_Images/eagle.png"};
    private String[] animalAudioPaths = {"/Project_Images/parrot_audio.mp3", "/Project_Images/owl_audio.mp3", "/Project_Images/eagle_audio.mp3"};
    private double[] imageTranslateX = {-240, -240, -240};
    private double[] imageTranslateY = {20, 20, 20};
    private double[] textTranslateX = {-210, -210, -210};
    private double[] textTranslateY = {-260, -260, -260};
    private double[] infoTranslateX = {250, 280, 250};
    private double[] infoTranslateY = {20, 20, 0};

    // Arrays to store scale transition values for each animal
    private double[] scaleXValues = {3, 3, 3}; // Example values for each animal
    private double[] scaleYValues = {3, 3, 3}; // Example values for each animal

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

        playAnimalAudio(0);
    }

    private void changeScene(int increment, Stage stage) {
        // Stop current animal's audio
        if (animalMediaPlayer != null) {
            animalMediaPlayer.stop();
        }

        // Calculate the next scene index
        currentSceneIndex = (currentSceneIndex + increment + scenes.size()) % scenes.size();
        stage.setScene(scenes.get(currentSceneIndex));

        // Start animations for the new scene
        startAnimalAnimation();

        // Play audio for the new animal scene
        playAnimalAudio(currentSceneIndex);
    }
    private Scene createAnimalScene(Stage stage, int animalIndex) throws IOException {
        Image backgroundImageClass = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Project_Images/backGround.jpg")));
        ImageView backGroundImageView = new ImageView(backgroundImageClass);
        backGroundImageView.setFitHeight(770);
        backGroundImageView.setFitWidth(1470);

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
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
        leftButtonImageView.setTranslateX(-850);
        leftButtonImageView.setTranslateY(0);

        Image rightButtonImageClass = new Image(getClass().getResourceAsStream("/Project_Images/rightArrow.png"));
        ImageView rightButtonImageView = new ImageView(rightButtonImageClass);
        rightButtonImageView.setFitHeight(90);
        rightButtonImageView.setFitWidth(90);
        rightButtonImageView.setTranslateX(850);
        rightButtonImageView.setTranslateY(0);

        // Add event handlers for navigation buttons
        rightButtonImageView.setOnMouseClicked(event -> changeScene(1, stage));
        leftButtonImageView.setOnMouseClicked(event -> changeScene(-1, stage));

        // Create image container
        Image imageContainerImageClass = new Image(getClass().getResourceAsStream("/Project_Images/EditedBook.png"));
        ImageView imageContainerImageView = new ImageView(imageContainerImageClass);
        imageContainerImageView.setFitHeight(900);
        imageContainerImageView.setFitWidth(1300);
        imageContainerImageView.setTranslateY(35);

        // Create animal image and its animation
        Image animalImageClass = new Image(getClass().getResourceAsStream(animalImagePaths[animalIndex]));
        ImageView animalImageView = new ImageView(animalImageClass);
        animalImageView.setFitHeight(100);
        animalImageView.setFitWidth(100);
        animalImageView.setTranslateX(imageTranslateX[animalIndex]);
        animalImageView.setTranslateY(imageTranslateY[animalIndex]);

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
        scaleTransitionAnimalText.setByX(1.3);
        scaleTransitionAnimalText.setByY(1.3);

        ParallelTransition parallelTransitionAnimalText = new ParallelTransition(rotateTransitionAnimalText, scaleTransitionAnimalText);
        parallelTransitionAnimalText.play();

        // Create animal info text and its animation
        Text animalInfoText = new Text(animalInfoTexts[animalIndex]);
        Font animalInfoTextFont = Font.loadFont(getClass().getResourceAsStream("/Project_Images/font3.ttf"), 55);
        animalInfoText.setFont(animalInfoTextFont);
        animalInfoText.setTranslateX(infoTranslateX[animalIndex]);
        animalInfoText.setTranslateY(infoTranslateY[animalIndex]);
        animalInfoText.setFill(Color.BROWN);

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


        //TODO Hyperlink
        Hyperlink hyperlink = hyperlinks[animalIndex];
        hyperlink.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 40)); // Override font if needed
        hyperlink.setTextFill(Color.BROWN); // Override text color if needed
        hyperlink.setOnAction(e -> {
            // Open Wikipedia page corresponding to the animal
            switch (animalIndex) {
                case 0: // Lion
                    getHostServices().showDocument("https://en.wikipedia.org/wiki/Parrot");
                    break;
                case 1: // Elephant
                    getHostServices().showDocument("https://en.wikipedia.org/wiki/Owl");
                    break;
                case 2: // Tiger
                    getHostServices().showDocument("https://en.wikipedia.org/wiki/Eagle");
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
        stackPane.getChildren().addAll(imageContainerImageView, animalText, animalImageView, animalInfoText,hyperlink, homeButtonImageView, backButtonImageView, leftButtonImageView, rightButtonImageView);

        if(modeOn){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.5); // Adjust brightness for gray effect
            stackPane.setEffect(colorAdjust);
        }
        return new Scene(stackPane, 1800, 1012.5);
    }

    private void startAnimalAnimation() {
        // This method can be used to restart animations if needed
        // In this case, animations are already started in createAnimalScene()
    }

    private void playAnimalAudio(int animalIndex) {
        // Play audio for the specified animal index
        String animalAudioPath = getClass().getResource(animalAudioPaths[animalIndex]).toString();
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