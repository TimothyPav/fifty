package com.timothypav.musicplayer;

import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class PlaybackBar {
    private final ProgressBar bar;
    private final MediaPlayer mediaPlayer;

    private void handleMouseClick(MouseEvent mouseEvent){
        double clickPosition = mouseEvent.getX()/bar.getWidth();
        Duration seek_time = mediaPlayer.getTotalDuration().multiply(clickPosition);
        mediaPlayer.seek(seek_time);
        bar.setProgress(clickPosition);
    }

    private void setupTimeListener() {
        mediaPlayer.currentTimeProperty().addListener((observable,  oldValue, newValue) -> {
            double value = newValue.toMinutes();
            double total_duration = mediaPlayer.getTotalDuration().toMinutes();
            double progress = value/total_duration;
            if (progress > 0.999)
                System.out.println("COMPLETED");
            setTime(progress);
        });
    }

    public PlaybackBar(MediaPlayer mediaPlayer){
        bar = new ProgressBar(0);
        this.mediaPlayer = mediaPlayer;

        bar.setOnMouseClicked(this::handleMouseClick);
        setupTimeListener();
    }


    public void setTime(double time){
        bar.setProgress(time);
    }

    public ProgressBar getBar() {
        return bar;
    }
}
