package competitive;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import coop.ControlsCoop;
import logicClasses.Airport;
import logicClasses.Airspace;

public class AirportCompetitive extends Airport {
	
	AirportCompetitive(int airportNumber, Airspace airspace) {
		super(airportNumber, airspace);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void render(Graphics g, GameContainer gc) throws SlickException { 
		
		getAirportImage().setRotation(getRunwayHeading());
		// Airport image centred in middle of airspace
		if (getAirportNumber() == 1){
			getAirportImage().drawCentered(1120, 495);
		}
		
		
		if((((ControlsCompetitive)this.getAirspace().getControls()).getSelectedFlight1() != null) && ((AirspaceCompetitive)this.getAirspace()).getCargo().getCurrentHolder() != null) {
			if(((ControlsCompetitive)this.getAirspace().getControls()).getSelectedFlight1() == ((AirspaceCompetitive)this.getAirspace()).getCargo().getCurrentHolder()){
				getLandingApproachImageRight().drawCentered(900, 495);
				
				
				
			}
		}
		if((((ControlsCompetitive)this.getAirspace().getControls()).getSelectedFlight2() != null) && ((AirspaceCompetitive)this.getAirspace()).getCargo().getCurrentHolder() != null){
			if(((ControlsCompetitive)this.getAirspace().getControls()).getSelectedFlight2() == ((AirspaceCompetitive)this.getAirspace()).getCargo().getCurrentHolder()){
				getLandingApproachImageRight().drawCentered(900, 495);
				
				
				
			}
		}
		
		
		
	} 

}



