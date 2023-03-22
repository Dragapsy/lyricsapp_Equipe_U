package app.lyricsapp.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Song implements Serializable {
    private final ArrayList<Author> authors;
    private Lyrics lyrics;
    private  String title;
    private int lyricsId;
    private int rank;
    private int songId;
    private String songUrl;
    private String artistUrl;
    private String lyricChecksum;
    private String lyricUrl;
    private String lyricCovertArtUrl;
    private String lyricCorrectUrl;
    private String trackChecksum;



    public Song(ArrayList<Author> authors, Lyrics lyrics, String title, int lyricsId, int rank, String lyricChecksum, String lyricUrl,String lyricCorrectUrl) {
        this.authors = authors;
        this.lyrics = lyrics;
        this.title = title;
        this.lyricsId = lyricsId;
        this.rank = rank;
        this.songId = 0;
        this.lyricChecksum = lyricChecksum;
        this.lyricUrl = lyricUrl;
//        this.lyricCovertArtUrl = lyricCovertArtUrl;
        this.lyricCorrectUrl=lyricCorrectUrl;
    }

    public Song(ArrayList<Author> authors, String title, int rank, int songId, String songUrl, String artistUrl,String trackChecksum) {
        this.authors = authors;
        this.title = title;
        this.lyricsId = 0;
        this.rank = rank;
        this.songId = songId;
        this.songUrl = songUrl;
        this.artistUrl = artistUrl;
        this.trackChecksum = trackChecksum;
    }


    public Song(ArrayList<Author> authors, String title, int lyricsId, int rank, int songId, String songUrl, String artistUrl,String lyricChecksum) throws IOException {
        this.authors = authors;
        this.title = title;
        this.lyricsId = lyricsId;
        this.rank = rank;
        this.songId = songId;
        this.songUrl = songUrl;
        this.artistUrl = artistUrl;
        this.lyricChecksum = lyricChecksum;
    }



    public Song(ArrayList<Author> authors, Lyrics lyrics, String title){
        this.authors = authors;
        this.lyrics = lyrics;
        this.title = title;
    }

    public String getLyricUrl() {
        return lyricUrl;
    }

    public String getLyricCovertArtUrl() {
        return lyricCovertArtUrl;
    }

    public String getLyricCorrectUrl() {
        return lyricCorrectUrl;
    }

    public void setLyricUrl(String lyricUrl) {
        this.lyricUrl = lyricUrl;
    }

    public void setLyricCorrectUrl(String lyricCorrectUrl) {
        this.lyricCorrectUrl = lyricCorrectUrl;
    }

    public void setLyrics(Lyrics lyrics) {
        this.lyrics = lyrics;
    }

    public String getTitle() {
        return title;
    }

    public int getRank() {
        return rank;
    }

    public int getSongId() {
        return songId;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public String getArtistUrl() {
        return artistUrl;
    }

    public String getLyricChecksum() {
        return lyricChecksum;
    }

    public Lyrics getLyrics() {
        return lyrics;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public int getLyricsId() {
        return lyricsId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String lyricsIdToString(){
        return Integer.toString(this.lyricsId);
    }
    public String songIdToString(){return Integer.toString(this.songId);}


    public String getTrackChecksum() {
        return trackChecksum;
    }

    @Override
    public String toString(){

        String authors = "";
        for (Author author : this.authors) {
            authors += author.getName();
        }
        return " LyricId : " + this.lyricsId +"\n"+" Lyric checksum : "+ this.lyricChecksum+"\n"+" Id : " + this.songId +"\n "+ "Title : " + this.title + "\n " + "Artist : "  + authors +"\n "+ "Lyrics : " + this.lyrics+"\n\n";

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        if (!Objects.equals(authors, song.authors)) return false;
        if (!Objects.equals(lyrics, song.lyrics)) return false;
        return Objects.equals(title, song.title);
    }
}
