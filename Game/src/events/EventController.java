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
	
	public void render(){
		this.volcano.render();
	}
	
	public void update(){
		this.volcano.update();
	}
	
	public Volcano getVolcano(){
		return this.volcano;
	}

}
