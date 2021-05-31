package pl.edu.pwr.ztw.lyricsQuiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.lyricsQuiz.model.Score;
import pl.edu.pwr.ztw.lyricsQuiz.repository.IScoreRepository;

import java.util.List;

@RestController
public class ScoreController {

    @Autowired
    IScoreRepository scoreRepository;

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/add/score", method = RequestMethod.POST)
    public ResponseEntity<Object> createScore(@RequestBody Score score) {
        scoreRepository.createScore(score);
        return new ResponseEntity<>("Score data was created successfully", HttpStatus.CREATED);
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/get/scores", method = RequestMethod.GET)
    public List<Score> getScores(){
        return scoreRepository.getScores();
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/get/score/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getScore(@PathVariable("id") int id){
        Score requestedScore = scoreRepository.getScore(id);
        if (requestedScore == null){
            return new ResponseEntity<String>("No score data with this id was found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Score>(requestedScore, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/update/score/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateScore(@PathVariable("id") int id, @RequestBody Score score) {

        if(scoreRepository.getScore(id) == null){
            return new ResponseEntity<String>("No score data with this id was found",HttpStatus.NOT_FOUND);
        }
        scoreRepository.updateScore(id, score);
        return new ResponseEntity<>("Score data was updated successsfully", HttpStatus.OK);
    }


    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/delete/score/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteScore(@PathVariable("id") int id) {
        Score to_be_deleted_score = scoreRepository.getScore(id);
        if(to_be_deleted_score == null){
            return new ResponseEntity<String>("No score data with this id was found",HttpStatus.NOT_FOUND);
        }
        scoreRepository.deleteScore(id);
        return new ResponseEntity<>("Score data was deleted successsfully", HttpStatus.OK);
    }


}
