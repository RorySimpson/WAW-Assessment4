package competitive;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import coop.AirportCoop;
import coop.ControlsCoop;
import coop.FlightCoop;
import logicClasses.Airspace;
import logicClasses.EntryPoint;
import logicClasses.ExitPoint;
import logicClasses.Flight;
import logicClasses.ScoreTracking;
import logicClasses.Waypoint;

public class AirspaceCompetitive extends Airspace {

	private ArrayList<FlightCompetitive> listOfFlightsPlayer1;
	private ArrayList<FlightCompetitive> listOfFlightsPlayer2;
	boolean addPlayer1Flight;

	public AirspaceCompetitive() {

		super();
		this.setControls(new ControlsCompetitive(this));
		this.listOfFlightsPlayer1 = new ArrayList<FlightCompetitive>();
		this.listOfFlightsPlayer2 = new ArrayList<FlightCompetitive>();
		this.addPlayer1Flight=false;
		this.airportLeft = null;
		this.eventController = null;
		this.setAirportRight(new AirportCompetitive(1, this));
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
		
		Flight selected1 = ((ControlsCompetitive) this.getControls()).getSelectedFlight1();
		Flight selected2 = ((ControlsCompetitive) this.getControls()).getSelectedFlight2();
		// If flight was selected, de-select it
		if (!(this.getListOfFlightsInAirspace().contains(selected1))) {
			((ControlsCompetitive) this.getControls()).setSelectedFlight1(null);

		}
		if (!(this.getListOfFlightsInAirspace().contains(selected2))) {
			((ControlsCompetitive) this.getControls()).setSelectedFlight2(null);

		}
	}
	
	
	/**
	 * init: Initialises all the resources required for the airspace class, and any other classes that are rendered within it
	 * @param gc GameContainer
	 * @throws SlickException
	 */
	
	public void init(GameContainer gc) throws SlickException {
		
		this.controls.init(gc);
		this.airportRight.init(gc);
		this.eventController.init(gc);
		for (int i = 0; i < this.listOfEntryPoints.size(); i++) { // Initialising entry point
			this.listOfEntryPoints.get(i).init(gc);
		}

		
	}
	
	/**
	 * update: Update all logic in the airspace class
	 * @param gc GameContainer
	 */
	
	public void update(GameContainer gc) throws SlickException {
		
		this.numberOfGameLoopsSinceLastFlightAdded++;
		this.numberOfGameLoops++;
		if (this.numberOfGameLoops >= this.numberOfGameLoopsWhenDifficultyIncreases) {
			this.increaseDifficulty();
	
		}
		
		for (int i = 0; i < this.listOfFlightsInAirspace.size(); i++) {
			this.listOfFlightsInAirspace.get(i).update(score);
			if(this.listOfFlightsInAirspace.get(i).getFlightPlan().getCurrentRoute().size()==0) {
				this.removeSpecificFlight(i);
			}
			else if (this.checkIfFlightHasLeftAirspace(this.getListOfFlights().get(i))) {
				if(this.getListOfFlights().get(i).isControllable()){
					score.reduceScoreOnFlightLost();
					score.reduceMultiplierOnFlightLost();
					
				}
				this.removeSpecificFlight(i);
			}
			
		}
		
		this.separationRules.update(this);
		this.controls.update(gc, this);
	}
	

	/**
	 * render: Render all of the graphics in the airspace
	 * @param g Graphics
	 * @param gc GameContainer
	 * 
	 * @throws SlickException
	 */
	public void render(Graphics g, GameContainer gc) throws SlickException { 
		
		
		this.airportRight.render(g, gc);
		
		for (EntryPoint e:listOfEntryPoints) { // Draws entry points
			e.render(g);
		}

		for (Flight f:listOfFlightsInAirspace) { // Draws flights in airspace
			f.render(g, gc);
		}
		
		separationRules.render(g, gc, this);
		controls.render(gc,g);
	}

	
	
	public ArrayList<FlightCompetitive> getListOfFlightsPlayer1() {
		return listOfFlightsPlayer1;
	}

	public void setListOfFlightsPlayer1(ArrayList<FlightCompetitive> listOfFlightsPlayer1) {
		this.listOfFlightsPlayer1 = listOfFlightsPlayer1;
	}

	public ArrayList<FlightCompetitive> getListOfFlightsPlayer2() {
		return listOfFlightsPlayer2;
	}

	public void setListOfFlightsPlayer2(ArrayList<FlightCompetitive> listOfFlightsPlayer2) {
		this.listOfFlightsPlayer2 = listOfFlightsPlayer2;

	}
}





