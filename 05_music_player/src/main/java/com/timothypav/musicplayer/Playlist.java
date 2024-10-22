package com.timothypav.musicplayer;

import javafx.scene.media.MediaPlayer;
import java.util.ArrayList;

public class Playlist {
    private ArrayList<Song> playlist;
    private int currentIndex = 0;

    public Playlist(){
        playlist = new ArrayList<Song>();
    }

    public void addSong(Song song){
        playlist.add(song);
    }

    public void removeSong(Song song){
        playlist.remove(song);
    }

    public int getSize(){
        return playlist.size();
    }

    public void playSong(int index){
        if (getSize() > index){
            currentIndex = index;
            Song currSong = playlist.get(currentIndex);
            currSong.play();
            currSong.getMediaPlayer().setOnEndOfMedia(() -> {
                playSong(currentIndex + 1);
            });
        }
    }

    public void playAll(){
        playSong(0);
    }

}
