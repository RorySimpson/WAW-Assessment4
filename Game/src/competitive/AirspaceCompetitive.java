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
	private int player1Score, player2Score;
	private CargoCompetitive cargo;
	private SeparationRulesCompetitive 	separationRules;
	private ControlsCompetitive controls;


	public AirspaceCompetitive() {

		super();
		this.controls = new ControlsCompetitive(this);
		this.listOfFlightsPlayer1 = new ArrayList<FlightCompetitive>();
		this.listOfFlightsPlayer2 = new ArrayList<FlightCompetitive>();
		this.addPlayer1FlightNext=false;
		this.airportLeft = null;
		this.eventController = null;
		this.setAirportRight(new AirportCompetitive(1, this));
		numberOfGameLoopsSinceLastPlayer1FlightAdded = 60*5; // These are set to this value so that that flights spawn,
		numberOfGameLoopsSinceLastPlayer2FlightAdded = 60*5; // on the game being started.
		this.setMaximumNumberOfFlightsInAirspace(6);
		player1Score = 0;
		player2Score = 0;
		cargo = new CargoCompetitive();

	}

	// Overriden in order to create an instance of SeparationRulesCompetitive
	@Override
	public void createAndSetSeparationRules(){
		this.separationRules = new SeparationRulesCompetitive(difficultyValueOfGame);

	}

	// Overridden in order to reset airspace for new game.
	@Override
	public void resetAirspace() {

		this.setListOfFlightsInAirspace(new ArrayList<Flight>());
		this.listOfFlightsPlayer1 = new ArrayList<FlightCompetitive>();
		this.listOfFlightsPlayer2 = new ArrayList<FlightCompetitive>();

		this.setNumberOfGameLoopsSinceLastFlightAdded(0); 
		this.setNumberOfGameLoops(0); 
		this.setNumberOfGameLoopsWhenDifficultyIncreases(3600);

		this.numberOfGameLoopsSinceLastPlayer1FlightAdded = 60*5;
		this.numberOfGameLoopsSinceLastPlayer2FlightAdded =60*5;

		// Prevents user immediately entering game over state upon replay
		this.getSeparationRules().setGameOverViolation(false);

		// Prevents information about flight from previous game being displayed

		this.getControls().setSelectedFlight1(null);
		this.getControls().setSelectedFlight2(null);

		// Resetting variables for cargo
		cargo.setCurrentHolder(null);
		cargo.setLocation(cargo.generateRandomCargoLocation());

		// Resetting scores and separation rules
		this.createAndSetSeparationRules();
		player1Score = 0;
		player2Score = 0;


	}
	
	public void checkWhatPlayerNeedsFlight(){
		if((this.listOfFlightsPlayer1.size() <= this.listOfFlightsPlayer2.size())){
			this.addPlayer1FlightNext = true;
		}
		
		else {
			this.addPlayer1FlightNext = false;
		}
	}

	/**
	 * newCompetitiveFlight: If a flight was added to the airspace assign the flight
	 * to one of the two players.
	 */

	public void newCompetitiveFlight(GameContainer gc) throws SlickException {

		if(this.newFlight(gc)){
			int heading;
			FlightCompetitive addedFlight = (FlightCompetitive) this.getListOfFlightsInAirspace().get(this.getListOfFlightsInAirspace().size()-1);

			// If next flight should be given to player 1 then set up flight so it behaves accordingly.
			if(this.addPlayer1FlightNext) {

				// Ensure next flight is given to other player.
				this.addPlayer1FlightNext = false;

				//Set up flight for Player 1
				addedFlight.setPlayer2(false);
				this.listOfFlightsPlayer1.add(addedFlight);
				numberOfGameLoopsSinceLastPlayer1FlightAdded = 0;
				heading = 90;
				addedFlight.getFlightPlan().assignEntryPoint(this);


			}

			// If next flight should be given to player 2 then set up flight so it behaves accordingly.
			else {

				// Ensure next flight is given to other player
				this.addPlayer1FlightNext = true;

				//Set up flight for Player 2
				addedFlight.setPlayer2(true);
				this.listOfFlightsPlayer2.add(addedFlight);
				numberOfGameLoopsSinceLastPlayer2FlightAdded = 0;
				heading = 270;
				addedFlight.getFlightPlan().assignEntryPoint(this);
			}

			// Make sure flights are assigned correct initial heading.
			addedFlight.setTargetHeading(heading);
			addedFlight.setCurrentHeading(heading);


		}
	}
	

	/**
	 * newFlight: Checks whether a flight should be added into the airspace and adds
	 * a flight if it's the appropriate time.
	 */
	
	@Override
	public boolean newFlight(GameContainer gc) throws SlickException {
		
		checkWhatPlayerNeedsFlight();
		if (this.getListOfFlightsInAirspace().size() < this.getMaximumNumberOfFlightsInAirspace()) {

			if(this.addPlayer1FlightNext){
				this.numberOfGameLoopsSinceLastFlightAdded = numberOfGameLoopsSinceLastPlayer1FlightAdded;
			}

			else{
				this.numberOfGameLoopsSinceLastFlightAdded = numberOfGameLoopsSinceLastPlayer2FlightAdded;
			}
			
			
			

			// Minimum wait for 5 seconds 
			if (this.getNumberOfGameLoopsSinceLastFlightAdded() >= (60*5) ) {


				Flight tempFlight = new FlightCompetitive(this, true);

				tempFlight.setFlightName(this.generateFlightName());
				tempFlight.setTargetAltitude(tempFlight.getAltitude());

				if(addFlight(tempFlight)){
					this.getListOfFlightsInAirspace().get(
							this.getListOfFlightsInAirspace().size() -1).init(gc);
					return true;
				}

			}
		}

		return false;
	}


	/**
	 * updateScore: Used to update score for either player 1 or 2
	 */

	public void updateScore(FlightCompetitive flight){

		if(flight.isPlayer2()){
			player2Score++;
		}
		else{
			player1Score++;
		}

	}

	/**
	 * throwbackIntoAirspace: When a flight is about to leave the airspace it is spun 180 degrees
	 * back into the airspace to ensure it doesn't leave.
	 */

	public void throwbackIntoAirspace(Flight flight){
		Random rand = new Random();
		// Flight is spun between 135 degrees to 225 degrees back into the airspace.
		double newHeading = (flight.getCurrentHeading() + (rand.nextInt(90) + 135) % 360);
		flight.setCurrentHeading(newHeading);
		flight.setTargetHeading(newHeading);
	}

	

	/**
	 * removeSpecificFlight: Used to remove a flight from the airspace.
	 */

	
	@Override
	public void removeSpecificFlight(int flight) {
		if(this.listOfFlightsPlayer1.contains(this.getListOfFlightsInAirspace().get(flight))) {
			this.listOfFlightsPlayer1.remove(this.getListOfFlightsInAirspace().get(flight));
		}
		else if(this.listOfFlightsPlayer2.contains(this.getListOfFlightsInAirspace().get(flight))) {
			this.listOfFlightsPlayer2.remove(this.getListOfFlightsInAirspace().get(flight));
		}
		this.getListOfFlightsInAirspace().remove(flight);

		Flight selected1 = this.getControls().getSelectedFlight1();
		Flight selected2 = this.getControls().getSelectedFlight2();
		// If flight was selected, de-select it
		if (!(this.getListOfFlightsInAirspace().contains(selected1))) {
			this.getControls().setSelectedFlight1(null);
		}
		
		if (!(this.getListOfFlightsInAirspace().contains(selected2))) {
			this.getControls().setSelectedFlight2(null);

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
		this.cargo.init(gc);


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
				if(cargo.getCurrentHolder() == this.getListOfFlights().get(i)){
					cargo.setCurrentHolder(null);
					cargo.setLocation(cargo.generateRandomCargoLocation());
					updateScore((FlightCompetitive)this.getListOfFlights().get(i));
				}
				this.removeSpecificFlight(i);
			}
			else if (this.checkIfFlightHasLeftAirspace(this.getListOfFlights().get(i))) {

				throwbackIntoAirspace(this.getListOfFlights().get(i));

				/*if(cargo.getCurrentHolder() == this.getListOfFlights().get(i)){
					cargo.setCurrentHolder(null);
					cargo.setLocation(cargo.generateRandomCargoLocation());
				}

				this.removeSpecificFlight(i);*/

			}

		}


		this.separationRules.update(this);
		this.controls.update(gc);
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

	public int getPlayer1Score() {
		return player1Score;
	}

	public void setPlayer1Score(int player1Score) {
		this.player1Score = player1Score;
	}

	public int getPlayer2Score() {
		return player2Score;
	}

	public void setPlayer2Score(int player2Score) {
		this.player2Score = player2Score;
	}
	
	@Override
	public ControlsCompetitive getControls(){
		return this.controls;
	}
	
	public void setControls(ControlsCompetitive controls){
		this.controls = controls;
	}

	public boolean isAddPlayer1FlightNext() {
		return addPlayer1FlightNext;
	}

	public void setAddPlayer1FlightNext(boolean addPlayer1FlightNext) {
		this.addPlayer1FlightNext = addPlayer1FlightNext;
	}

	public int getNumberOfGameLoopsSinceLastPlayer1FlightAdded() {
		return numberOfGameLoopsSinceLastPlayer1FlightAdded;
	}

	public void setNumberOfGameLoopsSinceLastPlayer1FlightAdded(
			int numberOfGameLoopsSinceLastPlayer1FlightAdded) {
		this.numberOfGameLoopsSinceLastPlayer1FlightAdded = numberOfGameLoopsSinceLastPlayer1FlightAdded;
	}

	public int getNumberOfGameLoopsSinceLastPlayer2FlightAdded() {
		return numberOfGameLoopsSinceLastPlayer2FlightAdded;
	}

	public void setNumberOfGameLoopsSinceLastPlayer2FlightAdded(
			int numberOfGameLoopsSinceLastPlayer2FlightAdded) {
		this.numberOfGameLoopsSinceLastPlayer2FlightAdded = numberOfGameLoopsSinceLastPlayer2FlightAdded;
	}
	
	
}





