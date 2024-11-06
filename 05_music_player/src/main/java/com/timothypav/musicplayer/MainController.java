package com.timothypav.musicplayer;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.Queue;

public class MainController {
    private VBox layout;

    public Song currentSong;
    public Queue<Song> q;

    public MainController(){
        layout = new VBox();

        currentSong = new Song("/home/tim/projects/fifty/05_music_player/dummy.mp3", "dummy.mp3");
        q = new LinkedList<>();
    }

    public VBox getSongsInQueue() {
        VBox list = new VBox();
        for (Song song : q){
            Label songName = new Label(song.getName());
            list.getChildren().add(songName);
        }

        return list;
    }

    public VBox getLayout() {
        System.out.println("getLayout method. q size: " + q.size());
        if (layout.getChildren() != null)
            layout.getChildren().clear();

        if (q.isEmpty()){
            Label empty = new Label("No song selected");
            layout.getChildren().add(empty);
        }
        else {
            Button p = new Button("Previous");

            Button n = new Button("Next");
            n.setOnAction(e -> {
                // TODO: Fix this nullptr exception and make MainController layout pretty with no songs
                Song curr = q.peek();
                curr.reset();
                getLayout();
            });
            Song currentSong = q.remove();
            Label currentSongLabel = new Label("Current Song: " + currentSong.getName());
            layout.getChildren().add(currentSong.getLayout(p, n));
            layout.getChildren().add(currentSongLabel);
            currentSong.play();
        }

        // Update queue list
        layout.getChildren().add(getSongsInQueue());

        return layout;
    }
}

