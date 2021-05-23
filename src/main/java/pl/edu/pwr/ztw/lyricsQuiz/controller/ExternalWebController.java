package pl.edu.pwr.ztw.lyricsQuiz.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


@RestController
public class ExternalWebController {

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @RequestMapping(value = "/get/lyrics/{author}/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getLyricsText(@PathVariable("author") String author, @PathVariable("title") String title){

        String lyrics = null;
        try {
            lyrics = getLyrics(author,title);
        } catch (IOException e) {
            lyrics = "No lyrics for this song available in our database";
        }

        return new ResponseEntity<String>(lyrics, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @RequestMapping(value = "/get/video/{author}/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getVideoID(@PathVariable("author") String author, @PathVariable("title") String title){

        String video_id = null;
        video_id = getVideo(author,title);

        return new ResponseEntity<String>(video_id, HttpStatus.OK);
    }

    private String getVideo(String author, String title) {
        String API_KEY = "AIzaSyDOxLq0-IJL8GUrZ_Fjda5Tk6FKL8WDb_8";

        title = replace_spaces(title);
        author = replace_spaces(author);

        String query = title+"%20"+author;

        String url_address = "https://www.googleapis.com/youtube/v3/search?part=snippet&key=" +API_KEY + "&type=video&maxResults=1&videoEmbeddable=true&order=relevance&q="  + query;

        URL url = null;
        try {
            url = new URL(url_address);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        con.setRequestProperty("Content-Type", "application/json");

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String inputLine = null;
        StringBuffer content = new StringBuffer();
        while (true) {
            try {
                if (!((inputLine = in.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            content.append(inputLine);
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        con.disconnect();


        String response = String.valueOf(content);

        ///ZROBIć coś z tym
        JsonParser parser = new JsonParser();
        JsonElement jElement = parser.parse(response);
        JsonObject jObjectAll = jElement.getAsJsonObject();
        JsonArray video_list = jObjectAll.getAsJsonArray("items");
        JsonObject video = (JsonObject) video_list.get(0);
        JsonObject videoIDObject = video.getAsJsonObject("id");
        String video_id = String.valueOf(videoIDObject.get("videoId"));



        return video_id;
    }

    private String getLyrics(String artist,String title) throws IOException {

        title = replace_spaces(title);
        artist = replace_spaces(artist);

        String url_address = "https://api.lyrics.ovh/v1/" + artist +"/" + title;
        URL url = new URL(url_address);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        int status = con.getResponseCode();
        con.disconnect();


        String response = String.valueOf(content);


        if (status > 299) {
            return response;
        } else {
            JsonParser parser = new JsonParser();
            JsonElement jElement = parser.parse(response);
            JsonObject jObjectAll = jElement.getAsJsonObject();
            String lyrics = jObjectAll.get("lyrics").toString();

            lyrics = lyrics.replaceAll("\n","\n\n");
            lyrics = lyrics.replaceAll("\r","\n\n");
            lyrics = lyrics.replaceAll("\n\n\n\n","\n\n");
            lyrics = lyrics.replaceAll("\n\n\n","\n\n");



            return lyrics;
        }

    }

    private String replace_spaces(String title) {
        String [] words = title.split(" ");
        String editedWord =words[0];
        for(int i=1;i<words.length;i++){
            editedWord+="%20" + words[i];
        }
        return editedWord;
    }










}
