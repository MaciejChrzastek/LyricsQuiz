package pl.edu.pwr.ztw.lyricsQuiz.model;

import org.springframework.jdbc.core.RowMapper;
import pl.edu.pwr.ztw.lyricsQuiz.repository.SongRepository;
import pl.edu.pwr.ztw.lyricsQuiz.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ScoreRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Score score = new Score();
        score.setId(resultSet.getInt("id"));

        UserRepository userRepository = new UserRepository();
        User referencedUser = userRepository.getUser(resultSet.getInt("user_id"));
        score.setUser(referencedUser);

        SongRepository songRepository = new SongRepository();
        Song referencedSong = songRepository.getSong(resultSet.getInt("song_id"));
        score.setSong(referencedSong);

        score.setScore(resultSet.getInt("score"));
        score.setDifficulty(resultSet.getInt("difficulty"));
        score.setMax_score(resultSet.getInt("max_score"));

        score.setDate(resultSet.getString("date"));


        return score;
    }
}


//TO BE ADDED SOON