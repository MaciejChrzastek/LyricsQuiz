package pl.edu.pwr.ztw.lyricsQuiz.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.edu.pwr.ztw.lyricsQuiz.repository.SongRepository;
import pl.edu.pwr.ztw.lyricsQuiz.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ScoreRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Score score = new Score();
        score.setId(resultSet.getInt("id"));

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName( "com.mysql.cj.jdbc.Driver");
        dataSource.setUrl( "jdbc:mysql://localhost:3306/lyrics_quiz_db");
        dataSource.setUsername( "root");
        dataSource.setPassword( "root");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        User referencedUser = (User) jdbcTemplate.queryForObject("SELECT id,email,password FROM User WHERE id = ?", new Object[] {resultSet.getInt("user_id")}, new UserRowMapper());
        score.setUser(referencedUser);

        Song referencedSong = (Song) jdbcTemplate.queryForObject("SELECT id,author,title FROM Song WHERE id = ?", new Object[] {resultSet.getInt("song_id")}, new SongRowMapper());
        score.setSong(referencedSong);

        score.setScore(resultSet.getInt("score"));
        score.setDifficulty(resultSet.getInt("difficulty"));
        score.setMax_score(resultSet.getInt("max_score"));

        score.setDate(resultSet.getString("date"));


        return score;
    }
}


//TO BE ADDED SOON