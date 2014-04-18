package competitive;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import coop.AirspaceCoop;
import coop.FlightCoop;
import logicClasses.Airspace;
import logicClasses.Controls;
import logicClasses.Flight;

public class ControlsCompetitive extends Controls {
	
	private FlightCompetitive selectedFlight1;
	private FlightCompetitive selectedFlight2;
	private AirspaceCompetitive airspaceCompetitive;
	private boolean selectedThisLoop;
	
	public ControlsCompetitive(AirspaceCompetitive airspace) {
		super(airspace);
		this.selectedFlight1=null;
		this.selectedFlight2=null;
		this.airspaceCompetitive=airspace;
		this.selectedThisLoop=false;
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		
	}
	
	@Override
	public void update(GameContainer gc, Airspace airspace) {

		
		if(gc.getInput().isKeyPressed(Input.KEY_LSHIFT)) {
			if(airspaceCompetitive.getListOfFlightsPlayer1().size()>0) {
				if (this.selectedFlight1 == null){
					for (int i = 0; i< airspaceCompetitive.getListOfFlightsPlayer1().size(); i++){
						if(airspaceCompetitive.getListOfFlightsPlayer1().get(i % airspaceCompetitive.getListOfFlightsPlayer1().size()).isSelectable()){
							this.selectedFlight1 = airspaceCompetitive.getListOfFlightsPlayer1().get(i % airspaceCompetitive.getListOfFlightsPlayer1().size());
							this.selectedFlight1.setSelected(true);
							break;
						}
					}
				}
				else{
					for (int i = 1; i<= airspaceCompetitive.getListOfFlightsPlayer1().size(); i++){
						if(airspaceCompetitive.getListOfFlightsPlayer1().get((i + airspaceCompetitive.getListOfFlightsPlayer1().indexOf(selectedFlight1)) % airspaceCompetitive.getListOfFlightsPlayer1().size()).isSelectable()){
							this.selectedFlight1.setSelected(false);
							this.selectedFlight1 = airspaceCompetitive.getListOfFlightsPlayer1().get((i + airspaceCompetitive.getListOfFlightsPlayer1().indexOf(selectedFlight1)) % airspaceCompetitive.getListOfFlightsPlayer1().size());
							this.selectedFlight1.setSelected(true);
							break;
						}
						
					}
				}
				
				
			}
			
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_RSHIFT)) {
			if(airspaceCompetitive.getListOfFlightsPlayer2().size()>0) {
				if (this.selectedFlight2 == null){
					for (int i = 0; i< airspaceCompetitive.getListOfFlightsPlayer2().size(); i++){
						if(airspaceCompetitive.getListOfFlightsPlayer2().get(i % airspaceCompetitive.getListOfFlightsPlayer2().size()).isSelectable()){
							this.selectedFlight2 = airspaceCompetitive.getListOfFlightsPlayer2().get(i % airspaceCompetitive.getListOfFlightsPlayer2().size());
							this.selectedFlight2.setSelected(true);
							break;
						}
					}
				}
				else{
					for (int i = 1; i<= airspaceCompetitive.getListOfFlightsPlayer2().size(); i++){
						if(airspaceCompetitive.getListOfFlightsPlayer2().get((i + airspaceCompetitive.getListOfFlightsPlayer2().indexOf(selectedFlight2)) % airspaceCompetitive.getListOfFlightsPlayer2().size()).isSelectable()){
							this.selectedFlight2.setSelected(false);
							this.selectedFlight2 = airspaceCompetitive.getListOfFlightsPlayer2().get((i + airspaceCompetitive.getListOfFlightsPlayer2().indexOf(selectedFlight2)) % airspaceCompetitive.getListOfFlightsPlayer2().size());
							this.selectedFlight2.setSelected(true);
							break;
						}
						
					}
				}
				
				
			}
			
		}
		
		if(airspaceCompetitive.getListOfFlightsPlayer1().size() >= 1){
			if(gc.getInput().isKeyDown(Input.KEY_1)){
				if(this.selectedFlight1 != null){
					this.selectedFlight1.setSelected(false);
				}
				this.selectedFlight1 = airspaceCompetitive.getListOfFlightsPlayer1().get(0);
				this.selectedFlight1.setSelected(true);
			}
		}
		
