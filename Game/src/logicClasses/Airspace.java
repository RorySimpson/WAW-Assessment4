package logicClasses;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import events.*;

import stateContainer.Game;

public class Airspace {

	/* The maximum number of flights allowed in the airspace */
	protected int maximumNumberOfFlightsInAirspace;

	/* Helps with keeping track of achievementj */
	protected int flightLostTimer;

	/* Counts the game loops for adjusting settings */
	protected int numberOfGameLoopsSinceLastFlightAdded;
	protected int numberOfGameLoops;
	protected int numberOfGameLoopsWhenDifficultyIncreases;

	/* Randomises the flights generation */
	protected int randomNumberForFlightGeneration;

	/* Creates lists of planes and points */
	protected List<Flight> listOfFlightsInAirspace;
	protected List<Waypoint> listOfWaypoints;
	protected List<EntryPoint> listOfEntryPoints;
	protected List<ExitPoint> listOfExitPoints;

	/* Creates the required objects */
	protected SeparationRules separationRules;
	protected Airport airportLeft;
	protected Airport airportRight;
	protected Controls controls;
	protected ScoreTracking score;
	protected EventController eventController;
	/*
	 * Difficulty of the game is set with integers, but it will be overwritten
	 * by the user choice
	 */
	protected int difficultyValueOfGame = 1;

	// CONSTRUCTOR
	public Airspace() {
		/* A maximum number of flights for the airspace */
		this.maximumNumberOfFlightsInAirspace = 10;

		/* Creates a list of planes and points */
		this.listOfFlightsInAirspace = new ArrayList<Flight>();
		this.listOfWaypoints = new ArrayList<Waypoint>();
		this.listOfEntryPoints = new ArrayList<EntryPoint>();
		this.listOfExitPoints = new ArrayList<ExitPoint>();
		/*
		 * Creates the 2 airports and assigns them an integer to differentiate
		 * them an integer has been used instead of boolean to allow scalability
		 */
		this.airportRight = new Airport(1, this);
		this.airportLeft = new Airport(2, this);
		/* Create an event controller object */
		this.eventController = new EventController(this);

		// Stores how many loops since the last flight was spawned
		// before another flight can enter
		this.numberOfGameLoopsSinceLastFlightAdded = 0;

		// Stores how many loops there have been in total
		this.numberOfGameLoops = 0;

		// this is how many loops until planes come more quickly,
		// difficulty increase once a minute
		this.numberOfGameLoopsWhenDifficultyIncreases = 3600;

		this.randomNumberForFlightGeneration = 500;
		this.controls = new Controls(this);

		// This value will be changed when the user selects a difficulty
		// in the playstate
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

		// Prevents user immediately entering game over state upon
		// replay
		this.separationRules.setGameOverViolation(false);

		// Prevents information about flight from previous game being
		// displayed
		this.controls.setSelectedFlight(null);
		this.eventController = new EventController(this);
	}

	/**
	 * createAndSetSeperationRules: Create and set the separation rules for the
	 * airpsace based on the difficulty value of the game
	 */

	public void createAndSetSeparationRules() {
		this.separationRules = new SeparationRules(difficultyValueOfGame);
	}

	/**
	 * Checks whether a point is within the airpsace
	 * 
	 * @param x
	 *            - x coordinate
	 * @param y
	 *            - y coordinate
	 * @return - whether it is or not
	 */
	public boolean withinAirspace(int x, int y) {
		if (x < Game.MAXIMUMWIDTH + 50 && x >= 0 && y < Game.MAXIMUMHEIGHT + 50
				&& y > -50) {
			return true;
		}

		return false;
	}

	/**
	 * newWaypoint: Add a new waypoint to the list of waypoints in the airspace
	 * 
	 * @param x
	 *            The x coordinate of the waypoint
	 * @param y
	 *            The y coordinate of the waypoint
	 * @param name
	 *            The name used to reference the waypoint
	 */

