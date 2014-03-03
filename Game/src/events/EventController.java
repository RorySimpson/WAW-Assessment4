package events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logicClasses.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class EventController {
	
	private List<HunterFlight> 	listOfHunterFlights;
	private Volcano volcano;
	
	public EventController(){
		
		this.listOfHunterFlights  = new ArrayList<HunterFlight>();
		this.volcano = new Volcano();
		
	}
	
	public void init (GameContainer gc) throws SlickException{
		this.volcano.init(gc);
	}
	
	public void render (Graphics g, GameContainer gc) throws SlickException{
		this.volcano.render(g,gc);
	}
	
	public void update(GameContainer gc) throws SlickException{
		this.volcano.update(gc);
	}
	
	public Volcano getVolcano(){
		return this.volcano;
	}

}
