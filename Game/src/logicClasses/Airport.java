package logicClasses;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.geom.*;

import stateContainer.Game;
import util.DeferredFile;



public class Airport {
	
	//FIELDS
	protected static Image airportImage;
	
	protected String airportName = "Nothing"; // {!} needs a name
	
	protected float x, y;
	protected float runwayHeading;
	protected int runwayLength, airportNumber;
	protected boolean flightLanding;
	protected Flight 		planeWaitingtoTakeoff;
	protected ExitPoint 	beginningOfRunway;
	protected EntryPoint endOfRunway;
	protected Polygon 	landingApproachArea;
	protected Airspace airspace;
	protected Image landingApproachImageRight, landingApproachImageLeft;
	
	
	//CONSTRUCTOR
	



	protected Airport (int airportNumber,Airspace airspace) {
		// Placing the airport off screen as the airport graphics are part of the graphics already
		
		this.airspace = airspace;
		this.airportNumber = airportNumber;
    	
    	
    	// Airport status
    	this.flightLanding 				= false;
    	this.planeWaitingtoTakeoff	 	= null;
    	
    	// Creating the runway waypoints
    	
    	if(this.airportNumber == 1){
    		runwayHeading = 270;
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
    		runwayHeading = 90;
       		this.beginningOfRunway 			= new ExitPoint(151, 495, "APL");
    		this.endOfRunway 				= new EntryPoint(31, 495);

    		// Creating the landing area. This is the triangle that appears when a flight needs to land. It
    		// is used to check whether the flights have the right approach.
    		landingApproachArea 			= new Polygon();
    		landingApproachArea.addPoint(184, 495);
    		landingApproachArea.addPoint(434, 405);
    		landingApproachArea.addPoint(434, 585);
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
		
		LoadingList.get().add(new DeferredFile("res/graphics/new/airspaceIndicatorGreen.png"){
			public void loadFile(String filename) throws SlickException{
				landingApproachImageRight = new Image(filename);
              
                
            }	
		});
		
		LoadingList.get().add(new DeferredFile("res/graphics/new/airspaceIndicatorGreenLeft.png"){
			public void loadFile(String filename) throws SlickException{
				landingApproachImageLeft = new Image(filename);
              
                
            }	
		});
		
		
		

		
		
		
	}
	
	public void render(Graphics g, GameContainer gc) throws SlickException { 
		
		g.setWorldClip(11, 0, Game.MAXIMUMWIDTH -11, Game.MAXIMUMHEIGHT-40);
		
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
		
		g.setWorldClip(0, 0, Game.MAXIMUMWIDTH, Game.MAXIMUMHEIGHT);
		
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





	public static Image getAirportImage() {
		return airportImage;
	}





	public static void setAirportImage(Image airportImage) {
		Airport.airportImage = airportImage;
	}





	public int getAirportNumber() {
		return airportNumber;
	}





	public void setAirportNumber(int airportNumber) {
		this.airportNumber = airportNumber;
	}





	public Airspace getAirspace() {
		return airspace;
	}





	public void setAirspace(Airspace airspace) {
		this.airspace = airspace;
	}





	public Image getLandingApproachImageRight() {
		return landingApproachImageRight;
	}





	public void setLandingApproachImageRight(Image landingApproachImageRight) {
		this.landingApproachImageRight = landingApproachImageRight;
	}





	public Image getLandingApproachImageLeft() {
		return landingApproachImageLeft;
	}





	public void setLandingApproachImageLeft(Image landingApproachImageLeft) {
		this.landingApproachImageLeft = landingApproachImageLeft;
	}


}
