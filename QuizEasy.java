package com.example.natureexplorationapp;

import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

import static com.example.natureexplorationapp.QuizQuitPage.questionCount;
import static com.example.natureexplorationapp.QuizQuitPage.score;
import static com.example.natureexplorationapp.Setting.AppSettings.modeOn;

/**
 * This JavaFX application class, QuizEasy, implements a quiz interface
 * featuring multiple-choice questions with associated images and results.
 * The quiz progresses through a series of predefined questions with three
 * answer choices each, allowing users to navigate using next and previous
 * buttons. It includes functionality for displaying correct answers, updating
 * the score, and transitioning between questions with animations. The class
 * handles audio playback for question prompts and result feedback. Users can
 * quit the quiz and return to the main menu. Overall, this class encapsulates
 * the interactive quiz experience with graphical elements and user interaction
 * for educational or recreational purposes.
 */


public class QuizEasy  extends Application {


    // Instance variables for managing the quiz state
    private int currentScreen = 0;  // Tracks the current question index
    private boolean ansChoosed = false;  // boolean type to check if an answer has been chosen or not
    private int nextButtonPressCount=0;  // Tracks the count of next button presses
    private int previousButtonPressCount=0;  // Tracks the count of previous button presses
    MediaPlayer mediaPlayer1;


    // Arrays holding questions, options, correct answers, results, and related media
    private final String[] questions = {
            "Which animal group is called prides?",
            "Which animal is known for its intelligence?",
            "Which animal likes to eat oak tree branches?",
            "Which flower is used in food items?",
            "In which food item below can we use pears?"
    };

    private final String[][] options = {
            {"Lions", "Elephants", "Wolves"}, // Correct answer is "Lions"
            {"Giraffe", "Zebra", "Elephant"}, // Correct answer is "Elephant"
            {"Rabbits", "Squirrels", "Deer"}, // Correct answer is "Squirrels"
            {"Rose", "Tulip", "Daisy"}, // Correct answer is "Rose"
            {"Soup", "Bread", "Pies"} // Correct answer is "Pies"
    };

    private final String[] results = {
            "Lions are known to form prides.",
            "Elephants are known for their intelligence.",
            "Deer like to eat oak tree branches.",
            "Rose is often used in food items.",
            "Pears can be used in pies."
    };

    private final String[] resultImages = {
            "/projectImages/congratulation.png",
            "/projectImages/oops.png",
            "/projectImages/oops.png"
    };

    private final String[] audioAnswers = {
            "audioAns.mp3",
            "audioAns2.mp3",
            "audioAns3.mp3"
    };


    private final String[] correctAnswers = {
            "Lions", "Elephant", "Squirrels", "Rose", "Pies"
    };

    // Instance variables for labels and containers
    private Label question_text;
    private Text option1_text;
    private Text option2_text;
    private Text option3_text;
    private Label result;
    private HBox hBox_options;
    private HBox rightMiddleBox;
    private HBox leftMiddleBox;
    private HBox result_hBox;
    private Button quit_button;
    private HBox buttonBox;
    private HBox centerBottomBox;


