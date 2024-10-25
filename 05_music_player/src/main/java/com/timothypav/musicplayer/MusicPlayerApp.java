package com.timothypav.musicplayer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayerApp extends Application {

    private Song song;

    @Override
    public void start(Stage stage) {
        Song song = new Song("./songs/creative-technology-showreel.mp3", "creative technology showreel");
        MediaPlayer player = song.getMediaPlayer();
        Song song2 = new Song("./songs/night-detective.mp3", "night detective");

        Playlist playlist = new Playlist("test playlist");
        playlist.addSong(song);
        playlist.addSong(song2);

        VBox mainControls = new VBox(playlist.getVBox());

        // Layout with buttons
        HBox root = new HBox(mainControls);

        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Music Player");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
