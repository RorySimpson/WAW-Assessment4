package competitive;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;

import util.DeferredFile;
import coop.ControlsCoop;
import logicClasses.Airport;
import logicClasses.Airspace;
import logicClasses.EntryPoint;
import logicClasses.ExitPoint;

public class AirportCompetitive extends Airport {
	
	private Image landingApproachImage;
	
	AirportCompetitive(int airportNumber, Airspace airspace) {
		super(airportNumber, airspace);
		
	    this.beginningOfRunway 			= new ExitPoint(1060, 505, "APR");
   		this.endOfRunway 				= new EntryPoint(1180, 555);
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		LoadingList.get().add(new DeferredFile("res/graphics/new/airport.png"){
			public void loadFile(String filename) throws SlickException{
                airportImage = new Image(filename);
                

                x = (stateContainer.Game.MAXIMUMWIDTH)/2;
                y = stateContainer.Game.MAXIMUMHEIGHT/2;
                runwayLength = airportImage.getHeight();
                
            }	
		});
		
		LoadingList.get().add(new DeferredFile("res/graphics/new/airspaceIndicatorCompetitive.png"){
			public void loadFile(String filename) throws SlickException{
				landingApproachImage = new Image(filename);
              
                
            }	
		});
		

		
	}
	

	
	
	@Override
	public void render(Graphics g, GameContainer gc) throws SlickException { 
		
		getAirportImage().setRotation(0);
		
		// Airport image centred in middle of airspace
	
		getAirportImage().drawCentered(620, 600);
		
		
		
		if((((ControlsCompetitive)this.getAirspace().getControls()).getSelectedFlight1() != null) && ((AirspaceCompetitive)this.getAirspace()).getCargo().getCurrentHolder() != null) {
			if(((ControlsCompetitive)this.getAirspace().getControls()).getSelectedFlight1() == ((AirspaceCompetitive)this.getAirspace()).getCargo().getCurrentHolder()){
				landingApproachImage.drawCentered(620, 390);
				
				
				
			}
		}
		if((((ControlsCompetitive)this.getAirspace().getControls()).getSelectedFlight2() != null) && ((AirspaceCompetitive)this.getAirspace()).getCargo().getCurrentHolder() != null){
			if(((ControlsCompetitive)this.getAirspace().getControls()).getSelectedFlight2() == ((AirspaceCompetitive)this.getAirspace()).getCargo().getCurrentHolder()){
				landingApproachImage.drawCentered(620, 390);
				
				
				
			}
		}
		
		
		
	} 

}



