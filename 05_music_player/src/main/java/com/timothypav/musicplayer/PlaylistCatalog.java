package com.timothypav.musicplayer;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Optional;

public class PlaylistCatalog {
    public static final ArrayList<Playlist> playlistCatalog = new ArrayList<Playlist>();
    private VBox layout;
    public ComboBox playlistChoice;
    private Button newPlaylist;
    private Button deletePlaylist;

    public PlaylistCatalog(Playlist playlist){
        playlistCatalog.add(playlist);

        layout = new VBox();
        playlistChoice = new ComboBox<>();
        playlistChoice.setValue(playlistCatalog.get(0));
        playlistChoice.setOnAction(this::handleChange);
        newPlaylist = new Button("+");
        newPlaylist.setOnAction(e -> handleMakePlaylist());

        deletePlaylist = new Button("-");
        deletePlaylist.setOnMouseClicked(this::handleDeletePlaylist);
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

    public void setPlaylists() {
        Playlist currentSelection = (Playlist) playlistChoice.getValue();
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
        if (currentSelection != null) {
            playlistChoice.setValue(currentSelection);
        }
    }

    public void addToPlaylistCatalog(Playlist playlist){
        playlistCatalog.add(playlist);
        setPlaylists();
    }

    public void deletePlaylistInCatalog(Playlist playlist){
        System.out.println("deleting playlist... SIZE OF PLAYLIST CATALOG = " + playlistCatalog.size());
        for(int i = 1; i < playlistCatalog.size(); i++){
            if (playlistCatalog.get(i) == playlist){
                playlistCatalog.remove(i);
                break;
            }
        }
        setPlaylists();
    }

    private void handleMakePlaylist(){
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Enter playlist name");

        Optional<String> result = td.showAndWait();
        // this shows the dialog, waits until it is closed, and stores the result


        // if the result is present (i.e. if a string was entered) call doSomething() on it
        result.ifPresent(string -> {
            Playlist newPlaylist = new Playlist(string);
            addToPlaylistCatalog(newPlaylist);
            playlistChoice.setValue(newPlaylist);
        });
    }

    private void handleDeletePlaylist(MouseEvent e){
        ContextMenu contextMenu = new ContextMenu();

        MenuItem del = new MenuItem("Delete this playlist?");
        del.setOnAction(event -> {
            deletePlaylistInCatalog((Playlist) playlistChoice.getValue());
            playlistChoice.setValue(playlistCatalog.get(0));
        });

        contextMenu.getItems().add(del);
        contextMenu.show(e.getPickResult().getIntersectedNode(), e.getScreenX(), e.getScreenY());
    }

    public VBox getLayout() {
        if (layout != null)
            layout.getChildren().clear();

//        setPlaylists();

        HBox playlistControls = new HBox(playlistChoice, newPlaylist, deletePlaylist);

        layout.getChildren().add(playlistControls);

        // get playlist from combo box and cast to playlist object
        Playlist selectedPlaylist = (Playlist) playlistChoice.getValue();
        if (selectedPlaylist != null) {  // Add this null check
            layout.getChildren().add(selectedPlaylist.getVBox());
        }

        return layout;
    }
}
