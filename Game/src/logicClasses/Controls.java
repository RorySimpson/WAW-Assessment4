package logicClasses;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.GameContainer;

public class Controls {

	// FIELDS
	// Sets the difficulty of the control scheme

	/* Store the difficulty value */
	private int difficultyValueOfGame;
	/* Each difficulty is assigned a number */
	public static int EASY = 1, NORMAL = 2, HARD = 3;

	private FlightMenu menu;
	/*
	 * Whether the mouse is held down on a mouse to change the heading or if the
	 * heading has already been changed
	 */
	private boolean mouseHeldDownOnFlight, headingAlreadyChangedByMouse;
	/* The currently selected flight */
	private Flight selectedFlight;
	/* Airspace object */
	private Airspace airspace;

	/**
	 * Constructor that initialises the needed resources
	 * 
	 * @param airspace
	 */
	public Controls(Airspace airspace) {
		// Initializes all boolean values controlling selections to
		// false
		mouseHeldDownOnFlight = false;
		headingAlreadyChangedByMouse = false;
		selectedFlight = null;
		this.airspace = airspace;

	}

	// INIT
	/**
	 * Initialises the resources needed
	 * 
	 * @param gc
	 *            game container
	 * @throws SlickException
	 */
	public void init(GameContainer gc) throws SlickException {
		menu = new FlightMenu(this.airspace);
		menu.init();
		menu.setInput(gc.getInput());
	}

	// METHODS
	/**
	 * Calculates the distance between 2 points
	 * 
	 * @param x1
	 *            x coordinate of the first point
	 * @param y1
	 *            y coordinate of the first point
	 * @param x2
	 *            x coordinate of the second point
	 * @param y2
	 *            y coordinate of the second point
	 * @return
	 */
	private double distance(double x1, double y1, double x2, double y2) {
		// DON'T PANIC, just Pythagora's
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	/**
	 * changeModeByClickingOnFlight: Handles changing between plan and nav modes
	 * by clicking on the selected flight
	 * 
	 * @param nearestFlight
	 *            Flight object
	 */
	public void changeModeByClickingOnFlight() {

		selectedFlight.getFlightPlan().setChangingPlan(
				!selectedFlight.getFlightPlan().getChangingPlan());
		System.out.println(selectedFlight.getFlightPlan().getChangingPlan());
	}

	/**
	 * checkSelected: Handles changing the selected flight and ensures that the
	 * flight is a valid selection Also makes sure that if two flights are
	 * intersecting that you only select one, not both
	 * 
	 * @param pointX
	 * @param pointY
	 * @param airspace
	 */
	public void checkSelected(int pointX, int pointY, Airspace airspace) {

		double minimumDistanceBetweenFlightAndMouseClick;// Distance
															// between
															// where you
															// clicked
															// on the
															// airspace
															// and the
															// closest
															// flight
		Flight nearestFlight;

		// If mouse is being held down don't change selected flight.
		if (mouseHeldDownOnFlight) {
			return;
		} else
			mouseHeldDownOnFlight = true;

		// continue only if first click
		// Checking if user is dragging a waypoint they can't change
		// flights
		if (selectedFlight != null) {
			if (selectedFlight.getFlightPlan().getDraggingWaypoint()) {
				return;
			}
		}

		// continue only if user is not dragging a waypoint

		// Working out nearest flight to click
		nearestFlight = null;
		minimumDistanceBetweenFlightAndMouseClick = Integer.MAX_VALUE;
		for (Flight f : airspace.getListOfFlights()) {
			double d = distance(pointX, pointY, f.getX(), f.getY());
			if (d < minimumDistanceBetweenFlightAndMouseClick) {
				nearestFlight = f;
				minimumDistanceBetweenFlightAndMouseClick = d;
			}
		}

		// Working out whether the nearest flight to click is close
		// enough
		// to be selected.
		if (minimumDistanceBetweenFlightAndMouseClick <= 50) { // If the mouse
																// if further
																// from the
																// flight than
																// 50 then it
																// cannot be
																// selected

			if (nearestFlight.isLanding() || !nearestFlight.isControllable()) {
				return;
			}

			if (nearestFlight == selectedFlight) { // If you are clicking on the
													// currently selected
													// flight then change the
													// airspace mode instead
													// of changing flight
				changeModeByClickingOnFlight();
			}

			// only allow switching flights if not in navigator mode
			else {

				if (selectedFlight != null) {
					// deselect old flight (if any)
					selectedFlight.setSelected(false);
					selectedFlight.getFlightPlan().setChangingPlan(false);

					// select new flight
					nearestFlight.setSelected(true);
					setSelectedFlight(nearestFlight);
				} else {
					// set selected flight
					nearestFlight.setSelected(true);
					setSelectedFlight(nearestFlight);
				}

			}
		}
	}

	/**
	 * giveHeadingWithMouse: Handles updating the currently selected flights
	 * heading by clicking in it's control circle with the left mouse button
	 * 
	 * @param pointX
	 *            X Coordinate of the mouse click
	 * @param pointY
	 *            Y Coordinate of the mouse click
	 * @param airspace
	 */
	public void giveHeadingWithMouse(int pointX, int pointY, Airspace airspace) {

		double deltaX, deltaY;
		double distanceBetweenMouseAndPlane;

		// If mouse is being held down don't change selected flight.
		if (headingAlreadyChangedByMouse) {
			return;
		} else {
			headingAlreadyChangedByMouse = true;
		}

		// Finding the distance between the mouse click and the plane
		distanceBetweenMouseAndPlane = distance(pointX, pointY,
				selectedFlight.getX(), selectedFlight.getY());
		// If the distance between the mouse and the plane is
		// greater than 50 then don't do anything
		if (distanceBetweenMouseAndPlane < 50) {
			deltaY = pointY - selectedFlight.getY();
			deltaX = pointX - selectedFlight.getX();
			double angle = Math.toDegrees(Math.atan2(deltaY, deltaX)); // Find
																		// the
																		// angle
																		// between
																		// the
																		// current
																		// heading
																		// and
																		// where
																		// the
																		// mouse
																		// was
																		// clicked
			angle += 90;
			if (angle < 0) {
				angle += 360;
			}
			selectedFlight.giveHeading((int) Math.round(angle));
		}
	}

	// RENDER AND UPDATE
	/**
	 * render: Render all of the graphics required by controls
	 * 
	 * @param g
	 *            The slick2d graphics object
	 * @param gc
	 *            The slick2d game container
	 * @throws SlickException
	 */
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (selectedFlight != null) {
			if (!selectedFlight.getFlightPlan().getChangingPlan()) {
				g.setColor(Color.white);

				menu.render(g, gc);
			}
		}
	}

