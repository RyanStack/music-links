package stackdaddy.music_links.adapters;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Properties;

import stackdaddy.music_links.models.*;
import stackdaddy.music_links.constants.Statics;

public class SpotifyAdapter extends BaseAdapter {
	
	private final String apiFormat = Statics.SPOTIFY_API_FORMAT;
	private SearchDetails searchDetails;
	
	public SpotifyAdapter(SearchDetails searchDetails) {
		this.searchDetails = searchDetails;
	}
	
	public String getSearchQuery() 
	{
		Properties details = getSearchParametersTailSpace(this.searchDetails);
		return String.format(apiFormat, details.getProperty("artist"), details.getProperty("album"), details.getProperty("track"));
		
	}
	
	public FormattedResult performSearch() 
	{
		String searchQuery = this.getSearchQuery();
		String rawResults = getEndPointResponse(searchQuery);
		FormattedResult formattedResult = this.getFormattedResult(rawResults);
		return formattedResult;
	}

	
	public FormattedResult getFormattedResult(Object rawResults) 
	{
		String uri         = "";
		String url         = "";
		String title       = "";
		String provider    = "";
		String referenceId = "";
		
		try {
			JSONParser jsonParser  = new JSONParser();
			
			JSONObject jsonObject  = (JSONObject) jsonParser.parse((String)rawResults);
			JSONObject tracks      = (JSONObject) jsonObject.get("tracks");
			JSONArray items        = (JSONArray)  tracks.get("items");
			JSONObject jsonObject2 = (JSONObject) items.get(0);
			JSONObject jsonObject3 = (JSONObject) jsonObject2.get("external_urls");
			
			uri      = (String) jsonObject2.get("uri");
			url      = (String) jsonObject3.get("spotify");
		    title    = (String) jsonObject2.get("name");
		    provider = (String) this.getClass().getSimpleName();
		    
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FormattedResult formattedResult = new FormattedResult(uri, url, title, provider, referenceId);
		
		return formattedResult;
	}
}