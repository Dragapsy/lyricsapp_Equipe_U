package app.lyricsapp;

import app.lyricsapp.model.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LyricsAppCLI {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, ClassNotFoundException {
        System.out.println("Welcome to the lyrics app");
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        Favorites favoriteSongs = new Favorites().favoritesFromFile();
        ArrayList<Song> searchResult = new ArrayList<>();
        int songNumber;
        String forbiddenWords = "about, after, all, also, an, and, another, any, are, as, at, be, because, been, before, being, between, both, but, by, came, can, come, could, did, do, does, each, else, for, from, get, got, had, has, have, he, her, here, him, himself, his, how, if, in, into, is, it, its, just, like, make, many, me, might, more, most, much, must, my, never, no, now, of, on, only, or, other, our, out, over, re, said, same, see, should, since, so, some, still, such, take, than, that, the, their, them, then, there, these, they, this, those, through, to, too, under, up, use, very, want, was, way, we, well, were, what, when, where, which, while, who, will, with, would, you, your";
        String [] forbiddenWordsTab = forbiddenWords.split(", ");
        ArrayList<String> forbiddenWordsOrCharactersList = new ArrayList<>();
        forbiddenWordsOrCharactersList.addAll(List.of(forbiddenWordsTab));
        ArrayList<String> forbiddenCharacters= new ArrayList<>(List.of("(", ")", "[", "]", "\'", "!", ".", ":", ";", ",", "\"", "|", "~", "?", "!"));
        forbiddenWordsOrCharactersList.addAll(forbiddenCharacters);
        while (flag) {
            System.out.println("Input your command: ");
            String s = scanner.nextLine().toLowerCase();
            if (s.startsWith("search")) {
                boolean validSearch = false;
                if (s.endsWith("artist and title")) {
                    validSearch = true;
                    System.out.println("Input the name of the artist: ");
                    String artistInput = scanner.nextLine();
                    boolean artistIsOnlyForbiddenSpecialChars = containsOnlySpecialCharacters(artistInput,forbiddenCharacters);
                    while(artistInput.length()>75 || forbiddenWordsOrCharactersList.containsAll(List.of(artistInput.toLowerCase().split(" ")))||artistIsOnlyForbiddenSpecialChars){
                        System.out.println("Your query contains only forbidden words or characters or is longer than 75 characters, " +
                                "please input again the artist's name or type \"show forbidden words\" (case sensitive) to print the forbidden words or characters.");
                        artistInput = scanner.nextLine();
                        artistIsOnlyForbiddenSpecialChars = containsOnlySpecialCharacters(artistInput,forbiddenCharacters);
                        if(artistInput.startsWith("show forbidden words")){
                            LyricsAppCLI.printForbiddenWordsOrCharacters();
                            System.out.println("Input the word of the song: ");
                            artistInput = scanner.nextLine().toLowerCase();
                            artistIsOnlyForbiddenSpecialChars = containsOnlySpecialCharacters(artistInput,forbiddenCharacters);

                        }
                    }
                    System.out.println("Input the title: ");
                    String titleInput = scanner.nextLine();
                    boolean titleIsOnlyForbiddenSpecialChars = containsOnlySpecialCharacters(titleInput,forbiddenCharacters);
                    while(titleInput.length()>125 || forbiddenWordsOrCharactersList.containsAll(List.of(titleInput.toLowerCase().split(" ")))||titleIsOnlyForbiddenSpecialChars){
                        System.out.println("Your query contains only forbidden words or characters or is longer than 125 characters, " +
                                "please input again the title or type \"show forbidden words\" (case sensitive) to print the forbidden words or characters.");
                        titleInput = scanner.nextLine();
                        titleIsOnlyForbiddenSpecialChars = containsOnlySpecialCharacters(titleInput,forbiddenCharacters);
                        if(titleInput.startsWith("show forbidden words")){
                            LyricsAppCLI.printForbiddenWordsOrCharacters();
                            System.out.println("Input the word of the song: ");
                            titleInput = scanner.nextLine().toLowerCase();
                            titleIsOnlyForbiddenSpecialChars = containsOnlySpecialCharacters(titleInput,forbiddenCharacters);
                        }
                    }
                    System.out.println("Do you want to select a minimal rank for the songs that will be displayed ? type \"yes\" or \"no\"");
                    String rankSelection = scanner.nextLine().toLowerCase();
                    while(!(rankSelection.equalsIgnoreCase("yes")||rankSelection.equalsIgnoreCase("no"))){
                        System.out.println("Please, type \"yes\" to select a minimal rank for the songs that will be displayed,\"no\" otherwise");
                        rankSelection = scanner.nextLine();
                    }
                    if (rankSelection.equals("yes")){
                        System.out.println("Please, type the minimal rank (from 0 to 10)");
                        String rank = scanner.nextLine();
                        while(!(rank.matches("^[0-9][0-9]")||rank.matches("^[0-9]"))||Integer.parseInt(rank)>10||Integer.parseInt(rank)<0){
                            System.out.println("Please, type a valid minimal rank (from 0 to 10)");
                            rank = scanner.nextLine();
                        }
                        searchResult = ArrayOfSearchLyricResultParser.parseByArtistAndTitleArrayOfSearchRankSelection(artistInput,titleInput,"api_query.xml",Integer.parseInt(rank));
                    }
                    else{
                        searchResult = ArrayOfSearchLyricResultParser.parseByArtistAndTitleArrayOfSearch(artistInput,titleInput,"api_query.xml");
                    }
                }
                else if (s.endsWith("lyrics")) {
                    validSearch = true;
                    System.out.println("Input the word of the song: ");
                    String searchInput = scanner.nextLine().toLowerCase();
                   boolean lyricsAreOnlyForbiddenSpecialChars = containsOnlySpecialCharacters(searchInput,forbiddenCharacters);
                    while(searchInput.length()>250 || forbiddenWordsOrCharactersList.containsAll(List.of(searchInput.toLowerCase().split(" ")))||lyricsAreOnlyForbiddenSpecialChars){
                        System.out.println("Your query contains only forbidden words or characters or is longer than 250 characters, " +
                                "please input again the lyrics or type \"show forbidden words\" (case sensitive) to print the forbidden words or characters.");
                        searchInput = scanner.nextLine().toLowerCase();
                        lyricsAreOnlyForbiddenSpecialChars = containsOnlySpecialCharacters(searchInput,forbiddenCharacters);
                        if(searchInput.startsWith("show forbidden words")){
                            LyricsAppCLI.printForbiddenWordsOrCharacters();
                            System.out.println("Input the word of the song: ");
                            searchInput = scanner.nextLine().toLowerCase();
                            lyricsAreOnlyForbiddenSpecialChars = containsOnlySpecialCharacters(searchInput,forbiddenCharacters);
                        }
                    }
                    System.out.println("Do you want to select a minimal rank for the songs that will be displayed ? type \"yes\" or \"no\"");
                    String rankSelection = scanner.nextLine().toLowerCase();
                    while(!(rankSelection.equalsIgnoreCase("yes")||rankSelection.equalsIgnoreCase("no"))){
                        System.out.println("Please, type \"yes\" to select a minimal rank for the songs that will be displayed,\"no\" otherwise");
                        rankSelection = scanner.nextLine();
                    }
                    if (rankSelection.equals("yes")){
                        System.out.println("Please, type the minimal rank (from 0 to 10)");
                        String rank = scanner.nextLine();
                        while(!(rank.matches("^[0-9][0-9]")||rank.matches("^[0-9]"))||Integer.parseInt(rank)>10||Integer.parseInt(rank)<0){
                            System.out.println("Please, type a valid minimal rank (from 0 to 10)");
                            rank = scanner.nextLine();
                        }
                        searchResult = ArrayOfSearchLyricResultParser.parseByLyricsRankSelection(searchInput,"api_query.xml",Integer.parseInt(rank));
                    }
                    else{
                        searchResult = ArrayOfSearchLyricResultParser.parseByLyrics(searchInput,"api_query.xml");
                    }
                }
                else if (s.equals("search")) {
                    System.out.println("You need to choose an option for search type. Type help for more information.");
                }
                else {
                    System.out.println("This kind of search is not available.");
                }
                if (validSearch) {
                    LyricsAppCLI.displayAsList(searchResult, favoriteSongs);
                }
            }
            else if (s.startsWith("add") || s.startsWith("delete")||s.startsWith("info")) {
                if (!searchResult.isEmpty()) {
                    try{
                        songNumber = Integer.parseInt(s.split(" ")[s.split(" ").length - 1]);
                        if(songNumber >= 1 && songNumber <= searchResult.size()) {
                            if (s.startsWith("add")) {
                                boolean result = favoriteSongs.add(searchResult.get(songNumber - 1));
                                if (result) {
                                    System.out.println("Successfully added.");
                                } else {
                                    System.out.println("The song is already in Favorites.");
                                }
                            }
                            else if (s.startsWith("delete")) {
                                boolean result = favoriteSongs.delete(searchResult.get(songNumber - 1));
                                if (result) {
                                    System.out.println("Successfully deleted.");
                                } else {
                                    System.out.println("The song is not in Favorites.");
                                }
                            }
                            else if(s.startsWith("info")){
                                if (searchResult.get(songNumber-1).getSongId()==0)
                                LyricsAppCLI.displaySongWithLyrics(searchResult.get(songNumber - 1), favoriteSongs);
                                else{
                                    LyricsAppCLI.displaySongWithoutLyrics(searchResult.get(songNumber - 1), favoriteSongs);
                                System.out.println("Input your command :");
                                String addLyricsInput = scanner.nextLine();
                                if(addLyricsInput.startsWith("Add lyrics")||addLyricsInput.toLowerCase().contains("add lyrics")) {
                                    System.out.println("Please type the lyrics you want to add : ");
                                    String lyricsInput = scanner.nextLine();
                                    System.out.println("Please type your email : ");
                                    String emailInput = scanner.nextLine();
//                                    LyricsAppCLI.addLyrics(searchResult.get(songNumber - 1), lyricsInput, emailInput);
                                    if(LyricsAppCLI.addLyrics(searchResult.get(songNumber - 1),lyricsInput,emailInput).contains("SendEmail failed: Failure sending mail."))
                                        System.out.println("Sorry, this option is unavailable at the moment. You have been redirected to the main menu.");
                                    else
                                        System.out.println("An email has been sent to you, check it out. You have been redirected to the main menu.");
                                }
                                else if (addLyricsInput.equalsIgnoreCase("help")){
                                    printHelp();
                                    System.out.println("\nYou have been redirected to the main menu");
                                }
                                else if (addLyricsInput.equalsIgnoreCase("return")){
                                    System.out.println("You have been redirected to the main menu");
                                }
                                else{
                                    System.out.println("This is not a valid command. You have been redirected the main menu. Type \"help\" to show allowed commands.");
                                    }
                                }
                            }
                        }
                        else{
                            System.out.println("The number of the song is incorrect.");
                        }
                    }
                    catch (NumberFormatException e){
                        System.out.println("You need to give a number of the song from the search result.");
                    }
                }
                else{
                    System.out.println("The result of search is empty.");
                }
            }
            else if (s.startsWith("show favorites")) {
                LyricsAppCLI.displayAsList(favoriteSongs.getSongs(), favoriteSongs);
                displayFavoriteArtists(favoriteSongs);
                if(!favoriteSongs.isEmpty()){
                    boolean returnValue = false;
                    while(!returnValue) {
                        System.out.println("Do you want to delete songs from favorites, get information or return?");
                        String favoriteInput = scanner.nextLine();
                        if (favoriteInput.equals("return")) {
                            returnValue = true;
                        }
                        else if (favoriteInput.startsWith("delete") || favoriteInput.startsWith("info")) {
                            if (favoriteSongs.size() > 0) {
                                try {
                                    songNumber = Integer.parseInt(favoriteInput.split(" ")[favoriteInput.split(" ").length - 1]);
                                    if (songNumber >= 1 && songNumber <= favoriteSongs.size()) {
                                        if (favoriteInput.startsWith("delete")) {
                                            boolean result = favoriteSongs.delete(favoriteSongs.get(songNumber - 1));
                                            if (result) {
                                                System.out.println("Successfully deleted.");
                                                if(favoriteSongs.isEmpty()){
                                                    returnValue = true;
                                                }
                                            } else {
                                                System.out.println("The song is not in Favorites.");
                                            }
                                        } else if (favoriteInput.startsWith("info")) {
                                            if (favoriteSongs.get(songNumber-1).getSongId()==0)
                                                LyricsAppCLI.displaySongWithLyrics(favoriteSongs.get(songNumber - 1), favoriteSongs);
                                            else{
                                                LyricsAppCLI.displaySongWithoutLyricsInFavoritesMenu(favoriteSongs.get(songNumber - 1), favoriteSongs);
                                                System.out.println("Input your command :");
                                                String addLyricsInput = scanner.nextLine();
                                                if(addLyricsInput.startsWith("Add lyrics")||addLyricsInput.toLowerCase().contains("add lyrics")){
                                                    System.out.println("Please type the lyrics you want to add : ");
                                                    String lyricsInput = scanner.nextLine();
                                                    System.out.println("Please type your email : ");
                                                    String emailInput = scanner.nextLine();
                                                    if(LyricsAppCLI.addLyrics(favoriteSongs.get(songNumber - 1),lyricsInput,emailInput).contains("SendEmail failed: Failure sending mail."))
                                                        System.out.println("Sorry, this option is unavailable at the moment.");
                                                    else
                                                        System.out.println("An email has been sent to you, check it out.");
                                                }
                                                else if (addLyricsInput.equalsIgnoreCase("help")){
                                                    printHelp();
                                                    System.out.println("\nYou have been redirected to the favorites menu");
                                                }
                                                else if (addLyricsInput.equalsIgnoreCase("return")){
                                                    System.out.println("You have been redirected to the favorites menu");
                                                }
                                                else{
                                                    System.out.println("It is not a valid command.You have been redirected to the favorites menu.");
                                                }
                                            }
                                        }
                                    } else {
                                        System.out.println("The number of the song is incorrect.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("You need to give a number of the song from the favorite list.");
                                }
                            } else {
                                System.out.println("The favorite list is empty.");
                            }
                        }
                        else {
                            System.out.println("It is not a valid command. Do you want to delete songs from favorites, get information or return?");
                        }
                    }
                }
            }
            else if(s.startsWith("show search result")){
                LyricsAppCLI.displayAsList(searchResult, favoriteSongs);
            }
            else if (s.startsWith("exit")) {
                System.out.println("Bye!");
                flag = false;
            }
            else if (s.startsWith("help")) {
                LyricsAppCLI.printHelp();
            }else if (s.startsWith("show forbidden words")) {
                LyricsAppCLI.printForbiddenWordsOrCharacters();
            }
            else {
                System.out.println("I do not understand your command. Please try something else or type help to see available commands.");
            }
        }
    }
    public static void printHelp() {
        String help = "You can use the following commands:\n";
        help += "search <option>: search of songs by <option>.\n";
        help += "\tartist and title (option of search): search of songs by artist's name and song's title.\n";
        help += "\tlyrics (option of search): search of songs by lyrics.\n";
        help += "add <number>: adds the song number <number> to favorite list.\n";
        help += "delete <number>: deletes the song number <number> from the favorite list.\n";
        help += "info <number>: shows information about the song number <number>.\n";
        help += "\tAdd lyrics <lyrics>, <user's email>: enables the user to add lyrics for a song if it has no lyrics yet.\n";
        help += "show favorites: prints the list of favorite songs.\n";
        help += "show search result: prints the last search result.\n";
        help+="show forbidden words : prints all forbidden words and characters for the search with lyrics";
        help += "help: prints all the available commands.\n";
        help += "exit: closes the program.\n";
        System.out.println(help);
    }

    public static void printForbiddenWordsOrCharacters(){
        String  forbidden ="Here are the forbidden words or characters when searching by lyrics:\n";
                forbidden +="\tabout, after, all, also, an, and, another, any, are, as, at, be, because, been, before, being, between, both\n";
                forbidden +="\tbut, by, came, can, come, could, did, do, does, each, else, for, from, get, got, had, has, have, he, her, here, him\n" ;
                forbidden +="\thimself, his, how, if, in, into, is, it, its, just, like, make, many, me, might, more, most, much, must, my, never\n" ;
                forbidden +="\tno, now, of, on, only, or, other, our, out, over, re, said, same, see, should, since, so, some, still, such, take\n" ;
                forbidden +="\tthan, that, the, their, them, then, there, these, they, this, those, through, to, too, under, up, use, very, want\n" ;
                forbidden +="\twas, way, we, well, were, what, when, where, which, while, who, will, with, would, you, your\n";
                forbidden+="\t( ) [ ] ' ! . : ; , \" | ~ ? !\n";
        System.out.println(forbidden);
    }
    public static void displaySongWithLyrics(Song song, Favorites favorites) throws IOException {
            String lyricText = GetLyricResultParser.parseGetLyricWithIdAndChecksum(song.lyricsIdToString(), song.getLyricChecksum(), "api_query.xml").getLyrics().getLyrics();
            String authors = "";
            for (int i = 0; i < song.getAuthors().size(); i++) {
                if (i == song.getAuthors().size() - 1)
                    authors += song.getAuthors().get(i).getName();
                else authors += song.getAuthors().get(i).getName() + ", ";
            }

                if (favorites.getSongs().contains(song)) {
                    System.out.println("Title: " + song.getTitle() + " (Favorite)\n" + "Artist: " + authors + "\nRank: " + song.getRank() + "/10\n"
                            + "Lyrics: " + lyricText + "\n\n");
                } else {
                    System.out.println("Title: " + song.getTitle() + "\n\nArtist: " + authors + "\n\nRank: " + song.getRank() + "/10\n\n"
                            + "Lyrics: \n\n" + lyricText + "\n\n");
                }
        }
    public static void displaySongWithoutLyricsInFavoritesMenu(Song song,Favorites favorites) {
        String authors = "";
        for (int i = 0; i < song.getAuthors().size(); i++) {
            if (i == song.getAuthors().size() - 1)
                authors += song.getAuthors().get(i).getName();
            else authors += song.getAuthors().get(i).getName() + ", ";
        }
        if (favorites.getSongs().contains(song)) {
            System.out.println("Title: " + song.getTitle() + " (Favorite)\n" + "Artist: " + authors + "\nRank: " + song.getRank() + "/10\n"
                    + "Lyrics: " +song.getLyrics().getLyrics()+ "Type \"Add lyrics\" (case sensitive) to add lyrics for this song or \"return\" to get redirected to the favorites menu." + "\n");
        } else {
            System.out.println("Title: " + song.getTitle() + "\n\nArtist: " + authors + "\n\nRank: " + song.getRank() + "/10\n\n"
                    + "Lyrics: " +song.getLyrics().getLyrics()+ " Type \"Add lyrics\" (case sensitive) to add lyrics for this song or \"return\" to get redirected to the main menu.");
        }
    }
    public static void displaySongWithoutLyrics(Song song,Favorites favorites) {
        String authors = "";
        for (int i = 0; i < song.getAuthors().size(); i++) {
            if (i == song.getAuthors().size() - 1)
                authors += song.getAuthors().get(i).getName();
            else authors += song.getAuthors().get(i).getName() + ", ";
        }
        if (favorites.getSongs().contains(song)) {
            System.out.println("Title: " + song.getTitle() + " (Favorite)\n" + "Artist: " + authors + "\nRank: " + song.getRank() + "/10\n"
                    + "Lyrics: " +song.getLyrics().getLyrics()+ "Type \"Add lyrics\" (case sensitive) to add lyrics for this song or \"return\" to get redirected to the main menu." + "\n");
        } else {
            System.out.println("Title: " + song.getTitle() + "\n\nArtist: " + authors + "\n\nRank: " + song.getRank() + "/10\n\n"
                    + "Lyrics: " +song.getLyrics().getLyrics()+ " Type \"Add lyrics\" (case sensitive) to add lyrics for this song or \"return\" to get redirected to the main menu.");
        }
    }
    public static void displayAsList(ArrayList<Song> listOfSongs, Favorites favorites){
        int lineNumber = 1;
        if(!listOfSongs.isEmpty()){
            for (Song song : listOfSongs) {
                String authors = "";
                for (int i = 0; i < song.getAuthors().size(); i++) {
                    if(i==song.getAuthors().size()-1)
                        authors += song.getAuthors().get(i).getName();
                    else authors += song.getAuthors().get(i).getName() + ", ";
                }
                if (favorites.getSongs().contains(song)) {
                    System.out.println(lineNumber + " " + authors + " " + song.getTitle() + " " + song.getRank() + "/10   (Favorite)");
                }
                else System.out.println(lineNumber + " " + authors + " " + song.getTitle() + " " + song.getRank() + "/10");
                lineNumber++;
            }
        }
        else{
            System.out.println("It is empty.");
        }
    }
        public static String addLyrics(Song song,String lyrics,String userEmail) throws IOException {
        lyrics = lyrics.replace(" ","%20");
         String content = ApiRequest.addLyricsRequest(song.songIdToString(),song.getTrackChecksum(),lyrics,userEmail);
            return content;
    }

    private static boolean containsOnlySpecialCharacters(String input,ArrayList<String> forbiddenCharacters){
        String forbiddenCharactersInInput = "";
        for(int i=0;i<input.length();i++){
            if(forbiddenCharacters.contains(String.valueOf(input.charAt(i)))){
                forbiddenCharactersInInput+=String.valueOf(input.charAt(i));
            }
        }
        if (input.equals(forbiddenCharactersInInput)){
            return true;
        }
        return false;
    }
    private static void displayFavoriteArtists(Favorites favoriteSongs) throws IOException, ClassNotFoundException {
        String authors = "";
        for (String authorName : favoriteSongs.getAuthors().keySet()){
            authors+= authorName + " : "+favoriteSongs.getAuthors().get(authorName)+" song(s) from this artist in your favorites\n";
        }
        if (!favoriteSongs.isEmpty()){
            System.out.println("\nBelow you can see your favorite artists, based on your favorite songs :\n\n" + authors);
        }
        else {
            System.out.println("You don't have any favorite song, therefore there is no favorite artist.\n");
        }
    }
}