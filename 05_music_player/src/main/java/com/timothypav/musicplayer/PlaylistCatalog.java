package com.timothypav.musicplayer;

import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;

public class PlaylistCatalog {
    private static final ArrayList<Playlist> playlistCatalog = new ArrayList<Playlist>();
    private VBox layout;

    public PlaylistCatalog(Playlist playlist){
        playlistCatalog.add(playlist);

        layout = new VBox();
    }

    public static void resetAllSongs(MediaPlayer currentSong){
        for (Playlist p : playlistCatalog){
            for (Song s : p.getPlaylist()){
                if(s.getMediaPlayer() != currentSong)
                    s.reset();
            }
        }
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
