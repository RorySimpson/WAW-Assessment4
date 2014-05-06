package coop;

import logicClasses.Airspace;
import logicClasses.Controls;
import logicClasses.Flight;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ControlsCoop extends Controls {

	private FlightCoop selectedFlight1;
	private FlightCoop selectedFlight2;
	private AirspaceCoop airspaceCoop;
	private boolean selectedThisLoop;

	public ControlsCoop(AirspaceCoop airspace) {
		super(airspace);
		this.selectedFlight1 = null;
		this.selectedFlight2 = null;
		this.airspaceCoop = airspace;
		this.selectedThisLoop = false;
	}

	/**
	 * init: This method is overwritten to ensure that the flight menu used for
	 * single player isn't initialised.
	 */
	@Override
	public void init(GameContainer gc) throws SlickException {

	}

	/**
	 * update: Handles all key button control events for the cooperative mode.
	 */

	@Override
	public void update(GameContainer gc, Airspace airspace) {

		// Toggle through player 1 flights using "Left Control"
		if (gc.getInput().isKeyPressed(Input.KEY_LCONTROL)) {
			if (airspaceCoop.getListOfFlightsPlayer1().size() > 0) {
				// If a flight is already selected, make flight
				// at start of list selected
				if (this.selectedFlight1 == null) {
					for (int i = 0; i < airspaceCoop.getListOfFlightsPlayer1()
							.size(); i++) {
						if (airspaceCoop
								.getListOfFlightsPlayer1()
								.get(i
										% airspaceCoop
												.getListOfFlightsPlayer1()
												.size()).isSelectable()) {
							this.selectedFlight1 = airspaceCoop
									.getListOfFlightsPlayer1()
									.get(i
											% airspaceCoop
													.getListOfFlightsPlayer1()
													.size());
							this.selectedFlight1.setSelected(true);
							break;
						}
					}
				}
				// Otherwise toggle through to next flight in
				// list after already selected flight
				else {
					for (int i = 1; i <= airspaceCoop.getListOfFlightsPlayer1()
							.size(); i++) {
						if (airspaceCoop
								.getListOfFlightsPlayer1()
								.get((i + airspaceCoop
										.getListOfFlightsPlayer1().indexOf(
												selectedFlight1))
										% airspaceCoop
												.getListOfFlightsPlayer1()
												.size()).isSelectable()) {
							this.selectedFlight1.setSelected(false);
							this.selectedFlight1 = airspaceCoop
									.getListOfFlightsPlayer1()
									.get((i + airspaceCoop
											.getListOfFlightsPlayer1().indexOf(
													selectedFlight1))
											% airspaceCoop
													.getListOfFlightsPlayer1()
													.size());
							this.selectedFlight1.setSelected(true);
							break;
						}

					}
				}

			}

		}

		// Toggle through player 2 flights using "Right Control"
		if (gc.getInput().isKeyPressed(Input.KEY_RCONTROL)) {
			if (airspaceCoop.getListOfFlightsPlayer2().size() > 0) {
				// If a flight is already selected, make flight
				// at start of list selected
				if (this.selectedFlight2 == null) {
					for (int i = 0; i < airspaceCoop.getListOfFlightsPlayer2()
							.size(); i++) {
						if (airspaceCoop
								.getListOfFlightsPlayer2()
								.get(i
										% airspaceCoop
												.getListOfFlightsPlayer2()
												.size()).isSelectable()) {
							this.selectedFlight2 = airspaceCoop
									.getListOfFlightsPlayer2()
									.get(i
											% airspaceCoop
													.getListOfFlightsPlayer2()
													.size());
							this.selectedFlight2.setSelected(true);
							break;
						}
					}
				}
				// Otherwise toggle through to next flight in
				// list after already selected flight
				else {
					for (int i = 1; i <= airspaceCoop.getListOfFlightsPlayer2()
							.size(); i++) {
						if (airspaceCoop
								.getListOfFlightsPlayer2()
								.get((i + airspaceCoop
										.getListOfFlightsPlayer2().indexOf(
												selectedFlight2))
										% airspaceCoop
												.getListOfFlightsPlayer2()
												.size()).isSelectable()) {
							this.selectedFlight2.setSelected(false);
							this.selectedFlight2 = airspaceCoop
									.getListOfFlightsPlayer2()
									.get((i + airspaceCoop
											.getListOfFlightsPlayer2().indexOf(
													selectedFlight2))
											% airspaceCoop
													.getListOfFlightsPlayer2()
													.size());
							this.selectedFlight2.setSelected(true);
							break;
						}

					}
				}

			}

		}

		if (this.selectedFlight1 != null) {

			// If flight isn't waiting to take off, allow the below
			// commands.
			if (!this.getSelectedFlight1().isGrounded()) {

				// Press 'A' to turn left
				if (gc.getInput().isKeyDown(Input.KEY_A)) {
					this.selectedFlight1.decrementHeading();
				}

				// Press "D" to turn right
				if (gc.getInput().isKeyDown(Input.KEY_D)) {
					this.selectedFlight1.incrementHeading();
				}

				// Press "S" to reduce altitude
				if (gc.getInput().isKeyPressed(Input.KEY_S)) {
					if (this.selectedFlight1.getTargetAltitude() > 2000) {
						this.selectedFlight1
								.setTargetAltitude(this.selectedFlight1
										.getTargetAltitude() - 1000);
					}
				}

				// Press "W" to raise altitude
				if (gc.getInput().isKeyPressed(Input.KEY_W)) {
					if (this.selectedFlight1.getTargetAltitude() < 5000) {
						this.selectedFlight1
								.setTargetAltitude(this.selectedFlight1
										.getTargetAltitude() + 1000);
					}
				}
			}

			// Press "Q" to take a flight off
			if (gc.getInput().isKeyDown(Input.KEY_Q)) {
				this.selectedFlight1.takeOff(this.selectedFlight1);
			}

			// Press "E" to land a flight
			if (gc.getInput().isKeyDown(Input.KEY_E)) {
				this.selectedFlight1.land();
			}

			// Press "H" to hand a flight over to the other player
			if (gc.getInput().isKeyDown(Input.KEY_H)) {
				this.selectedFlight1.setSelected(false);
				airspaceCoop.getListOfFlightsPlayer2()
						.add(this.selectedFlight1);
				airspaceCoop.getListOfFlightsPlayer1().remove(
						this.selectedFlight1);
				this.selectedFlight1.setPlayer2(true);
				this.selectedFlight1 = null;
			}

		}
		if (this.selectedFlight2 != null) {

			// If flight isn't waiting to take off, allow the below
			// commands.
			if (!this.getSelectedFlight2().isGrounded()) {

				// Press Left Arrow Key to turn left
				if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
					this.selectedFlight2.decrementHeading();
				}

				// Press Right Arrow Key to turn right
				if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
					this.selectedFlight2.incrementHeading();
				}

				// Press Down Arrow Key to decrease altitude
				if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
					if (this.selectedFlight2.getTargetAltitude() > 2000) {
						this.selectedFlight2
								.setTargetAltitude(this.selectedFlight2
										.getTargetAltitude() - 1000);
					}
				}

				// Press Up Arrow Key to increase altitude
				if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
					if (this.selectedFlight2.getTargetAltitude() < 5000) {
						this.selectedFlight2
								.setTargetAltitude(this.selectedFlight2
										.getTargetAltitude() + 1000);
					}
				}

			}

			// Press Right Shift Key to take a flight off
			if (gc.getInput().isKeyDown(Input.KEY_RSHIFT)) {
				(this.selectedFlight2).takeOff(this.selectedFlight2);
			}

			// Press Delete Key to land a flight
			if (gc.getInput().isKeyDown(Input.KEY_DELETE)) {
				this.selectedFlight2.land();
			}

			// Press Page Down Key to hand a flight to the other
			// player
			if (gc.getInput().isKeyDown(Input.KEY_NEXT)) {
				this.selectedFlight2.setSelected(false);
				airspaceCoop.getListOfFlightsPlayer1()
						.add(this.selectedFlight2);
				airspaceCoop.getListOfFlightsPlayer2().remove(
						this.selectedFlight2);
				this.selectedFlight2.setPlayer2(false);
				this.selectedFlight2 = null;
			}

		}

	}

	/**
	 * render: Render all of the graphics for the controls coop
	 * 
	 * @param g
	 *            Graphics
	 * @param gc
	 *            GameContainer
	 * 
	 * @throws SlickException
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {

		if (selectedFlight1 != null) {

			// Hold 'F' to view flight plan of selected flight
			if (gc.getInput().isKeyDown(Input.KEY_F)) {
				this.selectedFlight1.drawFlightPlan(gc, g);

			}

			g.setColor(Color.white);
			g.drawString("P1", (int) this.selectedFlight1.getX() - 5,
					(int) this.selectedFlight1.getY() - 45);

		}

		if (selectedFlight2 != null) {

			// Hold Page Down Key to view plan of a selected flight
			if (gc.getInput().isKeyDown(Input.KEY_END)) {
				this.selectedFlight2.drawFlightPlan(gc, g);

			}

			g.setColor(Color.white);
			g.drawString("P2", (int) this.selectedFlight2.getX() - 5,
					(int) this.selectedFlight2.getY() - 45);

		}

	}

	public Flight getSelectedFlight1() {
		return selectedFlight1;
	}

	public void setSelectedFlight1(FlightCoop selectedFlight1) {
		this.selectedFlight1 = selectedFlight1;
	}

	public Flight getSelectedFlight2() {
		return selectedFlight2;
	}

	public void setSelectedFlight2(FlightCoop selectedFlight2) {
		this.selectedFlight2 = selectedFlight2;
	}

}
