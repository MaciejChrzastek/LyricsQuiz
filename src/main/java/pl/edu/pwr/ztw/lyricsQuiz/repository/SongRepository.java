package pl.edu.pwr.ztw.lyricsQuiz.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.ztw.lyricsQuiz.model.Song;
import pl.edu.pwr.ztw.lyricsQuiz.model.SongRowMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class SongRepository implements ISongRepository {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Song> getSongs() {
        return jdbcTemplate.query("SELECT id,author,title FROM Song", new SongRowMapper());
    }

    @Override
    public Song getSong(int id) {
        return (Song) this.jdbcTemplate.queryForObject("SELECT id,author,title FROM Song WHERE id = ?", new Object[] {id}, new SongRowMapper());
    }

    @Override
    public Boolean createSong(Song song) {
        String query = "INSERT INTO Song VALUES (?,?,?)";
        return jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.setInt(1,song.getId());
                preparedStatement.setString(2,song.getAuthor());
                preparedStatement.setString(3,song.getTitle());
                return preparedStatement.execute();
            }
        });
    }

    @Override
    public Integer updateSong(int id, Song song) {
        String query = "UPDATE Song SET author = ?, title = ? WHERE id = ?";
        Object [] params = {song.getAuthor(), song.getTitle(), id};
        int [] types = {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.INTEGER};

        return jdbcTemplate.update(query,params,types);

    }

    @Override
    public Integer deleteSong(int id) {
        String sql = "DELETE FROM Song WHERE id = ?";
        Object[] args = new Object[] {id};

        return jdbcTemplate.update(sql, args);

    }


}
