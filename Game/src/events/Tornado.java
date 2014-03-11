package events;

import java.util.List;
import java.util.Random;

import logicClasses.Airspace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;



public class Tornado {

	// ATTRIBUTES
	
	// Image
	private Image tornadoImage;
	
	// Coordinates
	private double x, y;
	
	// Whether the tornado is attacking at the moment
	boolean attacking;
	
	// Timer for how long until the tornado tries to leave or something
	int whatAmIDoing;
	
	// Tornados queued for removing
	private List<Tornado> toRemove;
	
	// Save the coordinates from which the Tornado came to get a better path when leaving
	double originalX;
	double originalY;
	
	// Constructor
	public Tornado(Airspace airspace, EventController eventController){
		
		attacking 		= false;
		whatAmIDoing 	= 450;
		
		eventController.addTornado(this);

		this.randomiseLocation();
	}
	
	public void randomiseLocation(){
		Random rand = new Random();
		int random = rand.nextInt(10);
	
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
				this.x = 0;
				this.y = 500;
				break;
			case 6:
				this.x = 200;
				this.y = 0;
				break;
			case 7:
				this.x = 550;
				this.y = 0;
				break;
			case 8:
				this.x = 0;
				this.y = 100;
				break;
			case 9:
				this.x = 0;
				this.y = 450;
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
	
	// Update coordinates
	public void updateXY(){
		Random randx = new Random();
		Random randy = new Random();
		int directionx = randx.nextInt(4)-2;
		int directiony = randy.nextInt(4)-2;
		
		if (whatAmIDoing > 0){
			
			this.whatAmIDoing -= 1;
			
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
			if (this.x <= originalX - 600){
				this.x += directionx;
			} else {
				this.x -= directionx;
			}
			
			if (this.y <= originalY - 300){
				this.y += directiony;
			} else {
				this.y -= directiony;
			}
		}
	}
	
	// Initialise
	public void init (GameContainer gc) throws SlickException{
		tornadoImage = new Image("res/graphics/new/wind_indicator.png");
		}
	
	// Renders the image
	public void render(Graphics g, GameContainer gc) throws SlickException{
		tornadoImage.setRotation(tornadoImage.getRotation()+50);
		tornadoImage.draw((int) this.x, (int) this.y);
	}
	
	// Updates the logic
	public void update(GameContainer gc) throws SlickException{
		if (attacking && this.inAirspace()){
			this.updateXY();
		}
	}
}
