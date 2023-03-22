package app.lyricsapp.model;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GetLyricResultParser {
    public static Song parse(String filePath){
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        List < Song > songsList = new ArrayList<>();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName() + "\n");
            NodeList nodeList = doc.getElementsByTagName("GetLyricResult");
            // now XML is loaded as Document in memory, lets convert it to Object List

            for (int i = 0; i < nodeList.getLength()-1; i++) {
                songsList.add(getSong(nodeList.item(i)));
            }

            for (int i = 0; i < nodeList.getLength(); i++) {
                songsList.add(getSong(nodeList.item(i)));
            }
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
        return songsList.get(0);
    }
    static Song parseGetLyricDirect(String artist,String title, String filePath) throws IOException {
        ApiRequest.searchLyricDirectRequest(artist,title);
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        List < Song > songsList = new ArrayList<>();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName() + "\n");
            NodeList nodeList = doc.getElementsByTagName("GetLyricResult");
            // now XML is loaded as Document in memory, lets convert it to Object List

            for (int i = 0; i < nodeList.getLength()-1; i++) {
                songsList.add(getSong(nodeList.item(i)));
            }

            for (int i = 0; i < nodeList.getLength(); i++) {
                songsList.add(getSong(nodeList.item(i)));
            }
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
        System.out.println(songsList.get(0));
        return songsList.get(0);
    }

    public static Song parseGetLyricWithIdAndChecksum(String lyricId, String lyricChecksum, String filePath) throws IOException {
        ApiRequest.searchLyricWithIdAndChecksum(lyricId,lyricChecksum);
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        List < Song > songsList = new ArrayList<>();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName() + "\n");
            NodeList nodeList = doc.getElementsByTagName("GetLyricResult");
            // now XML is loaded as Document in memory, lets convert it to Object List

            for (int i = 0; i < nodeList.getLength()-1; i++) {
                songsList.add(getSong(nodeList.item(i)));
            }

            for (int i = 0; i < nodeList.getLength(); i++) {
                songsList.add(getSong(nodeList.item(i)));
            }
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
        return songsList.get(0);
    }
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
    private static Song getSong(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            Author author = new Author(getTagValue("LyricArtist", element));
            ArrayList<Author> authors = new ArrayList<>();
            authors.add(author);
            if(Integer.parseInt(getTagValue("TrackId",element))==0){
                Song song = new Song(
                        authors,
                        new Lyrics(Integer.parseInt(getTagValue("LyricId",element)),getTagValue("Lyric",element)),
                        getTagValue("LyricSong",element),
                        Integer.parseInt(getTagValue("LyricId",element)),
                        Integer.parseInt(getTagValue("LyricRank",element)),
                        getTagValue("LyricChecksum",element),
                        getTagValue("LyricUrl",element),
                        getTagValue("LyricCorrectUrl",element));
                song.setLyrics(new Lyrics(song.getLyricsId(),getTagValue("Lyric",element)));
                song.setSongId(0);
                return song;
            }
            else{
                Song song = new Song(
                        authors,
                        getTagValue("Song",element),
                        Integer.parseInt(getTagValue("SongRank",element)),
                        Integer.parseInt(getTagValue("TrackId",element)),
                        getTagValue("SongUrl",element),
                        getTagValue("ArtistUrl",element),
                        getTagValue("TrackChecksum",element));
                song.setLyrics(new Lyrics(0,"Nobody added lyrics for this song yet, feel free to contribute."));
                return song;

            }
        }
        throw new RuntimeException("Problem due to the node");

    }
//    private static Song getSongWithLyrics(Node node) throws Exception {
//        // XMLReaderDOM domReader = new XMLReaderDOM();
//
//        if (node.getNodeType() == Node.ELEMENT_NODE) {
//            Element element = (Element) node;
//            Author author = new Author(getTagValue("Artist", element));
//            ArrayList<Author> authors = new ArrayList<>();
//            authors.add(author);
//            Song song = new Song(
//                    authors,
//                    getTagValue("Song",element),
//                    Integer.parseInt(getTagValue("LyricId",element)),
//                    Integer.parseInt(getTagValue("SongRank",element)),
//                    Integer.parseInt(getTagValue("TrackId",element)),
//                    getTagValue("SongUrl",element),
//                    getTagValue("ArtistUrl",element),
//                    getTagValue("LyricChecksum",element));
//            song.setLyrics(new Lyrics(Integer.parseInt(getTagValue("LyricId",element)),""));
//
//
//            return song;
//        }
//        throw new Exception("Problem due to the node");
//    }
    private static Song getSongWithoutLyrics(Node node) throws Exception {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            Author author = new Author(getTagValue("Artist", element));
            ArrayList<Author> authors = new ArrayList<>();
            authors.add(author);
            Song song = new Song(
                    authors,
                    getTagValue("Song",element),
                    Integer.parseInt(getTagValue("SongRank",element)),
                    Integer.parseInt(getTagValue("TrackId",element)),
                    getTagValue("SongUrl",element),
                    getTagValue("ArtistUrl",element),
                    getTagValue("TrackChecksum",element));
            song.setLyrics(new Lyrics(0,""));
            return song;
        }
        throw new Exception("Problem due to the node");
    }
}
