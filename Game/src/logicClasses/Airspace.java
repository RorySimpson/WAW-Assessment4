package logicClasses;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import stateContainer.Game;



public class Airspace {

	private int maximumNumberOfFlightsInAirspace;
	private int	numberOfGameLoopsSinceLastFlightAdded, numberOfGameLoops,
		numberOfGameLoopsWhenDifficultyIncreases, randomNumberForFlightGeneration;
	private List<Flight> 		listOfFlightsInAirspace;
	private List<Waypoint> 		listOfWaypoints;
	private List<EntryPoint>	listOfEntryPoints;
	private List<ExitPoint> 	listOfExitPoints;
	private SeparationRules 	separationRules;
	private Airport 			airport1, airport2;
	private int 				difficultyValueOfGame; 
	private Controls 			controls;
	private ScoreTracking 		score;
	
	
	// CONSTRUCTOR
	public Airspace() {
		this.maximumNumberOfFlightsInAirspace 	= 10;
		this.listOfFlightsInAirspace 			= new ArrayList<Flight>();
		this.listOfWaypoints 					= new ArrayList<Waypoint>();
		this.listOfEntryPoints 					= new ArrayList<EntryPoint>();
		this.listOfExitPoints 					= new ArrayList<ExitPoint>();
		this.airport1							= new Airport(1,this);
		this.airport2						    = new Airport(2, this);
		
		
		// Stores how many loops since the last flight was spawned before another flight can enter
		this.numberOfGameLoopsSinceLastFlightAdded = 0;
		
		// Stores how many loops there have been in total
		this.numberOfGameLoops = 0; 
		
		// this is how many loops until planes come more quickly, difficulty increase once a minute
		this.numberOfGameLoopsWhenDifficultyIncreases = 3600; 
		
		this.randomNumberForFlightGeneration = 500;
		this.controls = new Controls(this);
		
		// This value will be changed when the user selects a difficulty in the playstate
		this.difficultyValueOfGame = 0; 
		this.score = new ScoreTracking();	
	}

	// METHODS
	
	/**
	 * resetAirspace: Reset all of the attributes in airspace back to default
	 */
	
	public void resetAirspace() {
		
		this.listOfFlightsInAirspace = new ArrayList<Flight>();
		
		this.numberOfGameLoopsSinceLastFlightAdded = 0; 
		this.numberOfGameLoops = 0; 
		this.numberOfGameLoopsWhenDifficultyIncreases = 3600;
		
		// Prevents user immediately entering game over state upon replay
		this.separationRules.setGameOverViolation(false);
		
		// Prevents information about flight from previous game being displayed
		this.controls.setSelectedFlight(null);  	
		
	}
	
	/**
	 * createAndSetSeperationRules: Create and set the separation rules for the airpsace based on the difficulty value of the game
	 */
	
	public void createAndSetSeparationRules(){
		this.separationRules = new SeparationRules(difficultyValueOfGame); 
	}
	
	/**
	 * Checks whether a point is within the airpsace
	 * 
	 * @param x  -  x coordinate
	 * @param y  -  y coordinate
	 * @return   -  whether it is or not 
	 */
	public boolean withinAirspace(int x, int y)
	{
		if (x < Game.MAXIMUMWIDTH + 50 && x >= 0 && y < Game.MAXIMUMHEIGHT + 50 && y > -50)
		{
			return true;
		}
		
		return false;
	}
	
	
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
			
			Waypoint tmpWp = new Waypoint(x, y, name);
			
