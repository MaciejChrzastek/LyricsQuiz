package pl.edu.pwr.ztw.lyricsQuiz.model;

public class User {
    private int id;
    private String email;
    private String password;

    public User(){

    }

    @Override
    public String toString() {
        return "{id: "+id+", email: "+email+", password: "+password+"}";
    }

    public User(int id, String password, String email) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
