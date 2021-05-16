package pl.edu.pwr.ztw.lyricsQuiz.model;

public class Song {
    private int id;
    private String author;
    private String title;

    public Song(){

    }

    public Song(int id, String author, String title) {
        this.id = id;
        this.author = author;
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        Song otherSong = (Song) obj;
        return ((this.title.equals(otherSong.getTitle()))&&(this.author.equals(otherSong.getAuthor())));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
