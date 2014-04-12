package competitive;

import java.awt.geom.Point2D;
import java.util.Random;

import events.*;
import logicClasses.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


public class SeparationRulesCompetitive extends SeparationRules {
	
	protected final static int CARGOPICKUPDISTANCE = 5;
	
	public SeparationRulesCompetitive(int difficultyVal){
		super(difficultyVal);
		 
	 }

	public double lateralDistanceBetweenFlightAndCargo(Flight flight1, CargoCompetitive cargo){
		return Math.sqrt(Math.pow((flight1.getX() - cargo.getLocation().getX()), 2) + Math.pow(( flight1.getY() - cargo.getLocation().getY()),2));
	}

	public void detectCargoPickUp(AirspaceCompetitive airspace){
		for (int i = 0; i < airspace.getListOfFlights().size(); i++){

			if ((lateralDistanceBetweenFlightAndCargo(airspace.getListOfFlights().get(i), airspace.getCargo()) < CARGOPICKUPDISTANCE)){

				if(airspace.getCargo().getCurrentHolder() == null){
					airspace.getCargo().setCurrentHolder(airspace.getListOfFlights().get(i));
				}


			}

		}
	}
	
	
	public void update(AirspaceCompetitive airspace) {

		detectCargoPickUp(airspace);
		this.checkFlightOnFlightViolation(airspace);
		
	}

}
