package stackdaddy.music_links.adapters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import stackdaddy.music_links.models.*;

public abstract class BaseAdapter implements InterfaceAdapter {
	
	public String getEndPointResponse(String httpEndPoint)
	{
		
		URL obj;
		try {
			obj = new URL(httpEndPoint);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			obj = null;
			e.printStackTrace();
		}
		HttpURLConnection con;
		try {
			con = (HttpURLConnection) obj.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			con = null;
			e.printStackTrace();
		}

		// optional default is GET
		try {
			con.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int responseCode;
		try {
			responseCode = con.getResponseCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			responseCode = 404;
			e.printStackTrace();
		}

		BufferedReader in;
		try {
			in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			in = null;
			e.printStackTrace();
		}
		String inputLine;
		StringBuffer response = new StringBuffer();

		try {
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.toString();
	}
	
	public Properties getSearchParametersTailSpace(SearchDetails searchDetails) 
	{	
		Properties details = new Properties();
		
		try {
			details.put("track", URLEncoder.encode(searchDetails.getTrack() + " ", "UTF-8"));
			details.put("artist", URLEncoder.encode(searchDetails.getArtist() + " ", "UTF-8"));
			details.put("album", URLEncoder.encode(searchDetails.getAlbum() + " ", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return details;
	}
	
	public Properties getSearchParametersNoTailSpace(SearchDetails searchDetails) 
	{	
		Properties details = new Properties();
		try {
			details.put("track", URLEncoder.encode(searchDetails.getTrack().trim(), "UTF-8"));
			details.put("artist", URLEncoder.encode(searchDetails.getArtist().trim(), "UTF-8"));
			details.put("album", URLEncoder.encode(searchDetails.getAlbum().trim(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return details;
	}
}