	public boolean newWaypoint(int x, int y, String name) {
		if (withinAirspace(x, y)) {
			// x and y must be within these bounds to be within
			// screen space

			Waypoint tmpWp = new Waypoint(x, y, name);

			if (this.addWaypoint(tmpWp)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * newExitPoint: Add a new exitpoint to the list in the airspace
	 * 
	 * @param x
	 *            The x coordinate of the exitpoint
	 * @param y
	 *            The y coordinate of the exitpoint
	 * @param name
	 *            The name used to reference the exitpoint
	 */

	public boolean newExitPoint(int x, int y, String name) {
		if (withinAirspace(x, y)) {
			// x and y must be within these bounds to be within
			// screen space

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
	 * 
	 * @param x
	 *            The x coordinate of the entry point
	 * @param y
	 *            The y coordinate of the entry point
	 */

	public boolean newEntryPoint(int x, int y) {
		if (withinAirspace(x, y)) {

			EntryPoint tmpEp = new EntryPoint(x, y);

			if (this.addEntryPoint(tmpEp)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * newFlight: Add a new flight to the list of flights in the airspace if it
	 * has been long enough since the last flight was added and if random number
	 * satisfies condition The flight is also given a name
	 * 
	 * @param gc
	 *            Game container required by Slick2d
	 * @throws SlickException
	 */

	public boolean newFlight(GameContainer gc) throws SlickException {

		if (this.listOfFlightsInAirspace.size() < this.maximumNumberOfFlightsInAirspace) {

			if ((this.numberOfGameLoopsSinceLastFlightAdded >= 850 || this.listOfFlightsInAirspace
					.isEmpty())) {

				Random rand = new Random();
				int checkNumber;

				if (this.listOfFlightsInAirspace.isEmpty()) {
					// A random number (checkNumber) is
					// generated in the range [0, 100)
					checkNumber = rand.nextInt(100);
				}

				else {
					// A random number (checkNumber) is
					// generated in range [0,
					// randomNumberForFlightGeneration)
					checkNumber = rand
							.nextInt(this.randomNumberForFlightGeneration);
				}

				/*
				 * The random number is generated in the range [0, 100) if the
				 * airspace is empty, as this increases the likelihood of a
				 * value of 1 being returned, and therefore a flight being
				 * generated; this stops the user having to potentially wait a
				 * long period of time for a flight to be generated. If the
				 * airspace is not empty, the random number generated is in the
				 * range [0, randomNumberForFlight Generation) which is > 100.
				 * This decreases the likelihood of a flight being generated.
				 */

				if (checkNumber == 1) {

					Flight tempFlight = new Flight(this);

					tempFlight.setFlightName(this.generateFlightName());
					tempFlight.setTargetAltitude(tempFlight.getAltitude());

					/*
					 * If the plane needs to take off from the right airport
					 * prepare it to take off
					 */
					double heading;
					if (tempFlight.getFlightPlan().getEntryPoint() == this.airportLeft
							.getEndOfRunway()) {
						heading = airportLeft.getRunwayHeading();
						tempFlight.setWaitingToTakeOff(true);
					}

					/*
					 * If the plane needs to take off from the right airport
					 * prepare it to take off
					 */
					else if (tempFlight.getFlightPlan().getEntryPoint() == this.airportRight
							.getEndOfRunway()) {

						heading = airportRight.getRunwayHeading();
						tempFlight.setWaitingToTakeOff(true);
					}

					/*
					 * If the plane needs to go to a normal waypoint prepare it
					 * to enter with the right heading facing the first waypoint
					 */
					else {
						heading = tempFlight.calculateHeadingToNextWaypoint(
								tempFlight.getFlightPlan().getPointByIndex(0)
										.getX(), tempFlight.getFlightPlan()
										.getPointByIndex(0).getY());
					}

					tempFlight.setTargetHeading(heading);
					tempFlight.setCurrentHeading(heading);

					/* Set a bonus flight with a 5% chance */
					if (rand.nextInt(20) == 1) {
						tempFlight.setBonus(true);
					}

					/* Add plane */
					if (addFlight(tempFlight)) {
						this.numberOfGameLoopsSinceLastFlightAdded = 0;
						this.listOfFlightsInAirspace.get(
								this.listOfFlightsInAirspace.size() - 1).init(
								gc);
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * generateFlightName: Generate a random name for a flight, based on UK
	 * flight tail numbers
	 * 
	 * @return Returns a random string that can be used to identify a flight.
	 */
	public String generateFlightName() {
		String name = "G-";
		Random rand = new Random();
		for (int i = 0; i < 4; i++) {
			int thisChar = rand.nextInt(10) + 65; // Generates int
													// in range [65,
													// 74]
			name += (char) thisChar; // Generate corresponding ascii
										// character for int
		}
		return name;
	}

	/**
	 * checkIfFlightHasLeftAirspace: Check if a flight is outside the area of
	 * the game, and if it is removed the object so it is not using unnecessary
	 * resources.
	 * 
	 * @param flight
	 *            The flight being checked.
	 */
	public boolean checkIfFlightHasLeftAirspace(Flight flight) {
		// x and y must be within these bounds to be within screen space
		// Not quite the same with withinAirspace method
		if (flight.getX() > 1250 || flight.getX() < -50 || flight.getY() > 650
				|| flight.getY() < -50) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * increaseDifficulty
	 */
	public void increaseDifficulty() {
		this.numberOfGameLoopsWhenDifficultyIncreases += 3600;
		if (this.randomNumberForFlightGeneration - 50 > 0) {
			this.randomNumberForFlightGeneration -= 50;
		}
	}

	// INIT, RENDER, UPDATE

	/**
	 * init: Initialises all the resources required for the airspace class, and
	 * any other classes that are rendered within it
	 * 
	 * @param gc
	 *            GameContainer
	 * @throws SlickException
	 */
	public void init(GameContainer gc) throws SlickException {

		this.controls.init(gc);
		this.airportLeft.init(gc);
		this.airportRight.init(gc);
		this.eventController.init(gc);

		for (int i = 0; i < this.listOfWaypoints.size(); i++) { // Initialising
																// waypoints
			this.listOfWaypoints.get(i).init(gc);
		}

		for (int i = 0; i < this.listOfExitPoints.size(); i++) { // Initailising
																	// exit
																	// points
			this.listOfExitPoints.get(i).init(gc);
		}

		for (int i = 0; i < this.listOfEntryPoints.size(); i++) { // Initialising
																	// entry
																	// point
			this.listOfEntryPoints.get(i).init(gc);
		}
	}

	/**
	 * update: Update all logic in the airspace class
	 * 
	 * @param gc
	 *            GameContainer
	 */
	public void update(GameContainer gc) throws SlickException {

		/* Increase the number of loop every update */
		this.numberOfGameLoopsSinceLastFlightAdded++;
		this.numberOfGameLoops++;

		/* Increase difficulty after a set number of loops */
		if (this.numberOfGameLoops >= this.numberOfGameLoopsWhenDifficultyIncreases) {
			this.increaseDifficulty();
		}

		/* Loop through all the planes */
		for (int i = 0; i < this.listOfFlightsInAirspace.size(); i++) {
			/* Update score */
			this.listOfFlightsInAirspace.get(i).update(score);
			/*
			 * Remove plane that finished its flight plan and add score for
			 * finishing a flight plan
			 */
			if (this.listOfFlightsInAirspace.get(i).getFlightPlan()
					.getCurrentRoute().size() == 0) {
				this.removeSpecificFlight(i);
				score.procCompleteFlightAchieve();
			}
			/*
			 * If plane left the screen without finishing its flight plan it
			 * decreases score and penalises the multiplier
			 */
			else if (this.checkIfFlightHasLeftAirspace(this.getListOfFlights()
					.get(i))) {
				if (this.getListOfFlights().get(i).isControllable()) {
					score.reduceScoreOnFlightLost();
					score.reduceMultiplierOnFlightLost();
					this.flightLostTimer = 0;

				}
				this.removeSpecificFlight(i);
			}
		}

		// Tracking for minutes without flight lost achievement
		flightLostTimer += 1;
		if (flightLostTimer >= 3000) {
			score.procminsWithoutPlaneLossAchievement();
		}

		/* Update all objects */
		this.eventController.update(gc);
		this.separationRules.update(this);
		this.controls.update(gc, this);
	}

	public ScoreTracking getScore() {
		return score;
	}

	/**
	 * render: Render all of the graphics in the airspace
	 * 
	 * @param g
	 *            Graphics
	 * @param gc
	 *            GameContainer
	 * 
	 * @throws SlickException
	 */
	public void render(Graphics g, GameContainer gc) throws SlickException {

		this.airportLeft.render(g, gc);
		this.airportRight.render(g, gc);

		for (Waypoint w : listOfWaypoints) { // Draws waypoints
			w.render(g, this);
		}
		for (ExitPoint e : listOfExitPoints) { // Draws exit points
			e.render(g, this);
		}
		for (EntryPoint e : listOfEntryPoints) { // Draws entry points
			e.render(g);
		}

		this.eventController.render(g, gc);

		for (Flight f : listOfFlightsInAirspace) { // Draws flights in airspace
			f.render(g, gc);
		}
		separationRules.render(g, gc, this);
		controls.render(gc, g);
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

	/**
	 * Add a flight
	 * 
	 * @param flight
	 *            object
	 * @return whether the plane was added
	 */
	public boolean addFlight(Flight flight) {

		if (flight == null) {
			return false;
		}

		// Checks whether the flight was already added before, and if it
		// won't pass the maximum number of flights allowed
		if ((this.listOfFlightsInAirspace.contains(flight))
				&& (this.listOfFlightsInAirspace.size() > this.maximumNumberOfFlightsInAirspace - 1)) {
			return false;

		} else {

			// TODO Fix.

			/*
			 * Forbid adding a plane to an airport that already has a plane
			 */
			for (int i = 0; i < listOfFlightsInAirspace.size(); i++) {
				if (listOfFlightsInAirspace.get(i).isGrounded()
						&& flight.getFlightPlan().getEntryPoint().isRunway()) {
					System.out.println("Flight already on runway!");
					return false;
				}
			}
			/* Add flight to airspace */
			this.listOfFlightsInAirspace.add(flight);
			return true;
		}
	}

	/**
	 * Remove a specific flight
	 * 
	 * @param flight
	 *            the flight that needs to be removed
	 */
	public void removeSpecificFlight(int flight) {
		this.listOfFlightsInAirspace.remove(flight);

		// If flight was selected, de-select it
		if (!(this.listOfFlightsInAirspace.contains(this.controls
				.getSelectedFlight()))) {
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

	public SeparationRules getSeparationRules() {
		return this.separationRules;
	}

	public void setListOfEntryPoints(List<EntryPoint> listOfEntryPoints) {
		this.listOfEntryPoints = listOfEntryPoints;
	}

	public Controls getControls() {
		return this.controls;
	}

	public void setSeparationRules(SeparationRules separationRules) {
		this.separationRules = separationRules;
	}

	public void setDifficultyValueOfGame(int i) {
		this.difficultyValueOfGame = i;

	}

	public int getDifficultyValueOfGame() {
		return this.difficultyValueOfGame;
	}

	public int getNumberOfGameLoops() {
		return this.numberOfGameLoops;
	}

	public int getNumberOfGameLoopsWhenDifficultyIncreases() {
		return this.numberOfGameLoopsWhenDifficultyIncreases;
	}

	public Airport getAirportLeft() {
		return airportLeft;
	}

	public Airport getAirportRight() {
		return airportRight;
	}

	public void setAirportLeft(Airport airportLeft) {
		this.airportLeft = airportLeft;
	}

	public void setAirportRight(Airport airportRight) {
		this.airportRight = airportRight;
	}

	@Override
	public String toString() {
		String s = "Airspace: " + airportLeft.toString();
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

	public EventController getEventController() {
		return this.eventController;
	}

	public int getMaximumNumberOfFlightsInAirspace() {
		return maximumNumberOfFlightsInAirspace;
	}

	public void setMaximumNumberOfFlightsInAirspace(
			int maximumNumberOfFlightsInAirspace) {
		this.maximumNumberOfFlightsInAirspace = maximumNumberOfFlightsInAirspace;
	}

	public int getRandomNumberForFlightGeneration() {
		return randomNumberForFlightGeneration;
	}

	public void setRandomNumberForFlightGeneration(
			int randomNumberForFlightGeneration) {
		this.randomNumberForFlightGeneration = randomNumberForFlightGeneration;
	}
}
