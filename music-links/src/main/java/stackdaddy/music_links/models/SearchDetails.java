package stackdaddy.music_links.models;

public class SearchDetails {
	
	protected String artist = "";
	protected String album  = "";
	protected String track  = "";
	
	public SearchDetails(String artist, String album, String track) {
		this.artist = artist;
		this.album  = album;
		this.track  = track;
	}
	
	public String getArtist() {
		return this.artist;
	}
	
	public String getAlbum() {
		return this.album;
	}
	
	public String getTrack() {
		return this.track;
	}
}
