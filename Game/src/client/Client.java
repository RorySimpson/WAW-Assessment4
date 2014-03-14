package client;
import java.net.*;
import java.util.ArrayList;
import java.io.*;

import logicClasses.Flight;
public class Client extends Thread {
	
	private boolean newFlight;
	private Flight flight;
	public int num;

	public void run() {
		long lastTime = System.currentTimeMillis();
		long currentTime = System.currentTimeMillis();
		float connectionsPerSecond = 5;
		float secondsPerLoop = 1000/connectionsPerSecond;
		try {
			while(true) {
				
				currentTime=System.currentTimeMillis();
				if(currentTime-lastTime>secondsPerLoop) {
					Socket client = new Socket("localhost",6789);
					DataInputStream is = new DataInputStream(client.getInputStream());
					this.num = is.readInt();
					//System.out.println(num);
					lastTime = currentTime;
				}
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
