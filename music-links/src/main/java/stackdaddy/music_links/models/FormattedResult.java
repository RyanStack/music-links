package stackdaddy.music_links.models;

public class FormattedResult {
	public String uri;
	public String url;
	public String title;
	public String provider;
	public String referenceId;
	
	public FormattedResult(String uri, String url, String title, String provider, String referenceId) {
		this.uri         = uri;
		this.url         = url;
		this.title       = title;
		this.provider    = provider;
		this.referenceId = referenceId;
	}
	
	public String getURI() {
		return this.uri;
	}
	
	public void setURI(String uri) {
		this.uri = uri;
	}
	
	public String getURL() {
		return this.url;
	}
	
	public void setURL(String url) {
		this.uri = url;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getProvider() {
		return this.provider;
	}
	
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public String getReferenceId() {
		return this.referenceId;
	}
	
	public void setrRferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
}

	



