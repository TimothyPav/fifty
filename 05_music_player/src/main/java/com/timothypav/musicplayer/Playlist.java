package com.timothypav.musicplayer;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

import javafx.scene.control.Label;

public class Playlist {

    private final ArrayList<Song> playlist;
    private String playlistName;
    private int currentIndex = 0;
    private VBox layout;

    private HBox getMusicControls(Button previous, Button next){
        try {
            return currentSong().getLayout(previous, next);
        } catch (NullPointerException e){
            Song dummy = new Song("/home/tim/projects/fifty/05_music_player/dummy.mp3", "dummy.mp3");
            Button dummyPrevious = new Button("Previous");
            Button dummyNext = new Button("Next");

            return dummy.getLayout(dummyPrevious, dummyNext);
        }
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
            MusicPlayerApp.MAIN_CONTROLLER.getLayout();
        }
    }

    public Song currentSong(){
        Song song;
        try {
            song = playlist.get(currentIndex);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
        return song;
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

    public void resetAll(){
        for (Song song : playlist){

        }
    }

    public ArrayList<Song> getPlaylist(){
        return playlist;
    }

    public Song getSong(int i){
        return playlist.get(i);
    }

    public VBox getVBox() {

        // Declare buttons at the top so i can pass them into the playlist.empty easier
        Button prevButton = new Button("Previous");
        Button nextButton = new Button("Next");
        if (layout != null) {
            layout.getChildren().clear();
        }

        if (playlist.isEmpty()){
            Label noSongs = new Label("No songs in this playlist");
            layout.getChildren().add(noSongs);
            layout.getChildren().add(getMusicControls(prevButton, nextButton));
            return layout;
        }

        int index = 0;
        for (Song song : playlist) {
            Label songName = new Label(song.getName());

            int finalIndex = index;
            songName.setOnMouseClicked(e -> handleSongClick(e, finalIndex));

            if (index == currentIndex)
                songName.setStyle("-fx-background-color: #e0e0e0; -fx-font-weight: bold;");
            songName.setStyle(songName.getStyle() + "-fx-cursor: hand;");

            layout.getChildren().add(songName);
            index++;
        }
        prevButton.setOnAction(e -> playPrevious());

        nextButton.setOnAction(e -> playNext());
        HBox songControls = new HBox(getMusicControls(prevButton, nextButton));
        layout.getChildren().add(songControls);
        return layout;
    }

    @Override
    public String toString() {
        return getPlaylistName();
    }

    private void handleSongClick(MouseEvent e, int clickedIndex){
        if (e.getButton() == MouseButton.PRIMARY) {
            try {
                currentSong().reset();
            } catch (NullPointerException ignored) {}
            currentIndex = clickedIndex;
            MusicPlayerApp.MAIN_CONTROLLER.resetMainQ(playlist, clickedIndex);
            getVBox();
            playSong(currentIndex);
        } else if (e.getButton() == MouseButton.SECONDARY) {
            if (!playlistName.equals("main playlist")) {
                // create a menu
                ContextMenu contextMenu = new ContextMenu();
                Song clickedSong = playlist.get(clickedIndex);

                // create menu items
                MenuItem delete = new MenuItem("Delete this song?");
                delete.setOnAction(event -> {

                    // reset and remove song
                    clickedSong.reset();
                    removeSong(clickedSong);

                    // Keep the index in line with the current song playing in the list
                    if (clickedIndex < currentIndex)
                        currentIndex--;
                    else if (clickedIndex == currentIndex)
                        currentIndex = -1;

                    getVBox();
                });

                // menu item for adding song to queue
                MenuItem add = new MenuItem("Add song to queue?");
                add.setOnAction(event -> {
                    MusicPlayerApp.MAIN_CONTROLLER.mainQ.add(clickedSong);
                    MusicPlayerApp.MAIN_CONTROLLER.getSongsInQueue();
                });

                // add menu items to menu
                contextMenu.getItems().add(delete);
                contextMenu.getItems().add(add);

                contextMenu.show(e.getPickResult().getIntersectedNode(), e.getScreenX(), e.getScreenY());
            }
        }
    }


}
