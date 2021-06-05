package pl.edu.pwr.ztw.lyricsQuiz.repository;


import pl.edu.pwr.ztw.lyricsQuiz.model.Score;
import pl.edu.pwr.ztw.lyricsQuiz.model.Song;

import java.util.List;

public interface IScoreRepository {

    public abstract List<Score> getScores();
    public abstract List<Score> getScoresFromLastWeek();


    public abstract Score getScore(int id);
    public abstract Boolean createScore(Score score);

    public abstract Integer updateScore(int id, Score score);
    public abstract Integer deleteScore(int id);

}
