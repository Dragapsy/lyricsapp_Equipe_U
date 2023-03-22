package app.lyricsapp.model;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiRequest {
    public static void searchLyricRequest(String artist, String song) throws IOException {
        song = song.replace(" ","+");
        artist = artist.replace(" ","+");
        URL url = new URL("http://api.chartlyrics.com/apiv1.asmx/SearchLyric?artist="+artist+"&song="+song);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine+"\n");
        }
        in.close();
        con.disconnect();
        FileWriter xmlWriter = new FileWriter("api_query.xml", false);
        xmlWriter.write(String.valueOf(content));
        xmlWriter.close();
    }
    public static void searchLyricDirectRequest(String artist, String song) throws IOException {
       song =  song.replace(" ","+");
        URL url = new URL("http://api.chartlyrics.com/apiv1.asmx/SearchLyricDirect?artist="+artist+"&song="+song);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine + "\n");
        }
        in.close();
        con.disconnect();
        FileWriter xmlWriter = new FileWriter("api_query.xml", false);
        // false to overwrite.
        xmlWriter.write(String.valueOf(content));
        xmlWriter.close();
    }
    /**@Author Abakar Issa ALI*/
    public static void searchLyricTextRequest(String lyric) throws IOException {
        lyric =  lyric.replace(" ","%20");
        URL url = new URL("http://api.chartlyrics.com/apiv1.asmx/SearchLyricText?lyricText="+lyric);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine + "\n");
        }
        in.close();
        con.disconnect();
        FileWriter xmlWriter = new FileWriter("api_query.xml", false);
        xmlWriter.write(String.valueOf(content));
        xmlWriter.close();
    }

    public static String addLyricsRequest(String trackId,String trackCheckSum,String lyrics,String userEmail) throws IOException {
        URL url = new URL(" http://api.chartlyrics.com/apiv1.asmx/AddLyric?trackId="+trackId+"&trackCheckSum="+trackCheckSum+"&lyric="+lyrics+"&email="+userEmail);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        System.out.println(con.getResponseCode());
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine + "\n");
        }
        in.close();
        con.disconnect();
        FileWriter xmlWriter = new FileWriter("api_query.xml", false);
        xmlWriter.write(String.valueOf(content));
        xmlWriter.close();
        return content.toString();
    }

    public static void searchLyricWithIdAndChecksum(String lyricId, String lyricChecksum) throws IOException {
        URL url = new URL("http://api.chartlyrics.com/apiv1.asmx/GetLyric?lyricId="+lyricId+"&lyricCheckSum="+lyricChecksum);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine + "\n");
        }
        in.close();
        con.disconnect();
        FileWriter xmlWriter = new FileWriter("api_query.xml", false);
        // false to overwrite.
        xmlWriter.write(String.valueOf(content));
        xmlWriter.close();
    }
}
