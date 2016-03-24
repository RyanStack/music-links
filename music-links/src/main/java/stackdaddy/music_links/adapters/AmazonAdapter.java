package stackdaddy.music_links.adapters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import stackdaddy.music_links.models.*;
import stackdaddy.music_links.constants.Statics;
import stackdaddy.music_links.helpers.SignedRequestsHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AmazonAdapter extends BaseAdapter {

	private SearchDetails searchDetails;
	
	public AmazonAdapter(SearchDetails searchDetails) {
		this.searchDetails = searchDetails;
	}
	
	public String getSearchQuery() {
		String access_key = Statics.ACCESS_KEY;
		String secret_key = Statics.SECRET_KEY;
		
		Properties details = getSearchParametersNoTailSpace(this.searchDetails);
        
        SignedRequestsHelper helper;
		try {
			helper = SignedRequestsHelper.getInstance(Statics.HOST, access_key, secret_key);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

        Map<String, String> params = new HashMap<String, String>();
        
        params.put("Service", Statics.SERVICE);
        params.put("AssociateTag", Statics.ASSOCIATE_TAG);
        params.put("Operation", Statics.OPERATION);
        params.put("SearchIndex", Statics.SEARCH_INDEX);
        params.put("Author", details.getProperty("artist"));
        params.put("Title", details.getProperty("track"));
        params.put("TotalResults", Statics.TOTAL_RESULTS);

        String url = helper.sign(params);
        
        return url;
	}
	
	public FormattedResult performSearch() {
		
		String searchQuery = this.getSearchQuery();
		
        try {
            Document response = getResponse(searchQuery);
            FormattedResult formattedResult = this.getFormattedResult(response);
   
    		return formattedResult;
        } catch (Exception ex) {
            return null;
        }
	}

	public FormattedResult getFormattedResult(Object rawResults) 
	{
		String uri         = "";
		String url         = "";
		String title       = "";
		String provider    = "";
		String referenceId = "";
		
		Document doc   = (Document) rawResults;
		NodeList nList = doc.getElementsByTagName("Item");
    	Node nNode     = nList.item(0);
    						
    	if (nNode.getNodeType() == Node.ELEMENT_NODE)  {

    		Element eElement = (Element) nNode;
    		
    		url         = eElement.getElementsByTagName("DetailPageURL").item(0).getTextContent();
    		referenceId = eElement.getElementsByTagName("ASIN").item(0).getTextContent();
    		provider    = (String) this.getClass().getSimpleName();
    	}
		
		FormattedResult formattedResult = new FormattedResult(uri, url, title, provider, referenceId);
		
		return formattedResult;
	}
	
	private static Document getResponse(String url) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(url);
        return doc;
    }
}
