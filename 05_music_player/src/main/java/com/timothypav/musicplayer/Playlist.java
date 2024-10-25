package com.timothypav.musicplayer;

import javafx.scene.control.Button;
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

    private HBox getMusicControls(){
        return currentSong().getLayout();
    }

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
        currentSong().reset();
        currentIndex++;

        if (currentIndex == playlist.size())
            currentIndex = 0;

        getVBox();
        playSong(currentIndex);
    }

    public void playAll() {
        playSong(0);
    }

    public VBox getVBox() {
        if (layout != null)
            layout.getChildren().clear();

        Label playlistName = new Label("Current Playlist: " + this.playlistName);
        layout.getChildren().add(playlistName);
        for (Song song : playlist) {
            Label songName = new Label(song.getName());
            layout.getChildren().add(songName);
        }
        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> playNext());
        VBox songControls = new VBox(nextButton, getMusicControls());
        layout.getChildren().add(songControls);
        return layout;
    }


}
