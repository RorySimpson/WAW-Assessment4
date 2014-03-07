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
	
	public double x, y;
	private double currentHeading, targetHeading;
	private int velocity;
	private EntryPoint entryPoint;
	private Flight victim;
	
	public HunterFlight(Airspace airspace){
		x = 0;
		y = 0;
		currentHeading = 0;
		targetHeading = 0;
		velocity = 500;
		victim = generateVictim(airspace);
		entryPoint = generateEntryPoint(airspace);
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
	
	public double calculateHeadingToVictim(Flight victim) {
		
		double deltaX;
		double deltaY;
		deltaY = victim.getY() - y;
		deltaX = victim.getX() - x;
		double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
		angle += 90;
		if (angle < 0) {
			angle += 360;
		}
		return angle;
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
