package server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import logicClasses.Flight;

public class Server extends Thread {

	private List<Flight> listOfFlights;
	
	public Server() {
		
		this.listOfFlights=new ArrayList<Flight>();
		
	}
	
	public void run() {
		int num = 0;
		try {
			ServerSocket server = new ServerSocket(6789);
			
			while(true) {
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
	
	public static void main(String[] args) {
		
		Server server = new Server();
		server.start();
		

	}

}
