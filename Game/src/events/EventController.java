package events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logicClasses.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class EventController {
	
	private List<HunterFlight> listOfHunterFlights;
	private int nextHunterFlightTime, nextTornadoTime;
	private List<Tornado> listOfTornadoes;
	private int timeUntilRadioMalfunction;
	private Airspace airspace;
	public List<Tornado> getListOfTornados()
		{
			return listOfTornadoes;
		}

	private Volcano volcano;
	
	
	public EventController(Airspace airspace){
		
		this.volcano = new Volcano();
		this.listOfHunterFlights = new ArrayList<HunterFlight>();
		this.nextHunterFlightTime = 3600;	// One minute initial wait until spawn
		this.nextTornadoTime = 1800;	// 30 second initial wait until spawn
		this.listOfTornadoes = new ArrayList<Tornado>();
		this.timeUntilRadioMalfunction=newRadioMalfunctionTime();
		this.airspace=airspace;
	}
	
	/**
	 * newRadioMalfunctionTime: Generates a new point in time at which a 
	 * radio malfunction will occur.
	 * 
	 */
	
	public int newRadioMalfunctionTime() {
		
		//Radio Malfunction occurs at a time between 4 minutes and 6 minutes
		Random rand = new Random();
		int randNum = rand.nextInt(14400)+ 7200;
		return randNum;
	}
	
	/**
	 * newHunterFlightTime: Generates a new point in time at which a 
	 * hunter flight will spawn.
	 * 
	 */
	
	public int newHunterFlightTime(){
		
		// Hunter flights spawn every 4 to and 6 minutes
		Random rand = new Random();
		int timeTillNextHunterFlight = rand.nextInt(14400) + 7200;
		
		return timeTillNextHunterFlight;
	}
	
	/**
	 * newHunterTornadoTime: Generates a new point in time at which a 
	 * tornado will spawn.
	 * 
	 */
	
	public int newTornadoTime(){
		
		// Tornadoes spawn every 1 to 1 and a half minutes 
		Random rand = new Random();
		int timeTillNextTornado = rand.nextInt(3600) + 1800;
		
		return timeTillNextTornado;
	}
	
	/**
	 * spawnHunterFlight: Adds a hunter flight into the airspace.
	 * 
	 */
	
	public boolean spawnHunterFlight() throws SlickException{
		
		nextHunterFlightTime --;
		
		// If the time is appropriate then spawn hunter flight
		if (nextHunterFlightTime == 0){
			 listOfHunterFlights.add(new HunterFlight(airspace));
			 nextHunterFlightTime = newHunterFlightTime();
			 return true;
		}
		
		return false;
	}
	
	/**
	 * spawnTornado: Adds a tornado into the airspace.
	 *
	 */
	
	public boolean spawnTornado() throws SlickException{
		nextTornadoTime --;
		// If the time is appropriate then spawn tornado
		if (nextTornadoTime == 0){
			 listOfTornadoes.add(new Tornado(airspace));
			 nextTornadoTime = newTornadoTime();
			 return true;
		}
		
		return false;
	}
	
	/**
	 * updateRadioMalfunctionEvent: Updates the state of the radio malfunction event.
	 */
	
	public void updateRadioMalfunctionEvent(){
		
		this.timeUntilRadioMalfunction--;
		
		// If the time is appropriate then make radio malfunction occur.
		if(this.timeUntilRadioMalfunction==0){
			
			if(this.airspace.getListOfFlights().size()>0) {
				int size = this.airspace.getListOfFlights().size();
				Random rand = new Random();
				int indexOfVictimFlight = rand.nextInt(size);
				if(this.airspace.getControls().getSelectedFlight()==this.airspace.getListOfFlights().get(indexOfVictimFlight)){
					this.airspace.getControls().setSelectedFlight(null);
				}
				if(!this.airspace.getListOfFlights().get(indexOfVictimFlight).isTakingOff()&&!this.airspace.getListOfFlights().get(indexOfVictimFlight).isWaitingToTakeOff()){
					this.airspace.getListOfFlights().get(indexOfVictimFlight).setSelected(false);
					this.airspace.getListOfFlights().get(indexOfVictimFlight).setControllable(false);
				}
				
				
			}
			this.timeUntilRadioMalfunction=this.newRadioMalfunctionTime();
			
		}
		
	}
	
	/**
	 * init: Initialises all the resources required for the event controller class, and any other classes that are rendered within it
	 * @param gc GameContainer
	 * @throws SlickException
	 */
	
	public void init (GameContainer gc) throws SlickException{
		
		this.volcano.init(gc);
	}
	
	/**
	 * render: Render all of the graphics for the event controller
	 * @param g Graphics
	 * @param gc GameContainer
	 * 
	 * @throws SlickException
	 */
	
	public void render (Graphics g, GameContainer gc) throws SlickException{
		
		this.volcano.render(g, gc);
		
		// Loop through and draw hunter flights
		for (HunterFlight hunterFlight : listOfHunterFlights){
			hunterFlight.render(g, gc);
		}

		// Loop through and draw tornadoes
		for (Tornado tornado : listOfTornadoes){
			tornado.render(g, gc);
		}
		
		
	}
	
	/**
	 * update: Update all logic in the event controller class
	 * @param gc GameContainer
	 */
	
	public void update(GameContainer gc) throws SlickException{
		this.volcano.update(gc);
		updateRadioMalfunctionEvent();
		
		// Spawn Hunter Flights
		if(spawnHunterFlight()){
			listOfHunterFlights.get(listOfHunterFlights.size()-1).init(gc);
		}
		
		// Spawn Tornadoes
		if(spawnTornado()){
			 listOfTornadoes.get(listOfTornadoes.size()-1).init(gc);
			 listOfTornadoes.get(listOfTornadoes.size()-1).attack();
		}

		// Update Hunter Flights
		for (int i = 0; i < listOfHunterFlights.size(); i++){
			if (listOfHunterFlights.get(i).inAirspace()){
				listOfHunterFlights.get(i).update(airspace);
			}
			else {
				removeHunterFlight(i);
			}
		}
		
		// Update Tornadoes
		for (int i = 0; i < this.listOfTornadoes.size(); i++){
			if (this.listOfTornadoes.get(i).inAirspace()){
				listOfTornadoes.get(i).update(gc);
			} else {
				removeTornado(i);
			}
		}
	}
	
	public void addTornado(Tornado tornado){
		this.listOfTornadoes.add(tornado);
	}
	
	public void removeTornado(int tornado){
		this.listOfTornadoes.remove(tornado);
	}
	
	public Volcano getVolcano(){
		return this.volcano;
	}

	public List<HunterFlight> getListOfHunterFlights(){
		return listOfHunterFlights;
	}
	
	public void addHunterFlight(HunterFlight hunterFlight){
		listOfHunterFlights.add(hunterFlight);
	}
	
	public void removeHunterFlight(int hunterFlight){
		listOfHunterFlights.remove(hunterFlight);
	}
	
	public void setTimeUntilRadioMalfunction(int time){
		this.timeUntilRadioMalfunction = time;
	}
	
	public int getTimeUntilRadioMalfunction(){
		return timeUntilRadioMalfunction;
	}
	
	public int getNextHunterFlightTime(){
		return nextHunterFlightTime;
	}
	
	public void setNextHunterFlightTime(int nextHunterFlightTime){
		this.nextHunterFlightTime = nextHunterFlightTime;
	}
	
	public int getNextTornadoTime(){
		return nextTornadoTime;
	}
	
	public void setNextTornadoTime(int nextTornadoTime){
		this.nextTornadoTime = nextTornadoTime;
	}
	
}
