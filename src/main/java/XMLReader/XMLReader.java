package XMLReader;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLReader {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("query1.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("SearchLyricResult");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("TrackId : " + eElement.getElementsByTagName("TrackId").item(0).getTextContent());
                    System.out.println("LyricChecksum : " + eElement.getElementsByTagName("LyricChecksum").item(0).getTextContent());
                    System.out.println("LyricId : " + eElement.getElementsByTagName("LyricId").item(0).getTextContent());
                    System.out.println("SongUrl : " + eElement.getElementsByTagName("SongUrl").item(0).getTextContent());
                    System.out.println("ArtistUrl : " + eElement.getElementsByTagName("ArtistUrl").item(0).getTextContent());
                    System.out.println("Artist : " + eElement.getElementsByTagName("Artist").item(0).getTextContent());
                    System.out.println("Song : " + eElement.getElementsByTagName("Song").item(0).getTextContent());
                    System.out.println("SongRank : " + eElement.getElementsByTagName("SongRank").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






