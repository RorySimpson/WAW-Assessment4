package logicClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import stateContainer.Game;

public class FlightPlan {

	// FIELDS
	protected EntryPoint entryPoint;
	protected ExitPoint exitPoint;
	protected List<Point>
	/* Array that stores the current list of waypoints */
	currentRoute = new ArrayList<Point>(),
	/* Array that stores all the waypoints the flight has passed through */
	waypointsAlreadyVisited;
	/* The flight object associated with the flight plan */
	protected Flight flight;

	/* What waypoint is the mouse currently hovering over */
	protected Point waypointMouseIsOver, waypointClicked;
	/* Is the user currently changing the flight plan? */
	protected Boolean changingPlan,
	/* Is the user currently dragging a waypoint? */
	draggingWaypoint;
	protected int closestDistance;
	/* Waypoint ID references */
	protected static final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6,
			H = 7, I = 8, J = 9;

	/**
	 * Constructor to initalise all the default values
	 * 
	 * @param airspace
	 *            takes an airspace
	 * @param flight
	 *            take a flight
	 */
	public FlightPlan(Airspace airspace, Flight flight) {
		/* Generates an entry point for a new flight */
		this.flight = flight;
		this.entryPoint = generateEntryPoint(airspace);

		/* Generate a velocity for a new flight */
		double v = generateVelocity();
		flight.setVelocity(v);
		flight.setTargetVelocity(v);

		/* Sets the default values for a new plane */
		this.currentRoute = buildRoute(airspace, this.entryPoint);
		this.waypointsAlreadyVisited = new ArrayList<Point>();
		this.changingPlan = false;
		this.draggingWaypoint = false;

	}

	/**
	 * Constructor for competitve mode
	 * 
	 * @param airspace
	 *            an airspace object
	 * @param flight
	 *            a plane
	 * @param competitive
	 *            whether it's competitive mode to differentiate between
	 *            constructors
	 */
	public FlightPlan(Airspace airspace, Flight flight, Boolean competitive) {
		this.flight = flight;
	}

	// METHODS

	/**
	 * generateEntryPoint: Creates the entry point for the flight
	 * 
	 * @param airspace
	 *            airspace object
	 * @return airspace.getListofEntryPoints
	 */
	public EntryPoint generateEntryPoint(Airspace airspace) {

		/*
		 * Create new random number to randomise the spwaning entry point
		 */
		Random rand = new Random();
		int randomNumber = rand.nextInt(airspace.getListOfEntryPoints().size());

		// Setting flights x and y to the coordinates of it's entrypoint
		// choose one a get the x and y values
		flight.setX(airspace.getListOfEntryPoints().get(randomNumber).getX());
		flight.setY(airspace.getListOfEntryPoints().get(randomNumber).getY());

		return airspace.getListOfEntryPoints().get(randomNumber);
	}

	/**
	 * buildRoute: Creates an array of waypoints that the aircraft will be
	 * initially given
	 * 
	 * @param airspace
	 *            airspace object
	 * @param entryPoint
	 *            entry point object
	 * @return tempRoute
	 */

