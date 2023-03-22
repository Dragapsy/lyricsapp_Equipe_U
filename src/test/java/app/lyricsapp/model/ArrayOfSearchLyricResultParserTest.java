package app.lyricsapp.model;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

public class ArrayOfSearchLyricResultParserTest {
    @Test
    void testParser () throws IOException {
        ArrayList<Song> testedList =ArrayOfSearchLyricResultParser.parse("query1.xml");
        assertThat(testedList.size()).isEqualTo(25);
        assertThat("The taxman's taken all my dough And left me in my stately home Lazing on a sunny afternoon And I can't sail my yacht He's taken everything I've got All I've got's this sunny afternoon Save me, save me, save me from this squeeze I've got a big fat momma trying to break me And I love to live so pleasantly Live this life of luxury Lazin' on a sunny afternoon In the summertime In the summertime In the summertime My girlfriend's run off with my car And gone back to her ma and pa Tellin' tales of drunkenness and cruelty Now I'm sittin' here Sippin' at my ice-cool beer Lazin on a sunny afternoon Help me, help me, help me sail away Or give me two good reasons why I oughta stay 'Cause I love to live so pleasantly Live this life of luxury Lazin' on a sunny afternoon In the summertime In the summertime In the summertime Save me, save me, save me from this squeeze I got a big fat momma trying to break me And I love to live so pleasantly Live this life of luxury Lazin' on a sunny afternoon In the summertime In the summertime In the summertime In the summertime In the summertime")
                .isEqualTo(testedList.get(0).getLyrics().getLyrics());
        assertThat("").isEqualTo(testedList.get(15).getLyrics().getLyrics());
        assertThat(testedList.get(2).getLyricsId()).isEqualTo(1304);
        assertThat(testedList.get(1).getAuthors().get(0).getName()).isEqualTo("Starship");
        assertThat(testedList.get(6).getLyricChecksum()).isEqualTo("d8c56cf54f9a70a1bcfca4deb6e85ea6");
        assertThat(testedList.get(0).getLyricUrl()).isEqualTo("http://www.chartlyrics.com/nz21F2NcCUqlk93kYI4NuQ/Sunny+Afternoon.aspx");
        assertThat(testedList.get(0).getLyricCorrectUrl()).isEqualTo("http://www.chartlyrics.com/app/correct.aspx?lid=MwA4ADkA");
        assertThat(testedList.get(1).getLyricCorrectUrl()).isNull();
    }

    @Test
    void testParseByLyrics() throws ParserConfigurationException, IOException, SAXException {
        ArrayList<Song> parsedSongsList =ArrayOfSearchLyricResultParser.parse("query1.xml");
        ArrayList<Song> expectedResult = new ArrayList<>();
        expectedResult.add(parsedSongsList.get(0));
        assertThat(ArrayOfSearchLyricResultParser.parseByLyrics("The taxman's","query1.xml")).isEqualTo(expectedResult);
        assertThat(ArrayOfSearchLyricResultParser.parseByLyrics("taxman", "query1.xml").size()).isEqualTo(1);
        assertThat(ArrayOfSearchLyricResultParser.parseByLyrics("azertyuiop","query1.xml")).isEmpty();
        assertThat(ArrayOfSearchLyricResultParser.parseByLyrics("","query1.xml").size()).isEqualTo(25);
        assertThat(parsedSongsList.get(1)).isNotIn(ArrayOfSearchLyricResultParser.parseByLyrics("taxman","query1.xml"));
    }
    @Test
    void testParseByArtist() throws IOException, ParserConfigurationException, SAXException {
        ArrayList<Song> parsedSongsList =ArrayOfSearchLyricResultParser.parse("query1.xml");
        ArrayList<Song> expectedResult = new ArrayList<>();
        expectedResult.add(parsedSongsList.get(7));
        expectedResult.add(parsedSongsList.get(18));
        assertThat(ArrayOfSearchLyricResultParser.parseByArtistAndTitleArrayOfSearch("Creedence Clearwater Revival", "Have You Ever Seen the Rain", "query1.xml")).isEqualTo(expectedResult);
        assertThat(ArrayOfSearchLyricResultParser.parseByArtistAndTitleArrayOfSearch("aepfksfj","aepifksfj","query1.xml")).isEmpty();
    }
   /* @Test
    void testparseByTitle() throws IOException, ParserConfigurationException, SAXException {
        ArrayList<Song> parsedSongsList =ArrayOfSearchLyricResultParser.parse("query1.xml");
        ArrayList<Song> expectedResult = new ArrayList<>();
        expectedResult.add(parsedSongsList.get(3));
        assertThat(ArrayOfSearchLyricResultParser.parseByTitle("Tarzan Boy","query1.xml")).isEqualTo(expectedResult);
        assertThat(ArrayOfSearchLyricResultParser.parseByTitle("aepfksfj","query1.xml")).isEmpty();
    }*/
}
