package com.timothypav.musicplayer;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import javafx.scene.control.Label;


public class Song {
    public Button previous;
    public Button next;

    private MediaPlayer songPlayer;
    private String name;
    private PlaybackBar playbackBar;
    private VolumeBar volumeBar;
    private HBox layout;
    private Button playPauseButton;

    public Song(String file, String name){
        Media song = new Media(new File(file).toURI().toString());
        this.songPlayer = new MediaPlayer(song);
        this.name = name;
        this.playbackBar = new PlaybackBar(songPlayer);
        this.volumeBar = new VolumeBar(songPlayer);
        this.layout = new HBox();
    }

    public void setSong(Media song){
        songPlayer = new MediaPlayer(song);
    }

    public void pause(){
        Playlist.isPlaying = false;
        songPlayer.pause();
    }
    public void play(){
        PlaylistCatalog.resetAllSongs(this.songPlayer);
        Playlist.isPlaying = true;
        songPlayer.play();
    }

    public void reset(){
        // TODO: fix this from setting isPlaying to false when you play any song
        System.out.println("RESET WAS CALLED AND SET isPlaying TO FALSE");
        Playlist.isPlaying = false;
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

    public HBox getLayout(Button prev, Button next) {
        if (layout != null)
            layout.getChildren().clear();

        volumeBar.setVolume();

        playPauseButton = new Button("Play");
        updatePlayPauseButton();
        playPauseButton.setOnAction(e -> {
            if (Playlist.isPlaying) {
                pause();
            }
            else {
                play();
            }
            updatePlayPauseButton();
        });

        HBox mediaButtons = new HBox(prev, playPauseButton, next);

        HBox mediaBars = new HBox(playbackBar.getBar(), volumeBar.getScrollBar());

        VBox mediaControls = new VBox(mediaButtons, mediaBars);

        layout.getChildren().add(mediaControls);
        System.out.println("UPDATED");
        return layout;
    }

    private void updatePlayPauseButton() {
        if (Playlist.isPlaying) {
            playPauseButton.setText("Pause");
            // Optionally add an icon or style
//             playPauseButton.setGraphic(new ImageView(pauseIcon));
        } else {
            playPauseButton.setText("Play");
            // Optionally add an icon or style
            // playPauseButton.setGraphic(new ImageView(playIcon));
        }
    }
}