	/**
	 * Build a route for a plane
	 * 
	 * @param airspace
	 *            an airspace object
	 * @param entryPoint
	 *            the entry point that start the flight plan
	 * @return
	 */
	public ArrayList<Point> buildRoute(Airspace airspace, EntryPoint entryPoint) {
		// Create the array lists for route and points
		ArrayList<Point> tempRoute = new ArrayList<Point>();
		ArrayList<Waypoint> tempListOfWaypoints = new ArrayList<Waypoint>();
		ArrayList<ExitPoint> tempListOfExitPoints = new ArrayList<ExitPoint>();
		Boolean exitpointAdded = false;

		// if there is a list of waypoints and a list of exit points
		if (!airspace.getListOfWaypoints().isEmpty()
				&& !airspace.getListOfExitPoints().isEmpty()) {
			Random rand = new Random();

			// Initialising Temporary Lists
			// loop through all waypoints and add them to
			// tempwaypoints
			for (int i = 0; i < airspace.getListOfWaypoints().size(); i++) {
				tempListOfWaypoints.add(airspace.getListOfWaypoints().get(i));
			}

			// loop through all exit points and add them to
			// temppoints
			for (int i = 0; i < airspace.getListOfExitPoints().size(); i++) {
				tempListOfExitPoints.add(airspace.getListOfExitPoints().get(i));
			}

			// Adding Waypoints to Plan
			int pointsInPlan = rand.nextInt(3) + 3;

			/* Add the waypoints to the route */
			for (int i = 0; i < pointsInPlan - 1; i++) {
				int waypointIndex = rand.nextInt(tempListOfWaypoints.size());
				tempRoute.add(tempListOfWaypoints.get(waypointIndex));
				tempListOfWaypoints.remove(waypointIndex);
			}

			// Adding ExitPoint to Plan
			int ExitPointIndex = rand.nextInt(tempListOfExitPoints.size());

			/* Make sure an exitpoint exists for each route */
			while (exitpointAdded == false) {

				if (this.entryPoint.getY() == tempListOfExitPoints.get(
						ExitPointIndex).getY()) {
					tempListOfExitPoints.remove(ExitPointIndex);
					ExitPointIndex = rand.nextInt(tempListOfExitPoints.size());
				}

				else if (this.entryPoint.getX() == tempListOfExitPoints.get(
						ExitPointIndex).getX()) {
					tempListOfExitPoints.remove(ExitPointIndex);
					ExitPointIndex = rand.nextInt(tempListOfExitPoints.size());
				}

				else if (entryPoint.getX() == 760
						&& tempListOfExitPoints.get(ExitPointIndex).getX() == 590) { // This
																						// will
																						// need
																						// to
																						// be
																						// changed
																						// when
																						// the
																						// airport
																						// is
																						// moved
					tempListOfExitPoints.remove(ExitPointIndex);
					ExitPointIndex = rand.nextInt(tempListOfExitPoints.size());
				} else {
					tempRoute.add(tempListOfExitPoints.get(ExitPointIndex));
					exitpointAdded = true;
					exitPoint = tempListOfExitPoints.get(ExitPointIndex);
				}
			}

			/*
			 * Special cases for when the exit point is the airports (landing
			 * plane)
			 */
			if (exitPoint == airspace.getAirportLeft().getBeginningOfRunway()) {
				tempRoute.add(airspace.getAirportLeft().getEndOfRunway());
			}

			else if (exitPoint == airspace.getAirportRight()
					.getBeginningOfRunway()) {
				tempRoute.add(airspace.getAirportRight().getEndOfRunway());
			}
		}

		return tempRoute;
	}

	/**
	 * generateVelocity: Creates a velocity from a range of values
	 */
	public int generateVelocity() {
		Random rand = new Random();

		/* If the plane is landed, its velocity is 0 */
		if (entryPoint.isRunway()) {
			return 0;
		}

		/* Return a velocity between max and min */
		int min = flight.getMinVelocity(), max = flight.getMaxVelocity();
		return (rand.nextInt(min) + (max - min));
	}

	/**
	 * isMouseOnWaypoint: Used to tell what waypoint the mouse is currently over
	 * for changing the flight plan successfully
	 */
	private boolean isMouseOnWaypoint() {
		// Get mouse coordinates
		int mouseX = Mouse.getX();
		int mouseY = Game.MAXIMUMHEIGHT - Mouse.getY();

		// If there are no waypoints in the flight plan, there's nothing
		// to change
		if (this.getCurrentRoute().isEmpty()) {
			return false;
		}

		/*
		 * Whether there is a waypoint within the range of where the mouse
		 * clicked
		 */
		for (Waypoint w : flight.getAirspace().getListOfWaypoints()) {
			if (Math.abs(mouseX - w.getX()) <= 15
					&& Math.abs(mouseY - w.getY()) <= 15) {
				waypointMouseIsOver = w;
				return true;
			}
		}

		// else if no waypoints in range
		this.waypointMouseIsOver = null;
		return false;
	}

	/**
	 * updateFlightPlan: Handles updating the flight plan when a flight passes
	 * through a waypoint
	 */
	public void updateFlightPlan(ScoreTracking score) {
		int waypointScore = 0;
		// Check to see if there are still waypoints to visit and
		// then check if the flight is passing through waypoint
		if (this.currentRoute.size() > 0) {
			if (this.flight.checkIfFlightAtWaypoint(currentRoute.get(0))) {
				this.waypointsAlreadyVisited.add(this.currentRoute.get(0));

				// get the closest distance from the waypoint
				closestDistance = this.flight
						.minDistanceFromWaypoint(this.currentRoute.get(0));
				flight.resetMinDistanceFromWaypoint();
				// update the score based on how close to the
				// waypoints
				waypointScore = score.updateWaypointScore(closestDistance);
				score.increaseMultiplierOnWaypointPassed();

				this.currentRoute.remove(0);
			}
			score.updateScore(waypointScore, flight.isBonus());
		}
	}

