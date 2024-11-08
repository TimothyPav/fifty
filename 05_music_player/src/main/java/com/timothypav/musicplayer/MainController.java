package com.timothypav.musicplayer;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.*;

public class MainController {
    private VBox layout;

    public Song currentSong;
    public List<Song> mainQ;
    public int mainIndex;
    public static boolean isPlaying = false;

    public MainController(){
        layout = new VBox();

        currentSong = new Song("/home/tim/projects/fifty/05_music_player/dummy.mp3", "dummy.mp3");
        mainQ = new ArrayList<>();
    }

    public VBox getSongsInQueue() {
        VBox list = new VBox();
        // get all songs AFTER the mainIndex
        for (int i = mainIndex+1; i < mainQ.size(); i++){
            Song song = mainQ.get(i);
            Label songName = new Label(song.getName());
            list.getChildren().add(songName);
        }

        return list;
    }

    public void resetMainQ(ArrayList<Song> p, int currPlaylistIdx){
        mainQ.clear();
        mainIndex = 0;
        for (int i=currPlaylistIdx; i < p.size(); i++){
            System.out.println(p.get(i).getName());
            mainQ.add(p.get(i));
        }
        System.out.println("size: " + mainQ.size());
        getLayout();
    }

    public VBox getLayout() {
        System.out.println("getLayout method. mainQ size: " + mainQ.size());
        if (layout.getChildren() != null)
            layout.getChildren().clear();

        if (mainQ.isEmpty()){
            Label empty = new Label("No song selected");
            layout.getChildren().add(empty);
        }
        else {
            Button p = new Button("Previous");
            p.setOnAction(e -> {
                if (mainIndex >= 1){
                    isPlaying = true;
                    mainIndex--;
                    Song curr = mainQ.get(mainIndex);
                    curr.reset();
                    getLayout();
                }
            });

            Button n = new Button("Next");
            n.setOnAction(e -> {
                if (mainIndex < mainQ.size()-1) {
                    isPlaying = true;
                    mainIndex++;
                    Song curr = mainQ.get(mainIndex);
                    curr.reset();
                    getLayout();
                }
            });
            // TODO: Fix next song playing on natural finish

            Song currentSong = mainQ.get(mainIndex);
            Label currentSongLabel = new Label("Current Song: " + currentSong.getName());
            isPlaying = true;
            layout.getChildren().add(currentSong.getLayout(p, n));
            layout.getChildren().add(currentSongLabel);
            currentSong.play();
        }

        // Update queue list
        layout.getChildren().add(getSongsInQueue());

        return layout;
    }
}

