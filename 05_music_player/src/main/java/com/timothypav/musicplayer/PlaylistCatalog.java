package com.timothypav.musicplayer;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;

public class PlaylistCatalog {
    private static final ArrayList<Playlist> playlistCatalog = new ArrayList<Playlist>();
    private VBox layout;
    public ComboBox playlistChoice;

    public PlaylistCatalog(Playlist playlist){
        playlistCatalog.add(playlist);

        layout = new VBox();
        playlistChoice = new ComboBox<>();
        playlistChoice.setValue(playlistCatalog.get(0));
        playlistChoice.setOnAction(this::handleChange);
    }

    public static void resetAllSongs(MediaPlayer currentSong){
        for (Playlist p : playlistCatalog){
            for (Song s : p.getPlaylist()){
                if(s.getMediaPlayer() != currentSong)
                    s.reset();
            }
        }
    }

    private void handleChange(Event event){
        getLayout();
    }

    public void setPlaylists(){
        this.playlistChoice.setItems(FXCollections.observableArrayList(this.playlistCatalog));

        this.playlistChoice.setCellFactory(param -> new ListCell<Playlist>() {
            @Override
            protected void updateItem(Playlist item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null)
                    setText(null);
                else
                    setText(item.getPlaylistName());
            }
        });
    }

    public void addToPlaylistCatalog(Playlist playlist){
        playlistCatalog.add(playlist);
        getLayout();
    }

    public void deletePlaylistInCatalog(Playlist playlist){
        for(int i = 1; i < playlistCatalog.size(); i++){
            if (playlistCatalog.get(i) == playlist){
                playlistCatalog.remove(i);
                break;
            }
        }
    }


    public VBox getLayout() {
        if (layout != null)
            layout.getChildren().clear();

        setPlaylists();
        layout.getChildren().add(playlistChoice);

        // get playlist from combo box and cast to playlist object
        Playlist selectedPlaylist = (Playlist) playlistChoice.getValue();
        layout.getChildren().add(selectedPlaylist.getVBox());

        return layout;
    }
}