    @Override
    public void start(Stage stage) {

        // Start playing audio for the current question
        playQuestionAudio(currentScreen);

        score=0;
        questionCount=0;

        // Create the main StackPane for the quiz interface
        StackPane mainStackPane = new StackPane();

        // Load and set up images for navigation and buttons
        Image home_image = new Image(getClass().getResourceAsStream("/projectImages/home1.png"));
        Image next_image = new Image(getClass().getResourceAsStream("/projectImages/nextBW.png"));
        Image previous_image = new Image(getClass().getResourceAsStream("/projectImages/previousBW.png"));
        Image back_image = new Image(getClass().getResourceAsStream("/projectImages/back.png"));

        // Create image views and set dimensions
        ImageView home_imageView = new ImageView(home_image);
        ImageView next_imageView = new ImageView(next_image);
        ImageView previous_imageView = new ImageView(previous_image);
        ImageView back_imageView = new ImageView(back_image);


        home_imageView.setFitWidth(80);
        home_imageView.setFitHeight(80);
        next_imageView.setFitWidth(80);
        next_imageView.setFitHeight(80);
        previous_imageView.setFitWidth(80);
        previous_imageView.setFitHeight(80);
        back_imageView.setFitWidth(80);
        back_imageView.setFitHeight(80);

        // Create and style the quit button
        Button quit_button = new Button();
        Text quitText = new Text("Quit");
        quitText.setFill(Color.WHITE);
        quitText.setFont(Font.font("Impact", FontWeight.BOLD, 35));
        quit_button.setGraphic(quitText);
        quit_button.setBackground(new Background(new BackgroundFill(Color.rgb(70, 83, 3), CornerRadii.EMPTY, Insets.EMPTY)));
        quit_button.setMinWidth(80);
        quit_button.setMinHeight(35);
        quit_button.setPadding(new Insets(4, 6, 4, 6));

        // Set up click handlers for navigation buttons
        next_imageView.setOnMouseClicked(e -> handleNextButtonClick(stage));
        previous_imageView.setOnMouseClicked(e->handlePreviousButtonClick(stage));
        home_imageView.setOnMouseClicked(e -> {
            // Stop background music
            stopCurrentAudio();

            // Navigate back to the Welcome Screen
            PlayPage playPage = new PlayPage();
            try {
                playPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        back_imageView.setOnMouseClicked(e->{  //setting up event handler for back button
            stopCurrentAudio();
            QuizHomeScreen quizHomeScreen = new QuizHomeScreen();
            quizHomeScreen.start(stage);
        });

        // Create option buttons with text and styling
        Button option_button1 = new Button("Option A");
        Button option_button2 = new Button("Option B");
        Button option_button3 = new Button("Option C");

        // Styles for option buttons
        //Font customFont = Font.loadFont(getClass().getResourceAsStream("font3.ttf"), 30);
        option_button1.setFont(Font.font("impact", 30));
        option_button1.setBackground(new Background(new BackgroundFill(Color.rgb(70, 83, 3), CornerRadii.EMPTY, Insets.EMPTY)));
        option_button1.setTextFill(Color.WHITE);
        option_button1.setPadding(new Insets(4, 6, 4, 6));

        option_button2.setFont(Font.font("impact", 30));
        option_button2.setBackground(new Background(new BackgroundFill(Color.rgb(70, 83, 3), CornerRadii.EMPTY, Insets.EMPTY)));
        option_button2.setTextFill(Color.WHITE);
        option_button2.setPadding(new Insets(4, 6, 4, 6));

        option_button3.setFont(Font.font("impact", 30));
        option_button3.setBackground(new Background(new BackgroundFill(Color.rgb(70, 83, 3), CornerRadii.EMPTY, Insets.EMPTY)));
        option_button3.setTextFill(Color.WHITE);
        option_button3.setPadding(new Insets(4, 6, 4, 6));

        // Create a HBox for option buttons and set alignment and spacing
        buttonBox = new HBox(option_button1, option_button2, option_button3);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(300);
        option_button1.setTranslateX(50);
        option_button2.setTranslateX(18);

        // Handle click events for option buttons
        option_button1.setOnAction(e -> handleOptionClicked1( result_hBox,audioAnswers[currentScreen% audioAnswers.length], resultImages[currentScreen % resultImages.length], options[currentScreen][0], stage));
        option_button2.setOnAction(e -> handleOptionClicked1( result_hBox,audioAnswers[(currentScreen+1)%audioAnswers.length], resultImages[(currentScreen + 1) %resultImages.length], options[currentScreen][1], stage));
        option_button3.setOnAction(e -> handleOptionClicked1( result_hBox,audioAnswers[(currentScreen+2)%audioAnswers.length],resultImages[(currentScreen + 2) % resultImages.length], options[currentScreen][2], stage));


        // Setup HBox for top right (home) and center right (next)
        HBox topRightBox = new HBox(1640, back_imageView,home_imageView);
        topRightBox.setAlignment(Pos.TOP_CENTER);

        rightMiddleBox = new HBox(next_imageView);
        rightMiddleBox.setAlignment(Pos.CENTER_RIGHT);

        leftMiddleBox = new HBox(previous_imageView);
        leftMiddleBox.setAlignment(Pos.CENTER_LEFT);

        centerBottomBox = new HBox(quit_button);
        centerBottomBox.setAlignment(Pos.CENTER);
        centerBottomBox.setPadding(new Insets(10));
        quit_button.setOnMouseClicked(event -> {
            stopCurrentAudio();
            QuizQuitPage quizQuitPage = new QuizQuitPage();
            quizQuitPage.start(stage);
        });

        // Create a BorderPane for layout and set components
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topRightBox);
        borderPane.setRight(rightMiddleBox);
        borderPane.setLeft(leftMiddleBox);
        borderPane.setCenter(buttonBox);
        borderPane.setBottom(centerBottomBox);

        // Add the BorderPane and quiz screen to the main StackPane
        mainStackPane.getChildren().addAll(createQuizScreen(0), borderPane);

        // Create and set the Scene with specified dimensions
        Scene scene = new Scene(mainStackPane, 1800, 1012.5);
        stage.setTitle("Quiz Screens");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to create the main quiz screen.
     * @param index Index of the question to display.
     * @return StackPane containing the quiz screen elements.
     */
    private StackPane createQuizScreen(int index) { //here pass the index as argument

        // Load images for options
        Image option_image = new Image(getClass().getResourceAsStream("/projectImages/monkey.png"));

        // Create ImageViews for options
        ImageView option1_imageView = new ImageView(option_image);
        option1_imageView.setFitWidth(370);
        option1_imageView.setFitHeight(310);
        ImageView option2_imageView = new ImageView(option_image);
        option2_imageView.setFitWidth(370);
        option2_imageView.setFitHeight(310);
        ImageView option3_imageView = new ImageView(option_image);
        option3_imageView.setFitWidth(370);
        option3_imageView.setFitHeight(310);

        // Create labels and texts for question and options
        question_text = new Label(questions[index]);
        option1_text = new Text(options[index][0]);
        option2_text = new Text(options[index][1]);
        option3_text = new Text(options[index][2]);

        // Set fonts and styles for question and options
        question_text.setFont(Font.font("impact", FontWeight.BOLD, 45));
        option1_text.setFont(Font.font(30));
        option2_text.setFont(Font.font(30));
        option3_text.setFont(Font.font(30));

        // Positioning adjustments for option texts
        option1_text.setTranslateX(-20);
        option1_text.setTranslateY(20);
        option2_text.setTranslateX(-20);
        option2_text.setTranslateY(20);
        option3_text.setTranslateX(-20);
        option3_text.setTranslateY(20);


        // Styling for question text background
        question_text.setBackground(new Background(new BackgroundFill(Color.rgb(199,233,216), CornerRadii.EMPTY, Insets.EMPTY)));
        question_text.setPadding(new Insets(10));

        // Layout for Question text, Question tag, and Question mark
        HBox que_box = new HBox(question_text);
        que_box.setAlignment(Pos.CENTER);

        // StackPanes to overlay text on option images
        StackPane addTextToOption1 = new StackPane(option1_imageView, option1_text);
        StackPane addTextToOption2 = new StackPane(option2_imageView, option2_text);
        StackPane addTextToOption3 = new StackPane(option3_imageView, option3_text);

        // HBox to hold option images and texts
        hBox_options = new HBox(addTextToOption1, addTextToOption2, addTextToOption3);
        hBox_options.setAlignment(Pos.CENTER);
        hBox_options.setSpacing(20);


        // Animation setup for options
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), hBox_options);
        translateTransition.setFromY(-400);
        translateTransition.setToY(-100);
        translateTransition.setCycleCount(1);
        //translateTransition.play();

        TranslateTransition bounceBack = new TranslateTransition(Duration.seconds(0.5), hBox_options);
        bounceBack.setFromY(-100);
        bounceBack.setToY(-150);
        bounceBack.setAutoReverse(true);
        bounceBack.setCycleCount(2);
        //bounceBack.play();

        SequentialTransition sequentialTransition = new SequentialTransition(translateTransition, bounceBack);
        sequentialTransition.play();

        // Create result HBox
        result_hBox = createResultHBox(index);

        // Background setup for the quiz screen
        Image background_image = new Image(getClass().getResourceAsStream("/projectImages/quizBackgroundImage.JPEG"));
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(background_image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        // StackPane setup with components
        StackPane stackPane = new StackPane(hBox_options, result_hBox, que_box);
        stackPane.setBackground(background);
        stackPane.setMargin(que_box, new Insets(0, 0, 700, 0));

        if(modeOn){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.5); // Adjust brightness for gray effect
            stackPane.setEffect(colorAdjust);
        }

        return stackPane;
    }


    /**
     * Method to create the result HBox.
     * @param index Index of the question to display result.
     * @return HBox containing the result text.
     */

    // Method to create the result HBox for displaying correct answers
    private HBox createResultHBox(int index) {
        HBox resultHBox = new HBox(5);
        result = new Label(results[index]);
        result.setFont(Font.font("impact", FontWeight.BOLD, 35));
        result.setPadding(new Insets(10));
        result.setBackground(new Background(new BackgroundFill(Color.rgb(199,233,216), CornerRadii.EMPTY, Insets.EMPTY)));
        return resultHBox;
    }


    /**
     * Method to handle the click event on an option button
     * @param result_hBox HBox container for displaying result image and text.
     * @param resultImagePath Path to result image based on correctness.
     * @param selectedOption2 Selected option by the user.
     * @param stage Stage object for scene management.
     */
    private void handleOptionClicked1( HBox result_hBox,String audioAnswers, String resultImagePath, String selectedOption2, Stage stage) {
        if (!ansChoosed) {
            ansChoosed = true;
            mediaPlayer.stop();
            if (selectedOption2.equals(correctAnswers[currentScreen])) {
                score++;
            }

            // Display result image and text feedback
            Image result_image = new Image(getClass().getResourceAsStream(resultImagePath));
            ImageView result_imageview = new ImageView(result_image);
            result_imageview.setFitWidth(180);
            result_imageview.setFitHeight(180);
            result_hBox.getChildren().clear();

            // Animation for result feedback
            result_hBox.getChildren().addAll(result_imageview, result);
            result_hBox.setTranslateX(900);
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), result_hBox);
            translateTransition.setFromY(1013);
            translateTransition.setToY(600);
            translateTransition.play();

            questionCount++;

            // Display score after every 5 questions
            if (questionCount % 6== 0) {
                stopCurrentAudio();
                QuizQuitPage quizQuitPage = new QuizQuitPage();
                quizQuitPage.start(stage);
            }

            String audioPath = "/audio/"+audioAnswers;
            URL resource = getClass().getResource(audioPath);
            if (resource != null && Setting.AppSettings.musicOn) {

                String musicFile = resource.toString();
                Media sound = new Media(musicFile);
                mediaPlayer1 = new MediaPlayer(sound);
                mediaPlayer1.play();
            }
        }
    }


    /**
     * Method to handle the click event on the Next button.
     * @param stage Stage object for scene management.
     */
    private void handleNextButtonClick(Stage stage) {
        ansChoosed = false;
        currentScreen = (currentScreen + 1) % questions.length;
        result_hBox.getChildren().clear();

        // Check if it's time to show the quit page after completing 5 questions
        if (currentScreen == 0 && questionCount % 5 == 0) {
            stopCurrentAudio();
            QuizQuitPage quizQuitPage = new QuizQuitPage();
            quizQuitPage.start(stage);
        } else {
            updateQuizScreen();
        }

    }


    /**
     * Method to handle the click event on the Previous button.
     * @param stage Stage object for scene management.
     */
    private void handlePreviousButtonClick(Stage stage) {
        ansChoosed = false;
        currentScreen = (currentScreen - 1 + questions.length) % questions.length;
        result_hBox.getChildren().clear();

        // Check if it's time to show the quit page after completing 5 questions
        if (currentScreen == 0 && questionCount % 5 == 0) {
            stopCurrentAudio();
            QuizQuitPage quizQuitPage = new QuizQuitPage();
            quizQuitPage.start(stage);
        } else {
            updateQuizScreen();
        }

        // Track the count of previous button presses and show quit page after every 5 presses
        previousButtonPressCount++;
        if (previousButtonPressCount % 5 == 0) {
            stopCurrentAudio();
            QuizQuitPage quizQuitPage = new QuizQuitPage();
            quizQuitPage.start(stage);
        }
    }


    /**
     * Method to handle the click event on the Home button.
     */
    private void handleHomeButtonClick() {
        // Implement home button functionality
        System.out.println("Home button clicked");
        // Add your navigation logic here
    }

    /**
     * Method to update the quiz screen with the next question.
     */
    private void updateQuizScreen() {

        // Play audio for the current question
        playQuestionAudio(currentScreen);

        // Update the text of questionLabel, option1Label, option2Label, option3Label, and resultLabel
        question_text.setText(questions[currentScreen]);
        option1_text.setText(options[currentScreen][0]);
        option2_text.setText(options[currentScreen][1]);
        option3_text.setText(options[currentScreen][2]);
        result.setText(results[currentScreen]);

        // Animation for updating options
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), hBox_options);
        translateTransition.setFromY(-400);
        translateTransition.setToY(-100);
        translateTransition.setCycleCount(1);
        //translateTransition.play();

        TranslateTransition bounceBack = new TranslateTransition(Duration.seconds(0.5), hBox_options);
        bounceBack.setFromY(-100);
        bounceBack.setToY(-150);
        bounceBack.setAutoReverse(true);
        bounceBack.setCycleCount(2);
        //bounceBack.play();

        SequentialTransition sequentialTransition = new SequentialTransition(translateTransition, bounceBack);
        sequentialTransition.play();


    }



    private MediaPlayer mediaPlayer; // Declare MediaPlayer as a class-level variable

    /**
     * Method to play the audio for the current question.
     * @param index Index of the question to play audio.
     */
    private void playQuestionAudio(int index) {
        try {
            // Construct the audio file path based on index (assuming files are named audio1.mp3, audio2.mp3, etc.)
            String audioPath = "/audio/audioE" + (index + 1) + ".mp3";
            URL resource = getClass().getResource(audioPath);
            if (resource != null && Setting.AppSettings.musicOn) {
                String musicFile = resource.toString();
                Media sound = new Media(musicFile);

                // Stop any currently playing audio
                stopCurrentAudio();

                // Create a new MediaPlayer and play the audio
                mediaPlayer = new MediaPlayer(sound);

                // Ensure MediaPlayer is kept in scope
                mediaPlayer.setOnError(() -> {
                    System.err.println("Media error occurred: " + mediaPlayer.getError());
                });

                mediaPlayer.play();
            } else {
                System.err.println("Audio file not found: " + audioPath);
            }
        } catch (Exception e) {
            System.err.println("Error playing audio for question: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Method to stop the current audio playback.
     */
    private void stopCurrentAudio() {
        // Stop the current MediaPlayer if it's playing
        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop();
            }
            mediaPlayer.dispose();
        }
        if(mediaPlayer1 != null){
            if(mediaPlayer1.getStatus()== MediaPlayer.Status.PLAYING){
                mediaPlayer1.stop();
                mediaPlayer1.dispose();
            }
        }
    }


    // Main method to launch the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}
