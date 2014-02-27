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
	private float runwayHeading;
	private int runwayLength, airportNumber;
	private boolean flightLanding;
	private Flight 		planeWaitingtoTakeoff;
	private ExitPoint 	beginningOfRunway;
	private EntryPoint endOfRunway;
	private Polygon 	landingApproachArea;
	private Airspace airspace;
	private Image landingApproachImageRight, landingApproachImageLeft;
	
	
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
    		runwayHeading = 90;
    		this.beginningOfRunway 			= new ExitPoint(1060, 495, "APR");
    		this.endOfRunway 				= new EntryPoint(1180, 495);

    		// Creating the landing area. This is the triangle that appears when a flight needs to land. It
    		// is used to check whether the flights have the right approach.
    		landingApproachArea 			= new Polygon();
    		landingApproachArea.addPoint(1025, 495);
    		landingApproachArea.addPoint(775, 405);
    		landingApproachArea.addPoint(775, 585);
    	}
    	
    	else if(this.airportNumber ==2){
    		runwayHeading = 270;
       		this.beginningOfRunway 			= new ExitPoint(151, 495, "APL");
    		this.endOfRunway 				= new EntryPoint(31, 495);

    		// Creating the landing area. This is the triangle that appears when a flight needs to land. It
    		// is used to check whether the flights have the right approach.
    		landingApproachArea 			= new Polygon();
    		landingApproachArea.addPoint(116, 495);
    		landingApproachArea.addPoint(366, 405);
    		landingApproachArea.addPoint(366, 585);
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
		landingApproachImageRight = new Image("res/graphics/new/airspaceIndicatorGreen.png");
		landingApproachImageLeft = new Image("res/graphics/new/airspaceIndicatorGreenLeft.png");
		
		
	}
	
	public void render(Graphics g, GameContainer gc) throws SlickException { 
		
		airportImage.setRotation(runwayHeading);
		// Airport image centred in middle of airspace
		if (airportNumber == 1){
			airportImage.drawCentered(1120, 495);
		}
		
		else{
			airportImage.drawCentered(91, 495);
		}
		
		
		
		if(this.airspace.getControls().getSelectedFlight() != null){
			if(this.airspace.getControls().getSelectedFlight().getFlightPlan().getCurrentRoute().get(0) == this.beginningOfRunway){
				if (airportNumber == 1) landingApproachImageRight.drawCentered(900, 495);
				if (airportNumber == 2) landingApproachImageLeft.drawCentered(311, 495);
				
				
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
		return landingApproachImageRight;
	}


	public void setLandingApproachImage(Image landingApproachImage) {
		this.landingApproachImageRight = landingApproachImage;
	}


}
