package server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import logicClasses.Flight;

public class Server extends Thread {

	private List<Flight> listOfFlights;
	private long nextFlightTime;
	private int maximumNumberOfFlightsInAirspace;
	
	public Server() {
		
		this.listOfFlights=new ArrayList<Flight>();
		this.nextFlightTime=this.generateNewFlightTime();
		this.maximumNumberOfFlightsInAirspace=5;
		
	}
	
	public void run() { // Runs at the same rate as client due to all the I/O blocking
		int num = 0;
		try {
			ServerSocket server = new ServerSocket(6789);
			
			while(true) {
				//if(System.currentTimeMillis()>this.nextFlightTime) {
				//	this.newFlight();
				//	this.nextFlightTime=this.generateNewFlightTime();
				//	System.out.println("Made new Flight");
				//}
				Socket socket = server.accept();
				DataOutputStream oo = new DataOutputStream(socket.getOutputStream());
				num++;
				oo.writeInt(num);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Flight newFlight() throws SlickException {

		if (this.listOfFlights.size() < this.maximumNumberOfFlightsInAirspace) {

			Flight tempFlight = new Flight();

			tempFlight.setFlightName(this.generateFlightName());
			tempFlight.setTargetAltitude(tempFlight.getAltitude());

			if(addFlight(tempFlight)){
				return tempFlight;
			}

		}
		return null;
	}
	
	public boolean addFlight(Flight flight) {

		// Checks whether the flight was already added before, and if it won't pass the maximum number of flights allowed
		if ((this.listOfFlights.contains(flight))
				&& (this.listOfFlights.size() > this.maximumNumberOfFlightsInAirspace - 1)) {
			return false;
		} else {
			for(Flight a : listOfFlights){

			}
		}
		this.listOfFlights.add(flight);
		return true;
	}
	
	public String generateFlightName() {
		String name = "G-";
		Random rand = new Random();
		for (int i = 0; i < 4; i++) {
			int thisChar = rand.nextInt(10) + 65; // Generates int in range [65, 74]
			name += (char) thisChar; // Generate corresponding ascii character for int 
		}
		return name;
	}
	
	public long generateNewFlightTime() {
		
		Random rand = new Random();
		return System.currentTimeMillis()+rand.nextInt(5000)+8000;
		
	}
	
	public static void main(String[] args) {
		
		Server server = new Server();
		server.start();
		

	}

}
