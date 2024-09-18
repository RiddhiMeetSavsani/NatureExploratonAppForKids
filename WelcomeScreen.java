package com.example.natureexplorationapp;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;

import static com.example.natureexplorationapp.Setting.AppSettings.modeOn;


/**
 * This class represents the main application entry point for "Nature's Exploration".
 * It provides a welcome screen with animated text and buttons for various functionalities
 * such as playing the game, leaving feedback, and viewing credits. The welcome screen
 * includes background music, animations for text and buttons, and transitions to enhance
 * user interaction.
 */

public class WelcomeScreen extends Application {

    // URL and MediaPlayer for background music
    URL musicFile = getClass().getResource("/music.mp3");
    Media sound = new Media(musicFile.toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);

    Scene scene1, scene2;
    TextArea userMsg = new TextArea();
    TextField fileName = new TextField("Core4.txt");
    // Text element to display status messages, such as successful save
    Text text = new Text();


    /**
     * Starts the application by setting up the welcome screen with animated text,
     * background music, buttons for various functionalities, and event handlers
     * to respond to user actions such as playing the game, leaving feedback, and
     * viewing credits.
     *
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set.
     * @throws Exception
     */



    @Override
    public void start(Stage stage) throws Exception {

        StackPane stackPane = new StackPane();
        if (Setting.AppSettings.musicOn) {
            // Play background music
            mediaPlayer.play();
        }

        // Set background image

        Image backgroundImage = new Image("/BackPage.png");
        BackgroundSize backgroundSize = new BackgroundSize(100, 95, false, false, false, true);
        BackgroundImage bgImage = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(bgImage);
        stackPane.setBackground(background);
        if(modeOn){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.5); // Adjust brightness for gray effect
            stackPane.setEffect(colorAdjust);
        }

        // Create animated text with translations and rotations
        Text forestText = new Text("Welcome to\nNature's Exploration");
        Font forestFont = Font.loadFont(getClass().getResourceAsStream("/font3.ttf"), 120);
        forestText.setTextAlignment(TextAlignment.CENTER);
        forestText.setFont(forestFont);
        forestText.setFill(Color.rgb(74, 59, 51));

