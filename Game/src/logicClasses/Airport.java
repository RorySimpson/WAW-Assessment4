package logicClasses;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.geom.*;

import util.DeferredFile;



public class Airport {
	
	//FIELDS
	private static Image airportImage;
	
	private String airportName = "Nothing"; // {!} needs a name
	
	private float x, y;
	private float runwayHeading = 270;
	private int runwayLength, airportNumber;
	private boolean flightLanding;
	private Flight 		planeWaitingtoTakeoff;
	private ExitPoint 	beginningOfRunway;
	private EntryPoint endOfRunway;
	private Polygon 	landingApproachArea;
	private Airspace airspace;
	private Image landingApproachImage;
	
	
	//CONSTRUCTOR
	



	Airport (int airportNumber,Airspace airspace) {
		// Placing the airport off screen as the airport graphics are part of the graphics already
		
		this.airspace = airspace;
		this.airportNumber = airportNumber;
    	
    	
    	// Airport status
    	this.flightLanding 				= false;
    	this.planeWaitingtoTakeoff	 	= null;
    	
    	// Creating the runway waypoints
    	
    	if(this.airportNumber == 1){
    		this.beginningOfRunway 			= new ExitPoint(1060, 495, "AP");
    		this.endOfRunway 				= new EntryPoint(1180, 495);

    		// Creating the landing area. This is the triangle that appears when a flight needs to land. It
    		// is used to check whether the flights have the right approach.
    		landingApproachArea 			= new Polygon();
    		landingApproachArea.addPoint(1025, 495);
    		landingApproachArea.addPoint(775, 405);
    		landingApproachArea.addPoint(775, 585);
    	}
    	
    	else if(this.airportNumber ==2){
    		;
    	}
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
              
                x = (stateContainer.Game.MAXIMUMWIDTH)/2;
                y = stateContainer.Game.MAXIMUMHEIGHT/2;
                runwayLength = airportImage.getHeight();
            }	
		});
		landingApproachImage = new Image("res/graphics/new/airspaceIndicatorGreen.png");
	}
	
	public void render(Graphics g, GameContainer gc) throws SlickException { 
		
		// Airport image centred in middle of airspace
		airportImage.setRotation(runwayHeading);
		airportImage.drawCentered(1120, 495);
		if(this.airspace.getControls().getSelectedFlight() != null){
			if(this.airspace.getControls().getSelectedFlight().getFlightPlan().getCurrentRoute().get(0) == this.beginningOfRunway){
				landingApproachImage.drawCentered(900, 495);
				
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


}
