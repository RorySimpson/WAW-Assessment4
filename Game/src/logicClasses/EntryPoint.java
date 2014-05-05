package logicClasses;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;

import util.DeferredFile;


public class EntryPoint extends Point {
	
	/* Images */
	static Image entryPointTop, entryPointRight, entryPointLeft,entryPointRunway;
	/* Whether it's a runway entry point (the start of the airport) */
	private boolean runway;
	
	/**
	 * Constructor that takes the coordinates 
	 * @param xcoord x coordinate
	 * @param ycoord y coordinate
	 */
    public EntryPoint(double xcoord, double ycoord) {
    	super(xcoord, ycoord);
    }
    
    public void init(GameContainer gc) throws SlickException {
    	
    /**
     * init: Initialises the variables and resources required for the EntryPoint object render (Sets EntryPoint Images)
     * @param gc Game container required by Slick2d
     * @throws SlickException
     */
    	{
    		LoadingList loading = LoadingList.get();
    		if (entryPointTop == null)
    			loading.add(new DeferredFile("res/graphics/entrypoint_top.png"){
    				public void loadFile(String filename) throws SlickException{
    					entryPointTop = new Image(filename);
    				}
    			});
    		
    		if (entryPointRight == null)
    			loading.add(new DeferredFile("res/graphics/entrypoint_right.png"){
    				public void loadFile(String filename) throws SlickException{
    					entryPointRight = new Image(filename);
    				}
    			});
    		
    		if (entryPointLeft == null)
    			loading.add(new DeferredFile("res/graphics/entrypoint_left.png"){
    				public void loadFile(String filename) throws SlickException{
    					entryPointLeft = new Image(filename);
    				}
    			});
    	}
    }
    public boolean isRunway(){
    	return runway;
    }
    /**
	 * render: Render method for the EntryPoint object, position determines orientation of image
	 * @param g Graphics required by Slick2d
	 * @throws SlickException
	 */
	public void render(Graphics g) throws SlickException {
		runway = false;
		entryPointRunway = entryPointRight.copy();
		entryPointRunway.setRotation(45);

		/* Draw the 3 entry points */
		if(y == 0){
			entryPointTop.draw((int)x-20, (int)y);
		}
		
		else if(x == 11){
			entryPointLeft.draw((int)x, (int)y-20);
		}
		
		else if(x == 1200){
			entryPointRight.draw((int)x-20, (int)y-20);
        /* Or the runway point if it's none of the normal entry points */
		}else{
			runway = true;
		}
    }
}