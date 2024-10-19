package com.timothypav.musicplayer;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.ScrollBar;

public class MusicPlayerApp extends Application {

    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) {
        String bip = "./songs/creative-technology-showreel.mp3";
        Media hit = new Media(new File(bip).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);


        // Button to stop playback
        Button stopButton = new Button("Stop Music");
        stopButton.setOnAction(e -> mediaPlayer.stop());

        // Button to start playback
        Button playButton = new Button("Play Music");
        playButton.setOnAction(e -> mediaPlayer.play());

        ScrollBar s = new ScrollBar();
        s.setMin(0);
        s.setMax(.5);
        s.setValue(.05);
        s.setOrientation(Orientation.VERTICAL);
        s.setUnitIncrement(.001);
        s.setBlockIncrement(.001);
        mediaPlayer.setVolume(s.getValue());

        // Create a Label to display the value
        Label valueLabel = new Label("Current Value: " + s.getValue());

        // Add a listener to the ScrollBar's value property
        s.valueProperty().addListener((observable, oldValue, newValue) -> {
            // This code runs every time the value changes
            double value = newValue.doubleValue();
            valueLabel.setText(String.format("Current Value: %.2f", value));

            mediaPlayer.setVolume(value);
        });

        // Layout with buttons
        VBox root = new VBox(stopButton, playButton, s, valueLabel);

        Scene scene = new Scene(root, 300, 500);
        stage.setTitle("Music Player");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        System.out.println("Hello ABOVE");
        launch(args);
        System.out.println("Hello BELOW");
    }
}
