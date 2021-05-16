package pl.edu.pwr.ztw.lyricsQuiz.repository;

import pl.edu.pwr.ztw.lyricsQuiz.model.User;

import java.util.List;

public interface IUserRepository {
    public abstract List<User> getUsers();
    public abstract User getUser(int id);
    public abstract Boolean createUser(User user);

    public abstract Integer updateUser(int id, User user);
    public abstract Integer deleteUser(int id);

}
