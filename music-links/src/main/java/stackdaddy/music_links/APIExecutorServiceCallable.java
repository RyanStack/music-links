package stackdaddy.music_links;

import stackdaddy.music_links.models.SearchDetails;
import stackdaddy.music_links.models.FormattedResult;
import stackdaddy.music_links.adapters.*;

import java.util.concurrent.Callable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class APIExecutorServiceCallable implements Callable<String> {

	private String provider;
	private SearchDetails searchDetails; 

	public APIExecutorServiceCallable(String provider, SearchDetails searchDetails) {
		this.provider      = provider;
		this.searchDetails = searchDetails;
	}

	public String call() {
		
		InterfaceAdapter adapter   = Factory.getProvider(this.provider, this.searchDetails);

		//Real Code
		FormattedResult formattedResult = adapter.performSearch();
		
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(formattedResult);
		return json;
		
		//For viewing the pretty print while writing the adapters
//		return new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(willBePassedtoAdapter));
		
	}

};

