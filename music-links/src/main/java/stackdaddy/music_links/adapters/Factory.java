package stackdaddy.music_links.adapters;

import stackdaddy.music_links.models.SearchDetails;

public class Factory {

//	"AmazonAdapter" temporarily taken out
	public static final String[] providers = { "YouTubeAdapter", "Spotify", "iTunesAdapter", "GooglePlayAdapter"};
	
	public static InterfaceAdapter getProvider(String provider, SearchDetails searchDetails){
	   
	      if(provider.equalsIgnoreCase("Spotify")){
	         return new SpotifyAdapter(searchDetails);
	         
	      } else if(provider.equalsIgnoreCase("YouTubeAdapter")){
	         
	    	  return new YouTubeAdapter(searchDetails);
	      } else if(provider.equalsIgnoreCase("iTunesAdapter")){
	    	  
	         return new iTunesAdapter(searchDetails);
	      } else if(provider.equalsIgnoreCase("GooglePlayAdapter")){
	    	  
		     return new GooglePlayAdapter(searchDetails);
		  } else if(provider.equalsIgnoreCase("AmazonAdapter")){
	    	  
		     return new AmazonAdapter(searchDetails);
		  }
	      
	      return null;
	}

}