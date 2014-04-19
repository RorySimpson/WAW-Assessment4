package events;

import java.util.Random;

import logicClasses.Airspace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import stateContainer.Game;



public class Tornado {

	// ATTRIBUTES
	
	// Image
	private Image tornadoImage;
	
	// Coordinates
	private double x, y;
	
	// Whether the tornado is attacking at the moment
	boolean attacking;
	
	// Timer for how long until the tornado tries to leave 
	int countdownTillTornadoTriesToLeave;
	
	// Save the coordinates from which the Tornado came to get a better path when leaving
	double originalX;
	double originalY;
	
	// Whether the plane leaves through the bottom or the top of the screen
	Random rand = new Random();
	int randomDirection;
	
	// Constructor
	public Tornado(Airspace airspace){
		
		attacking 		= false;
		countdownTillTornadoTriesToLeave = 450; // 7.5 seconds
		this.randomiseLocation();
		Random rand = new Random();
		randomDirection = rand.nextInt(2);
	}
	
	public void randomiseLocation(){
		Random rand = new Random();
		int random = rand.nextInt(9);
	
		// Some set of starting locations on the edges of the screen
		switch (random){
			case 0:
				this.x = 0;
				this.y = 400;
				break;
			case 1:
				this.x = 1000;
				this.y = 600;
				break;
			case 2:
				this.x = 1200;
				this.y = 100;
				break;
			case 4:
				this.x = 1200;
				this.y = 500;
				break;
			case 5:
				this.x = 200;
				this.y = 600;
				break;
			case 6:
				this.x = 200;
				this.y = 0;
				break;
			case 7:
				this.x = 850;
				this.y = 0;
				break;
			case 8:
				this.x = 0;
				this.y = 100;
				break;
		}
	
		originalX = this.x;
		originalY = this.y;
	}
	
	// Tornado enters the screen
	public void attack(){
		attacking = true;
	}
	
	// Checks if the tornado is still within the airspace
	public boolean inAirspace(){
	
		if (this.x > 1220 || this.x < -20|| this.y > 620 || this.y < -20) { 
			return false;
		}
		else 
		{
			return true;
		}
	}
	
	public double getX()
		{
			return x;
		}

	public double getY()
		{
			return y;
		}

	// Update coordinates
	public void updateXY(){
		Random rand = new Random();
		int directionx = rand.nextInt(4)-2;
		int directiony = rand.nextInt(4)-2;
				
		if (countdownTillTornadoTriesToLeave > 0){
			
			this.countdownTillTornadoTriesToLeave -= 1;
			
			if (this.x <= 600){
				this.x -= directionx;
			} else {
				this.x += directionx;
			}
			
			if (this.y <= 300){
				this.y -= directiony;
			} else {
				this.y += directiony;
			}
		} else {
			if (randomDirection % 2 == 0){
				if (this.x <= originalX - 600){
					this.x += directionx;
				} else {
					this.x -= directionx;
				}
				
				if (this.y <= originalY - 300){
					this.y += directiony;
				} else {
					this.y += directiony;
				}
			} else {
				if (this.x <= originalX - 500){
					this.x += directionx;
				} else {
					this.x -= directionx;
				}
				if (this.y <= originalY - 400){
					this.y += directiony;
				} else {
					this.y -= directiony;
				}
			}
			
			
		}
	}
	
	// Initialise
	public void init (GameContainer gc) throws SlickException{
		tornadoImage = new Image("res/graphics/new/tornado.png");
		}
	
	// Renders the image
	public void render(Graphics g, GameContainer gc) throws SlickException{
		g.setWorldClip(11, 0, Game.MAXIMUMWIDTH -11, Game.MAXIMUMHEIGHT-40);
		tornadoImage.setRotation(tornadoImage.getRotation()+50);
		tornadoImage.draw((int) this.x, (int) this.y);
		g.setWorldClip(0, 0, Game.MAXIMUMWIDTH, Game.MAXIMUMHEIGHT);
	}
	
	// Updates the logic
	public void update(GameContainer gc) throws SlickException{
		if (attacking && this.inAirspace()){
			this.updateXY();
		}
	}
	
	public double getOriginalX(){
		return this.originalX;
	}
	
	public double getOriginalY(){
		return this.originalY;
	}
	
	public void setOriginalX(double x){
		this.originalX = x;
	}
	
	public void setOriginalY(double y){
		this.originalY = y;
	}

	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public boolean getAttacking(){
		return attacking;
	}
	
	public void setWhatAmIDoing(int whatAmIDoing){
		this.countdownTillTornadoTriesToLeave = whatAmIDoing;
	}
}
