package events;

import java.awt.geom.Point2D;
import java.util.Random;

public class VolcanoProjectile {
	
	private double x,y;
	private int heading, velocity;
	private static double gameScale = 1/1000.0;
	
	
	public VolcanoProjectile (){
		this.x = Volcano.VOLCANOPOSITION.getX(); 
		this.y = Volcano.VOLCANOPOSITION.getY(); 
		this.heading = generateRandomHeading();
		this.velocity = 400;
	}
	
	public int generateRandomHeading(){
		Random rand = new Random();
		return rand.nextInt(360);
		
	}
	
	public void updateXYCoordinates() {
		double vs = velocity *gameScale;
		
		this.x += vs * Math.sin(Math.toRadians(heading));

		this.y -= vs * Math.cos(Math.toRadians(heading));

	}
	
	public void init(){
		
	}
	
	public void render(){
		
	}
	
	public void update(){
		updateXYCoordinates();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
