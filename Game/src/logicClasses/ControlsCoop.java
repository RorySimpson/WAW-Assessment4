package logicClasses;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ControlsCoop extends Controls {
	
	private Flight selectedFlight1;
	private Flight selectedFlight2;
	
	public ControlsCoop(Airspace airspace) {
		super(airspace);
		this.selectedFlight1=null;
		this.selectedFlight2=null;
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		
	}
	
	
	public void update(GameContainer gc, AirspaceCoop airspace) {
		Input input = new Input(Input.ANY_CONTROLLER);
		if(input.isKeyDown(Input.KEY_LSHIFT)) {
			if(airspace.getListOfFlightsPlayer1().size()>0) {
				if(this.selectedFlight1==null) {
					this.selectedFlight1 = airspace.getListOfFlightsPlayer1().get(0);
				}
				else {
					if(airspace.getListOfFlightsPlayer1().indexOf(selectedFlight1)==airspace.getListOfFlightsPlayer1().size()-1) {
						this.selectedFlight1 = airspace.getListOfFlightsPlayer1().get(0);
					}
						this.selectedFlight1 = airspace.getListOfFlightsPlayer1().get(airspace.getListOfFlightsPlayer1().indexOf(selectedFlight1)+1);
				}
			}
		}
	}
	
	

}
