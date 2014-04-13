package competitive;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import stateContainer.Game;
import coop.AirspaceCoop;
import coop.ControlsCoop;
import logicClasses.*;

public class FlightCompetitive extends Flight {
	
	private static Image player1Image, player2Image;
	private boolean player2;
	private FlightPlanCompetitive flightPlan;
	
	public FlightCompetitive(Airspace airspace, Boolean competitive){
		super(airspace, competitive);
		this.player2 = false;
		this.flightPlan = new FlightPlanCompetitive(airspace, this, competitive);
		
		
	}
	
	/**
	 * generateAltitude: Randomly assigns one of three different altitudes to a flight
	 * @return A random altitude (either 28000, 29000 or 30000)
	 */

	public int generateAltitude() {	//{!} not converted to using min/max

		Random rand = new Random();
		int check = rand.nextInt(((maxAltitude-minAltitude)/1000) - 1);
		return minAltitude + (check + 1) * 1000;

	}
	
	public void steerLandingFlight(){
		if (this.flightPlan.getCurrentRoute().size() != 0){
			this.targetHeading = calculateHeadingToNextWaypoint(this.getFlightPlan().getCurrentRoute().get(0).getX(),this.getFlightPlan().getCurrentRoute().get(0).getY());
		}

	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {

		if (player2Image == null){
			player2Image = new Image("res/graphics/flight2_2.png");
		}
		if (player1Image == null){
			player1Image = new Image("res/graphics/flight1_1.png");
		}
		if(getShadowImage() == null){
			setShadowImage(new Image("res/graphics/flight_shadow.png"));	
		}
		if (getSlowFlightImage() == null){
			setSlowFlightImage(new Image("res/graphics/flight_slow.png"));
		}
		if (getFastFlightImage() == null){
			setFastFlightImage(new Image("res/graphics/flight_fast.png"));
		}

	}
	
	@Override
	public void land(){	
		// if next point is an exit point
		
		
		
		
		if (!isLanding()){
			if (this.getAirspace().getAirportRight().getLandingApproachArea()
					.contains((float)this.getX(), (float)this.getY()) 
					&& this.getCurrentHeading() >= 45 && this.getCurrentHeading() <= 135 && this.getCurrentAltitude() <= 2000
					&& this.getFlightPlan().getCurrentRoute().get(0) == this.getAirspace().getAirportRight().getBeginningOfRunway())
			{


				this.setLanding(true);
				
				this.setTargetVelocity(this.getVelocity());


				this.setLandingDescentRate(this.findLandingDescentRate());

				if(((ControlsCoop)this.getAirspace().getControls()).getSelectedFlight1().isLanding()) {
					((ControlsCoop)this.getAirspace().getControls()).setSelectedFlight1(null);
				}
				else {
					((ControlsCoop)this.getAirspace().getControls()).setSelectedFlight2(null);
				}


			}
			


		}
	}
	
	
	public void takeOff(Flight flight){
		setTakingOff(true);
		setTargetVelocity((minVelocity +maxVelocity) /2);
		setTargetAltitude(minAltitude);
		if(((AirspaceCoop)getAirspace()).getListOfFlightsPlayer1().contains(flight)){
			((ControlsCoop)getAirspace().getControls()).getSelectedFlight1().setSelected(false);
			((ControlsCoop)getAirspace().getControls()).setSelectedFlight1(null);
		}
		
		else{
			((ControlsCoop)getAirspace().getControls()).getSelectedFlight2().setSelected(false);
			((ControlsCoop)getAirspace().getControls()).setSelectedFlight2(null);
		}
		
	}
	


	@Override
	public void drawFlight(Graphics g, GameContainer gc ){

		g.setColor(Color.white);
		g.setWorldClip(11, 0, Game.MAXIMUMWIDTH -11, Game.MAXIMUMHEIGHT-40);

			// Scale the shadow in accordance to the altitude of the flight
			if (this.getCurrentAltitude() > 50)
			{
				float shadowScale = (float) (14 - (this.getCurrentAltitude() / 1000))/10; 
				getShadowImage().setRotation((int) getCurrentHeading());
				getShadowImage().draw((int) this.getX()-35, (int) this.getY(), shadowScale);
			}
			//Depending on a plane's speed, different images for the plane are drawn

			if(this.player2){	//{!} not converted to using min/max

				player2Image.setRotation((float) this.getCurrentHeading());
				player2Image.draw((int) this.getX()-10, (int) this.getY()-10);

			}
			else {
				player1Image.setRotation((float) this.getCurrentHeading());
				player1Image.draw((int) this.getX()-10, (int) this.getY()-10);
			}

		// Drawing Separation Circle




		// Drawing information around flight
		// If flight is selected then also display current heading

		if (this.isSelected()){
			if (this.getCurrentAltitude() != 0){
				
				
				
				g.drawString(Math.round(this.getCurrentAltitude()) + " ft",(int) this.getX()-30, (int) this.getY() + 10);

				if (this.getFlightPlan().getCurrentRoute().size() > 0) {
					if (this.getFlightPlan().getCurrentRoute().get(0) == this.getAirspace().getAirportRight().getBeginningOfRunway() && this.getCurrentAltitude() > 2000){
						g.drawString("Lower Me",(int) this.getX() -29, (int)this.getY()-28);
					}
					
					else if (this.getFlightPlan().getCurrentRoute().get(0) == this.getAirspace().getAirportRight().getBeginningOfRunway() && this.getCurrentAltitude() <= 2000){
						g.drawString("Line Me Up",(int) this.getX() -33, (int)this.getY()-28);
					}
					
					else if (this.getFlightPlan().getCurrentRoute().get(0) == this.getAirspace().getAirportRight().getBeginningOfRunway() && this.getCurrentAltitude() <= 2000){
						g.drawString("Landing",(int) this.getX() -33, (int)this.getY()-28);
					}
					
					else{
						g.drawString("Aim: "+this.getFlightPlan().getPointByIndex(0).getPointRef(),(int) this.getX() -22, (int)this.getY()-28);
					}

				}
				
				if(this.player2){	//{!} not converted to using min/max
					g.setColor(Color.red);

				}
				else {
					g.setColor(Color.blue);	
				}
				
				g.drawOval((int) this.getX() - 50, (int) this.getY() - 50, 100, 100);
			}
			g.setColor(Color.white);

		}

		// If flight isn't selected then don't display current heading
		else{
			if (this.getCurrentAltitude() != 0){
				g.setColor(Color.lightGray);
				g.drawString(Math.round(this.getCurrentAltitude()) + " ft",(int) this.getX()-30, (int) this.getY() + 10);
				if (this.getFlightPlan().getCurrentRoute().size() > 0) {
					if (this.getFlightPlan().getCurrentRoute().get(0) == this.getAirspace().getAirportRight().getBeginningOfRunway()){
						g.drawString("Land Me",(int) this.getX() -29, (int)this.getY()-28);
					}
					
					else{
						g.drawString("Aim: "+this.getFlightPlan().getPointByIndex(0).getPointRef(),(int) this.getX() -22, (int)this.getY()-28);
					}
				}
				g.drawOval((int) this.getX() - 50, (int) this.getY() - 50, 100, 100);
			}
			
			else if (this.getCurrentAltitude() == 0 && isTakingOff() != true && this.getFlightPlan().getEntryPoint() == getAirspace().getAirportRight().getEndOfRunway()){
				g.drawString("Take me off!",(int) this.getX()-80 , (int)this.getY()+28);
			}
			
		}

		g.setWorldClip(0, 0, Game.MAXIMUMWIDTH, Game.MAXIMUMHEIGHT);

	}
	
	public void render(Graphics g, GameContainer gc) throws SlickException {

		this.drawFlight(g, gc);

	}

	
	public void update(ScoreTracking score) {


		this.updateVelocity();
		this.updateCurrentHeading();
		this.updateXYCoordinates();
		this.updateAltitude();

		if(this.landing){
			this.steerLandingFlight();
		}

	}
	
	public boolean isSelectable(){
		return (!isLanding() && !isTakingOff());
	}
	
	public void setPlayer2(Boolean bool){
		player2 = bool;
	}

	public boolean isPlayer2() {
		return player2;
	}
	
	@Override
	public FlightPlanCompetitive getFlightPlan(){
		return this.flightPlan;
	}


}
