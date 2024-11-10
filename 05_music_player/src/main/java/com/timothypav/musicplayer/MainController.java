package com.timothypav.musicplayer;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

public class MainController {
    public VBox queue;
    public VBox songControls;
    private Song dummy;

    public Song currentSong;
    public List<Song> mainQ;
    public int mainIndex;
    public static boolean isPlaying = false;

    public MainController(){
        queue = new VBox();
        queue.getStyleClass().add("queue-container");

        songControls = new VBox();
        songControls.getStyleClass().add("controls-container");

        currentSong = new Song("/home/tim/projects/fifty/05_music_player/dummy.mp3", "dummy.mp3");
        dummy = currentSong;
        mainQ = new ArrayList<>();
    }

    public VBox getSongsInQueue() {
        VBox list = new VBox();
        list.getStyleClass().add("queue-list");

        for (int i = mainIndex+1; i < mainQ.size(); i++){
            Song song = mainQ.get(i);
            Label songName = new Label(song.getName());
            songName.getStyleClass().add("queued-song");
            list.getChildren().add(songName);
        }
        return list;
    }

    public void resetMainQ(ArrayList<Song> p, int currPlaylistIdx){
        mainQ.clear();
        mainIndex = 0;
        for (int i=currPlaylistIdx; i < p.size(); i++){
            mainQ.add(p.get(i));
        }
        getLayout();
    }

    public VBox getLayout() {
        // Clear existing content
        if (queue.getChildren() != null) {
            queue.getChildren().clear();
        }
        if (songControls.getChildren() != null) {
            songControls.getChildren().clear();
        }

        // Style the queue container
        queue.getStyleClass().add("queue-container");
        songControls.getStyleClass().add("controls-container");

        // Create navigation buttons
        Button prevButton = createNavigationButton("Previous");
        Button nextButton = createNavigationButton("Next");

        if (mainQ.isEmpty() || mainIndex >= mainQ.size()) {
            // No song selected state
            handleEmptyQueue(prevButton, nextButton);
        } else {
            // Song selected state
            handleActiveQueue(prevButton, nextButton);
        }

        return queue;
    }

    private void handleEmptyQueue(Button prevButton, Button nextButton) {
        // Create and style empty state label
        Label emptyLabel = new Label("No song selected");
        emptyLabel.getStyleClass().add("no-song-label");

        // Add components to containers
        queue.getChildren().add(emptyLabel);

        HBox dummyControls = dummy.getLayout(prevButton, nextButton);
        dummyControls.getStyleClass().add("controls-container");
        songControls.getChildren().add(dummyControls);
    }

    private void handleActiveQueue(Button prevButton, Button nextButton) {
        // Set up button actions
        setupNavigationButtons(prevButton, nextButton);

        // Get current song and create label
        Song currentSong = mainQ.get(mainIndex);
        Label currentSongLabel = new Label("Current Song: " + currentSong.getName());
        currentSongLabel.getStyleClass().add("current-song-label");

        // Set up controls
        isPlaying = true;

        // Create container for queue list
        VBox queueList = getSongsInQueue();
        queueList.getStyleClass().add("queue-list");

        // Add components to containers
        queue.getChildren().addAll(currentSongLabel, queueList);

        HBox songControlsLayout = currentSong.getLayout(prevButton, nextButton);
        songControlsLayout.getStyleClass().add("controls-container");
        songControls.getChildren().add(songControlsLayout);

        // Start playback
        currentSong.play();
    }

    private Button createNavigationButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("control-button");
        return button;
    }

    private void setupNavigationButtons(Button prevButton, Button nextButton) {
        prevButton.setOnAction(e -> handlePreviousButton());
        nextButton.setOnAction(e -> handleNextButton());
    }

    private void handlePreviousButton() {
        if (mainIndex >= 1) {
            isPlaying = true;
            mainIndex--;
            Song curr = mainQ.get(mainIndex);
            curr.reset();
            getLayout();
        }
    }

    private void handleNextButton() {
        if (mainIndex < mainQ.size() - 1) {
            isPlaying = true;
            mainIndex++;
            Song curr = mainQ.get(mainIndex);
            curr.reset();
            getLayout();
        }
    }

    private void styleControlButtons(Button prev, Button next) {
        prev.getStyleClass().add("control-button");
        next.getStyleClass().add("control-button");
    }
}

