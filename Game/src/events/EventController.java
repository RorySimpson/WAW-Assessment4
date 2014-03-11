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
	private List<Tornado> tornadosToRemove;
	private Volcano volcano;
	
	
	public EventController(Airspace airspace){
		
		this.volcano = new Volcano();
		// this.tornado = new Tornado();
		this.listOfHunterFlights = new ArrayList<HunterFlight>();
		hunterFlight = new HunterFlight(airspace, this);
		this.listOfTornados = new ArrayList<Tornado>();		
		this.tornadosToRemove = new ArrayList<Tornado>();		

		

	}
	
	public void init (GameContainer gc) throws SlickException{
		this.volcano.init(gc);
		
		//hunterFlight.init(gc);
		//for (HunterFlight hunterFlight : listOfHunterFlights){
		//	hunterFlight.init(gc);
		//}
		
		
	}
	
	public void render (Graphics g, GameContainer gc) throws SlickException{
		this.volcano.render(g, gc);
		//hunterFlight.render(g, gc);
		//for (HunterFlight hunterFlight : listOfHunterFlights){
		//	hunterFlight.render(g, gc);
		//}

		for (Tornado tornado : listOfTornados){
			tornado.render(g, gc);
		}
	}
	
	public void update(GameContainer gc, Airspace airspace) throws SlickException{
		this.volcano.update(gc);
		//hunterFlight.update(airspace);
		//for (HunterFlight hunterFlight : listOfHunterFlights){
		//	hunterFlight.update(airspace);
		//}
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
		System.out.println(this.listOfTornados.size());
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

}
