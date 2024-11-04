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
    public static boolean isPlaying = false;

    private final ArrayList<Song> playlist;
    private String playlistName;
    private int currentIndex = 0;
    private VBox layout;

    private HBox getMusicControls(Button previous, Button next){
        System.out.println("THIS IS THE CURRENT SONG: " + currentSong());
        try {
            return currentSong().getLayout(previous, next);
        } catch (NullPointerException e){
            return new HBox();
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
            isPlaying = true;
            currSong.play();
            currSong.getMediaPlayer().setOnEndOfMedia(() -> {
                playSong(currentIndex + 1);
            });
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
        isPlaying = true;
        playSong(currentIndex);
    }

    public void playPrevious() {
        currentSong().reset();
        currentIndex--;

        if (currentIndex == -1)
            currentIndex = 0;

        getVBox();
        isPlaying = true;
        playSong(currentIndex);
    }

    public void playAll() {
        isPlaying = true;
        playSong(0);
    }

    public ArrayList<Song> getPlaylist(){
        return playlist;
    }

    public VBox getVBox() {
        System.out.println(isPlaying);
        if (layout != null) {
            layout.getChildren().clear();
            System.out.println("layout cleared...");
        }

        if (playlist.isEmpty()){
            Label noSongs = new Label("No songs in this playlist");
            layout.getChildren().add(noSongs);
            return layout;
        }

        int index = 0;
        for (Song song : playlist) {
            System.out.println("PROCESSING... " + song.getName());
            Label songName = new Label(song.getName());

            int finalIndex = index;
            songName.setOnMouseClicked(e -> handleSongClick(e, finalIndex));

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

    private void handleSongClick(MouseEvent e, int clickedIndex){
        if (e.getButton() == MouseButton.PRIMARY) {
            try {
                currentSong().reset();
            } catch (NullPointerException ignored) {}
            currentIndex = clickedIndex;
            isPlaying = true;
            getVBox();
            playSong(currentIndex);
        } else if (e.getButton() == MouseButton.SECONDARY) {
            if (!playlistName.equals("main playlist")) {
                // create a menu
                ContextMenu contextMenu = new ContextMenu();

                // create menu items
                MenuItem delete = new MenuItem("Delete this song?");
                delete.setOnAction(event -> {
                    Song clickedSong = playlist.get(clickedIndex);

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

                // add menu items to menu
                contextMenu.getItems().add(delete);

                contextMenu.show(e.getPickResult().getIntersectedNode(), e.getScreenX(), e.getScreenY());
            }
        }
    }


}
