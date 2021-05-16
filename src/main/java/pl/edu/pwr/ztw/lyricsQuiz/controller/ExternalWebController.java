package pl.edu.pwr.ztw.lyricsQuiz.controller;

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


    @RequestMapping(value = "/get/lyrics/{author}/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("author") String author, @PathVariable("title") String title){

        String lyrics = null;
        try {
            lyrics = getLyrics(author,title);
        } catch (IOException e) {
            lyrics = "No lyrics for this song available in our database";
        }

        return new ResponseEntity<String>(lyrics, HttpStatus.OK);
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
        con.disconnect();


        String response = String.valueOf(content);

        /*
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response);
        JsonObject jsonObject = element.getAsJsonObject();
        JsonObject lyricsJsonObject = jsonObject.getAsJsonObject("lyrics");
        String lyrics = lyricsJsonObject.getAsString();

        lyrics = lyrics.replaceAll("\n","\n\n");
        lyrics = lyrics.replaceAll("\r","\n\n");
        lyrics = lyrics.replaceAll("\n\n\n\n","\n\n");
        lyrics = lyrics.replaceAll("\n\n\n","\n\n");


         */
        return response;
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
