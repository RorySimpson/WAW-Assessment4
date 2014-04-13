package competitive;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import coop.AirportCoop;
import coop.ControlsCoop;
import coop.FlightCoop;
import events.EventController;
import logicClasses.Airspace;
import logicClasses.EntryPoint;
import logicClasses.ExitPoint;
import logicClasses.Flight;
import logicClasses.ScoreTracking;
import logicClasses.SeparationRules;
import logicClasses.Waypoint;

public class AirspaceCompetitive extends Airspace {

	private ArrayList<FlightCompetitive> listOfFlightsPlayer1;
	private ArrayList<FlightCompetitive> listOfFlightsPlayer2;
	boolean addPlayer1FlightNext;
	private int	numberOfGameLoopsSinceLastPlayer1FlightAdded, numberOfGameLoopsSinceLastPlayer2FlightAdded;
	private CargoCompetitive cargo;
	protected SeparationRulesCompetitive 	separationRules;
	

	public AirspaceCompetitive() {

		super();
		this.setControls(new ControlsCompetitive(this));
		this.listOfFlightsPlayer1 = new ArrayList<FlightCompetitive>();
		this.listOfFlightsPlayer2 = new ArrayList<FlightCompetitive>();
		this.addPlayer1FlightNext=false;
		this.airportLeft = null;
		this.eventController = null;
		this.setAirportRight(new AirportCompetitive(1, this));
		numberOfGameLoopsSinceLastPlayer1FlightAdded = 0;
		numberOfGameLoopsSinceLastPlayer2FlightAdded = 0;
		cargo = new CargoCompetitive();
		
	}
	
	@Override
	public void createAndSetSeparationRules(){
		this.separationRules = new SeparationRulesCompetitive(difficultyValueOfGame);
	
	}
	
	// override reset to reset the two selected flights to null
	@Override
	public void resetAirspace() {
		
		this.setListOfFlightsInAirspace(new ArrayList<Flight>());
		this.listOfFlightsPlayer1 = new ArrayList<FlightCompetitive>();
		this.listOfFlightsPlayer2 = new ArrayList<FlightCompetitive>();
		
		this.setNumberOfGameLoopsSinceLastFlightAdded(0); 
		this.setNumberOfGameLoops(0); 
		this.setNumberOfGameLoopsWhenDifficultyIncreases(3600);
		
		numberOfGameLoopsSinceLastPlayer1FlightAdded = 0;
		numberOfGameLoopsSinceLastPlayer2FlightAdded = 0;
		
		// Prevents user immediately entering game over state upon replay
		this.getSeparationRules().setGameOverViolation(false);
		
		// Prevents information about flight from previous game being displayed
		//this.getControls().setSelectedFlight(null);  	
		((ControlsCompetitive) this.getControls()).setSelectedFlight1(null);
		((ControlsCompetitive) this.getControls()).setSelectedFlight2(null);
		
		cargo = new CargoCompetitive();
		this.createAndSetSeparationRules();
		
		
	}
	
	
	public void newCompetitiveFlight(GameContainer gc) throws SlickException {
		if(this.newFlight(gc)){
			FlightCompetitive addedFlight = (FlightCompetitive) this.getListOfFlightsInAirspace().get(this.getListOfFlightsInAirspace().size()-1);
			if(this.addPlayer1FlightNext) {
				this.addPlayer1FlightNext = false;
				addedFlight.setPlayer2(false);
				this.listOfFlightsPlayer1.add(addedFlight);
				numberOfGameLoopsSinceLastPlayer1FlightAdded = 0;
				addedFlight.getFlightPlan().assignEntryPoint(this);
				
				System.out.println("Added a flight to player 1");
			
			}
			else {
				addedFlight.setPlayer2(true);
				this.listOfFlightsPlayer2.add(addedFlight);
				this.addPlayer1FlightNext = true;
				numberOfGameLoopsSinceLastPlayer2FlightAdded = 0;
				addedFlight.getFlightPlan().assignEntryPoint(this);
			}
			
			
		}
	}
	
	//a new method for creating flights that calls the old one, but also adds
		//the flight to either the player 1 or 2 list
		@Override
		public boolean newFlight(GameContainer gc) throws SlickException {

			if (this.getListOfFlightsInAirspace().size() < this.getMaximumNumberOfFlightsInAirspace()) {
				
				if(this.addPlayer1FlightNext){
					this.numberOfGameLoopsSinceLastFlightAdded = numberOfGameLoopsSinceLastPlayer1FlightAdded;
				}
				
				else{
					this.numberOfGameLoopsSinceLastFlightAdded = numberOfGameLoopsSinceLastPlayer2FlightAdded;
				}

				// Minimum wait for 5 seconds 
				if ((this.getNumberOfGameLoopsSinceLastFlightAdded() >= (60*5)  || this.getListOfFlightsInAirspace().isEmpty())) {


					Flight tempFlight = new FlightCompetitive(this, true);

					tempFlight.setFlightName(this.generateFlightName());
					tempFlight.setTargetAltitude(tempFlight.getAltitude());

					double heading;


					heading = 90;

					tempFlight.setTargetHeading(heading);
					tempFlight.setCurrentHeading(heading);
					if(addFlight(tempFlight)){
						this.getListOfFlightsInAirspace().get(
								this.getListOfFlightsInAirspace().size() -1).init(gc);
						return true;
					}

				}
			}

			return false;
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
		for (int i = 0; i < this.listOfEntryPoints.size(); i++) { // Initialising entry point
			this.listOfEntryPoints.get(i).init(gc);
		}

		
	}
	
	/**
	 * update: Update all logic in the airspace class
	 * @param gc GameContainer
	 */
	
	public void update(GameContainer gc) throws SlickException {
		
		
		this.numberOfGameLoopsSinceLastPlayer1FlightAdded++;
		this.numberOfGameLoopsSinceLastPlayer2FlightAdded++;
		this.numberOfGameLoops++;

		
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
				
				if(cargo.getCurrentHolder() == this.getListOfFlights().get(i)){
					cargo.setCurrentHolder(null);
					cargo.setLocation(cargo.generateRandomCargoLocation());
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
	
		
		cargo.render(g, gc);
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

	public CargoCompetitive getCargo() {
		return cargo;
	}

	public void setCargo(CargoCompetitive cargo) {
		this.cargo = cargo;
	}
	
	public SeparationRulesCompetitive getSeparationRules(){
		return this.separationRules;
	}
}





