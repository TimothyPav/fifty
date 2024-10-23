package com.timothypav.musicplayer;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;

import javafx.scene.control.Label;

public class Playlist {
    private ArrayList<Song> playlist;
    private String playlistName;
    private int currentIndex = 0;
    private VBox layout;

    public Playlist(String name) {
        playlist = new ArrayList<Song>();
        playlistName = name;
        layout = new VBox();
    }

    public void addSong(Song song) {
        playlist.add(song);
    }

    public void removeSong(Song song) {
        playlist.remove(song);
    }

    public int getSize() {
        return playlist.size();
    }

    public void playSong(int index) {
        if (getSize() > index) {
            currentIndex = index;
            Song currSong = playlist.get(currentIndex);
            currSong.play();
            currSong.getMediaPlayer().setOnEndOfMedia(() -> {
                playSong(currentIndex + 1);
            });
        }
    }

    public Song currentSong(){
       return playlist.get(currentIndex);
    }

    public void playNext() {
        if (currentIndex == playlist.size() - 1)
            currentIndex = 0;

        currentSong().reset();
        currentIndex++;
        playSong(currentIndex);
    }

    public void playAll() {
        playSong(0);
    }

    public VBox getVBox() {
        Label playlistName = new Label(this.playlistName);
        layout.getChildren().add(playlistName);
        System.out.println("Length of playlist: " + playlist.size());
        for (Song song : playlist) {
            System.out.printf("NAME OF SONG ALLEGEDLY: " + song.getName());
            Label songName = new Label(song.getName());
            layout.getChildren().add(songName);
        }
        return layout;
    }

}
