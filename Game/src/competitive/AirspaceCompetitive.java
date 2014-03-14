package competitive;

import java.util.ArrayList;

import coop.AirportCoop;
import coop.ControlsCoop;
import coop.FlightCoop;
import logicClasses.Airspace;

public class AirspaceCompetitive extends Airspace {

	private ArrayList<FlightCoop> listOfFlightsPlayer1;
	private ArrayList<FlightCoop> listOfFlightsPlayer2;
	boolean addPlayer1Flight;
	
	public AirspaceCompetitive() {
		
		super();
		//this.setControls(new ControlsCompetitive(this));
		this.listOfFlightsPlayer1 = new ArrayList<FlightCoop>();
		this.listOfFlightsPlayer2 = new ArrayList<FlightCoop>();
		this.addPlayer1Flight=false;
		//this.setAirportLeft(new AirportCoop(2, this));
		//this.setAirportRight(new AirportCoop(1, this));
	}
}
