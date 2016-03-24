package stackdaddy.music_links.constants;

public class Statics {
	//SPOTIFY
	public final static String SPOTIFY_API_FORMAT = "https://api.spotify.com/v1/search?q=artist:%salbum:%strack:%s&type=track&limit=1";
	//GOOGLE
	public final static String GOOGLE_API_FORMAT  = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&key=AIzaSyAbBAGFhnj4kJJVkmRyGpmnl6c7HKx_Ll0&q=%s%s%ssite:play.google.com";
    //ITunes
	public final static String ITUNES_API_FORMAT  = "https://itunes.apple.com/search?term=%s&media=music&entity=musicTrack&artistTerm=%s&albumTerm=%s&limit=1";
	//AMAZON
	public final static String SECRET_KEY    = "r6dlTvW38WJIkcfp/tTzvYcuiQF/7w3g2U7SYNs6";
	public final static String ACCESS_KEY    = "AKIAIQ53G5SLUZXGJWBQ";
	public final static String HOST          = "ecs.amazonaws.com";
	public final static String SERVICE       = "AWSECommerceService";
	public final static String ASSOCIATE_TAG = "ti069-20";
	public final static String OPERATION     = "ItemSearch";
	public final static String SEARCH_INDEX  = "MP3Downloads";
	public final static String TOTAL_RESULTS = "1";
	
}
