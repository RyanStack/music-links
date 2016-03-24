package stackdaddy.music_links.adapters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import stackdaddy.music_links.models.*;
import stackdaddy.music_links.constants.Statics;


public class GooglePlayAdapter extends BaseAdapter {
	
	private final String apiFormat = Statics.GOOGLE_API_FORMAT;
	private SearchDetails searchDetails;
	
	public GooglePlayAdapter(SearchDetails searchDetails) {
		this.searchDetails = searchDetails;
	}

	public String getSearchQuery() 
	{
		Properties details = getSearchParametersTailSpace(this.searchDetails);
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
//			System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse((String)rawResults)));

			JSONParser jsonParser  = new JSONParser();
			
			JSONObject jsonObject  = (JSONObject) jsonParser.parse((String)rawResults);
			JSONObject responseData      = (JSONObject) jsonObject.get("responseData");
			JSONArray results        = (JSONArray)  responseData.get("results");
			JSONObject result = (JSONObject) results.get(0);
		
			
			url      = (String) result.get("unescapedUrl");
		    title    = (String) result.get("titleNoFormatting");
		    provider = (String) this.getClass().getSimpleName();
		    
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		FormattedResult formattedResult = new FormattedResult(uri, url, title, provider, referenceId);
		
		return formattedResult;
	}

}
