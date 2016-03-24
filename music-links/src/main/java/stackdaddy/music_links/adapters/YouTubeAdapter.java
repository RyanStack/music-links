package stackdaddy.music_links.adapters;

import stackdaddy.music_links.models.*;
import stackdaddy.music_links.adapters.InterfaceAdapter;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class YouTubeAdapter implements InterfaceAdapter 
{
    private static final long NUMBER_OF_VIDEOS_RETURNED = 1;
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final String API_KEY = "AIzaSyCP6-EAFAerTCRTIamO6ayAS1B5TTVlWMg";
    private static YouTube youtube;
    private SearchDetails searchDetails;
    
    public YouTubeAdapter(SearchDetails searchDetails) {
    	this.searchDetails = searchDetails;
    }

	public String getSearchQuery() {
		return this.searchDetails.getArtist() + this.searchDetails.getAlbum() + this.searchDetails.getTrack();
	}
	
	public FormattedResult performSearch () {
		try {
			youtube = new YouTube.Builder(YouTubeAdapter.HTTP_TRANSPORT, YouTubeAdapter.JSON_FACTORY, new HttpRequestInitializer() {
				public void initialize(HttpRequest request) throws IOException {
		        }
			}).setApplicationName("youtubeAdapter").build();
			
		    String queryTerm = this.getSearchQuery();
		    YouTube.Search.List search = youtube.search().list("id,snippet");
		    
		    search.setKey(YouTubeAdapter.API_KEY);
		    search.setQ(queryTerm);
		    search.setType("video");

		    // To increase efficiency, only retrieve the fields that the application uses.
		    search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
		    search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

		    // Call the API and print results.
		    SearchListResponse searchResponse = search.execute();
		    List<SearchResult> searchResultList = searchResponse.getItems();
		    if (searchResultList != null) {
		    	FormattedResult formattedResult = this.getFormattedResult(searchResultList.iterator());
		        return formattedResult;
		    }
		    
		} catch (GoogleJsonResponseException e) {
		            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
		                    + e.getDetails().getMessage());
		} catch (IOException e) {
		            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
		} catch (Throwable t) {
		            t.printStackTrace();
		}
		
		return new FormattedResult("", "", "", this.getClass().getSimpleName(), "");
	}
	

	public FormattedResult getFormattedResult(Object rawResults) {
		
		Iterator<SearchResult> iteratorSearchResults = (Iterator<SearchResult> ) rawResults;
		String uri         = "";
		String url         = "";
		String title       = "";
		String provider    = "";
		String referenceId = "";

		while (iteratorSearchResults.hasNext()) {
	        if (!iteratorSearchResults.hasNext()) {
	            System.out.println(" There aren't any results for your query.");
	        }
	
	        SearchResult singleVideo = iteratorSearchResults.next();
	        ResourceId rId = singleVideo.getId();
	     
	        // Confirm that the result represents a video. Otherwise, the item will not contain a video ID.
	        if (rId.getKind().equals("youtube#video")) {	
	        	
	        	url      = "https://www.youtube.com/watch?v=" + rId.getVideoId();
	            title    = singleVideo.getSnippet().getTitle();
	            provider = (String) this.getClass().getSimpleName();
	        }
		}
        FormattedResult formattedResult = new FormattedResult(uri, url, title, provider, referenceId);
		
		return formattedResult;
	}
}



  

    

