package com.timothypav.musicplayer;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.*;

public class SongSearch {
    private final Trie trie;
    private final VBox layout;
    private final TextField songField;
    // TODO
    // Make this a tuple so i never have to delete from songs set
    private final Set<Label> songs;

    private void onChange(String newValue){
        Set<String> words = this.trie.getWordsWithPrefix(newValue);
        ArrayList<Label> deleteSongs = new ArrayList<>();
        for (Label label : songs){
            if (!words.contains(label.getText())) {
                System.out.println("HELLO");
                this.layout.getChildren().remove(label);
                deleteSongs.add(label);
            }
            else if(!songs.contains(label)) {
                System.out.println("SECOND ELSE IF");
                this.layout.getChildren().add(label);
                songs.add(label);
            }
        }
        for (Label label : deleteSongs){
            songs.remove(label);
        }
        System.out.println(songs.toString());
    }

    public SongSearch(Playlist songsFolder){
        trie = new Trie();
        Label songSearch = new Label("Song Search");
        songField = new TextField();
        layout = new VBox(songSearch, songField);
        songs = new HashSet<>();
        for (Song song : songsFolder.getPlaylist()){
            Label songName = new Label(song.getName());
            trie.insert(song.getName());
            songs.add(songName);
            layout.getChildren().add(songName);
        }
        songField.textProperty().addListener((observable, oldValue, newValue) -> {
            onChange(newValue); // Call the onChange method whenever text changes
        });
    }


    public VBox getLayout() {
        return layout;
    }

    private static class TrieNode {
        public Map<Character, TrieNode> children;
        public boolean endOfWord = false;

        public TrieNode(){
            children = new HashMap<Character, TrieNode>();
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
