package pl.edu.pwr.ztw.lyricsQuiz.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.ztw.lyricsQuiz.model.User;
import pl.edu.pwr.ztw.lyricsQuiz.model.UserRowMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query("SELECT id,email,password FROM User", new UserRowMapper());
    }

    @Override
    public User getUser(int id) {
        return (User) this.jdbcTemplate.queryForObject("SELECT id,email,password FROM User WHERE id = ?", new Object[] {id}, new UserRowMapper());
    }

    @Override
    public Boolean createUser(User user) {
        String query = "INSERT INTO User VALUES (?,?,?)";
        return jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.setInt(1,user.getId());
                preparedStatement.setString(2,user.getEmail());
                preparedStatement.setString(3,user.getPassword());
                return preparedStatement.execute();
            }
        });
    }

    @Override
    public Integer updateUser(int id, User user) {
        String query = "UPDATE User SET email = ?, password = ? WHERE id = ?";
        Object [] params = {user.getEmail(), user.getPassword(), id};
        int [] types = {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.INTEGER};

        return jdbcTemplate.update(query,params,types);

    }

    @Override
    public Integer deleteUser(int id) {
        String sql = "DELETE FROM User WHERE id = ?";
        Object[] args = new Object[] {id};

        return jdbcTemplate.update(sql, args);

    }


}
