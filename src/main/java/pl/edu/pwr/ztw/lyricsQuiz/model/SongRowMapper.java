package pl.edu.pwr.ztw.lyricsQuiz.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Song song = new Song();
        song.setId(resultSet.getInt("id"));
        song.setAuthor(resultSet.getString("author"));
        song.setTitle(resultSet.getString("title"));
        return song;
    }
}
