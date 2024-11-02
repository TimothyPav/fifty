package com.timothypav.musicplayer;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

public class SongSearch {
    private final Trie trie;
    private final VBox layout;
    private final VBox songNames;
    private final TextField songField;
    private final HashMap<Label, Boolean> songs;

    private void onChange(String newValue){
        Set<String> words = this.trie.getWordsWithPrefix(newValue);
        if (this.songNames != null)
            this.songNames.getChildren().clear();
        for (Label label : songs.keySet()){
            if (words.contains(label.getText())) {
                assert songNames != null;
                songNames.getChildren().add(label);
            }
        }
        System.out.println(songs.toString());
    }

    public SongSearch(Playlist songsFolder){
        trie = new Trie();
        Label songSearch = new Label("Song Search");
        songField = new TextField();
        songNames = new VBox();
        layout = new VBox(songSearch, songField, songNames);
        songs = new HashMap<>();
        for (Song song : songsFolder.getPlaylist()){
            Label songName = new Label(song.getName());
            Button addSong = new Button("+");

            addSong.setOnAction(e -> addToPlaylist(song));

            trie.insert(song.getName());
            songs.put(songName, true);
            HBox songSection = new HBox(songName, addSong);
            songNames.getChildren().add(songSection);
        }
        songField.textProperty().addListener((observable, oldValue, newValue) -> {
            onChange(newValue); // Call the onChange method whenever text changes
        });
    }

    private void addToPlaylist(Song song){
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL); // blocks main window
        VBox playlistSelection = new VBox(10);
        Label title = new Label("Choose which playlist you would like to add " + song.getName() + " to.");
        playlistSelection.getChildren().add(title);

        for (int i = 1; i<PlaylistCatalog.playlistCatalog.size(); i++){
            Button playlist = getButton(song, i, popup);
            playlistSelection.getChildren().add(playlist);
        }
        Scene scene = new Scene(playlistSelection);
        popup.setScene(scene);
        popup.showAndWait();
    }

    private static Button getButton(Song song, int i, Stage popup) {
        final int playlistIndex = i;

        Button playlist = new Button(PlaylistCatalog.playlistCatalog.get(playlistIndex).getPlaylistName());
        playlist.setOnAction(e -> {
            PlaylistCatalog.playlistCatalog.get(playlistIndex).addSong(song);

            // Create Alert with content only, no header
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);  // Remove the header completely
            alert.setContentText("Added " + song.getName() + " to playlist: " +
                    PlaylistCatalog.playlistCatalog.get(playlistIndex).getPlaylistName());

            // Optional: Set a graphic to null if still empty
            alert.setGraphic(null);

            popup.close();
            alert.showAndWait();
            MusicPlayerApp.PLAYLIST_CATALOG.getLayout();
        });
        System.out.println("HELLO");
        return playlist;
    }

    public VBox getLayout() {
        return layout;
    }

    private static class TrieNode {
        public Map<Character, TrieNode> children;
        public boolean endOfWord = false;

        public TrieNode(){
            children = new HashMap<>();
        }
    }

    private static class Trie {
        private final TrieNode root;

        public Trie(){
           root = new TrieNode();
        }

        public void insert(String str){
            TrieNode curr = root;
            for (int i=0; i<str.length(); i++){
                Character c = str.charAt(i);
                if (!curr.children.containsKey(c))
                    curr.children.put(c, new TrieNode());
                curr = curr.children.get(c);
            }
            curr.endOfWord = true;
        }

        public boolean search(String str){
            TrieNode curr = root;
            for (int i=0; i<str.length(); i++){
                Character c = str.charAt(i);
                if (!curr.children.containsKey(c))
                    return false;
                curr = curr.children.get(c);
            }
            return curr.endOfWord;
        }

        public TrieNode startsWith(String str){
            TrieNode curr = root;
            for (int i=0; i<str.length(); i++){
                Character c = str.charAt(i);
                if (!curr.children.containsKey(c))
                    return null;
                curr = curr.children.get(c);
            }
            return curr;
        }

        public Set<String> getWordsWithPrefix(String prefix){
            Set<String> words = new HashSet<>();
            TrieNode curr = startsWith(prefix);
            if (curr != null){
                collectWords(curr, new StringBuilder(prefix), words);
            }
            return words;
        }

        private void collectWords(TrieNode curr, StringBuilder prefix, Set<String> words){
            if (curr != null) {
                if (curr.endOfWord)
                    words.add(prefix.toString());
                for (Character key : curr.children.keySet()) {
                    prefix.append(key);
                    collectWords(curr.children.get(key), prefix, words);
                    prefix.deleteCharAt(prefix.length()-1);
                }
            }
        }

    }
}
