package pl.edu.pwr.ztw.lyricsQuiz.repository;


import pl.edu.pwr.ztw.lyricsQuiz.model.Score;
import pl.edu.pwr.ztw.lyricsQuiz.model.Song;

import java.util.List;

public interface IScoreRepository {

    public abstract List<Score> getScores();
    public abstract List<Score> getScoresFromLastWeek();
    public abstract List<String> getScoresFromLastWeekString();
    public abstract List<String> getScoresFromLastWeekOfPlayerString(String email);
    public abstract List<String> getTopFiveScoresString();


    public abstract Score getScore(int id);
    public abstract Boolean createScore(Score score);

    public abstract Integer updateScore(int id, Score score);
    public abstract Integer deleteScore(int id);

}
