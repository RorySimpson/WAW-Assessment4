package logicClasses;
import java.awt.geom.Point2D;
import java.util.Random;

import events.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;



public class SeparationRules {

	//FIELDS

	protected int warningLateralSeparation, warningVerticalSeparation; 
	protected int gameOverLateralSeparation, gameOverVerticalSeparation;
	/* Whether the game over violation occurred */
	protected boolean gameOverViolation; 
	/* Where the crash happened */
	protected Point2D pointOfCrash;
	/* Distances for collision and hunterflight */
	protected final static int COLLISIONDISTANCE = 10;
	protected final static int HUNTERFLIGHTCOLLISIONDISTANCE = 25;

	//CONSTRUCTOR
	/**
	 * Constructor that sets the default values
	 * @param difficultyVal default values depend on the difficulty chosen
	 */
	public SeparationRules(int difficultyVal){
		/* Sets the warning separation */
		this.warningLateralSeparation = 100; 
		this.warningVerticalSeparation = 999; 
		this.gameOverViolation = false;
		pointOfCrash = new Point2D.Float();

		if (difficultyVal == 1) { // Easy: Only a Crash will cause a Game Over
			this.gameOverLateralSeparation = 30;
			this.gameOverVerticalSeparation = 200;
		}
		if (difficultyVal == 2) { // Medium: Can Violate, but not too closely
			this.gameOverLateralSeparation = 60;
			this.gameOverVerticalSeparation = 350;
		}
		if (difficultyVal == 3) { // Hard: Minimal Warning Violation allowed before end game achieved.
			this.gameOverLateralSeparation = 90;
			this.gameOverVerticalSeparation = 500;
		}

	}

	//METHODS

	/**
	 * lateralDistanceBetweenFlights: Calculates the lateral distance between two flights.
	 * @param flight1 - A flight from the airspace.
	 * @param flight2 - A flight from the airspace.
	 * @return A double representing the lateral distance between the two flights passed as parameters.
	 */
	public double lateralDistanceBetweenFlights(Flight flight1, Flight flight2){
		return Math.sqrt(Math.pow((flight1.getX() - flight2.getX()), 2) + Math.pow(( flight1.getY() - flight2.getY()),2));
	}

	public double lateralDistanceBetweenFlightAndHunterFlight(Flight flight1, HunterFlight flight2){
		return Math.sqrt(Math.pow((flight1.getX() - flight2.getX()), 2) + Math.pow(( flight1.getY() - flight2.getY()),2));
	}

	public double lateralDistanceBetweenFlightAndProjectile(Flight flight, VolcanoProjectile projectile){
		return Math.sqrt(Math.pow((flight.getX() - projectile.getX()), 2) + Math.pow(( flight.getY() - projectile.getY()),2));
	}

	public double lateralDistanceBetweenFlightAndTornado(Flight flight, Tornado tornado){
		return Math.sqrt(Math.pow((flight.getX() - tornado.getX()), 2) + Math.pow(( flight.getY() - tornado.getY()),2));
	}

	/**
	 * verticalDistanceBetweenFlights: Calculates the vertical distance between two flights.
	 * @param flight1 - A flight from the airspace.
	 * @param flight2 - A flight from the airspace.
	 * @return An int representing the vertical distance between the two flights passed as parameters.
	 */

	public int verticalDistanceBetweenFlights(Flight flight1, Flight flight2){
		return Math.abs(flight1.getAltitude() - flight2.getAltitude());	
	}

	/**
	 * checkViolation: Calculates whether two flights have breached the game over separation rules.
	 * @param airspace - The airspace object is passed as the checkViolation() method requires knowledge of
	 * flights in the airspace, which is stored within the airspace.
	 */

	public void checkFlightOnFlightViolation(Airspace airspace){

		for (int i = 0; i < airspace.getListOfFlights().size(); i++){

			for (int j = i+1; j < airspace.getListOfFlights().size(); j++){

				if ((lateralDistanceBetweenFlights(airspace.getListOfFlights().get(i), airspace.getListOfFlights().get(j)) < this.gameOverLateralSeparation)){
					if ((verticalDistanceBetweenFlights(airspace.getListOfFlights().get(i), airspace.getListOfFlights().get(j)) < this.gameOverVerticalSeparation)){
						if(!(airspace.getListOfFlights().get(i).isGrounded() || airspace.getListOfFlights().get(j).isGrounded())){
							this.gameOverViolation = true;
							airspace.getListOfFlights().get(i).setVelocity(0);
							airspace.getListOfFlights().get(i).setTargetVelocity(0);
							airspace.getListOfFlights().get(j).setVelocity(0);
							airspace.getListOfFlights().get(j).setTargetVelocity(0);
							this.pointOfCrash.setLocation(airspace.getListOfFlights().get(i).getX(), airspace.getListOfFlights().get(i).getY());
						}
					}
				}
			}
		}
	}

	/**
	 * Checks if a volcano projectile hit a plane, and if it did, it stops the plane and
	 * calls the game over screen
	 * @param airspace
	 */
	public void checkVolcanoProjectileOnFlightCollision(Airspace airspace){

		for(VolcanoProjectile projectile: airspace.getEventController().getVolcano().getListOfProjectilesLaunched()){
			for(Flight flight: airspace.getListOfFlights()){
				if(lateralDistanceBetweenFlightAndProjectile(flight, projectile) <= COLLISIONDISTANCE){
					this.gameOverViolation = true;
					flight.setVelocity(0);
					flight.setVelocity(0);
					this.pointOfCrash.setLocation(flight.getX(), flight.getY());
				}
			}
		}
	}

