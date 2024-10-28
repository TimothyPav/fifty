package com.timothypav.musicplayer;

import javafx.scene.layout.VBox;
import java.util.HashMap;
import java.util.Map;

public class SongSearch {
    private VBox layout;

    public SongSearch(){

    }

    private class TrieNode {
        public Map<TrieNode, TrieNode> children;
        public boolean endOfWord;
    }

    private class Trie {

    }
}
