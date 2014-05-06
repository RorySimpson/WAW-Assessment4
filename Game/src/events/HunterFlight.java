package events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import stateContainer.Game;
import logicClasses.*;

public class HunterFlight {
	
	private static Image hunterFlightImage;
	private static double gameScale = 1/1000.0;
	private double turnRate = 0.9;
	
	private double x, y;
	private double currentHeading, targetHeading;
	private int velocity;
	private EntryPoint entryPoint;
	private Flight victim;
	private boolean turningLeft, turningRight, hasVictim;
	private Airspace airspace;
	
	
	public HunterFlight(Airspace airspace){
		x = generateX();
		y = -10;
		this.airspace = airspace;
		currentHeading = 225;
		targetHeading = 225;
		velocity = 150;
		turningLeft = false;
		turningRight = false;
		hasVictim = false;
		generateVictim(airspace);
	}
	
	/**
	 * generateVictim: Selects a flight in the airspace for the hunter flight
	 * to chase.
	 */
	
	public void generateVictim(Airspace airspace){
		
		// If airspace is empty don't pick flight
		if (airspace.getListOfFlightsInAirspace().size() == 0){
			;
		}
		
		// Otherwise loop through airspace and choose victim.
		else {
			for (int i = 0; i < airspace.getListOfFlightsInAirspace().size(); i++){
				if (!airspace.getListOfFlightsInAirspace().get(i).getFlightPlan().getEntryPoint().isRunway()){
					hasVictim = true;
					victim = airspace.getListOfFlightsInAirspace().get(i);
				}
			}
		}
	}
	
	/**
	 * generateX: Selects whether the flight will enter from the top left hand corner or the top right
	 * hand corner.
	 */
	
	public int generateX(){
		// If random number is less than 0.5, then spawn on left hand side
		if (Math.random() < 0.5){
			return 0;
		}
		// Otherwise then spawn on right hand side
		else {
			return 1200;
		}
	}
	
	/**
	 * calculateHeadingToVictim: generate heading to the hunter flights victim.
	 */
	
	public void calculateHeadingToVictim() {
		
		if (victim != null){
			if(!airspace.checkIfFlightHasLeftAirspace(victim)){
				// If flight has victim and is within airspace
				// hunt the victim
				double deltaX;
				double deltaY;
				deltaY = victim.getY() - y;
				deltaX = victim.getX() - x;
				double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
				angle += 90;
				if (angle < 0) {
					angle += 360;
				}
				targetHeading = angle;
				
			}
			// Otherwise fly straight
			else{
				targetHeading = currentHeading;
			}
			
		}
		
		// Otherwise fly straight
		else {
			targetHeading = currentHeading;
		}
		
	}
	
	/**
	 * updateXYCoordinates: updates the x and y values of the plane depending on it's velocity 
	 * and it's current heading. The velocity of the plane is scaled so that it can be used for 
	 * movement in terms of pixels.
	 */
	
	public void updateXYCoordinates() {
		
		double vs = velocity * gameScale;
		x += vs * Math.sin(Math.toRadians(currentHeading));
		y -= vs * Math.cos(Math.toRadians(currentHeading));
	}
	
	/**
	 * updateCurrentHeading(): Moves the current heading towards the target heading. If a user has issued
	 * a heading but not specified what way to turn, this method will determine what way it would be quicker 
	 * to turn towards it's target heading.
	 */
	
	public void updateCurrentHeading() {
		
		calculateHeadingToVictim();
		if ((Math.round(this.targetHeading) <= Math.round(this.currentHeading) - 3 
				|| Math.round(this.targetHeading) >= Math.round(this.currentHeading) + 3)) {

			/*
			 * If plane has been given a heading so no turning direction specified,
			 * below works out whether it should turn left or right to that heading
			 */
			
			if(this.turningRight == false && this.turningLeft == false){

				if (Math.abs(this.targetHeading - this.currentHeading) == 180) {
					this.turningRight = true;
				} 
				
				else if (this.currentHeading + 180 <= 359){
					
					if (this.targetHeading < this.currentHeading + 180 && this.targetHeading > this.currentHeading){
						this.turningRight = true;
					}
					else {
						this.turningLeft = true;
					}
				}
				
				else {
					
					if (this.targetHeading > this.currentHeading - 180 && this.targetHeading < this.currentHeading){
						this.turningLeft = true;
					}
					else {
						this.turningRight = true;
					}
				}

			}
			
			// If plane is already turning right or user has told it to turn right
			
			if (this.turningRight == true) {
				this.currentHeading += turnRate;
				if (Math.round(this.currentHeading) >= 360 && this.targetHeading != 360) {
					this.currentHeading = 0;
				}
				turningRight = false;
			}

			// If plane is already turning left or user has told it to turn left
			
			if (this.turningLeft == true) {
				this.currentHeading -= turnRate;
				if (Math.round(this.currentHeading) <= 0 && this.targetHeading != 0) {
					this.currentHeading = 360;
				}
				turningLeft = false;
			}
		}
	}
	
	/**
	 * inAirspace: Calculates whether the hunter flight is still within the airspace.
	 */
	
	public boolean inAirspace(){
		
		// If flight not within airspace, return false
		if (x > 1220 || x < -20|| y > 620 || y < -20) { 
			return false;
		}
		// Otherwise return true
		else {
			return true;
		}
	}
	
	/**
	 * drawHunterFlight: Draws the graphical representation of the hunter flight in the airspace.
	 */
	
	public void drawHunterFlight(Graphics g, GameContainer gc ){
		
		g.setWorldClip(11, 0, Game.MAXIMUMWIDTH -11, Game.MAXIMUMHEIGHT-40);
		
		hunterFlightImage.setRotation((int) currentHeading);
		hunterFlightImage.drawCentered((int) this.x, (int) this.y);
		
		g.setWorldClip(0, 0, Game.MAXIMUMWIDTH, Game.MAXIMUMHEIGHT);
	}
	
	/**
	 * init: Initialises all the resources required for the hunter flight class.
	 * @param gc GameContainer
	 * @throws SlickException
	 */
	
	public void init(GameContainer gc) throws SlickException {
		hunterFlightImage = new Image("res/graphics/new/hunterflight.png");
	}
	
	/**
	 * update: Update all logic in the hunter flight class
	 * @param gc GameContainer
	 */
	
	public void update(Airspace airspace) {
		
		// If flight hasn't got a victim, generate a victim
		if (!hasVictim){
			generateVictim(airspace);
		}
		
		// Update Flight State
		updateCurrentHeading();
		updateXYCoordinates();	
	}
	
	/**
	 * render: Render all of the graphics for the hunter flight
	 * @param g Graphics
	 * @param gc GameContainer
	 * 
	 * @throws SlickException
	 */
	
	public void render(Graphics g, GameContainer gc) throws SlickException {
		this.drawHunterFlight(g, gc);	
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void setTargetHeading(double targetHeading){
		this.targetHeading = targetHeading;
	}
	
	public double getTargetHeading(){
		return targetHeading;
	}
	
	public boolean getHasVictim(){
		return hasVictim;
	}
	
	public double getCurrentHeading(){
		return currentHeading;
	}
	
	public void setCurrentHeading(double currentHeading){
		this.currentHeading = currentHeading;
	}
	
	public boolean getTurningRight(){
		return turningRight;
	}

}
