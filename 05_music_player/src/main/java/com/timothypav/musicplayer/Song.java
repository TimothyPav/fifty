package com.timothypav.musicplayer;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import javafx.scene.control.Label;


public class Song {
    private MediaPlayer songPlayer;
    private String name;
    private PlaybackBar playbackBar;
    private VolumeBar volumeBar;
    private HBox layout;

    public Song(String file, String name){
        Media song = new Media(new File(file).toURI().toString());
        this.songPlayer = new MediaPlayer(song);
        this.name = name;
        this.playbackBar = new PlaybackBar(songPlayer);
        this.volumeBar = new VolumeBar(songPlayer);
        this.layout = new HBox();
    }

    public Song(Media song) {
        this.songPlayer = new MediaPlayer(song);
    }

    public void setSong(Media song){
        songPlayer = new MediaPlayer(song);
    }

    public void pause(){
        songPlayer.pause();
    }
    public void play(){
        PlaylistCatalog.resetAllSongs(this.songPlayer);
        songPlayer.play();
    }

    public void reset(){
        songPlayer.stop();
    }

    public double getPlaytime(){
        return songPlayer.getCurrentTime().toMinutes();
    }

    public MediaPlayer getMediaPlayer(){
        return songPlayer;
    }

    public PlaybackBar getPlaybackBar() {
        return playbackBar;
    }

    public String getName(){
        return name;
    }

    public HBox getLayout(Button previous, Button next) {
        if (layout != null)
            layout.getChildren().clear();

        volumeBar.setVolume();

        Button playButton = new Button("Play");
        playButton.setOnAction(e -> play());
        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(e -> pause());

        HBox mediaButtons = new HBox(previous, playButton, pauseButton, next);
        HBox mediaBars = new HBox(playbackBar.getBar(), volumeBar.getScrollBar());

        VBox mediaControls = new VBox(mediaButtons, mediaBars);

        layout.getChildren().add(mediaControls);
        System.out.println("UPDATED");
        return layout;
    }
}
