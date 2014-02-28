package logicClasses;

import java.util.HashMap;
import java.io.*;
import java.net.*;

public class Connection {
    private HashMap<String, String> scoreMap = new HashMap<String, String>();
    
    public HashMap<String,String> getScores(){
        try {
    	URL address = new URL("http://teamwaw.co.uk/whenPlanesCollide/connection.php");
    	//URLConnection yc = oracle.openConnection();        Server 403s's when using URL connection so we are now using http
    	HttpURLConnection httpcon = (HttpURLConnection) address.openConnection(); 
    	httpcon.addRequestProperty("User-Agent", "WhenPlanesCollide"); 
    	BufferedReader in = new BufferedReader(new InputStreamReader(
    	httpcon.getInputStream()));
            
    	String inputLine;
    	while ((inputLine = in.readLine()) != null){
    	    String[] parts = inputLine.split(":");
    	    scoreMap.put(parts[0], parts[1]);
    	}
    	in.close();
        } 
        catch (MalformedURLException e) {
    	e.printStackTrace();
        } 
        catch (IOException e) {
    	e.printStackTrace();
        }
	return scoreMap;
    }
}
