package events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logicClasses.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class EventController {
	
	private HunterFlight hunterFlight;
	private List<HunterFlight> listOfHunterFlights;
	private List<Tornado> listOfTornados;
	private int timeUntilRadioMalfunction;
	private Airspace airspace;
	public List<Tornado> getListOfTornados()
		{
			return listOfTornados;
		}

	private Volcano volcano;
	
	
	public EventController(Airspace airspace){
		
		this.volcano = new Volcano();
		// this.tornado = new Tornado();
		this.listOfHunterFlights = new ArrayList<HunterFlight>();
		this.listOfTornados = new ArrayList<Tornado>();
		this.timeUntilRadioMalfunction=newRadioMalfunctionTime();
		this.airspace=airspace;
		//hunterFlight = new HunterFlight(airspace, this);

		

	}
	
	public int newRadioMalfunctionTime() {
		
		Random rand = new Random();
		int randNum = rand.nextInt(600)+2400;
		return randNum;
		
	}
	
	public void init (GameContainer gc) throws SlickException{
		this.volcano.init(gc);
		
		for (HunterFlight hunterFlight : listOfHunterFlights){
			hunterFlight.init(gc);
		}
		
		
	}
	
	public void render (Graphics g, GameContainer gc) throws SlickException{
		this.volcano.render(g, gc);
		
		for (HunterFlight hunterFlight : listOfHunterFlights){
			hunterFlight.render(g, gc);
		}

		for (Tornado tornado : listOfTornados){
			tornado.render(g, gc);
		}
	}
	
	public void update(GameContainer gc, Airspace airspace) throws SlickException{
		this.volcano.update(gc);
		this.timeUntilRadioMalfunction--;
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
		
		for (int i = 0; i < listOfHunterFlights.size(); i++){
			if (listOfHunterFlights.get(i).inAirspace()){
				listOfHunterFlights.get(i).update(airspace);
			}
			else {
				removeHunterFlight(i);
			}
		}
		
		for (int i = 0; i < this.listOfTornados.size(); i++){
			if (this.listOfTornados.get(i).inAirspace()){
				listOfTornados.get(i).update(gc);
			} else {
				removeTornado(i);
			}
		}
	}
	
	public void addTornado(Tornado tornado){
		this.listOfTornados.add(tornado);
	}
	
	public void removeTornado(int tornado){
		this.listOfTornados.remove(tornado);
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

}
