package stackdaddy.music_links.adapters;

import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import stackdaddy.music_links.models.*;
import stackdaddy.music_links.constants.Statics;
public class iTunesAdapter extends BaseAdapter {
	
	private final String apiFormat = Statics.ITUNES_API_FORMAT;
	private SearchDetails searchDetails;
	
	public iTunesAdapter(SearchDetails searchDetails) {
		this.searchDetails = searchDetails;
	}

	public String getSearchQuery() 
	{

		Properties details = getSearchParametersNoTailSpace(this.searchDetails);
		return String.format(apiFormat, details.getProperty("track"), details.getProperty("artist"), details.getProperty("album"));
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
			JSONArray results      = (JSONArray) jsonObject.get("results");
			JSONObject jsonObject1 = (JSONObject) results.get(0);
			
			url      = (String) jsonObject1.get("trackViewUrl");
		    title    = (String) jsonObject1.get("trackName");
		    provider = (String) this.getClass().getSimpleName();
		    
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FormattedResult formattedResult = new FormattedResult(uri, url, title, provider, referenceId);
		
		return formattedResult;
	}

}