		if(airspaceCompetitive.getListOfFlightsPlayer1().size() >= 2){
			if(gc.getInput().isKeyDown(Input.KEY_2)){
				if(this.selectedFlight1 != null){
					this.selectedFlight1.setSelected(false);
				}
				this.selectedFlight1 = airspaceCompetitive.getListOfFlightsPlayer1().get(1);
				this.selectedFlight1.setSelected(true);
			}
		}
		
		if(airspaceCompetitive.getListOfFlightsPlayer1().size() >= 3){
			if(gc.getInput().isKeyDown(Input.KEY_3)){
				if(this.selectedFlight1 != null){
					this.selectedFlight1.setSelected(false);
				}
				this.selectedFlight1 = airspaceCompetitive.getListOfFlightsPlayer1().get(2);
				this.selectedFlight1.setSelected(true);
			}
		}
		
		
		if(airspaceCompetitive.getListOfFlightsPlayer2().size() >= 1){
			if(gc.getInput().isKeyDown(Input.KEY_NUMPAD1)){
				if(this.selectedFlight2 != null){
					this.selectedFlight2.setSelected(false);
				}
				this.selectedFlight2 = airspaceCompetitive.getListOfFlightsPlayer2().get(0);
				this.selectedFlight2.setSelected(true);
			}
		}
		
		if(airspaceCompetitive.getListOfFlightsPlayer2().size() >= 2){
			if(gc.getInput().isKeyDown(Input.KEY_NUMPAD2)){
				if(this.selectedFlight2 != null){
					this.selectedFlight2.setSelected(false);
				}
				this.selectedFlight2 = airspaceCompetitive.getListOfFlightsPlayer2().get(1);
				this.selectedFlight2.setSelected(true);
			}
		}
		
		if(airspaceCompetitive.getListOfFlightsPlayer2().size() >= 3){
			if(gc.getInput().isKeyDown(Input.KEY_NUMPAD3)){
				if(this.selectedFlight2 != null){
					this.selectedFlight2.setSelected(false);
				}
				this.selectedFlight2 = airspaceCompetitive.getListOfFlightsPlayer2().get(2);
				this.selectedFlight2.setSelected(true);
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
				if(this.selectedFlight1.getTargetAltitude() > 2000){
					this.selectedFlight1.setTargetAltitude(this.selectedFlight1.getTargetAltitude()-1000);
				}
			}
			if(gc.getInput().isKeyPressed(Input.KEY_W)) {
				if(this.selectedFlight1.getTargetAltitude() < 5000){
					this.selectedFlight1.setTargetAltitude(this.selectedFlight1.getTargetAltitude() + 1000);
				}
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
				if(this.selectedFlight2.getTargetAltitude() > 2000){
					this.selectedFlight2.setTargetAltitude(this.selectedFlight2.getTargetAltitude()-1000);
				}
			}
			if(gc.getInput().isKeyPressed(Input.KEY_UP)) {
				if(this.selectedFlight2.getTargetAltitude() < 5000){
					this.selectedFlight2.setTargetAltitude(this.selectedFlight2.getTargetAltitude()+1000);
				}
			}
			
			if(gc.getInput().isKeyDown(Input.KEY_DELETE)) {
				System.out.println(this.selectedFlight2);
				this.selectedFlight2.land();
			}


			
		}
		
	}
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if(selectedFlight1 != null) {
						
			
			
				g.setColor(Color.white);
				g.drawString("P1", (int)this.selectedFlight1.getX()-5, (int)this.selectedFlight1.getY()-45);
				
			
		}
			if(selectedFlight2 != null) {

				g.setColor(Color.white);
				g.drawString("P2", (int)this.selectedFlight2.getX()-5, (int)this.selectedFlight2.getY()-45);
				
			
		}
		
	}

	public Flight getSelectedFlight1() {
		return selectedFlight1;
	}

	public void setSelectedFlight1(FlightCompetitive selectedFlight1) {
		this.selectedFlight1 = selectedFlight1;
	}

	public Flight getSelectedFlight2() {
		return selectedFlight2;
	}

	public void setSelectedFlight2(FlightCompetitive selectedFlight2) {
		this.selectedFlight2 = selectedFlight2;
	}
	
	
	
	

}

	


