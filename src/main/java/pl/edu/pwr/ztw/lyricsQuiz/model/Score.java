package pl.edu.pwr.ztw.lyricsQuiz.model;

public class Score {
    private int id;
    private User user;
    private Song song;

    private int score;
    private int difficulty;
    private int max_score;
    private String date;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Score(){

    }

    public Score(int id, User user, Song song, int score, int difficulty, int max_score, String date) {
        this.id = id;
        this.user = user;
        this.song = song;
        this.score = score;
        this.difficulty = difficulty;
        this.max_score = max_score;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getMax_score() {
        return max_score;
    }

    public void setMax_score(int max_score) {
        this.max_score = max_score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