        // Translation Transition for font
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), forestText);
        translateTransition.setFromX(-1000);
        translateTransition.setToX(0);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true); // Reverse direction on reaching the end
        translateTransition.play(); // Start the animation

        // Rotational Transition
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), forestText);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(2);
        rotateTransition.setAutoReverse(false);

        rotateTransition.play();

        // Display wooden board image
        ImageView woodenBoard = imageDisplay("/WoodenBoard.png");
        woodenBoard.setFitHeight(500);
        woodenBoard.setFitWidth(1200);

        // TranslateTransition for wooden board animation
        TranslateTransition woodenBoardTransition = new TranslateTransition(Duration.seconds(1), woodenBoard);
        woodenBoardTransition.setFromY(-400);
        woodenBoardTransition.setToY(0);
        woodenBoardTransition.setCycleCount(1);
        woodenBoardTransition.setAutoReverse(true); // Reverse direction on reaching the end
        woodenBoardTransition.play(); // Start the animation

        // Create buttons with images and set up transitions
        ImageView play = imageDisplay("/play-button.png");
        Button playBT = buttonCreate(play);//method set image to button
        play.setFitHeight(200);
        play.setFitWidth(200);

        ImageView credit = imageDisplay("/information-button.png");
        Button creditBT = buttonCreate(credit);

        ImageView rating = imageDisplay("/customer-review.png");
        Button ratingBT = buttonCreate(rating);

        // Transition for play button
        TranslateTransition playButtonTransition = new TranslateTransition(Duration.seconds(2), creditBT);
        playButtonTransition.setFromX(-900);
        playButtonTransition.setToX(0);
        playButtonTransition.setCycleCount(1);
        playButtonTransition.setAutoReverse(true); // Reverse direction on reaching the end
        playButtonTransition.play(); // Start the animation

        // Rotational Transition
        RotateTransition playBTransition = new RotateTransition(Duration.seconds(3), creditBT);
        playBTransition.setByAngle(360);
        playBTransition.setCycleCount(1);
        playBTransition.setAutoReverse(false);
        playBTransition.play();

        // Transition for rating button
        TranslateTransition reviewBTransition = new TranslateTransition(Duration.seconds(2), ratingBT);
        reviewBTransition.setFromX(900);
        reviewBTransition.setToX(0);
        reviewBTransition.setCycleCount(1);
        reviewBTransition.setAutoReverse(true); // Reverse direction on reaching the end
        reviewBTransition.play(); // Start the animation

        // RotateTransition for rating button
        RotateTransition reviewTransition = new RotateTransition(Duration.seconds(2), ratingBT);
        reviewTransition.setByAngle(-360);
        reviewTransition.setCycleCount(1);
        reviewTransition.setAutoReverse(false);
        reviewTransition.play();

        // Transition for credit button
        TranslateTransition creditTransition = new TranslateTransition(Duration.seconds(2), playBT);
        creditTransition.setFromY(400);// Start from Y = 400
        creditTransition.setToY(0); // Move to Y = 0
        creditTransition.setCycleCount(1);
        creditTransition.setAutoReverse(true); // Reverse direction on reaching the end
        creditTransition.play(); // Start the animation


        // Event handlers for button actions
        EventHandler playHandler = new EventHandler() {
            @Override
            public void handle(Event event) {
                mediaPlayer.stop();
                PlayPage playPage = new PlayPage();
                try {
                    playPage.start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        EventHandler ratingHandler = new EventHandler() {
            @Override
            public void handle(Event event) {

                // Create a new stage for the rating screen
                Stage stage1 = new Stage();

                // Create a BorderPane as the root layout for the rating screen
                BorderPane pane = new BorderPane();

                // TextField to input or display the file name for saving feedback


                // Text elements for various messages and instructions
                Text message = new Text("Your review will help us to give you a better experience");
                Text greetings = new Text("Hello Friends");
                Text suggestion = new Text("What do you want to tell us today?");
                Text review = new Text("This will help us to improve our application and serve you better.\nDo you want to see your feedback?");

                // Set fonts for the text elements using a custom font loaded from resources
                greetings.setFont(Font.loadFont(getClass().getResourceAsStream("/font3.ttf"), 60));
                message.setFont(Font.loadFont(getClass().getResourceAsStream("/font3.ttf"), 30));
                suggestion.setFont(Font.loadFont(getClass().getResourceAsStream("/font3.ttf"), 20));
                review.setFont(Font.loadFont(getClass().getResourceAsStream("/font3.ttf"), 20));

                // TextArea for users to input their feedback

                userMsg.setMaxWidth(600);
                userMsg.setPromptText("Please leave your feedback here");


                // Button to save the user's feedback
                Button save = new Button("Send Feedback");
                save.setOnAction(new MyInner());

                // Button to load and display all previously saved feedback
                Button load = new Button("See Your Feedback");
                load.setOnAction(e -> {

                    // Create a new stage to display all feedback
                    Stage stage2 = new Stage();
                    BorderPane pane2 = new BorderPane();
                    TextArea allFeedback = new TextArea();
                    allFeedback.setEditable(false); // Set the TextArea to read-only

                    StringBuilder sb = new StringBuilder();
                    try (BufferedReader in = new BufferedReader(new FileReader(fileName.getText()))) {
                        String line;
                        while ((line = in.readLine()) != null) {
                            sb.append(line).append("\n"); // Read each line of feedback and append to StringBuilder
                        }
                        allFeedback.setText(sb.toString());  // Set the text of TextArea to display all feedback
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);  // Handle IOException by throwing a runtime exception
                    }

                    pane2.setCenter(allFeedback);  // Set TextArea as the center content of the BorderPane
                     scene2 = new Scene(pane2, 800, 500);
                    stage2.setTitle("All Feedback");
                    stage2.setScene(scene2);
                    stage2.show();
                });


                // VBox to arrange all UI elements vertically with specified spacing
                VBox vBox = new VBox(15, greetings, message, suggestion, userMsg, save, review, load, text);
                vBox.setAlignment(Pos.CENTER);

                pane.setCenter(vBox);

                Scene scene1 = new Scene(pane, 800, 500);
                stage1.setTitle("Rating Screen");
                stage1.setScene(scene1);
                stage1.show();
            }
        };

        EventHandler creditHandler = new EventHandler() {
            @Override
            public void handle(Event event) {

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.setAlignment(Pos.CENTER); // Center the grid in the scene

                // Title and Description
                Label titleLabel = new Label("Nature's Exploration App");
                titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR,30));
                GridPane.setHalignment(titleLabel, HPos.CENTER); // Center the title label in its cell
                GridPane.setColumnSpan(titleLabel, 2); // Span the title label across two columns
                //GridPane.setS

                Label descriptionLabel = new Label("Nature's Exploration is an educational app developed using JavaFX for Windows. "
                        + "It features intuitive UI elements like buttons, labels, and animations to engage children "
                        + "in learning about animals, Flowers, Seasons, Birds, Tree,and Fruits. The app includes interactive quizzes for "
                        + "testing knowledge, with feedback received through Java I/O operations.");
                descriptionLabel.setFont(new Font("Arial", 14));
                descriptionLabel.setWrapText(true);

                // Project created by and lead by labels
                Label projectLeadLabel = new Label("Developed by: Riddhi Savsani, Sakshi Patel, Sagar Parmar, Priyank Patel");
                Label projectLeadByLabel = new Label("Lead by: Riddhi Savsani");
                projectLeadLabel.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR,14));
                projectLeadByLabel.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR,14));

                // Special Thanks
                Label specialThanksLabel = new Label("Special Thanks for guidance and support : Prof. Ammar Albakhi ");
                specialThanksLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR,18));

                // Tools and Resources
                Label toolsLabel = new Label("Tools and Resources:");
                toolsLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR,18));
                Label developmentToolsLabel = new Label("Development Tools: IntelliJ IDEA");
                Label designToolsLabel = new Label("Design Tools: Canva, PicsArt");
                Label iconsLabel = new Label("Icons and Graphics: FlatIcon.com");
                Label voiceOverLabel = new Label("Voice Over: Created using Narakeet website");
                Label imagesLabel = new Label("Images: FreePik");
                Label openSource = new Label("Open Source: Google open source resources");

                developmentToolsLabel.setFont(Font.font("Arial", FontWeight.NORMAL,FontPosture.REGULAR, 14));
                designToolsLabel.setFont(Font.font("Arial", FontWeight.NORMAL,FontPosture.REGULAR, 14));
                iconsLabel.setFont(Font.font("Arial", FontWeight.NORMAL,FontPosture.REGULAR, 14));
                voiceOverLabel.setFont(Font.font("Arial", FontWeight.NORMAL,FontPosture.REGULAR, 14));
                imagesLabel.setFont(Font.font("Arial", FontWeight.NORMAL,FontPosture.REGULAR, 14));
                openSource.setFont(Font.font("Arial", FontWeight.NORMAL,FontPosture.REGULAR, 14));

                // Acknowledgments
                Label acknowledgmentsLabel = new Label("Acknowledgments:");
                acknowledgmentsLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR,18));
                Label educationalInstitutionsLabel = new Label("Educational Institution: St. Clair College");
                educationalInstitutionsLabel.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR,14));

                // Add elements to the grid
                grid.add(titleLabel, 0, 0);
                grid.add(descriptionLabel, 0, 1);
                grid.add(projectLeadLabel, 0, 2);
                grid.add(projectLeadByLabel, 0, 3);
                grid.add(specialThanksLabel, 0, 5);
                grid.add(toolsLabel, 0, 7);
                grid.add(developmentToolsLabel, 0, 8);
                grid.add(designToolsLabel, 0, 9);
                grid.add(iconsLabel, 0, 10);
                grid.add(voiceOverLabel, 0, 11);
                grid.add(imagesLabel, 0, 12);
                grid.add(openSource, 0, 13 );
                grid.add(acknowledgmentsLabel, 0, 15, 2, 1);
                grid.add(educationalInstitutionsLabel, 0, 16);

                Stage stage2 = new Stage();
                scene1 = new Scene(grid, 800, 600);
                stage2.setTitle("Credits Screen!");
                stage2.setScene(scene1);
                stage2.show();
            }
        };

        // Set event handlers for buttons
        playBT.setOnAction(playHandler);
        ratingBT.setOnAction(ratingHandler);
        creditBT.setOnAction(creditHandler);


        // Layout setup for buttons
        HBox buttons = new HBox(120);
        buttons.getChildren().addAll(creditBT, playBT, ratingBT);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(800, 0, 50, 0));


        // Add elements to main stack pane
        stackPane.getChildren().addAll(woodenBoard ,forestText, buttons);
        StackPane.setAlignment(buttons, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(woodenBoard, Pos.TOP_CENTER);
        StackPane.setMargin(forestText, new Insets(-330, 0, 0, 0));


        // Scene and Stage setup
        Scene scene = new Scene(stackPane, 1800, 1012.5);
        stage.setTitle("Nature's Exploration");
        stage.setScene(scene);
        stage.show();
    }

    // Method to display an image
    private ImageView imageDisplay(String image) {
        InputStream stream = getClass().getResourceAsStream(image);
        Image imageF = (stream != null) ? new Image(stream) : null;
        ImageView imageDisplay = new ImageView(imageF);
        imageDisplay.setFitHeight(110);
        imageDisplay.setFitWidth(110);
        return imageDisplay;
    }

    // Method to create buttons from images
    private Button buttonCreate(ImageView imageDisplay) {
        Button buttonCreate = new Button();
        buttonCreate.setGraphic(imageDisplay);
        buttonCreate.setShape(new Circle(40));
        buttonCreate.setBackground(null);
        buttonCreate.setPadding(new Insets(0));
        return buttonCreate;
    }

    //Inner class
    private class MyInner implements EventHandler{
        @Override
        public void handle(Event event) {
            try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName.getText(), true))) {
                out.write(userMsg.getText() + "\n");
                text.setText("Your message has been saved");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
