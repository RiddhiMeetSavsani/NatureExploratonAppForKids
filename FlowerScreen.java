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

public class FlowerScreen extends Application {

    private void stopAnimalAudio() {
        if (animalMediaPlayer != null) {
            animalMediaPlayer.stop();
        }
    }

    private List<Scene> scenes = new ArrayList<>();
    private int currentSceneIndex = 0;

    // Arrays to store animal data
    private String[] flowerNames = {"SunFlower", "Rose", "Daisy"};
    private String[] flowerInfoTexts = {
            "I have large,yellow \npetals surrounding a\ndark center filled with\nseeds.\nMy seeds are healthy\nand can be used to\nproduced sunflower oil.\nI symbolize happiness\nand positivity.",
            "I have so many\ncolor red,white and\nblue also.\nMy petals used to \nmake perfume.\nI also have thorns on\nmy stems.\nPeople also use me\nwhile making food.",
            "I have white petals\nsurrounding yellow\npetals.\nI am hardy plant so that\nI can grow in fields,gardens\nand even by the roadside.\nI close my petals\nat night."
    };

    private Hyperlink[] hyperlinks = new Hyperlink[] {
            new Hyperlink("For more Info."),
            new Hyperlink("For more Info."),
            new Hyperlink("For more Info.")
    };
    private String[] animalImagePaths = {"/Project_Images/sunflower.png", "/Project_Images/rose.png", "/Project_Images/daisy.png"};

    private String[] animalAudioPaths = {"/Project_Images/sunflower_audio.mp3", "/Project_Images/rose_audio.mp3", "/Project_Images/diasy_audio.mp3"};
    private double[] imageTranslateX = {-270, -270, -270};
    private double[] imageTranslateY = {10, 10, 10};
    private double[] textTranslateX = {-270, -300, -300};
    private double[] textTranslateY = {-300, -300, -300};
    private double[] infoTranslateX = {250, 240, 290};
    private double[] infoTranslateY = {0, -30, -20};

    // Arrays to store scale transition values for each animal
    private double[] scaleXValues = {4, 3, 3.5}; // Example values for each animal
    private double[] scaleYValues = {4, 3, 3.5}; // Example values for each animal

    private MediaPlayer animalMediaPlayer;

    @Override
    public void start(Stage stage) throws IOException {
        // Create scenes for each animal
        for (int i = 0; i < flowerNames.length; i++) {
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
        Image flowerImageClass = new Image(getClass().getResourceAsStream(animalImagePaths[animalIndex]));
        ImageView flowerImageView = new ImageView(flowerImageClass);
        flowerImageView.setFitHeight(100);
        flowerImageView.setFitWidth(100);
        flowerImageView.setTranslateX(imageTranslateX[animalIndex]);
        flowerImageView.setTranslateY(imageTranslateY[animalIndex]);

        ScaleTransition scaleTransitionFlowerImage = new ScaleTransition(Duration.seconds(17), flowerImageView);
        scaleTransitionFlowerImage.setByX(scaleXValues[animalIndex]);
        scaleTransitionFlowerImage.setByY(scaleYValues[animalIndex]);
        scaleTransitionFlowerImage.setAutoReverse(true);
        scaleTransitionFlowerImage.play();

        // Create animal text and its animation
        Text flowerText = new Text(flowerNames[animalIndex]);
        Font animalFont = Font.loadFont(getClass().getResourceAsStream("/Project_Images/font3.ttf"), 55);
        flowerText.setFont(animalFont);
        flowerText.setTranslateX(textTranslateX[animalIndex]);
        flowerText.setTranslateY(textTranslateY[animalIndex]);
        flowerText.setFill(Color.BROWN);

        RotateTransition rotateTransitionFlowerText = new RotateTransition(Duration.seconds(1), flowerText);
        rotateTransitionFlowerText.setFromAngle(-2);
        rotateTransitionFlowerText.setToAngle(2);
        rotateTransitionFlowerText.setAutoReverse(true);
        rotateTransitionFlowerText.setCycleCount(RotateTransition.INDEFINITE);

        ScaleTransition scaleTransitionFlowerText = new ScaleTransition(Duration.seconds(10), flowerText);
        scaleTransitionFlowerText.setByX(1.8);
        scaleTransitionFlowerText.setByY(1.8);

        ParallelTransition parallelTransitionAnimalText = new ParallelTransition(rotateTransitionFlowerText, scaleTransitionFlowerText);
        parallelTransitionAnimalText.play();

        // Create animal info text and its animation
        Text flowerInfoText = new Text(flowerInfoTexts[animalIndex]);
        Font animalInfoTextFont = Font.loadFont(getClass().getResourceAsStream("/Project_Images/font3.ttf"), 55);
        flowerInfoText.setFont(animalInfoTextFont);
        flowerInfoText.setTranslateX(infoTranslateX[animalIndex]);
        flowerInfoText.setTranslateY(infoTranslateY[animalIndex]);
        flowerInfoText.setFill(Color.BROWN);

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
                    getHostServices().showDocument("https://en.wikipedia.org/wiki/Common_sunflower");
                    break;
                case 1: // Elephant
                    getHostServices().showDocument("https://en.wikipedia.org/wiki/Ros%C3%A9");
                    break;
                case 2: // Tiger
                    getHostServices().showDocument("https://en.wikipedia.org/wiki/Bellis_perennis");
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
        stackPane.getChildren().addAll(imageContainerImageView, flowerText, flowerImageView, flowerInfoText,hyperlink, homeButtonImageView, backButtonImageView, leftButtonImageView, rightButtonImageView);

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