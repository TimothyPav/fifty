package com.timothypav.musicplayer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Song {
    private MediaPlayer songPlayer;

    public Song(String file){
        Media song = new Media(new File(file).toURI().toString());
        this.songPlayer = new MediaPlayer(song);
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

    public double getPlaytime(){
        return songPlayer.getCurrentTime().toMinutes();
    }

    public MediaPlayer getMediaPlayer(){
        return songPlayer;
    }



}
