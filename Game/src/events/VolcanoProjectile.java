package events;

import java.awt.geom.Point2D;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import stateContainer.Game;

public class VolcanoProjectile {
	
	private double x,y, climbRate = 60/60.0;
	private int heading, velocity, currentAltitude, targetAltitude;
	private static double gameScale = 1/1000.0;
	private Image volcanoProjectileImage;
	
	
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
	
	/**
	 * updateXYCoordinates: updates the x and y values of the projectiles depending on it's velocity 
	 * and it's current heading. The velocity of the plane is scaled so that it can be used for 
	 * movement in terms of pixels.
	 */
	
	public void updateXYCoordinates() {
		double vs = velocity *gameScale;
		
		this.x += vs * Math.sin(Math.toRadians(heading));

		this.y -= vs * Math.cos(Math.toRadians(heading));

	}
	
	/**
	 * init: Initialises all the resources required for the volcano projectile class.
	 * @param gc GameContainer
	 * @throws SlickException
	 */
	public void init(GameContainer gc) throws SlickException{
		volcanoProjectileImage = new Image("res/graphics/new/volcanoProjectile.png");
	}
	
	/**
	 * render: Render all of the graphics for the volcano projectile
	 * @param g Graphics
	 * @param gc GameContainer
	 * 
	 * @throws SlickException
	 */
	public void render(Graphics g, GameContainer gc) throws SlickException{
		
		
		volcanoProjectileImage.setRotation(volcanoProjectileImage.getRotation()+5);
		g.setWorldClip(11, 0, Game.MAXIMUMWIDTH -11, Game.MAXIMUMHEIGHT-40);
		volcanoProjectileImage.drawCentered((float)x, (float) y);
		g.setWorldClip(0, 0, Game.MAXIMUMWIDTH, Game.MAXIMUMHEIGHT);
	}
	
	/**
	 * update: Update all logic in the volcano projectile class
	 * @param gc GameContainer
	 */
	
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
	
	public void setCurrentAltitude(int altitude){
		this.currentAltitude = altitude;
	}
	
	public int getTargetAltitude(){
		return targetAltitude;
	}
	
	public void setTargetAltitude(int targetAltitude){
		this.targetAltitude = targetAltitude;
	}
	
	public int getCurrentAltitude(){
		return currentAltitude;
	}

}
