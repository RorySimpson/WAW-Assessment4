package coop;

import logicClasses.Airspace;
import logicClasses.Controls;
import logicClasses.Flight;

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
	private boolean selectedThisLoop;
	
	public ControlsCoop(AirspaceCoop airspace) {
		super(airspace);
		this.selectedFlight1=null;
		this.selectedFlight2=null;
		this.airspaceCoop=airspace;
		this.selectedThisLoop=false;
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		
	}
	
	@Override
	public void update(GameContainer gc, Airspace airspace) {

		if(gc.getInput().isKeyPressed(Input.KEY_LSHIFT)) {
			if(airspaceCoop.getListOfFlightsPlayer1().size()>0) {
				if(this.selectedFlight1==null) {
					this.selectedFlight1 = airspaceCoop.getListOfFlightsPlayer1().get(0);
					this.selectedFlight1.setSelected(true);
				}
				else {
					if(airspaceCoop.getListOfFlightsPlayer1().indexOf(selectedFlight1)==airspaceCoop.getListOfFlightsPlayer1().size()-1) {
						this.selectedFlight1 = airspaceCoop.getListOfFlightsPlayer1().get(0);
						this.selectedFlight1.setSelected(true);
					} 
					else {
						this.selectedFlight1 = airspaceCoop.getListOfFlightsPlayer1().get(airspaceCoop.getListOfFlightsPlayer1().indexOf(selectedFlight1)+1);
						this.selectedFlight1.setSelected(true);
					}
				}
			}
		}
		if(gc.getInput().isKeyPressed(Input.KEY_RSHIFT)) {
			if(airspaceCoop.getListOfFlightsPlayer2().size()>0) {
				if(this.selectedFlight2==null) {
					this.selectedFlight2 = airspaceCoop.getListOfFlightsPlayer2().get(0);
					this.selectedFlight2.setSelected(true);
				}
				else {
					if(airspaceCoop.getListOfFlightsPlayer2().indexOf(selectedFlight2)==airspaceCoop.getListOfFlightsPlayer2().size()-1) {
						this.selectedFlight2 = airspaceCoop.getListOfFlightsPlayer2().get(0);
						this.selectedFlight2.setSelected(true);
					} 
					else {
						this.selectedFlight2 = airspaceCoop.getListOfFlightsPlayer2().get(airspaceCoop.getListOfFlightsPlayer2().indexOf(selectedFlight2)+1);
						this.selectedFlight2.setSelected(true);
					}
				}
			}
		}
		if(this.selectedFlight1!=null) {
			if(gc.getInput().isKeyDown(Input.KEY_A)) {
				this.selectedFlight1.decrementHeading();
			}
			if(gc.getInput().isKeyDown(Input.KEY_D)) {
				this.selectedFlight1.incrementHeading();
			}
			if(gc.getInput().isKeyPressed(Input.KEY_S)) {
				this.selectedFlight1.setTargetAltitude(this.selectedFlight1.getTargetAltitude()-1000);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_W)) {
				this.selectedFlight1.setTargetAltitude(this.selectedFlight1.getTargetAltitude()+1000);
			}
			if(gc.getInput().isKeyDown(Input.KEY_T)) {
				this.selectedFlight1.takeOff();
			}
			if(gc.getInput().isKeyDown(Input.KEY_L)) {
				this.selectedFlight1.land();
			}
		}
		if(this.selectedFlight2!=null) {
			if(gc.getInput().isKeyDown(Input.KEY_LEFT)) {
				this.selectedFlight2.decrementHeading();
			}
			if(gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
				this.selectedFlight2.incrementHeading();
			}
			if(gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
				this.selectedFlight2.setTargetAltitude(this.selectedFlight2.getTargetAltitude()-1000);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_UP)) {
				this.selectedFlight2.setTargetAltitude(this.selectedFlight2.getTargetAltitude()+1000);
			}
			if(gc.getInput().isKeyDown(Input.KEY_SLASH)) {
				this.selectedFlight2.takeOff();
			}
			if(gc.getInput().isKeyDown(Input.KEY_DELETE)) {
				this.selectedFlight2.land();
			}
		}
		
	}
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if(selectedFlight1 != null) {
						
			
			if(!selectedFlight1.getFlightPlan().getChangingPlan()){
				g.setColor(Color.white);
				g.drawString("P1", (int)this.selectedFlight1.getX()-5, (int)this.selectedFlight1.getY()-45);
			}	
			
		}
			if(selectedFlight2 != null) {
						
			
			if(!selectedFlight2.getFlightPlan().getChangingPlan()){
				g.setColor(Color.white);
				g.drawString("P2", (int)this.selectedFlight2.getX()-5, (int)this.selectedFlight2.getY()-45);
			}	
			
		}
	}

	public Flight getSelectedFlight1() {
		return selectedFlight1;
	}

	public void setSelectedFlight1(Flight selectedFlight1) {
		this.selectedFlight1 = selectedFlight1;
	}

	public Flight getSelectedFlight2() {
		return selectedFlight2;
	}

	public void setSelectedFlight2(Flight selectedFlight2) {
		this.selectedFlight2 = selectedFlight2;
	}
	
	

}