	/**
	 * Update method to update all the resources
	 * 
	 * @param gc
	 *            game container
	 * @param airspace
	 *            game airspace
	 */
	public void update(GameContainer gc, Airspace airspace) {
		/* Get the mouse position */
		int posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT - Mouse.getY();

		// if controls are active
		if (selectedFlight != null) {
			// Only allow controls if user isn't changing a plan
			if (!(selectedFlight.getFlightPlan().getChangingPlan())
					&& selectedFlight.isCommandable()) {
				// allow mouse control of flight if not in h
				if (Mouse.isButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
					giveHeadingWithMouse(posX, posY, airspace);
				}
			}
		}

		/* If the mouse left button is pressed, select plane */
		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			checkSelected(posX, posY, airspace);
		} else {
			mouseHeldDownOnFlight = false;
		}

		/*
		 * If the right button is pressed, it means the user is changing the
		 * heading at that moment
		 */
		if (!Mouse.isButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
			headingAlreadyChangedByMouse = false;
		}

	}

	// MUTATORS AND ACCESSORS
	public void setSelectedFlight(Flight flight1) {
		selectedFlight = flight1;
		menu.setFlight(flight1);
	}

	public void setSelectedFlight(Flight flight1, boolean bool) {
		selectedFlight = flight1;

	}

	public Flight getSelectedFlight() {
		return selectedFlight;
	}

	public void setDifficultyValueOfGame(int value) {
		difficultyValueOfGame = value;
	}
}