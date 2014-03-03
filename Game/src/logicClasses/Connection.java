package logicClasses;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.io.*;
import java.net.*;

public class Connection {
	private LinkedHashMap<String, String> scoreMap = new LinkedHashMap<String, String>();

	/**
	 * getScores: Opens a http connection to a php page which returns the
	 * highscore data, this is then saved in a hashMap
	 */
	public LinkedHashMap<String, String> getScores() {
		try {
			URL address = new URL("http://teamwaw.co.uk/whenPlanesCollide/connection.php");
			HttpURLConnection httpcon = (HttpURLConnection) address.openConnection();
			httpcon.addRequestProperty("User-Agent", "WhenPlanesCollide");
			BufferedReader in = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));

			// Store returned data to hashtable. The data is in the format
			// name:score, with each key => value pair being on a newline.
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				String[] parts = inputLine.split(":");
				scoreMap.put(parts[0], parts[1]);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			scoreMap.put("ERROR:", "Sorry, we couldn't load highscores!");
		} catch (IOException e) {
			e.printStackTrace();
			scoreMap.put("ERROR:", "Sorry, we couldn't load highscores!");
		}
		return scoreMap;
	}

	public int getHighestScore() {
		getScores();
		return Integer.parseInt(scoreMap.entrySet().iterator().next().getValue());
	}
	
	public int getLowestScore(){
		getScores();
		String last = new LinkedList<String>(scoreMap.values()).getLast();
		return Integer.parseInt(last);
	}
	
	public void sendNewScore(String Name, int Score){
	    try {
		// Construct data
		String data = URLEncoder.encode("score", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(Score), "UTF-8");
		data += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");

		// Send data
		URL url = new URL("http://teamwaw.co.uk/whenPlanesCollide/connection.php");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data);
		wr.flush();

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
		    System.out.println(line);
		}
		wr.close();
		rd.close();
	    }
	    catch (MalformedURLException e) {
		e.printStackTrace();
	    } 
	    catch (IOException e) {
		e.printStackTrace();
	    }
	}

	public void clearData() {
		scoreMap.clear();
	}
}
