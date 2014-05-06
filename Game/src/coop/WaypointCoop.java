package coop;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;

import util.DeferredFile;
import logicClasses.Airspace;
import logicClasses.Waypoint;

public class WaypointCoop extends Waypoint {
	
	public WaypointCoop(double xcoord, double ycoord, String name){
		super(xcoord, ycoord, name);
	}
	

	
	/**
	 * render: Render the graphics for the Waypoint class (Draws all Waypoints)
	 * @param g slick2d graphics object
	 * @param airspace object
	 * @throws SlickException Slick2d exception handler
	 */

	public void render(Graphics g, AirspaceCoop airspace) throws SlickException {
		Image image;
		image = null;
		ControlsCoop controls = airspace.getControls();
		
		 // If there is a selected flight use its next waypoint and draw it as next
		if(controls.getSelectedFlight1() !=null){
			if (controls.getSelectedFlight1().getFlightPlan().getCurrentRoute().indexOf(this)==0){
				image = getNextWaypointImage();
				g.setColor(Color.blue);
				g.drawOval((float)this.x-20, (float)this.y-20, 40, 40);
				g.setColor(Color.black);
			}
		}	
		
		// If there is a selected flight use its next waypoint and draw it as next
		if(controls.getSelectedFlight2() !=null){ 
			if (controls.getSelectedFlight2().getFlightPlan().getCurrentRoute().indexOf(this)==0){
				image = getNextWaypointImage();
				g.setColor(Color.red);
				g.drawOval((float)this.x-20, (float)this.y-20, 40, 40);
				g.setColor(Color.black);
			} 
		}
		
		// If both players selected flight's next waypoint is the same, surround it in a neutral colour.
		if(controls.getSelectedFlight2() !=null && controls.getSelectedFlight1() !=null ){
			if (controls.getSelectedFlight2().getFlightPlan().getCurrentRoute().indexOf(this) == 0 &&
					controls.getSelectedFlight1().getFlightPlan().getCurrentRoute().indexOf(this) == 0){
				image = getNextWaypointImage();
				g.setColor(Color.magenta);
				g.drawOval((float)this.x-20, (float)this.y-20, 40, 40);
				g.setColor(Color.black);
			} 
		}
		
		
		
		if (image == null){
			image = getWaypointImage();
		}
		
		
		
		image.draw((int)x-14, (int)y-14,30,30);
		g.setColor(Color.black);
		g.drawString(pointRef, (int)x-3, (int)y-9);

	}


}
