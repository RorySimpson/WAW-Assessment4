package logicClasses;

import java.util.LinkedHashMap;
import java.io.*;
import java.net.*;

public class Connection {
    private LinkedHashMap<String, String> scoreMap = new LinkedHashMap<String, String>();
    
    public LinkedHashMap<String,String> getScores(){
        try {
    	URL address = new URL("http://teamwaw.co.uk/whenPlanesCollide/connection.php");
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
    	scoreMap.put("ERROR:", "Sorry, we couldn't load highscores!");
        } 
        catch (IOException e) {
    	e.printStackTrace();
    	scoreMap.put("ERROR:", "Sorry, we couldn't load highscores!");
        }
	return scoreMap;
    }
    
    public int getHighestScore(){
	return Integer.parseInt(scoreMap.entrySet().iterator().next().getValue());
    }
    
    public void clearData(){
	scoreMap.clear();
    }
}