	/**
	 * Checks whether a tornado hit a plane, and if it did, change the altitude and heading
	 * to a random number within the limits
	 * @param airspace
	 */
	public void checkTornadoOnFlightCollision(Airspace airspace){

		for(Tornado tornado: airspace.getEventController().getListOfTornados()){
			for(Flight flight: airspace.getListOfFlights()){
				if((lateralDistanceBetweenFlightAndTornado(flight, tornado) <= warningLateralSeparation - 40)
						&& (flight.getAltitude() > 0)){
					Random rand = new Random();
					int random1 = (rand.nextInt(4) + 2) * 1000;
					int random2 = rand.nextInt(360);

					flight.setCurrentAltitude(random1);
					flight.setAltitude(random1);
					flight.setTargetAltitude(random1);

					flight.setCurrentHeading(random2);
					this.pointOfCrash.setLocation(flight.getX(), flight.getY());
				}
			}
		}
	}

	/**
	 * Checks if the hunter flight collided with any of the planes, and if it did
	 * stops the plane and calls a game over screen
	 * @param airspace
	 */
	public void checkHunterFlightCollision(Airspace airspace){

		for (HunterFlight hunterFlight : airspace.getEventController().getListOfHunterFlights()){
			for (Flight flight : airspace.getListOfFlights()){
				if (lateralDistanceBetweenFlightAndHunterFlight(flight, hunterFlight) <= HUNTERFLIGHTCOLLISIONDISTANCE){
					this.gameOverViolation = true;
					flight.setVelocity(0);
					this.pointOfCrash.setLocation(flight.getX(), flight.getY());
				}
			}
		}
	}



	/**
	 * render: This calculates whether any flights in the airspace are breaking warning separation rules
	 * If two flight are breaking warning separation rules, a line is drawn between them.
	 * @param g - Graphics libraries required by slick2d.
	 * @param gc - GameContainer required by slick2d.
	 * @param airspace - The airspace object is passed as the render method requires knowledge of
	 * flights in the airspace, which is stored within the airspace. 
	 */
	public void render(Graphics g, GameContainer gc, Airspace airspace){

		for (int i = 0; i < airspace.getListOfFlights().size(); i++) {
			for (int j = i + 1; j < airspace.getListOfFlights().size(); j++ ) {	
				if (this.lateralDistanceBetweenFlights(airspace.getListOfFlights().get(i), 
						airspace.getListOfFlights().get(j)) <= this.getWarningLateralSeparation()) {
					if (this.verticalDistanceBetweenFlights(airspace.getListOfFlights().get(i), 
							airspace.getListOfFlights().get(j)) <= this.getWarningVerticalSeparation()) {

						if(!(airspace.getListOfFlights().get(i).isGrounded() || airspace.getListOfFlights().get(j).isGrounded())){
							float f1x = (float) airspace.getListOfFlights().get(i).getX();
							float f1y = (float) airspace.getListOfFlights().get(i).getY();
							float f2x = (float) airspace.getListOfFlights().get(j).getX();
							float f2y = (float) airspace.getListOfFlights().get(j).getY();
							g.setColor(Color.red);
							g.setLineWidth(2);
							g.drawLine(f1x, f1y, f2x, f2y);
							g.setLineWidth(1);
						}
					}
				}
			}
		}
	}

	/**
	 * update: This calls the checkViolation method to detect whether the game over separation rules
	 * have been breached.
	 * @param airspace - The airspace object is passed as the checkViolation method requires knowledge of
	 * flights in the airspace, which is stored within the airspace.
	 */
	public void update(Airspace airspace) {

		this.checkFlightOnFlightViolation(airspace);
		this.checkVolcanoProjectileOnFlightCollision(airspace);
		this.checkTornadoOnFlightCollision(airspace);
		checkHunterFlightCollision(airspace);
	}


	//MUTATORS AND ACCESSORS

	public void setGameOverLateralSeparation(int lateralSeparation){
		this.gameOverLateralSeparation = lateralSeparation;
	}

	public void setGameOverVerticalSeparation(int verticalSeparation){
		this.gameOverVerticalSeparation = verticalSeparation;
	}

	public int getGameOverLateralSeparation(){
		return this.gameOverLateralSeparation;
	}

	public int getGameOverVerticalSeparation(){
		return this.gameOverVerticalSeparation;
	}

	public void setGameOverViolation(boolean gameOverViolation) {
		this.gameOverViolation = gameOverViolation;
	}

	public int getWarningLateralSeparation() {
		return this.warningLateralSeparation;
	}

	public int getWarningVerticalSeparation(){
		return this.warningVerticalSeparation;
	}


	public boolean getGameOverViolation(){
		return this.gameOverViolation;
	}

	public Point2D getPointOfCrash() {
		return pointOfCrash;
	}

	public void setPointOfCrash(Point2D pointOfCrash) {
		this.pointOfCrash = pointOfCrash;
	}
}