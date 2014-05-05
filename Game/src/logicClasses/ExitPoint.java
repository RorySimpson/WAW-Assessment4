package logicClasses;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;

import util.DeferredFile;


public class ExitPoint extends Point {

	/* Images */
	static Image exitPointTop, exitPointRight, exitPointLeft,exitPointRunway;
	/* Whether it's the airport exit point */
	private boolean runway = false;
	
	/**
	 * Constructor that takes the coordinates 
	 * @param xcoord x coordinate
	 * @param ycoord y coordinate
	 * @param name the name of the exit point
	 */
	public ExitPoint(double xcoord, double ycoord, String name){
		super(xcoord, ycoord, name);
	}
	
	/**
	 * Checks if it's a runway exitpoint 
	 * @return true if it's the runway, false otherwise
	 */
	public boolean isRunway(){
		return runway;
	}
	/**
	 * init: Initialises the variables and resources required for the ExitPoint object render (Sets ExitPoint Images)
	 * @param gc Game container required by Slick2d
	 * @throws SlickException 
	 */
	public void init(GameContainer gc) throws SlickException {
		{
			LoadingList loading = LoadingList.get();
			
			if (exitPointTop == null){
				loading.add(new DeferredFile("res/graphics/exitpoint_top.png"){
					public void loadFile(String filename) throws SlickException{
						exitPointTop = new Image(filename);
						
					}
				});
			}
			
			if (exitPointRight == null){
				loading.add(new DeferredFile("res/graphics/exitpoint_right.png"){
					public void loadFile(String filename) throws SlickException{
						exitPointRight = new Image(filename);						
					}
				});
				
			}
			
			if (exitPointLeft == null){
				loading.add(new DeferredFile("res/graphics/exitpoint_left.png"){
					public void loadFile(String filename) throws SlickException{
						exitPointLeft = new Image(filename);
					}
				});
			}
		}
	}

	/**
	 * render: Render method for the ExitPoint object, position determines orientation of image and String of name
	 * @param g Graphics required by Slick2d 
	 * @param airspace Airspace object
	 * @throws SlickException 
	 */
	public void render(Graphics g, Airspace airspace) throws SlickException {

		/* Checks if it's the runway point */
		if(exitPointLeft != null){
			exitPointRunway = exitPointLeft.copy();
			exitPointRunway.setRotation(0);
		}
	
		/* Checks if it's one of the 3 exitpoints */
		if(y == 0){
			exitPointTop.draw((int)x-20, (int)y);
		}

		else if(x == 11){
			exitPointLeft.draw((int)x, (int)y-20);
		}

		else if(x == 1200){
			exitPointRight.draw((int)x-20, (int)y-20);
		} else {
			
		}
		

		/* Sets the colour of the name of the exitpoints  */
		g.setColor(Color.white);
		if(y == 0){
			g.drawString(pointRef, (int)x-15, (int)y);
		}
		else if(x ==11){
			g.drawString(pointRef, (int)x, (int)y-7);
		}

		else if(x ==1200){
			g.drawString(pointRef, (int)x-35, (int)y-7);
		}else{
			runway = true;
		}
	}
}
