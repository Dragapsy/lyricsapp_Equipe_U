package app.lyricsapp.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

public class LikedSongsTest {
    private Favorites listToTest;

    @Test
    void addTest() throws IOException {
        Song songToAdd1 = GetLyricResultParser.parse("query2.xml");
        Song songToAdd2 = ArrayOfSearchLyricResultParser.parse("query1.xml").get(1);
        ArrayList<Song> listOfSong1AndSong2 = new ArrayList<>();
        listOfSong1AndSong2.add(songToAdd1);
        listOfSong1AndSong2.add(songToAdd2);
        Favorites listToTest = new Favorites();
        listToTest.add(songToAdd1);
        assertThat(listToTest.getSongs().size()).isEqualTo(1);
        assertThat(listToTest.getSongs().contains(songToAdd1));
        listToTest.add(songToAdd1);
        assertThat(listToTest.getSongs().size()).isEqualTo(1);
        listToTest.add(songToAdd2);
        assertThat(listToTest.getSongs().size()).isEqualTo(2);
        assertThat(listToTest.getSongs().containsAll(listOfSong1AndSong2));
    }
    @Test
    void deleteTest() throws IOException {
        Song songToDelete1 = GetLyricResultParser.parse("query2.xml");
        Song songToAdd2 = ArrayOfSearchLyricResultParser.parse("query1.xml").get(1);
        Favorites listToTest = new Favorites();
        listToTest.add(songToDelete1);
        assertThat(listToTest.getSongs().size()).isEqualTo(1);
        listToTest.delete(songToAdd2);
        assertThat(listToTest.getSongs().size()).isEqualTo(1);
        listToTest.delete(songToDelete1);
        assertThat(listToTest.getSongs().isEmpty());


    }

}
