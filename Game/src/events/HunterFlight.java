package events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

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
	
	
	public HunterFlight(Airspace airspace, EventController eventController){
		x = 600;
		y = 300;
		currentHeading = 225;
		targetHeading = 225;
		velocity = 300;
		turningLeft = false;
		turningRight = false;
		hasVictim = false;
		generateVictim(airspace);
		eventController.addHunterFlight(this);
	}
	
	public void generateVictim(Airspace airspace){
		if (airspace.getListOfFlightsInAirspace().size() == 0){
			;
		}
		else{
			hasVictim = true;
			victim = airspace.getListOfFlightsInAirspace().get(0);
		}
	}
	
	public int generateX(){
		if (Math.random() < 0.5){
			return 0;
		}
		else {
			return 1200;
		}
	}
	
	public void calculateHeadingToVictim() {
		
		if (victim != null){
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
		
	}
	
	public void updateXYCoordinates() {
		
		double vs = velocity * gameScale;
		x += vs * Math.sin(Math.toRadians(currentHeading));
		y -= vs * Math.cos(Math.toRadians(currentHeading));
	}
	
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
	
	public boolean inAirspace(){
		
		if (x > 1220 || x < -20|| y > 620 || y < -20) { 
			return false;
		}
		else 
		{
			return true;
		}
	}
	
	public void drawHunterFlight(Graphics g, GameContainer gc ){

		hunterFlightImage.draw((int) this.x, (int) this.y);
		hunterFlightImage.setRotation((int) currentHeading);
		
	}
	public void init(GameContainer gc) throws SlickException {
		hunterFlightImage = new Image("res/graphics/flight_fast.png");
	}
	
	public void update(Airspace airspace) {
		if (!hasVictim){
			generateVictim(airspace);
		}
		updateCurrentHeading();
		updateXYCoordinates();	
	}
	
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

}
