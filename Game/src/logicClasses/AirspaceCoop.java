package logicClasses;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class AirspaceCoop extends Airspace {
	
	private ArrayList<Flight> listOfFlightsPlayer1;
	private ArrayList<Flight> listOfFlightsPlayer2;
	boolean addPlayer1Flight;
	
	public AirspaceCoop() {
		
		super();
		this.setControls(new ControlsCoop(this));
		this.listOfFlightsPlayer1 = new ArrayList<Flight>();
		this.listOfFlightsPlayer2 = new ArrayList<Flight>();
		this.addPlayer1Flight=true;
		
	}
	
	@Override
	public void resetAirspace() {
		
		this.setListOfFlightsInAirspace(new ArrayList<Flight>());
		
		this.setNumberOfGameLoopsSinceLastFlightAdded(0); 
		this.setNumberOfGameLoops(0); 
		this.setNumberOfGameLoopsWhenDifficultyIncreases(3600);
		
		// Prevents user immediately entering game over state upon replay
		this.getSeparationRules().setGameOverViolation(false);
		
		// Prevents information about flight from previous game being displayed
		this.getControls().setSelectedFlight(null);  	
		
	}
	
	public void newCoopFlight(GameContainer gc) throws SlickException {
		this.newFlight(gc);
		Flight addedFlight = this.getListOfFlights().get(this.getListOfFlights().size()-1);
		if(this.addPlayer1Flight) {
			this.addPlayer1Flight=false;
			this.listOfFlightsPlayer1.add(addedFlight);
			
		}
		else {
			this.addPlayer1Flight=true;
			this.listOfFlightsPlayer2.add(addedFlight);
		}
	}
	
}
