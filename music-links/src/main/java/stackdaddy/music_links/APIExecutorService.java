package stackdaddy.music_links;

import stackdaddy.music_links.models.SearchDetails;
import stackdaddy.music_links.adapters.Factory;
import stackdaddy.music_links.models.FormattedResult;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.*;

//Research Execution exception
public class APIExecutorService {
	
	//This can probably be fetched from somewhere else
	//Simplify try catch block
	private String[] providers = Factory.providers; 
	
	public List<Future<FormattedResult>> runQueries(SearchDetails searchDetails) {
		//Investigate what is the optimal amount of threads based on # of providers and server qualities && whether or not this will
		//take away from the worker thread pool
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		Set<Callable<FormattedResult>> callables = new HashSet<>();
		
		for(String provider : providers) {
			callables.add(new APIExecutorServiceCallable(provider, searchDetails));
		}


		List<Future<FormattedResult>> futures = null;
		try {
			//Have more sophisticated error catching/data checking
			futures = executorService.invokeAll(callables);
			return futures;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return futures;
		} finally {
			executorService.shutdown();
		}
	}
}