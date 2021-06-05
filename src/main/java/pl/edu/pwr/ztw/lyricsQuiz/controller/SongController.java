package pl.edu.pwr.ztw.lyricsQuiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.lyricsQuiz.model.Song;
import pl.edu.pwr.ztw.lyricsQuiz.repository.ISongRepository;

import java.util.List;

@RestController
public class SongController {

    @Autowired
    ISongRepository songRepository;

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/add/song", method = RequestMethod.POST)
    public ResponseEntity<Object> createSong(@RequestBody Song song) {
        songRepository.createSong(song);
        return new ResponseEntity<>("Song data was created successfully", HttpStatus.CREATED);
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/get/songs", method = RequestMethod.GET)
    public List<Song> getSongs(){
        return songRepository.getSongs();
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/get/song/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSong(@PathVariable("id") int id){
        Song requestedSong = songRepository.getSong(id);
        if (requestedSong == null){
            return new ResponseEntity<String>("No song with this id was found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Song>(requestedSong, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/get/songByData/{title}/{author}", method = RequestMethod.GET)
    public ResponseEntity<?> getSongByData(@PathVariable("title") String title, @PathVariable("author") String author){
        Song requestedSong = songRepository.getSongByData(title,author);
        if (requestedSong == null){
            return new ResponseEntity<String>("No song with this title and author was found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Song>(requestedSong, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/update/song/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSong(@PathVariable("id") int id, @RequestBody Song song) {

        if(songRepository.getSong(id) == null){
            return new ResponseEntity<String>("No song with this id was found",HttpStatus.NOT_FOUND);
        }
        songRepository.updateSong(id, song);
        return new ResponseEntity<>("Song was updated successsfully", HttpStatus.OK);
    }


    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/delete/song/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSong(@PathVariable("id") int id) {
        Song to_be_deleted_song = songRepository.getSong(id);
        if(to_be_deleted_song == null){
            return new ResponseEntity<String>("No song with this id was found",HttpStatus.NOT_FOUND);
        }
        songRepository.deleteSong(id);
        return new ResponseEntity<>("Song was deleted successsfully", HttpStatus.OK);
    }







}
