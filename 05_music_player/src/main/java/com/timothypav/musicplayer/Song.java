package com.timothypav.musicplayer;

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
    private VBox layout;

    public Song(String file, String name){
        Media song = new Media(new File(file).toURI().toString());
        this.songPlayer = new MediaPlayer(song);
        this.name = name;
        this.playbackBar = new PlaybackBar(songPlayer);
        this.layout = new VBox();
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

    public VBox getLayout() {
        Label play = new Label("Play Song");
        Label pause = new Label("Pause Song");
        layout.getChildren().add(play);
        layout.getChildren().add(pause);
        return layout;
    }

}
