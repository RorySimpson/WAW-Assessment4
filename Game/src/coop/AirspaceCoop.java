package coop;

import java.util.ArrayList;

import logicClasses.Airspace;
import logicClasses.Flight;

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
	
	
	// override reset to reset the two selected flights to null
	@Override
	public void resetAirspace() {
		
		this.setListOfFlightsInAirspace(new ArrayList<Flight>());
		this.listOfFlightsPlayer1 = new ArrayList<Flight>();
		this.listOfFlightsPlayer2 = new ArrayList<Flight>();
		
		this.setNumberOfGameLoopsSinceLastFlightAdded(0); 
		this.setNumberOfGameLoops(0); 
		this.setNumberOfGameLoopsWhenDifficultyIncreases(3600);
		
		// Prevents user immediately entering game over state upon replay
		this.getSeparationRules().setGameOverViolation(false);
		
		// Prevents information about flight from previous game being displayed
		//this.getControls().setSelectedFlight(null);  	
		((ControlsCoop) this.getControls()).setSelectedFlight1(null);
		((ControlsCoop) this.getControls()).setSelectedFlight2(null);
		
		
	}
	
	//a new method for creating flights that calls the old one, but also adds
	//the flight to either the player 1 or 2 list
	public void newCoopFlight(GameContainer gc) throws SlickException {
		if(this.newFlight(gc)){
			Flight addedFlight = this.getListOfFlightsInAirspace().get(this.getListOfFlightsInAirspace().size()-1);
			if(this.addPlayer1Flight) {
				this.addPlayer1Flight=false;
				this.listOfFlightsPlayer1.add(addedFlight);
				System.out.println("Added a flight to player 1");
			
			}
			else {
				this.addPlayer1Flight=true;
				this.listOfFlightsPlayer2.add(addedFlight);
			}
		}
	}
	// override the method for removing flights to include the two selected flight lists
	@Override
	public void removeSpecificFlight(int flight) {
		if(this.listOfFlightsPlayer1.contains(this.getListOfFlightsInAirspace().get(flight))) {
			this.listOfFlightsPlayer1.remove(this.getListOfFlightsInAirspace().get(flight));
		}
		else if(this.listOfFlightsPlayer2.contains(this.getListOfFlightsInAirspace().get(flight))) {
			this.listOfFlightsPlayer2.remove(this.getListOfFlightsInAirspace().get(flight));
		}
		this.getListOfFlightsInAirspace().remove(flight);
		
		Flight selected1 = ((ControlsCoop) this.getControls()).getSelectedFlight1();
		Flight selected2 = ((ControlsCoop) this.getControls()).getSelectedFlight2();
		// If flight was selected, de-select it
		if (!(this.getListOfFlightsInAirspace().contains(selected1))) {
			((ControlsCoop) this.getControls()).setSelectedFlight1(null);

		}
		if (!(this.getListOfFlightsInAirspace().contains(selected2))) {
			((ControlsCoop) this.getControls()).setSelectedFlight2(null);

		}
	}
	//Don't think this needs to be changed, but leaving it just in case
	/*@Override
	public void update(GameContainer gc) {
		
		this.setNumberOfGameLoopsSinceLastFlightAdded(this.getNumberOfGameLoopsSinceLastFlightAdded()+1);
		this.setNumberOfGameLoops(this.getNumberOfGameLoops()+1);
		if (this.getNumberOfGameLoops() >= this.getNumberOfGameLoopsWhenDifficultyIncreases()) {
			this.increaseDifficulty();
	
		}
		
		for (int i = 0; i < this.getListOfFlightsInAirspace().size(); i++) {
			this.getListOfFlightsInAirspace().get(i).update(this.getScore());
			if(this.getListOfFlightsInAirspace().get(i).getFlightPlan().getCurrentRoute().size()==0) {
				this.removeSpecificFlight(i);
			}
			else if (this.checkIfFlightHasLeftAirspace(this.getListOfFlights().get(i))) {
				this.getScore().reduceScoreOnFlightLost();
				this.getScore(). reduceMultiplierOnFlightLost();
				this.removeSpecificFlight(i);
			}
			
		}
		
		this.getSeparationRules().update(this);
		this.getControls().update(gc, this);
	}*/

	public ArrayList<Flight> getListOfFlightsPlayer1() {
		return listOfFlightsPlayer1;
	}

	public void setListOfFlightsPlayer1(ArrayList<Flight> listOfFlightsPlayer1) {
		this.listOfFlightsPlayer1 = listOfFlightsPlayer1;
	}

	public ArrayList<Flight> getListOfFlightsPlayer2() {
		return listOfFlightsPlayer2;
	}

	public void setListOfFlightsPlayer2(ArrayList<Flight> listOfFlightsPlayer2) {
		this.listOfFlightsPlayer2 = listOfFlightsPlayer2;
	}
	
}