	/**
	 * changeFlightPlan: Handles the user changing the flightplan using the
	 * mouse in plan mode
	 */
	public void changeFlightPlan(ScoreTracking score) {
		if (this.flight.getSelected() && this.currentRoute.size() > 0) {

			boolean mouseOverWaypoint = this.isMouseOnWaypoint();

			// Checks if user is not currently dragging a waypoint
			if (!draggingWaypoint) {
				// Checks if user has clicked on a waypoint
				if (mouseOverWaypoint
						&& Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					this.waypointClicked = this.waypointMouseIsOver;
					this.draggingWaypoint = true;
				}
			}

			// Checks if user is currently dragging a waypoint
			else if (draggingWaypoint) {
				// Checks if user has released mouse from drag
				// over empty airspace
				if ((!Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON))
						&& !mouseOverWaypoint) {
					this.waypointClicked = null;
					this.draggingWaypoint = false;

				}

				// Checks if user has released mouse from drag
				// over another waypoint
				else if ((!Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON))
						&& mouseOverWaypoint) {

					// Finding waypoint that mouse is over
					for (int i = 0; i < this.currentRoute.size(); i++) {

						// Checks if new waypoint is not
						// already in the plan and adds
						// if not in plan
						if (this.waypointClicked == this.currentRoute.get(i)
								&& (!this.currentRoute
										.contains(this.waypointMouseIsOver))
								&& (!this.waypointsAlreadyVisited
										.contains(this.waypointMouseIsOver))) {
							this.currentRoute.remove(i);
							this.currentRoute.add(i, this.waypointMouseIsOver);
							this.waypointClicked = null;
							this.draggingWaypoint = false;

							// reduce the score if
							// the user changes the
							// flight plan
							score.reduceScoreOnFlightplanChange();
						}

						// else checks if waypoint
						// already in plan and doesn't
						// add if not
						else if (this.waypointClicked == this.currentRoute
								.get(i)
								&& ((this.currentRoute
										.contains(this.waypointMouseIsOver)) || (this.waypointsAlreadyVisited
										.contains(this.waypointMouseIsOver)))) {
							this.waypointClicked = null;
							this.draggingWaypoint = false;
							break;

						}
					}
				}
			}
		}
	}

	/**
	 * drawFlightsPlan: Draws the graphics required for the flightplan
	 * 
	 * @param g
	 *            Slick2d graphics object
	 * @param gs
	 *            Slick2d gamecontainer object
	 */
	public void drawFlightsPlan(Graphics g, GameContainer gc) {

		/* If there are any waypoints in the flight plan */
		if (this.currentRoute.size() > 0) {

			g.setColor(Color.cyan);

			// If not dragging waypoints, just draw lines between
			// all waypoints in plan
			if (!draggingWaypoint) {
				for (int i = 1; i < this.currentRoute.size(); i++) {
					g.drawLine((float) this.currentRoute.get(i).getX(),
							(float) this.currentRoute.get(i).getY(),
							(float) this.currentRoute.get(i - 1).getX(),
							(float) this.currentRoute.get(i - 1).getY());
				}
			}

			/* If a waypoint is being changed */
			else if (draggingWaypoint) {
				for (int i = 1; i < this.currentRoute.size(); i++) {

					// This is needed as i=1 behaviours
					// differently to other values of i
					// when first waypoint is being dragged.
					if (i == 1) {
						/*
						 * Draw the lines between the waypoints in plan mode
						 */
						if (this.waypointClicked == this.currentRoute.get(0)) {
							g.drawLine(Mouse.getX(), 600 - Mouse.getY(),
									(float) this.currentRoute.get(1).getX(),
									(float) this.currentRoute.get(1).getY());
						}

						/* Next waypoint */
						else if (this.waypointClicked == this.currentRoute
								.get(1)) {
							g.drawLine(
									(float) this.currentRoute.get(i + 1).getX(),
									(float) this.currentRoute.get(i + 1).getY(),
									Mouse.getX(), 600 - Mouse.getY());
							g.drawLine(
									(float) this.currentRoute.get(i - 1).getX(),
									(float) this.currentRoute.get(i - 1).getY(),
									Mouse.getX(), 600 - Mouse.getY());
							i++;

						}

						/*
						 * Random waypoint from the flight plan
						 */
						else {
							g.drawLine(
									(float) this.currentRoute.get(i).getX(),
									(float) this.currentRoute.get(i).getY(),
									(float) this.currentRoute.get(i - 1).getX(),
									(float) this.currentRoute.get(i - 1).getY());
						}

					}

					else {
						// If Waypoint is being changed
						// draw lines between mouse and
						// waypoint before and after the
						// waypoint being changed.
						if (this.waypointClicked == this.currentRoute.get(i)) {
							g.drawLine(
									(float) this.currentRoute.get(i + 1).getX(),
									(float) this.currentRoute.get(i + 1).getY(),
									Mouse.getX(), 600 - Mouse.getY());
							g.drawLine(
									(float) this.currentRoute.get(i - 1).getX(),
									(float) this.currentRoute.get(i - 1).getY(),
									Mouse.getX(), 600 - Mouse.getY());
							i++;
						}

						else {
							g.drawLine(
									(float) this.currentRoute.get(i).getX(),
									(float) this.currentRoute.get(i).getY(),
									(float) this.currentRoute.get(i - 1).getX(),
									(float) this.currentRoute.get(i - 1).getY());
						}
					}
				}
			}
		}
	}

	/**
	 * markUnavaliableWaypoints: Handles alerting the user to any waypoints that
	 * are invalid for selection
	 * 
	 * @param g
	 *            slick2d graphics object
	 * @param gc
	 *            slick2d gamecontainer object
	 */
	public void markUnavailableWaypoints(Graphics g, GameContainer gc) {
		for (int i = 0; i < this.waypointsAlreadyVisited.size(); i++) {
			g.drawLine((float) this.waypointsAlreadyVisited.get(i).getX() - 14,
					(float) this.waypointsAlreadyVisited.get(i).getY() - 14,
					(float) this.waypointsAlreadyVisited.get(i).getX() + 14,
					(float) this.waypointsAlreadyVisited.get(i).getY() + 14);
			g.drawLine((float) this.waypointsAlreadyVisited.get(i).getX() + 14,
					(float) this.waypointsAlreadyVisited.get(i).getY() - 14,
					(float) this.waypointsAlreadyVisited.get(i).getX() - 14,
					(float) this.waypointsAlreadyVisited.get(i).getY() + 14);
		}
	}

	/**
	 * Update method to keep track of score
	 * 
	 * @param score
	 *            takes the score
	 */
	public void update(ScoreTracking score) {

		this.updateFlightPlan(score);
		if (this.changingPlan == true) {
			this.changeFlightPlan(score);
		}
	}

	/**
	 * Renders the game graphics
	 * 
	 * @param g
	 *            graphics object
	 * @param gc
	 *            game container
	 * @throws SlickException
	 */
	public void render(Graphics g, GameContainer gc) throws SlickException {

		if (this.flight.getSelected()) {
			if (this.changingPlan == true) {
				/*
				 * Draws the lines between waypoints to show the flight plan
				 */
				this.drawFlightsPlan(g, gc);
				/* Mark teh unavailable waypoints with an X */
				this.markUnavailableWaypoints(g, gc);
			}
		}
	}

	// ACCESSORS AND MUTATORS

	public List<Point> getCurrentRoute() {
		return currentRoute;
	}

	public Point getPointByIndex(int i) {
		return this.currentRoute.get(i);

	}

	public boolean getChangingPlan() {
		return this.changingPlan;
	}

	public void setChangingPlan(boolean bool) {
		this.changingPlan = bool;
	}

	public boolean getDraggingWaypoint() {
		return this.draggingWaypoint;
	}

	public EntryPoint getEntryPoint() {
		return this.entryPoint;
	}

	public ExitPoint getExitPoint() {
		return exitPoint;
	}

	@Override
	public String toString() {
		String returnString = "";
		if (currentRoute.size() > 0)
			returnString = currentRoute.get(0).getPointRef();
		for (int i = 1; i < currentRoute.size(); i++) {
			returnString += ", " + currentRoute.get(i).getPointRef();
		}
		return returnString;
	}

}