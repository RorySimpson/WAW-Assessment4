package coop;

import java.util.ArrayList;
import java.util.Random;

import logicClasses.Airspace;
import logicClasses.Flight;
import logicClasses.Waypoint;
import coop.FlightCoop;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;


public class AirspaceCoop extends Airspace {
	
	private ArrayList<FlightCoop> listOfFlightsPlayer1;
	private ArrayList<FlightCoop> listOfFlightsPlayer2;
	boolean addPlayer1Flight;
	
	public AirspaceCoop() {
		
		super();
		this.setControls(new ControlsCoop(this));
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
				this.addPlayer1Flight=true;
				addedFlight.setPlayer2(true);
				this.listOfFlightsPlayer2.add(addedFlight);
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
	

	
}
