package pl.edu.pwr.ztw.lyricsQuiz.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.ztw.lyricsQuiz.model.Score;
import pl.edu.pwr.ztw.lyricsQuiz.model.ScoreRowMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ScoreRepository implements IScoreRepository {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Score> getScores() {
        return jdbcTemplate.query("SELECT id, user_id, song_id, score, difficulty, max_score, date FROM Score", new ScoreRowMapper());

    }

    @Override
    public Score getScore(int id) {
        return (Score) this.jdbcTemplate.queryForObject("SELECT id, user_id, song_id, score, difficulty, max_score, date FROM Score WHERE id = ?", new Object[] {id}, new ScoreRowMapper());
    }

    @Override
    public Boolean createScore(Score score) {
        String query = "INSERT INTO Score VALUES (?,?,?,?,?,?,?)";
        return jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.setInt(1,score.getId());
                preparedStatement.setInt(2,score.getUser().getId());
                preparedStatement.setInt(3,score.getSong().getId());
                preparedStatement.setInt(4,score.getScore());
                preparedStatement.setInt(5,score.getDifficulty());
                preparedStatement.setInt(6,score.getMax_score());
                preparedStatement.setString(7,score.getDate());
                return preparedStatement.execute();
            }
        });
    }

    @Override
    public Integer updateScore(int id, Score score) {
        String query = "UPDATE Score SET user_id = ?, song_id = ?, score = ?, difficulty = ?, max_score = ?, date = ? WHERE id = ?";
        Object [] params = {score.getUser().getId(), score.getSong().getId(), score.getScore(), score.getDifficulty(), score.getMax_score(), score.getDate(), id};
        int [] types = {Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.DATE, java.sql.Types.INTEGER};

        return jdbcTemplate.update(query,params,types);
    }

    @Override
    public Integer deleteScore(int id) {
        String sql = "DELETE FROM Score WHERE id = ?";
        Object[] args = new Object[] {id};

        return jdbcTemplate.update(sql, args);
    }

    //TO BE ADDED SOON


}
