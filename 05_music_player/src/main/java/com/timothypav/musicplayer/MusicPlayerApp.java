package com.timothypav.musicplayer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayerApp extends Application {

    private Song song;

    @Override
    public void start(Stage stage) {
        String bip = "./songs/creative-technology-showreel.mp3";
        Media hit = new Media(new File(bip).toURI().toString());
        System.out.println("HELLO!!!!: " + hit.getMetadata().toString());
        Song song = new Song(hit);
        MediaPlayer player = song.getMediaPlayer();
        Song song2 = new Song("./songs/night-detective.mp3");

        Playlist playlist = new Playlist();
        playlist.addSong(song);
        playlist.addSong(song2);
        playlist.playAll();


        // Button to stop playback
        Button stopButton = new Button("Stop Music");
        stopButton.setOnAction(e -> song.pause());

        // Button to start playback
        Button playButton = new Button("Play Music");
        playButton.setOnAction(e -> song.play());

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

        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Music Player");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
