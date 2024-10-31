package com.timothypav.musicplayer;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

import javafx.scene.control.Label;

public class Playlist {
    private final ArrayList<Song> playlist;
    private String playlistName;
    private int currentIndex = 0;
    private VBox layout;

    private HBox getMusicControls(Button previous, Button next){
        return currentSong().getLayout(previous, next);
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

    public String getPlaylistName() {
        return playlistName;
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

    public void playPrevious() {
        currentSong().reset();
        currentIndex--;

        if (currentIndex == -1)
            currentIndex = 0;

        getVBox();
        playSong(currentIndex);
    }

    public void playAll() {
        playSong(0);
    }

    public ArrayList<Song> getPlaylist(){
        return playlist;
    }

    public VBox getVBox() {
        if (layout != null)
            layout.getChildren().clear();

        Label playlistName = new Label("Current Playlist: " + this.playlistName);
        layout.getChildren().add(playlistName);
        int index = 0;
        for (Song song : playlist) {
            Label songName = new Label(song.getName());

            int finalIndex = index;
            songName.setOnMouseClicked(e -> handleSongClick(finalIndex));

            if (index == currentIndex)
                songName.setStyle("-fx-background-color: #e0e0e0; -fx-font-weight: bold;");
            songName.setStyle(songName.getStyle() + "-fx-cursor: hand;");

            layout.getChildren().add(songName);
            index++;
        }
        Button prevButton = new Button("Previous");
        prevButton.setOnAction(e -> playPrevious());

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> playNext());
        HBox songControls = new HBox(getMusicControls(prevButton, nextButton));
        layout.getChildren().add(songControls);
        return layout;
    }

    @Override
    public String toString() {
        return getPlaylistName();
    }

    private void handleSongClick(int clickedIndex){
        currentSong().reset();
        currentIndex = clickedIndex;
        getVBox();
        playSong(currentIndex);
    }


}
