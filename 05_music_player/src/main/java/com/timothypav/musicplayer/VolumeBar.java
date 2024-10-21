package com.timothypav.musicplayer;

import javafx.beans.Observable;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.media.MediaPlayer;

public class VolumeBar {
    private final ScrollBar volumeBar;
    private final MediaPlayer mediaPlayer;

    private void handleOnChange(Observable observable){
        mediaPlayer.setVolume(volumeBar.getValue());
    }

    public VolumeBar(MediaPlayer mediaPlayer){
        volumeBar = new ScrollBar();
        this.mediaPlayer = mediaPlayer;
        volumeBar.setMin(0);
        volumeBar.setMax(.5);
        volumeBar.setValue(.05);
        volumeBar.setOrientation(Orientation.HORIZONTAL);
        volumeBar.setUnitIncrement(.001);
        volumeBar.setBlockIncrement(.001);
        mediaPlayer.setVolume(volumeBar.getValue());

        volumeBar.valueProperty().addListener(this::handleOnChange);
    }

    public ScrollBar getScrollBar() {
        return volumeBar;
    }
}
