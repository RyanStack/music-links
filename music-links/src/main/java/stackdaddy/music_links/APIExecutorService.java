package stackdaddy.music_links;

import stackdaddy.music_links.models.SearchDetails;
import stackdaddy.music_links.adapters.Factory;

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
	
	public List<Future<String>> runQueries(SearchDetails searchDetails) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Set<Callable<String>> callables = new HashSet<Callable<String>>();
		
		for(String provider : providers) {
			callables.add(new APIExecutorServiceCallable(provider, searchDetails));
		}


		List<Future<String>> futures = null;
		try {
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