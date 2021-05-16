package pl.edu.pwr.ztw.lyricsQuiz.repository;

import pl.edu.pwr.ztw.lyricsQuiz.model.Song;

import java.util.List;

public interface ISongRepository {
    public abstract List<Song> getSongs();
    public abstract Song getSong(int id);
    public abstract Boolean createSong(Song song);

    public abstract Integer updateSong(int id, Song song);
    public abstract Integer deleteSong(int id);

}
