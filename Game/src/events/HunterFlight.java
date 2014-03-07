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
	private boolean turningLeft, turningRight;
	
	
	public HunterFlight(Airspace airspace){
		x = 0;
		y = 0;
		currentHeading = 0;
		targetHeading = 0;
		velocity = 500;
		victim = generateVictim(airspace);
		entryPoint = generateEntryPoint(airspace);
		turningLeft = false;
		turningRight = false;
	}
	
	public Flight generateVictim(Airspace airspace){
		return airspace.getListOfFlightsInAirspace().get(0);
	}
	
	public EntryPoint generateEntryPoint(Airspace airspace){
		
		Random rand = new Random();
		int randomNumber = rand.nextInt(airspace.getListOfEntryPoints().size());
		x = (airspace.getListOfEntryPoints().get(randomNumber).getX()); 
		y = (airspace.getListOfEntryPoints().get(randomNumber).getY());
			
		return airspace.getListOfEntryPoints().get(randomNumber);
	}
	
	public void calculateHeadingToVictim() {
		
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
	
	public void updateXYCoordinates() {
		
		double vs = velocity *gameScale;
		x += vs * Math.sin(Math.toRadians(currentHeading));
		y -= vs * Math.cos(Math.toRadians(currentHeading));
	}
	
	public void updateCurrentHeading() {
		
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
			}

			// If plane is already turning left or user has told it to turn left
			
			if (this.turningLeft == true) {
				this.currentHeading -= turnRate;
				if (Math.round(this.currentHeading) <= 0 && this.targetHeading != 0) {
					this.currentHeading = 360;
				}
			}
		}
	}
	
	public void init(GameContainer gc) throws SlickException {
		hunterFlightImage = new Image("res/graphics/flight_fast.png");
	}
	
	public void update() {
		updateCurrentHeading();
		calculateHeadingToVictim();
		updateXYCoordinates();
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}

}
