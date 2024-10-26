package com.timothypav.musicplayer;

import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class PlaylistCatalog {
    private final ArrayList<Playlist> playlistCatalog;
    private VBox layout;

    public PlaylistCatalog(Playlist playlist){
        playlistCatalog = new ArrayList<Playlist>();
        playlistCatalog.add(playlist);

        layout = new VBox();
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

        for(Playlist playlist : playlistCatalog){
            layout.getChildren().add(playlist.getVBox());
        }
        return layout;
    }
}
