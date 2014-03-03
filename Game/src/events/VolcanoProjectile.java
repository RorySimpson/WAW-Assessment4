package events;

import java.awt.geom.Point2D;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import stateContainer.Game;

public class VolcanoProjectile {
	
	private double x,y, climbRate = 60/60.0;
	private int heading, velocity, currentAltitude, targetAltitude;
	private static double gameScale = 1/1000.0;
	
	
	public VolcanoProjectile (){
		this.x = Volcano.VOLCANOPOSITION.getX(); 
		this.y = Volcano.VOLCANOPOSITION.getY(); 
		this.heading = generateRandomHeading();
		this.velocity = 400;
		this.currentAltitude = 1000;
		this.targetAltitude = 5000;
	}
	
	public int generateRandomHeading(){
		Random rand = new Random();
		return rand.nextInt(360);
		
	}
	
	public void updateAltitude(){
		
		if(this.currentAltitude == 5000){
			this.targetAltitude = 100;
		}
		
		if (this.currentAltitude > this.targetAltitude) {
			this.currentAltitude -= climbRate;
		}

		else if (this.currentAltitude < this.targetAltitude) {
			this.currentAltitude += climbRate;
		}
	}
	
	public void updateXYCoordinates() {
		double vs = velocity *gameScale;
		
		this.x += vs * Math.sin(Math.toRadians(heading));

		this.y -= vs * Math.cos(Math.toRadians(heading));

	}
	
	public void init(GameContainer gc) throws SlickException{
		
	}
	
	public void render(Graphics g, GameContainer gc) throws SlickException{
		
		g.setWorldClip(11, 0, Game.MAXIMUMWIDTH -11, Game.MAXIMUMHEIGHT-40);
		g.drawOval((float)x, (float) y, 10, 10);
		g.setWorldClip(0, 0, Game.MAXIMUMWIDTH, Game.MAXIMUMHEIGHT);
	}
	
	public void update(){
		updateXYCoordinates();
		updateAltitude();
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
