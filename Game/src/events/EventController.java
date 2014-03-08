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
	private Volcano volcano;
	
	public EventController(Airspace airspace){
		
		this.volcano = new Volcano();
		this.listOfHunterFlights = new ArrayList<HunterFlight>();
		hunterFlight = new HunterFlight(airspace, this);
		this.listOfTornados = new ArrayList<Tornado>();
		
		
		
		
	}
	
	public void init (GameContainer gc) throws SlickException{
		this.volcano.init(gc);
		//hunterFlight.init(gc);
		//for (HunterFlight hunterFlight : listOfHunterFlights){
		//	hunterFlight.init(gc);
		//}
	}
	
	public void render (Graphics g, GameContainer gc) throws SlickException{
		this.volcano.render(g,gc);
		//hunterFlight.render(g, gc);
		//for (HunterFlight hunterFlight : listOfHunterFlights){
		//	hunterFlight.render(g, gc);
		//}
	}
	
	public void update(GameContainer gc, Airspace airspace) throws SlickException{
		this.volcano.update(gc);
		//hunterFlight.update(airspace);
		//for (HunterFlight hunterFlight : listOfHunterFlights){
		//	hunterFlight.update(airspace);
		//}
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
