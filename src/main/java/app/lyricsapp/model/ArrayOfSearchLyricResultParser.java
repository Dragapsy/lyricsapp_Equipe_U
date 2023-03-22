package app.lyricsapp.model;




import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayOfSearchLyricResultParser {
    public static ArrayList<Song> parse(String filePath) throws IOException {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        ArrayList< Song > songsList = new ArrayList<>();
        xmlFileCorrector(filePath);
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("SearchLyricResult");

            for (int i = 0; i < nodeList.getLength()-1; i++) {
                if(nodeList.item(i).getChildNodes().item(3).getNodeName().equals("LyricChecksum"))
                    songsList.add(getSongWithLyrics(nodeList.item(i)));
                else
                    songsList.add(getSongWithoutLyrics(nodeList.item(i)));
            }
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        System.out.println(songsList);
        return songsList;
    }

    public static ArrayList<Song> parseByArtistAndTitleArrayOfSearch(String artist,String title,String filePath) throws IOException, ParserConfigurationException, SAXException {
       ApiRequest.searchLyricRequest(artist,title);
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        ArrayList<Song> searchResult = new ArrayList<>();
        xmlFileCorrector(filePath);
        dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName() + "\n");
//        NodeList nodeList = doc.getElementsByTagName("SearchLyricResult");
        try {
            // now XML is loaded as Document in memory, lets convert it to Object List
            for ( Song song : parse(filePath)){
//                String lowerCaseTitle = song.getTitle().toLowerCase();
                for (int i =0;i<song.getAuthors().size();i++) {
//                    String lowerCaseAuthor = song.getAuthors().get(i).getName().toLowerCase();
                    {
                        searchResult.add(song);
                    }
                }
            }

        }
        catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        System.out.println(searchResult);
        return searchResult;
    }
    public static ArrayList<Song> parseByArtistAndTitleArrayOfSearchRankSelection(String artist,String title,String filePath,int rank) throws IOException, ParserConfigurationException, SAXException {
        ApiRequest.searchLyricRequest(artist,title);
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        ArrayList<Song> searchResult = new ArrayList<>();
        xmlFileCorrector(filePath);
        dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName() + "\n");
//        NodeList nodeList = doc.getElementsByTagName("SearchLyricResult");
        try {
            // now XML is loaded as Document in memory, lets convert it to Object List
            for ( Song song : parse(filePath)){
//                String lowerCaseTitle = song.getTitle().toLowerCase();
                for (int i =0;i<song.getAuthors().size();i++) {
//                    String lowerCaseAuthor = song.getAuthors().get(i).getName().toLowerCase();
                    {
                        if (song.getRank()>=rank){
                            searchResult.add(song);
                        }
                    }
                }
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        System.out.println(searchResult);
        return searchResult;
    }

    public static ArrayList<Song> parseByLyrics(String lyric, String filePath) throws ParserConfigurationException, IOException, SAXException {
        ApiRequest.searchLyricTextRequest(lyric);
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        ArrayList<Song> searchResult = new ArrayList<>();
        xmlFileCorrector(filePath);
        dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        try {
            for ( Song song : parse(filePath)){
                    searchResult.add(song);
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return searchResult;
    }
    public static ArrayList<Song> parseByLyricsRankSelection(String lyric, String filePath,int rank) throws ParserConfigurationException, IOException, SAXException {
        ApiRequest.searchLyricTextRequest(lyric);
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        ArrayList<Song> searchResult = new ArrayList<>();
        xmlFileCorrector(filePath);
        dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
//        NodeList nodeList = doc.getElementsByTagName("SearchLyricResult");
        try {
            for ( Song song : parse(filePath)){
                if (song.getRank()>=rank) {
                    searchResult.add(song);
                }
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        System.out.println(searchResult);
        return searchResult;
    }
    private static String getTagValue(String tag, Element element) {
//        boolean hasChild = element.getElementsByTagName(tag).item(0)!=null;
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
    private static Song getSongWithLyrics(Node node) throws Exception {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            Author author = new Author(getTagValue("Artist", element));
            ArrayList<Author> authors = new ArrayList<>();
            authors.add(author);
            Song song = new Song(
                    authors,
                    getTagValue("Song",element),
                    Integer.parseInt(getTagValue("LyricId",element)),
                    Integer.parseInt(getTagValue("SongRank",element)),
                    Integer.parseInt(getTagValue("TrackId",element)),
                    getTagValue("SongUrl",element),
                    getTagValue("ArtistUrl",element),
                    getTagValue("LyricChecksum",element));
                    song.setLyrics(new Lyrics(Integer.parseInt(getTagValue("LyricId",element)),""));
            return song;
            }
        throw new Exception("Problem due to the node");
        }
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
            song.setLyrics(new Lyrics(0,"Nobody added lyrics for this song yet, feel free to contribute."));
            return song;
        }
        throw new Exception("Problem due to the node");
    }
    private static void xmlFileCorrector(String filePath) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filePath));
        String line;
        String xmlString = "";
        while ((line = in.readLine()) != null) {
            xmlString += line + "\n";
        }
        in.close();
            FileWriter xmlWriter = new FileWriter(filePath, false);
            xmlWriter.write(xmlString);
            xmlWriter.close();
        }
    }


