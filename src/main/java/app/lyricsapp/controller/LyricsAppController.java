package app.lyricsapp.controller;

import app.lyricsapp.model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LyricsAppController implements Initializable {
    @FXML private VBox favoriteArtistsBox;
    @FXML private VBox lyricsBox;
    @FXML private Button deleteFavoritesButton;
    @FXML private Button addFavoritesButton;
    @FXML private Label artist;
    @FXML private Label title;
    @FXML private VBox songArtistTitleBox;
    @FXML private Button accessFavoriteListButtonInSong;
    @FXML private Button returnButtonInSong;
    @FXML private VBox buttonsInSong;
    @FXML private HBox songPageHeader;
    @FXML private VBox songPage;
    @FXML private VBox favoriteSongsBox;
    @FXML private HBox searchPageHeader;
    @FXML private VBox searchPageFooter;
    @FXML private VBox lyricsSearchBox;
    @FXML private VBox titleArtistSearchBox;
    @FXML private VBox searchResultsBox;
    @FXML private Button returnButtonInFavorite;
    @FXML private VBox favoritePage;
    @FXML private TextField lyricsInput;
    @FXML private VBox searchPage;
    @FXML private TextField titleInput;
    @FXML private TextField artistInput;
    @FXML private Button titleArtistSearchButton;
    @FXML private Button lyricsSearchButton;
    @FXML private Button accessFavoriteListButton;
    @FXML private Label labelSearchResults;
    @FXML private ChoiceBox<Integer> rankSelection;
    @FXML private Button showHelpButton;
    @FXML private Button hideHelpButton;
    @FXML private VBox helpBox;

    private final Favorites favoriteSongs = new Favorites().favoritesFromFile();
    private ArrayList<Song> searchResult = new ArrayList<>();

    // @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        hide(hideHelpButton);
        hide(favoritePage);
        show(searchPage);
        hide(songPage);
    }

    public void hide(Node node){
        node.setVisible(false);
        node.managedProperty().bind(node.visibleProperty());
    }

    public void show(Node node){
        node.setVisible(true);
        node.managedProperty().bind(node.visibleProperty());
    }

    public HBox oneSongBox(Song song, Favorites favorites){
        HBox res = new HBox();
        res.setOnMouseClicked(event -> {
            try {
                songClicked(song);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        String authors ="";
        for(Author author: song.getAuthors()){
            authors += author.getName() + " ";
        }
        Text artist = new Text(authors);
        Text title = new Text(song.getTitle());
        Button addFavoritesButton = new Button("+");
        Button deleteFavoritesButton = new Button("suprimer");
        addFavoritesButton.setOnMouseClicked(event -> {
            try {
                addFavoritesButtonClicked(song, addFavoritesButton, deleteFavoritesButton);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        deleteFavoritesButton.setOnMouseClicked(event -> {
            try {
                deleteFavoritesButtonClicked(song, addFavoritesButton, deleteFavoritesButton);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        if(favorites.getSongs().contains(song)){
            hide(addFavoritesButton);
            show(deleteFavoritesButton);
        }
        else{
            hide(deleteFavoritesButton);
            show(addFavoritesButton);
        }
        res.getChildren().addAll(artist, title, addFavoritesButton, deleteFavoritesButton);
        return res;
    }

    public ObservableList<Node> songListBox(ArrayList<Song> songs, String id, Favorites favorites){
        VBox res = new VBox();
        for(Song song: songs){
            res.getChildren().add(oneSongBox(song, favorites));
        }
        res.setId(id);
        return res.getChildren();
    }

    public HBox oneAuthorBox(String author, Integer nbSongs){
        HBox res = new HBox();
        Text artistText = new Text(author);
        Text nbSongsText = new Text("" + nbSongs);
        res.getChildren().addAll(artistText, nbSongsText);
        return res;
    }

    public ObservableList<Node> favoriteAuthorsBox(HashMap<String, Integer> authors){
        VBox res = new VBox();
        for(String author: authors.keySet()){
            res.getChildren().add(oneAuthorBox(author, authors.get(author)));
        }
        return res.getChildren();
    }

    public int selectRank(){
        if(rankSelection.getValue()==null)
            return 0;
        return rankSelection.getValue();
    }

    @FXML
    private void addFavoritesButtonClicked(Song song, Button addButton, Button deleteButton) throws IOException {
        favoriteSongs.add(song);
        hide(addButton);
        show(deleteButton);
    }

    @FXML
    private void deleteFavoritesButtonClicked(Song song, Button addButton, Button deleteButton) throws IOException {
        favoriteSongs.delete(song);
        hide(deleteButton);
        show(addButton);
    }

    @FXML
    private void songClicked(Song song) throws IOException {
        hide(searchPage);
        hide(favoritePage);
        show(songPage);

        if(favoriteSongs.contains(song)){
            hide(addFavoritesButton);
            show(deleteFavoritesButton);
        }
        else{
            hide(deleteFavoritesButton);
            show(addFavoritesButton);
        }
        addFavoritesButton.setOnMouseClicked(event -> {
            try {
                addFavoritesButtonClicked(song, addFavoritesButton, deleteFavoritesButton);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        deleteFavoritesButton.setOnMouseClicked(event -> {
            try {
                deleteFavoritesButtonClicked(song, addFavoritesButton, deleteFavoritesButton);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        title.setText(song.getTitle());

        String authors ="";
        for(Author author: song.getAuthors()){
            authors += author.getName() + " ";
        }
        artist.setText("de " + authors);
        lyricsBox.getChildren().clear();
        try {
            lyricsBox.getChildren().add(new Text(GetLyricResultParser.parseGetLyricWithIdAndChecksum(song.lyricsIdToString(),
                song.getLyricChecksum(),"api_query.xml").getLyrics().getLyrics()));
        } catch (IOException e1){
            lyricsBox.getChildren().add(new Text(song.getLyrics().getLyrics()));
        }
    }

    @FXML
    private void titleArtistSearchButtonClicked() throws IOException, ParserConfigurationException, SAXException {
        searchResultsBox.getChildren().clear();
        String title = titleInput.getText();
        String artist = artistInput.getText();
        if(!title.equals("") && !artist.equals("")){
                titleInput.clear();
                artistInput.clear();
            try {
                searchResult = ArrayOfSearchLyricResultParser.parseByArtistAndTitleArrayOfSearchRankSelection(artist, title, "api_query.xml",selectRank());
                searchResultsBox.getChildren().addAll(songListBox(searchResult, "searchResultsBox", favoriteSongs));
            }catch (IOException e){
                searchResultsBox.getChildren().add(new Text("Le nom de l'artiste contient plus de 75 caracteres, ou le titre contient plus de 125 caracteres, ou le nom de l'artiste ou le titre, " +
                        "ou les deux contiennent uniquement des mots ou des caracteres non-connus. Veuillez réessayer."));
            }
        }
    }

    @FXML
    private void lyricsSearchButtonClicked() throws ParserConfigurationException,SAXException {
        searchResultsBox.getChildren().clear();
        String lyrics = lyricsInput.getText();
        if(!lyrics.equals("")) {
            lyricsInput.clear();
            try{
                searchResult = ArrayOfSearchLyricResultParser.parseByLyricsRankSelection(lyrics, "api_query.xml",selectRank());
                searchResultsBox.getChildren().addAll(songListBox(searchResult, "searchResultsBox", favoriteSongs));}
            catch (IOException e){
                searchResultsBox.getChildren().add(new Text("Votre recherche contient plus de 250 caracteres ou contient uniquement des mots ou des caracteres non-connus. Veuillez reessayer."));
            }
        }
    }

    @FXML
    private void accessFavoriteListButtonClicked(){
        favoriteSongsBox.getChildren().clear();
        favoriteSongsBox.getChildren().addAll(songListBox(favoriteSongs.getSongs(), "favoriteSongsBox", favoriteSongs));
        favoriteArtistsBox.getChildren().clear();
        favoriteArtistsBox.getChildren().addAll(favoriteAuthorsBox(favoriteSongs.getAuthors()));
        hide(searchPage);
        hide(songPage);
        show(favoritePage);
    }

    @FXML private void returnButtonClicked(){
        hide(favoritePage);
        hide(songPage);
        show(searchPage);
        searchResultsBox.getChildren().clear();
        searchResultsBox.getChildren().addAll(songListBox(searchResult, "searchResultsBox", favoriteSongs));
    }
@FXML private void showHelpButtonClicked(){
    String  forbidden ="Voici les mots ou caracteres non-connue lors de la recherche :\n";
    forbidden +="\tabout, after, all, also, an, and, another, any, are, as, at, be, because, been, before, being, between, both\n";
    forbidden +="\tbut, by, came, can, come, could, did, do, does, each, else, for, from, get, got, had, has, have, he, her, here, him\n" ;
    forbidden +="\thimself, his, how, if, in, into, is, it, its, just, like, make, many, me, might, more, most, much, must, my, never\n" ;
    forbidden +="\tno, now, of, on, only, or, other, our, out, over, re, said, same, see, should, since, so, some, still, such, take\n" ;
    forbidden +="\tthan, that, the, their, them, then, there, these, they, this, those, through, to, too, under, up, use, very, want\n" ;
    forbidden +="\twas, way, we, well, were, what, when, where, which, while, who, will, with, would, you, your\n";
    forbidden+="\t( ) [ ] ' ! . : ; , \" | ~ ? !\n";
        helpBox.getChildren().add(new Text(forbidden));
        hide(showHelpButton);
        show(hideHelpButton);
    }
    @FXML private void hideHelpButtonClicked(){
        helpBox.getChildren().clear();
        hide(hideHelpButton);
        show(showHelpButton);
    }
}
