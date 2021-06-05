package pl.edu.pwr.ztw.lyricsQuiz.model;

public class User {
    private int id;
    private String email;

    public User(){

    }

    @Override
    public String toString() {
        return "{id: "+id+", email: "+email+"}";
    }

    public User(int id, String email) {
        this.id = id;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
