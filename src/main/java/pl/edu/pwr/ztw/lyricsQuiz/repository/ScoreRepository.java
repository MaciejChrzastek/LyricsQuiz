package pl.edu.pwr.ztw.lyricsQuiz.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.ztw.lyricsQuiz.model.Score;
import pl.edu.pwr.ztw.lyricsQuiz.model.ScoreRowMapper;
import pl.edu.pwr.ztw.lyricsQuiz.model.Song;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public List<Score> getScoresFromLastWeek() {
        return jdbcTemplate.query("SELECT id, user_id, song_id, score, difficulty, max_score, date FROM Score WHERE date >= (DATE(NOW()) - INTERVAL 7 DAY)", new ScoreRowMapper());

    }

    @Override
    public List<String> getScoresFromLastWeekString() {
        String query = "SELECT title, author, score, max_score, date , count(Score.id) as times_played\n" +
                "FROM Score join User on User.id=user_id\n" +
                "join Song on Song.id=song_id\n" +
                "WHERE date >= (DATE(NOW()) - INTERVAL 7 DAY)\n" +
                "GROUP BY song_id\n" +
                "Order By date DESC";
        List<String> data = jdbcTemplate.query(query, new RowMapper<String>(){
            public String mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                return rs.getString(1);
            }
        });
        return data;
    }



    @Override
    public List<String> getScoresFromLastWeekOfPlayerString(String email) {
        String query = "SELECT title, author, score, max_score, date , count(Score.id) as times_played\n" +
                "FROM Score join User on User.id=user_id\n" +
                "join Song on Song.id=song_id\n" +
                "WHERE date >= (DATE(NOW()) - INTERVAL 7 DAY)\n" +
                "AND User.email=\"" + email +"\"\n" +
                "GROUP BY song_id\n" +
                "Order By date DESC ";
        List<String> data = jdbcTemplate.query(query, new RowMapper<String>(){
            public String mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                return rs.getString(1);
            }
        });
        return data;
    }

    @Override
    public List<String> getTopFiveScoresString() {
        String query = "SELECT title, author, count(Score.id) as times_played\n" +
                "FROM Score join User on User.id=user_id\n" +
                "join Song on Song.id=song_id\n" +
                "GROUP BY song_id\n" +
                "Order By times_played DESC\n" +
                "LIMIT 5";
        List<String> data = jdbcTemplate.query(query, new RowMapper<String>(){
            public String mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                return rs.getString(1);
            }
        });
        return data;
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
