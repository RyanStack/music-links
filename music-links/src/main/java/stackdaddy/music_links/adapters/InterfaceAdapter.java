package stackdaddy.music_links.adapters;

import stackdaddy.music_links.models.*;

public interface InterfaceAdapter {
	public String getSearchQuery();
	
	public FormattedResult getFormattedResult(Object rawResults);
	
	public FormattedResult performSearch();
}



