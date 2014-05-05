package coop;

import java.util.ArrayList;
import java.util.Random;

import logicClasses.Airspace;
import logicClasses.EntryPoint;
import logicClasses.ExitPoint;
import logicClasses.Flight;
import logicClasses.Waypoint;
import coop.FlightCoop;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import events.EventController;


public class AirspaceCoop extends Airspace {
	
	private ArrayList<FlightCoop> listOfFlightsPlayer1;
	private ArrayList<FlightCoop> listOfFlightsPlayer2;
	boolean addPlayer1Flight;
	private ControlsCoop controls;
	
	public AirspaceCoop() {
		
		super();
		this.controls = new ControlsCoop(this);
		this.setControls(this.controls);
		this.listOfFlightsPlayer1 = new ArrayList<FlightCoop>();
		this.listOfFlightsPlayer2 = new ArrayList<FlightCoop>();
		this.addPlayer1Flight=false;
		this.setAirportLeft(new AirportCoop(2, this));
		this.setAirportRight(new AirportCoop(1, this));
	}
	
	
	// override reset to reset the two selected flights to null
	@Override
	public void resetAirspace() {
		
		this.setListOfFlightsInAirspace(new ArrayList<Flight>());
		this.listOfFlightsPlayer1 = new ArrayList<FlightCoop>();
		this.listOfFlightsPlayer2 = new ArrayList<FlightCoop>();
		this.eventController = new EventController(this);
		
		this.setNumberOfGameLoopsSinceLastFlightAdded(0); 
		this.setNumberOfGameLoops(0); 
		this.setNumberOfGameLoopsWhenDifficultyIncreases(3600);
		
		// Prevents user immediately entering game over state upon replay
		this.getSeparationRules().setGameOverViolation(false);
		
		// Prevents information about flight from previous game being displayed
		//this.getControls().setSelectedFlight(null);  	
		this.controls.setSelectedFlight1(null);
		this.controls.setSelectedFlight2(null);
		
		
	}
	
	//a new method for creating flights that calls the old one, but also adds
	//the flight to either the player 1 or 2 list
	@Override
	public boolean newFlight(GameContainer gc) throws SlickException {

		if (this.getListOfFlightsInAirspace().size() < this.getMaximumNumberOfFlightsInAirspace()) {
			
			if ((this.getNumberOfGameLoopsSinceLastFlightAdded() >= 850  || this.getListOfFlightsInAirspace().isEmpty())) {
				
				Random rand = new Random();
				int checkNumber;
					
				if (this.getListOfFlightsInAirspace().isEmpty())
				{
					// A random number (checkNumber) is generated in the range [0, 100)
						checkNumber = rand.nextInt(100);  
				} 
					
				else
				{
					// A random number (checkNumber) is generated in range [0, randomNumberForFlightGeneration)
					checkNumber = rand.nextInt(this.getRandomNumberForFlightGeneration()); 
				}
				
				/* 
				 * The random number is generated in the range [0, 100) if the airspace is empty, as this increases 
				 * the likelihood of a value of 1 being returned, and therefore a flight being generated; this stops the user
				 * having to potentially wait a long period of time for a flight to be generated. 
				 * If the airspace is not empty, the random number generated is in the range [0, randomNumberForFlight Generation)
				 * which is > 100. This decreases the likelihood of a flight being generated.
				 */
		
				if (checkNumber == 1) {
			
					Flight tempFlight = new FlightCoop(this);
					
					tempFlight.setFlightName(this.generateFlightName());
					tempFlight.setTargetAltitude(tempFlight.getAltitude());
					
					double heading;
					
					
					if (tempFlight.getFlightPlan().getEntryPoint() == this.getAirportLeft().getEndOfRunway())
					{
						heading = this.getAirportLeft().getRunwayHeading();
					}
					
					else if(tempFlight.getFlightPlan().getEntryPoint() == this.getAirportRight().getEndOfRunway()){
						
						heading = this.getAirportRight().getRunwayHeading();
					}
					else
					{
						heading = tempFlight.calculateHeadingToNextWaypoint(
										tempFlight.getFlightPlan().getPointByIndex(0).getX() ,
										tempFlight.getFlightPlan().getPointByIndex(0).getY());
					}
					
					tempFlight.setTargetHeading(heading);
					tempFlight.setCurrentHeading(heading);
					if(addFlight(tempFlight)){
						this.setNumberOfGameLoopsSinceLastFlightAdded(0);
						this.getListOfFlightsInAirspace().get(
								this.getListOfFlightsInAirspace().size() -1).init(gc);
						return true;
					}
					
				}
			}
		}
		return false;
	}
	public void newCoopFlight(GameContainer gc) throws SlickException {
		if(this.newFlight(gc)){
			FlightCoop addedFlight = (FlightCoop) this.getListOfFlightsInAirspace().get(this.getListOfFlightsInAirspace().size()-1);
			if(this.addPlayer1Flight) {
				this.addPlayer1Flight=false;
				addedFlight.setPlayer2(false);
				this.listOfFlightsPlayer1.add(addedFlight);
				System.out.println("Added a flight to player 1");
			
			}
			else {
				addedFlight.setPlayer2(true);
				this.listOfFlightsPlayer2.add(addedFlight);
				this.addPlayer1Flight=true;
			}
		}
	}
	
