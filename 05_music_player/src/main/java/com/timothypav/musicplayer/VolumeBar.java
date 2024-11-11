package com.timothypav.musicplayer;

import javafx.beans.Observable;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.media.MediaPlayer;

public class VolumeBar {
    private final ScrollBar volumeBar;
    private final MediaPlayer mediaPlayer;
    private double volume;

    private void handleOnChange(Observable observable){
        MusicPlayerApp.VOLUME = volumeBar.getValue();
        mediaPlayer.setVolume(MusicPlayerApp.VOLUME);
    }

    public VolumeBar(MediaPlayer mediaPlayer){
        volumeBar = new ScrollBar();
        this.mediaPlayer = mediaPlayer;
        volumeBar.getStyleClass().addAll("volume-slider");
        volumeBar.setMin(0);
        volumeBar.setMax(.5);
        volumeBar.setValue(MusicPlayerApp.VOLUME);
        volumeBar.setOrientation(Orientation.VERTICAL);
        volumeBar.setUnitIncrement(.001);
        volumeBar.setBlockIncrement(.001);
        mediaPlayer.setVolume(MusicPlayerApp.VOLUME);

        volumeBar.valueProperty().addListener(this::handleOnChange);
    }

    public void setVolume() {
        mediaPlayer.setVolume(MusicPlayerApp.VOLUME);
        this.volumeBar.setValue(MusicPlayerApp.VOLUME);
    }

    public ScrollBar getScrollBar() {
        return volumeBar;
    }
}
