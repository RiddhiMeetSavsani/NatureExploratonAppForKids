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

public class TreeScreen extends Application {

    private void stopAnimalAudio() {
        if (animalMediaPlayer != null) {
            animalMediaPlayer.stop();
        }
    }

    private List<Scene> scenes = new ArrayList<>();
    private int currentSceneIndex = 0;

    // Arrays to store animal data
    private String[] animalNames = {"Oak", "Pine", "Cherry"};
    private String[] animalInfoTexts = {
            "Hey there,kids!I’m\nthe Oak Tree,strong\nand majestic.\nYou can spot me by\nmy sturdy trunk and\nwide branches that stretch\ntowards the sky.\nI'm a home for many animals\nlike squirrels and birds.",
            "Hi kids,I’m the\nPine Tree,tall and\nevergreen!\nYou can recognize me\nby my straight trunk\ncovered in rough bark\nand my needle-like leaves that\nstay green all year round\nI am used in Christmas tree.",
            "Hello,young friends!\nI’m the Cherry Tree,\nfamous for my\nbeautiful blossoms\nand delicious fruit.\nIn the spring,I burst into a\nspectacular display of pink and\nwhite flowers.\nBees buzz around me,collecting\nnectar to make honey"
    };

    private Hyperlink[] hyperlinks = new Hyperlink[] {
            new Hyperlink("For more Info."),
            new Hyperlink("For more Info."),
            new Hyperlink("For more Info.")
    };
    private String[] animalImagePaths = {"/Project_Images/oak.png", "/Project_Images/pine.png", "/Project_Images/cherry.png"};

    private String[] animalAudioPaths = {"/Project_Images/oak_audio.mp3", "/Project_Images/pine_audio.mp3", "/Project_Images/cherry_audio.mp3"};
    private double[] imageTranslateX = {-240, -280, -240};
    private double[] imageTranslateY = {20, 20, 20};
    private double[] textTranslateX = {-210, -280, -210};
    private double[] textTranslateY = {-260, -250, -260};
    private double[] infoTranslateX = {290, 300, 300};
    private double[] infoTranslateY = {-10, 0, 10};

    // Arrays to store scale transition values for each animal
    private double[] scaleXValues = {2, 2, 2}; // Example values for each animal
    private double[] scaleYValues = {2, 2, 2}; // Example values for each animal

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

    /**
     * Method to change the scene
     * like here the first time when my createAnimalScene is called it will change the current index
     */

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
    /**
     *Main method that is doing my main scene
     */
    private Scene createAnimalScene(Stage stage, int animalIndex) throws IOException {

        //background Image
        Image backgroundImageClass = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Project_Images/backGround.jpg")));
        ImageView backGroundImageView = new ImageView(backgroundImageClass);
        backGroundImageView.setFitHeight(770);
        backGroundImageView.setFitWidth(1470);

        //Background
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(backgroundImageClass, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        //navigation buttons
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

        //setting event handler to my back button
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

        // TODO event handlers for navigation buttons
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
                    getHostServices().showDocument("https://en.wikipedia.org/wiki/Oak");
                    break;
                case 1: // Elephant
                    getHostServices().showDocument("https://en.wikipedia.org/wiki/Pine");
                    break;
                case 2: // Tiger
                    getHostServices().showDocument("https://en.wikipedia.org/wiki/Cherry");
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
