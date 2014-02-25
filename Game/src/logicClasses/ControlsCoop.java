package logicClasses;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ControlsCoop extends Controls {
	
	private Flight selectedFlight1;
	private Flight selectedFlight2;
	private AirspaceCoop airspaceCoop;
	
	public ControlsCoop(AirspaceCoop airspace) {
		super(airspace);
		this.selectedFlight1=null;
		this.selectedFlight2=null;
		this.airspaceCoop=airspace;
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		
	}
	
	@Override
	public void update(GameContainer gc, Airspace airspace) {
		Input input = new Input(Input.ANY_CONTROLLER);
		if(input.isKeyDown(Input.KEY_LSHIFT)) {
			this.selectedFlight1 = airspaceCoop.getListOfFlightsPlayer1().get(0);
			/*if(airspaceCoop.getListOfFlightsPlayer1().size()>0) {
				if(this.selectedFlight1==null) {
					this.selectedFlight1 = airspaceCoop.getListOfFlightsPlayer1().get(0);
				}
				else {
					if(airspaceCoop.getListOfFlightsPlayer1().indexOf(selectedFlight1)==airspaceCoop.getListOfFlightsPlayer1().size()-1) {
						this.selectedFlight1 = airspaceCoop.getListOfFlightsPlayer1().get(0);
					}
						this.selectedFlight1 = airspaceCoop.getListOfFlightsPlayer1().get(airspaceCoop.getListOfFlightsPlayer1().indexOf(selectedFlight1)+1);
				}
			}*/
		}
	}
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if(selectedFlight1 != null) {
						
			
			if(!selectedFlight1.getFlightPlan().getChangingPlan()){
				g.setColor(Color.white);
				g.drawString("I am selected", (int)this.selectedFlight1.getX(), (int)this.selectedFlight1.getY());
			}	
			
		}	
	}
	
	

}
