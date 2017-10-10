package model;

public class Music {

    public static int COUNTER = 0;

    private int id;
    String song = null, songMbid = null, artist = null, artistMbid = null, album = null, albumMbid = null;

    public Music(){
        this.id = ++COUNTER;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSong() {
        return song;
    }

    public String getSongForQuery() {
        return (song == null) ? null : song.replaceAll("'","''");
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSongMbid() {
        return songMbid;
    }

    public void setSongMbid(String songMbid) {
        this.songMbid = songMbid;
    }

    public String getArtist() {
        return artist;
    }

    public String getArtistForQuery() {
        return (artist == null) ? null : artist.replaceAll("'","''");
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtistMbid() {
        return artistMbid;
    }

    public void setArtistMbid(String artistMbid) {
        this.artistMbid = artistMbid;
    }

    public String getAlbum() {
        return album;
    }
    public String getAlbumForQuery() {
        return (album == null) ? null : album.replaceAll("'","''");
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbumMbid() {
        return albumMbid;
    }

    public void setAlbumMbid(String albumMbid) {
        this.albumMbid = albumMbid;
    }
}
