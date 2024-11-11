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
    public static MainController MAIN_CONTROLLER = new MainController();

    public void listFilesInDirectory(final File folder, Playlist playlist) {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            Song song = new Song(fileEntry.getAbsolutePath(), fileEntry.getName());
            playlist.addSong(song);
        }
    }

    @Override
    public void start(Stage stage) {
        // Initialize playlists
        Playlist playlist = new Playlist("main playlist");
        listFilesInDirectory(SONGS_DIRECTORY, playlist);
        PLAYLIST_CATALOG = new PlaylistCatalog(playlist);
        Playlist playlist1 = new Playlist("second playlist");
        listFilesInDirectory(SONGS_DIRECTORY, playlist1);
        PLAYLIST_CATALOG.addToPlaylistCatalog(playlist1);

        // Initialize components
        SongSearch songSearch = new SongSearch(playlist);

        // Create main layout containers
        VBox mainControls = new VBox(PLAYLIST_CATALOG.getLayout());
        mainControls.getStyleClass().add("middle-section");

        // Create containers for right section (queue)
        VBox queueContainer = new VBox();
        queueContainer.getStyleClass().add("right-section");
        queueContainer.getChildren().add(MAIN_CONTROLLER.queue);

        // Top section with three columns
        HBox topContent = new HBox(songSearch.getLayout(), mainControls, queueContainer);
        topContent.getStyleClass().add("top-content");

        // Bottom controls section
        MAIN_CONTROLLER.getLayout();
        VBox bottomControls = MAIN_CONTROLLER.songControls;
        bottomControls.getStyleClass().add("bottom-controls");

        // Main root container
        VBox root = new VBox(topContent, bottomControls);
        root.getStyleClass().add("root-container");

        // Create and show scene
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        stage.setTitle("Music Player");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);


    }
}