	@ Override
	/**
	 * newWaypoint: Add a new waypoint to the list of waypoints in the airspace
	 * @param x The x coordinate of the waypoint
	 * @param y The y coordinate of the waypoint
	 * @param name The name used to reference the waypoint
	 */

	public boolean newWaypoint(int x, int y, String name)  {
		if (withinAirspace(x, y))
		{ 
			// x and y must be within these bounds to be within screen space

			Waypoint tmpWp = new WaypointCoop(x, y, name);

			if (this.addWaypoint(tmpWp)) {
				return true;
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
		
		Flight selected1 = this.controls.getSelectedFlight1();
		Flight selected2 = this.controls.getSelectedFlight2();
		// If flight was selected, de-select it
		if (!(this.getListOfFlightsInAirspace().contains(selected1))) {
			this.controls.setSelectedFlight1(null);

		}
		if (!(this.getListOfFlightsInAirspace().contains(selected2))) {
			this.controls.setSelectedFlight2(null);

		}
	}
	
	/**
	 * update: Update all logic in the airspace class
	 * @param gc GameContainer
	 */
	@Override
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
				score.procCompleteFlightAchieve();
			}
			else if (this.checkIfFlightHasLeftAirspace(this.getListOfFlights().get(i))) {
				if(this.getListOfFlights().get(i).isControllable()){
					score.reduceScoreOnFlightLost();
					score.reduceMultiplierOnFlightLost();
					this.flightLostTimer = 0;
					
				}
				this.removeSpecificFlight(i);
			}
			
		}
		
		//tracking for minutes without flight lost achievement
		flightLostTimer += 1;
		if (flightLostTimer >= 3000){
			score.procminsWithoutPlaneLossAchievement();
		}
		
		this.eventController.update(gc);
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
	@Override
	public void render(Graphics g, GameContainer gc) throws SlickException { 
		
		
		this.airportLeft.render(g, gc);
		this.airportRight.render(g, gc);
		

		for (Waypoint w:listOfWaypoints) { // Draws waypoints
			w.render(g, this);
		}
		for (ExitPoint e:listOfExitPoints) { // Draws exit points
			e.render(g, this);
		}
		for (EntryPoint e:listOfEntryPoints) { // Draws entry points
			e.render(g);
		}
		
		this.eventController.render(g,gc);
		
		for (Flight f:listOfFlightsInAirspace) { // Draws flights in airspace
			f.render(g, gc);
		}
		
		
		separationRules.render(g, gc, this);
		controls.render(gc,g);
	}

	public ArrayList<FlightCoop> getListOfFlightsPlayer1() {
		return listOfFlightsPlayer1;
	}

	public void setListOfFlightsPlayer1(ArrayList<FlightCoop> listOfFlightsPlayer1) {
		this.listOfFlightsPlayer1 = listOfFlightsPlayer1;
	}

	public ArrayList<FlightCoop> getListOfFlightsPlayer2() {
		return listOfFlightsPlayer2;
	}

	public void setListOfFlightsPlayer2(ArrayList<FlightCoop> listOfFlightsPlayer2) {
		this.listOfFlightsPlayer2 = listOfFlightsPlayer2;
	}

	public ControlsCoop getControls() {
		return controls;
	}


	public void setControls(ControlsCoop controls) {
		this.controls = controls;
	}
	

	
}
