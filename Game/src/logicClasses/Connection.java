package logicClasses;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.*;

public class Connection {
	private List<String> scores = new ArrayList<String>();

	/**
	 * getScores: Opens a HTTP connection to a PHP page which returns the
	 * highscore data, this is then saved in a hashMap
	 */
	public List<String> getScores() {
		try {
			URL address = new URL(
					"http://teamwaw.co.uk/whenPlanesCollide/connection.php");
			HttpURLConnection httpcon = (HttpURLConnection) address
					.openConnection();
			httpcon.addRequestProperty("User-Agent", "WhenPlanesCollide");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					httpcon.getInputStream()));

			// Store returned data to hashtable. The data is in the
			// format
			// name^%:^%score, with each key => value pair being on
			// a newline.
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				scores.add(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			scores.add("ERROR:::Sorry, we couldn't load highscores!");
		} catch (IOException e) {
			e.printStackTrace();
			scores.add("ERROR:::Sorry, we couldn't load highscores!");
		}
		return scores;
	}

	public String getHighestScore() {
		getScores();
		return scores.get(0);
	}

	/**
	 * Get the lowest score in order to know if the score made a high score
	 * table
	 * 
	 * @return
	 */
	public int getLowestScore() {
		getScores();
		String[] parts = scores.get(scores.size() - 1).split(":::");
		if (parts[1].equals("Sorry, we couldn't load highscores!")) {
			/* Return dummy value if error occurred */
			return 999999999;
		}
		return Integer.parseInt(parts[1]);
	}

	/**
	 * Method to send a new score to add to the high score table
	 * 
	 * @param Name
	 *            player name
	 * @param Score
	 *            the score that needs to be stored
	 * @return
	 */
	public Boolean sendNewScore(String Name, int Score) {
		try {
			// Construct data
			String data = URLEncoder.encode("score", "UTF-8") + "="
					+ URLEncoder.encode(Integer.toString(Score), "UTF-8");
			data += "&" + URLEncoder.encode("name", "UTF-8") + "="
					+ URLEncoder.encode(Name, "UTF-8");

			// Send data
			URL url = new URL(
					"http://teamwaw.co.uk/whenPlanesCollide/connection.php");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				if (line == "ACK") {
					return true;
				}
			}
			wr.close();
			rd.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Clear all the data
	 */
	public void clearData() {
		scores.clear();
	}
}
