package app.lyricsapp.model;

import java.io.Serializable;
import java.util.Objects;

public class Lyrics implements Serializable {
    private String lyrics;
    private int lyricsId;
    public Lyrics(int lyricsId,String lyrics){
        this.lyricsId = lyricsId;
        this.lyrics = lyrics;
    }

    public int getLyricsId() {
        return lyricsId;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyricsId(int lyricsId){
        this.lyricsId = lyricsId;
    }

    @Override
    public String toString() {
        return getLyrics();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lyrics lyrics1 = (Lyrics) o;

        return Objects.equals(lyrics, lyrics1.lyrics);
    }

}
