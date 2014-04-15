package competitive;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.awt.geom.Point2D;
import java.util.Random;

import logicClasses.Airspace;
import logicClasses.Flight;

public class CargoCompetitive {

	private Point2D location;
	private Flight currentHolder;


	CargoCompetitive(){
		this.location = generateRandomCargoLocation();
		this.currentHolder = null;
	}
	
	

	public Point2D generateRandomCargoLocation(){
		Point2D tempLocation = new Point2D.Float();
		Random rand = new Random();
		tempLocation.setLocation(rand.nextInt(1100) + 50, rand.nextInt(450) + 50);
		return tempLocation;
	}
	
	public void render(Graphics g, GameContainer gc){
		if(this.currentHolder == null){
			g.drawOval((float)location.getX(), (float)location.getY(), 10, 10);
		}
	}

	public Point2D getLocation() {
		return location;
	}

	public void setLocation(Point2D location) {
		this.location = location;
	}

	public Flight getCurrentHolder() {
		return currentHolder;
	}

	public void setCurrentHolder(Flight currentHolder) {
		this.currentHolder = currentHolder;
	}
}
