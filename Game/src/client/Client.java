package client;
import java.net.*;
import java.util.ArrayList;
import java.io.*;
public class Client extends Thread {
	
	public void run() {
		
		try {
				Socket client = new Socket("localhost",6789);
				ObjectInputStream is = new ObjectInputStream(client.getInputStream());
				ArrayList<Integer> ints = (ArrayList<Integer>) is.readObject();
				for(int i : ints) {
					System.out.println(i);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
