package com.timothypav.musicplayer;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.ScrollBar;
import javafx.util.Duration;

public class MusicPlayerApp extends Application {

    private SongPlayer songPlayer;

    @Override
    public void start(Stage stage) {
        String bip = "./songs/creative-technology-showreel.mp3";
        Media hit = new Media(new File(bip).toURI().toString());
        System.out.println("HELLO!!!!: " + hit.getMetadata().toString());
        SongPlayer songPlayer = new SongPlayer(hit);
        MediaPlayer player = songPlayer.getMediaPlayer();


        // Button to stop playback
        Button stopButton = new Button("Stop Music");
        stopButton.setOnAction(e -> songPlayer.pause());

        // Button to start playback
        Button playButton = new Button("Play Music");
        playButton.setOnAction(e -> songPlayer.play());

        VolumeBar volumeBar = new VolumeBar(player);

        PlaybackBar b = new PlaybackBar(player);

        player.currentTimeProperty().addListener((observable,  oldValue, newValue) -> {
            double value = newValue.toMinutes();
            double total_duration = player.getTotalDuration().toMinutes();
            double progress = value/total_duration;
            b.setTime(progress);
        });


        // Layout with buttons
        VBox root = new VBox(stopButton, playButton, volumeBar.getScrollBar(), b.getBar());

        Scene scene = new Scene(root, 300, 500);
        stage.setTitle("Music Player");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
