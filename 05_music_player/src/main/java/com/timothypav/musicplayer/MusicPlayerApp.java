package com.timothypav.musicplayer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

public class MusicPlayerApp extends Application {

    private Song song;

    public static double VOLUME = 0.1;
    public final static File SONGS_DIRECTORY = new File("./songs");
    public static PlaylistCatalog PLAYLIST_CATALOG = null;

    public void listFilesInDirectory(final File folder, Playlist playlist) {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            System.out.println(fileEntry.getAbsolutePath());
            System.out.println(fileEntry.getName());
            Song song = new Song(fileEntry.getAbsolutePath(), fileEntry.getName());
            playlist.addSong(song);
        }
    }

    @Override
    public void start(Stage stage) {

        Playlist playlist = new Playlist("main playlist");
        listFilesInDirectory(SONGS_DIRECTORY, playlist);
        PLAYLIST_CATALOG = new PlaylistCatalog(playlist);

        Playlist playlist1 = new Playlist("second playlist");
        listFilesInDirectory(SONGS_DIRECTORY, playlist1);
        PLAYLIST_CATALOG.addToPlaylistCatalog(playlist1);

        SongSearch songSearch = new SongSearch(playlist);

        VBox mainControls = new VBox(PLAYLIST_CATALOG.getLayout());

        // Layout with buttons
        HBox root = new HBox(songSearch.getLayout(), mainControls);

        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Music Player");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);


    }
}