			if (this.addWaypoint(tmpWp)) {
				return true;
			}	
		} 
		
		return false;
	}
	
	/**
	 * newExitPoint: Add a new exitpoint to the list in the airspace
	 * @param x The x coordinate of the exitpoint
	 * @param y The y coordinate of the exitpoint
	 * @param name The name used to reference the exitpoint
	 */
	
	public boolean newExitPoint(int x, int y, String name) {
		if (withinAirspace(x, y))
		{
			// x and y must be within these bounds to be within screen space
			
			ExitPoint tmpEp = new ExitPoint(x, y, name);
			
			tmpEp.setPointRef("EXP" + name);
			if (this.addExitPoint(tmpEp)) {
				return true;
			}
		} 
		
		return false;
	}
	
	/**
	 * newEntryPoint: Add a new entrypoint to the the list in the airspace
	 * @param x The x coordinate of the entry point
	 * @param y The y coordinate of the entry point
	 */
	
	public boolean newEntryPoint(int x, int y)  {
		if (withinAirspace(x, y)){
			
			EntryPoint tmpEp = new EntryPoint(x, y);
			
			if (this.addEntryPoint(tmpEp)) {
				return true;
			}
		} return false;
	}
	
	/**
	 * newFlight: Add a new flight to the list of flights in the airspace if it has been long enough since the last flight was added 
	 * and if random number satisfies condition
	 * The flight is also given a name 
	 * @param gc Game container required by Slick2d
	 * @throws SlickException
	 */
	
	public boolean newFlight(GameContainer gc) throws SlickException {

		if (this.listOfFlightsInAirspace.size() < this.maximumNumberOfFlightsInAirspace) {
			
			if ((this.numberOfGameLoopsSinceLastFlightAdded >= 850  || this.listOfFlightsInAirspace.isEmpty())) {
				
				Random rand = new Random();
				int checkNumber;
					
				if (this.listOfFlightsInAirspace.isEmpty())
				{
					// A random number (checkNumber) is generated in the range [0, 100)
						checkNumber = rand.nextInt(100);  
				} 
					
				else
				{
					// A random number (checkNumber) is generated in range [0, randomNumberForFlightGeneration)
					checkNumber = rand.nextInt(this.randomNumberForFlightGeneration); 
				}
				
				/* 
				 * The random number is generated in the range [0, 100) if the airspace is empty, as this increases 
				 * the likelihood of a value of 1 being returned, and therefore a flight being generated; this stops the user
				 * having to potentially wait a long period of time for a flight to be generated. 
				 * If the airspace is not empty, the random number generated is in the range [0, randomNumberForFlight Generation)
				 * which is > 100. This decreases the likelihood of a flight being generated.
				 */
		
				if (checkNumber == 1) {
			
					Flight tempFlight = new Flight(this);
					
					tempFlight.setFlightName(this.generateFlightName());
					tempFlight.setTargetAltitude(tempFlight.getAltitude());
					
					double heading;
					if (tempFlight.getFlightPlan().getEntryPoint().isRunway())
					{
						heading = airport1.getRunwayHeading();
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
						this.numberOfGameLoopsSinceLastFlightAdded = 0;
						this.listOfFlightsInAirspace.get(
								this.listOfFlightsInAirspace.size() -1).init(gc);
						return true;
					}
					
				}
			}
		}
		return false;
	}
	
	
	/**
	 * generateFlightName: Generate a random name for a flight, based on UK flight tail numbers
	 * @return Returns a random string that can be used to identify a flight.
	 */
	
	public String generateFlightName() {
		String name = "G-";
		Random rand = new Random();
		for (int i = 0; i < 4; i++) {
			int thisChar = rand.nextInt(10) + 65; // Generates int in range [65, 74]
			name += (char) thisChar; // Generate corresponding ascii character for int 
		}
		return name;
	}
	
	/**
	 * checkIfFlightHasLeftAirspace: Check if a flight is outside the area of the game, and if it is removed the object so it is not
	 * using unnecessary resources.
	 * @param flight The flight being checked.
	 */

	public boolean checkIfFlightHasLeftAirspace(Flight flight) {
		// x and y must be within these bounds to be within screen space
		// Not quite the same with withinAirspace method
		if (flight.getX() > 1250 || flight.getX() < -50|| flight.getY() > 650 || flight.getY() < -50) { 
			return true;
		}
		else 
		{
			return false;
		}

	}

	/**
	 * increaseDifficulty 
	 */
	
	public void increaseDifficulty(){
		this.numberOfGameLoopsWhenDifficultyIncreases += 3600;
		if (this.randomNumberForFlightGeneration - 50 > 0) {
			this.randomNumberForFlightGeneration -= 50;
		}
	}
	


	// INIT, RENDER, UPDATE
	
	/**
	 * init: Initialises all the resources required for the airspace class, and any other classes that are rendered within it
	 * @param gc GameContainer
	 * @throws SlickException
	 */
	
	public void init(GameContainer gc) throws SlickException {
		
		this.controls.init(gc);
		this.airport1.init(gc);
		
		for (int i = 0; i < this.listOfWaypoints.size(); i++) { // Initialising waypoints
			this.listOfWaypoints.get(i).init(gc);
		}
		
		for (int i = 0; i < this.listOfExitPoints.size(); i++) { // Initailising exit points
			this.listOfExitPoints.get(i).init(gc);
		}
		
		for (int i = 0; i < this.listOfEntryPoints.size(); i++) { // Initialising entry point
			this.listOfEntryPoints.get(i).init(gc);
		}
		
	}
	
	/**
	 * update: Update all logic in the airspace class
	 * @param gc GameContainer
	 */
	
	public void update(GameContainer gc) {
		
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
				score.reduceScoreOnFlightLost();
				score.reduceMultiplierOnFlightLost();
				this.removeSpecificFlight(i);
			}
			
		}
		
		this.separationRules.update(this);
		this.controls.update(gc, this);
	}
	
	public ScoreTracking getScore(){
		return score;
	}
	/**
	 * render: Render all of the graphics in the airspace
	 * @param g Graphics
	 * @param gc GameContainer
	 * 
	 * @throws SlickException
	 */
	public void render(Graphics g, GameContainer gc) throws SlickException { 
		
		this.airport1.render(g, gc);

		for (Waypoint w:listOfWaypoints) { // Draws waypoints
			w.render(g, this);
		}
		for (ExitPoint e:listOfExitPoints) { // Draws exit points
			e.render(g, this);
		}
		for (EntryPoint e:listOfEntryPoints) { // Draws entry points
			e.render(g);
		}
		for (Flight f:listOfFlightsInAirspace) { // Draws flights in airspace
			f.render(g, gc);
		}
		
		separationRules.render(g, gc, this);
		controls.render(gc,g);
	}



	// MUTATORS AND ACCESSORS

	public int getMaxNumberOfFlights() {
		return this.maximumNumberOfFlightsInAirspace;
	}

	public List<Flight> getListOfFlights() {
		return this.listOfFlightsInAirspace;
	}

	public List<Waypoint> getListOfWaypoints() {
		return this.listOfWaypoints;
	}

	public List<EntryPoint> getListOfEntryPoints() {
		return this.listOfEntryPoints;
	}

	public List<ExitPoint> getListOfExitPoints() {
		return this.listOfExitPoints;
	}

	public void setMaxNumberOfFlights(int maxNumberOfFlights) {
		this.maximumNumberOfFlightsInAirspace = maxNumberOfFlights;
	}

	public boolean addWaypoint(Waypoint waypoint) {
		if (this.listOfWaypoints.contains(waypoint)) {
			return false;
		} else {
			this.listOfWaypoints.add(waypoint);
			return true;
		}
	}

	public boolean addEntryPoint(EntryPoint entrypoint) {
		if (this.listOfEntryPoints.contains(entrypoint)) {
			return false;
		} else {
			this.listOfEntryPoints.add(entrypoint);
			return true;
		}
	}

	public boolean addExitPoint(ExitPoint exitpoint) {
		if (this.listOfExitPoints.contains(exitpoint)) {
			return false;
		} else {
			this.listOfExitPoints.add(exitpoint);
			return true;
		}
	}
	public boolean addFlight(Flight flight) {

		// Checks whether the flight was already added before, and if it won't pass the maximum number of flights allowed
		if ((this.listOfFlightsInAirspace.contains(flight))
				&& (this.listOfFlightsInAirspace.size() > this.maximumNumberOfFlightsInAirspace - 1)) {
			return false;
		} else {
			for(Flight a : listOfFlightsInAirspace){
				if(a.isGrounded() && flight.getFlightPlan().getEntryPoint().isRunway()){
					System.out.println("Flight already on runway!");
					return false;
				}
			}
			this.listOfFlightsInAirspace.add(flight);
			return true;
		}
	}
	
	public void removeSpecificFlight(int flight) {
		this.listOfFlightsInAirspace.remove(flight);
		
		// If flight was selected, de-select it
		if (!(this.listOfFlightsInAirspace.contains(this.controls.getSelectedFlight()))) {
			this.controls.setSelectedFlight(null);

		}
	}

	public void removeWaypoint(Waypoint waypoint) {
		this.listOfWaypoints.remove(waypoint);
	}

	public void removeEntryPoint(EntryPoint entrypoint) {
		this.listOfEntryPoints.remove(entrypoint);
	}

	public void removeExitPoint(ExitPoint exitpoint) {
		this.listOfExitPoints.remove(exitpoint);
	}

	public SeparationRules getSeparationRules(){
		return this.separationRules;
	}

	
	public void setListOfEntryPoints(List<EntryPoint> listOfEntryPoints) {
		this.listOfEntryPoints = listOfEntryPoints;
	}
	
	public Controls getControls(){
		return this.controls;
	}
	
	public void setDifficultyValueOfGame(int i){
		this.difficultyValueOfGame = i;
		
	}
	
	public int getDifficultyValueOfGame(){
		return this.difficultyValueOfGame;
	}
	
	public int getNumberOfGameLoops(){
		return this.numberOfGameLoops;
	}
	
	public int getNumberOfGameLoopsWhenDifficultyIncreases(){
		return this.numberOfGameLoopsWhenDifficultyIncreases;
	}
	
	public Airport getAirport() {
		return airport1;
	}
	
	@Override
	public String toString() {
		String s = "Airspace: "+ airport1.toString();
		return s;
	}

	public void setControls(Controls controls) {
		this.controls = controls;
	}

	public int getNumberOfGameLoopsSinceLastFlightAdded() {
		return numberOfGameLoopsSinceLastFlightAdded;
	}

	public void setNumberOfGameLoopsSinceLastFlightAdded(
			int numberOfGameLoopsSinceLastFlightAdded) {
		this.numberOfGameLoopsSinceLastFlightAdded = numberOfGameLoopsSinceLastFlightAdded;
	}

	public List<Flight> getListOfFlightsInAirspace() {
		return listOfFlightsInAirspace;
	}

	public void setListOfFlightsInAirspace(List<Flight> listOfFlightsInAirspace) {
		this.listOfFlightsInAirspace = listOfFlightsInAirspace;
	}

	public List<Waypoint> getListOfWayppoints() {
		return listOfWaypoints;
	}

	public void setListOfWayppoints(List<Waypoint> listOfWayppoints) {
		this.listOfWaypoints = listOfWayppoints;
	}

	public void setNumberOfGameLoops(int numberOfGameLoops) {
		this.numberOfGameLoops = numberOfGameLoops;
	}

	public void setNumberOfGameLoopsWhenDifficultyIncreases(
			int numberOfGameLoopsWhenDifficultyIncreases) {
		this.numberOfGameLoopsWhenDifficultyIncreases = numberOfGameLoopsWhenDifficultyIncreases;
	}

	public void setListOfExitPoints(List<ExitPoint> listOfExitPoints) {
		this.listOfExitPoints = listOfExitPoints;
	}

	public void setScore(ScoreTracking score) {
		this.score = score;
	}
}
