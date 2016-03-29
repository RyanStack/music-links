package stackdaddy.music_links;

import stackdaddy.music_links.models.SearchDetails;
import stackdaddy.music_links.models.FormattedResult;
import stackdaddy.music_links.adapters.*;

import java.util.concurrent.Callable;


public class APIExecutorServiceCallable implements Callable<FormattedResult> {

	private String provider;
	private SearchDetails searchDetails; 

	public APIExecutorServiceCallable(String provider, SearchDetails searchDetails) {
		this.provider      = provider;
		this.searchDetails = searchDetails;
	}

	public FormattedResult call() {
		InterfaceAdapter adapter = Factory.getProvider(this.provider, this.searchDetails);
		FormattedResult formattedResult = adapter.performSearch();
		return formattedResult;
	}
};

