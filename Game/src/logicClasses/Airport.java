package logicClasses;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.geom.*;

import util.DeferredFile;



public class Airport extends Point {
	
	//FIELDS
	private static Image airportImage;
	
	private String airportName = "Nothing"; // {!} needs a name
	
	private float x, y;
	private float runwayHeading = 270;
	private int runwayLength;
	private boolean flightLanding;
	private Flight 		planeWaitingtoTakeoff;
	private ExitPoint 	beginningOfRunway;
	private EntryPoint endOfRunway;
	private Polygon 	landingApproachArea;
	private Airspace airspace;
	private Image landingApproachImage;
	
	
	//CONSTRUCTOR
	
	public int getRunwayLength() {
		return runwayLength - 10;
	}


	public boolean isFlightLanding() {
		return flightLanding;
	}


	public void setFlightLanding(boolean flightLanding) {
		this.flightLanding = flightLanding;
	}


	public Flight getPlaneWaitingtoTakeoff() {
		return planeWaitingtoTakeoff;
	}


	public void setPlaneWaitingtoTakeoff(Flight planeWaitingtoTakeoff) {
		this.planeWaitingtoTakeoff = planeWaitingtoTakeoff;
	}


	public Polygon getLandingApproachArea() {
		return landingApproachArea;
	}


	public void setLandingApproachArea(Polygon landingApproachArea) {
		this.landingApproachArea = landingApproachArea;
	}


	public Image getLandingApproachImage() {
		return landingApproachImage;
	}


	public void setLandingApproachImage(Image landingApproachImage) {
		this.landingApproachImage = landingApproachImage;
	}


	Airport (int x, int y, Airspace airspace) {
		// Placing the airport off screen as the airport graphics are part of the graphics already
		super(x,y);
		this.airspace = airspace;
    	
    	
    	// Airport status
    	this.flightLanding 				= false;
    	this.planeWaitingtoTakeoff	 	= null;
    	
    	// Creating the runway waypoints
    	this.beginningOfRunway 			= new ExitPoint(1040, 465, "AP");
    	this.endOfRunway 				= new EntryPoint(1180, 465);
    	
    	// Creating the landing area. This is the triangle that appears when a flight needs to land. It
    	// is used to check whether the flights have the right approach.
    	landingApproachArea 			= new Polygon();
    	landingApproachArea.addPoint(1040, 465);
    	landingApproachArea.addPoint(720, 344);
    	landingApproachArea.addPoint(720, 576);
    }
		
		
	
	
	
	public ExitPoint getBeginningOfRunway() {
		return beginningOfRunway;
	}


	public void setBeginningOfRunway(ExitPoint beginningOfRunway) {
		this.beginningOfRunway = beginningOfRunway;
	}


	public EntryPoint getEndOfRunway() {
		return endOfRunway;
	}


	public void setEndOfRunway(EntryPoint endOfRunway) {
		this.endOfRunway = endOfRunway;
	}


	public void init(GameContainer gc) throws SlickException {
		LoadingList.get().add(new DeferredFile("res/graphics/new/airport.png"){
			public void loadFile(String filename) throws SlickException{
                airportImage = new Image(filename);
              
                x = (stateContainer.Game.MAXIMUMWIDTH-150)/2;
                y = stateContainer.Game.MAXIMUMHEIGHT/2;
                runwayLength = airportImage.getHeight();
            }	
		});
		landingApproachImage = new Image("res/graphics/new/airspaceIndicatorGreen.png");
	}
	
	public void render(Graphics g, GameContainer gc) throws SlickException { 
		
		// Airport image centred in middle of airspace
		airportImage.setRotation(runwayHeading);
		airportImage.drawCentered(1100, 465);
		if(this.airspace.getControls().getSelectedFlight() != null){
			if(this.airspace.getControls().getSelectedFlight().getFlightPlan().getCurrentRoute().get(0) == this.beginningOfRunway){
				landingApproachImage.drawCentered(880, 465);
				
			}
		}
		
	} 
	
	
	@Override
	public String toString(){
		String s = "Airport Name: " + airportName;
		return s;
	}
	


	public float getRunwayHeading() {
		return runwayHeading;
	}


}
