package stackdaddy.music_links.utils;

public class Config {
	private int port;
	public Config() {
		
	}
	public int getPort() {
		return this.port;
	}
	
	public Config setPort(String port) {
		this.port = Integer.parseInt(port);
		return this;
	}

}
