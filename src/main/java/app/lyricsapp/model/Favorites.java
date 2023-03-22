package app.lyricsapp.model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Favorites {
    private  ArrayList<Song> songs = new ArrayList<>();

    private  HashMap<String, Integer> authors = new HashMap<>();

    public boolean add(Song song) {
        if (!songs.contains(song)) {
            this.songs.add(song);
            for (Author author : song.getAuthors()) {
                if (authors.containsKey(author.getName())) {
                    authors.put(author.getName(), authors.get(author.getName()) + 1);
                } 
                else {
                    authors.put(author.getName(), 1);
                }
            }
            updateFavoritesFile();
            return true;
        }
        return false;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public boolean delete(Song song) {
        if (songs.contains(song)) {
            songs.remove(song);
            for (Author author : song.getAuthors()) {
                authors.put(author.getName(), authors.get(author.getName()) - 1);
                if (authors.get(author.getName()) == 0) {
                    authors.remove(author.getName());
                }
            }
            updateFavoritesFile();
            return true;
        }
        return false;
    }

    public HashMap<String, Integer> getAuthors() {
        return authors;
    }

    public int size(){
        return getSongs().size();
    }

    public Song get(int index) {
        return getSongs().get(index);
    }

    public boolean isEmpty() {
        return getSongs().isEmpty();
    }

    public boolean contains(Song song) {
        return getSongs().contains(song);
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public void setAuthors(HashMap<String, Integer> authors) {
        this.authors = authors;
    }

    public void updateFavoritesFile(){
        try {
            FileOutputStream fileOut = new FileOutputStream("favorites.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(songs);
            out.writeObject(authors);
            out.close();
            fileOut.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public Favorites favoritesFromFile(){
        Favorites favoriteSongs = new Favorites();
        try {
            FileInputStream fileIn = new FileInputStream("favorites.dat");
            ObjectInputStream ois = new ObjectInputStream(fileIn);
            favoriteSongs.setSongs((ArrayList<Song>) ois.readObject());
            favoriteSongs.setAuthors((HashMap<String, Integer>) ois.readObject());
            ois.close();
            fileIn.close();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return favoriteSongs;
    }
}

