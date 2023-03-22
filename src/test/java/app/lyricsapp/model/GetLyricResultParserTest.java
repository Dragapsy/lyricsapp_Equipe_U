package app.lyricsapp.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class GetLyricResultParserTest {
    @Test
    void testParser(){
        Song testedSong = GetLyricResultParser.parse("query2.xml");
        assertThat("The taxman's taken all my dough And left me in my stately home Lazing on a sunny afternoon And I can't sail my yacht He's taken everything I've got All I've got's this sunny afternoon Save me, save me, save me from this squeeze I've got a big fat momma trying to break me And I love to live so pleasantly Live this life of luxury Lazin' on a sunny afternoon In the summertime In the summertime In the summertime My girlfriend's run off with my car And gone back to her ma and pa Tellin' tales of drunkenness and cruelty Now I'm sittin' here Sippin' at my ice-cool beer Lazin on a sunny afternoon Help me, help me, help me sail away Or give me two good reasons why I oughta stay 'Cause I love to live so pleasantly Live this life of luxury Lazin' on a sunny afternoon In the summertime In the summertime In the summertime Save me, save me, save me from this squeeze I got a big fat momma trying to break me And I love to live so pleasantly Live this life of luxury Lazin' on a sunny afternoon In the summertime In the summertime In the summertime In the summertime In the summertime")
                .isEqualTo(testedSong.getLyrics().getLyrics());
        assertThat(testedSong.getLyricsId()).isEqualTo(389);
        assertThat(testedSong.getLyricChecksum()).isEqualTo("1d1971e584894ba408b08d32cfdcedb7");

    }
}
