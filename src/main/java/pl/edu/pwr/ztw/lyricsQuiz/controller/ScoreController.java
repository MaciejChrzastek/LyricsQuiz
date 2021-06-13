package pl.edu.pwr.ztw.lyricsQuiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.lyricsQuiz.model.Score;
import pl.edu.pwr.ztw.lyricsQuiz.model.Song;
import pl.edu.pwr.ztw.lyricsQuiz.model.User;
import pl.edu.pwr.ztw.lyricsQuiz.repository.IScoreRepository;

import java.util.ArrayList;
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
    @RequestMapping(value = "/add/scoreByData/{score}/{max_score}", method = RequestMethod.POST)
    public ResponseEntity<?> createScoreByData(@PathVariable("score") int score, @PathVariable("max_score") int max_score, @RequestBody Song song, @RequestBody User user){
        ArrayList<Score> list_of_scores = (ArrayList<Score>) scoreRepository.getScores();
        int current_max_id = list_of_scores.get(0).getId();
        for(int i=1;i<list_of_scores.size();i++){
            if(list_of_scores.get(i).getId()>current_max_id) current_max_id = list_of_scores.get(i).getId();
        }
        int new_score_id = current_max_id +1;
        String new_score_date = java.time.LocalDate.now().toString();
        int new_score_difficulty = 0;

        Score newScore = new Score(new_score_id,user,song,score,new_score_difficulty,max_score,new_score_date);
        scoreRepository.createScore(newScore);
        return new ResponseEntity<>("Score data was created successfully", HttpStatus.CREATED);
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/get/scores", method = RequestMethod.GET)
    public List<Score> getScores(){
        return scoreRepository.getScores();
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/get/scoresFromLastWeek", method = RequestMethod.GET)
    public List<Score> getScoresFromLastWeek(){
        return scoreRepository.getScoresFromLastWeek();
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/get/scoresFromLastWeekOfPlayer/{email}", method = RequestMethod.GET)
    public List<Score> getScoresFromLastWeekOfPlayer(@PathVariable("email") String email){
        ArrayList<Score> list_of_scores= (ArrayList<Score>) scoreRepository.getScoresFromLastWeek();
        List<Score> list_of_scores_of_player = new ArrayList<>();
        for(int i=0;i<list_of_scores.size();i++){
            if(list_of_scores.get(i).getUser().getEmail().equals(email)){
                list_of_scores_of_player.add(list_of_scores.get(i));
            }
        }

        return list_of_scores_of_player;
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/get/scoresFromLastWeekString", method = RequestMethod.GET)
    public List<String> getScoresFromLastWeekString(){
        return scoreRepository.getScoresFromLastWeekString();
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/get/scoresFromLastWeekOfPlayerString/{email}", method = RequestMethod.GET)
    public List<String> getScoresFromLastWeekOfPlayerString(@PathVariable("email") String email){
        return scoreRepository.getScoresFromLastWeekOfPlayerString(email);
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/get/topFiveScoresString", method = RequestMethod.GET)
    public List<String> getTopFiveScoresString(){
        return scoreRepository.getTopFiveScoresString();
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
