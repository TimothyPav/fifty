package com.timothypav.musicplayer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SongPlayer {
    private MediaPlayer songPlayer;

    public SongPlayer(Media song) {
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
