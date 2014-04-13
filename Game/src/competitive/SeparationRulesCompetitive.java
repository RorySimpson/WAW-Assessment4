package competitive;

import java.awt.geom.Point2D;
import java.util.Random;

import events.*;
import logicClasses.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


public class SeparationRulesCompetitive extends SeparationRules {
	
	protected final static int CARGOPICKUPDISTANCE = 15;
	
	public SeparationRulesCompetitive(int difficultyVal){
		super(difficultyVal);
		this.gameOverLateralSeparation = 30;
		this.gameOverVerticalSeparation = 200;
		 
	 }

	public double lateralDistanceBetweenFlightAndCargo(Flight flight1, CargoCompetitive cargo){
		return Math.sqrt(Math.pow((flight1.getX() - cargo.getLocation().getX()), 2) + Math.pow(( flight1.getY() - cargo.getLocation().getY()),2));
	}

	public void detectCargoPickUp(AirspaceCompetitive airspace){
		
		for (int i = 0; i < airspace.getListOfFlights().size(); i++){

			if ((lateralDistanceBetweenFlightAndCargo(airspace.getListOfFlights().get(i), airspace.getCargo()) < CARGOPICKUPDISTANCE)){
				System.out.println("check holder");
				if(airspace.getCargo().getCurrentHolder() == null){
					System.out.println("should pick up");
					airspace.getCargo().setCurrentHolder(airspace.getListOfFlights().get(i));
				}


			}

		}
	}
	
	public void checkFlightOnFlightViolation(AirspaceCompetitive airspace){
		
		System.out.println(this.gameOverLateralSeparation);
		System.out.println(this.gameOverVerticalSeparation);
		for (int i = 0; i < airspace.getListOfFlights().size(); i++){

			for (int j = i+1; j < airspace.getListOfFlights().size(); j++){
				//System.out.println(lateralDistanceBetweenFlights(airspace.getListOfFlights().get(i), airspace.getListOfFlights().get(j)));
				if ((lateralDistanceBetweenFlights(airspace.getListOfFlights().get(i), airspace.getListOfFlights().get(j)) < this.gameOverLateralSeparation)){
					//System.out.println(verticalDistanceBetweenFlights(airspace.getListOfFlights().get(i), airspace.getListOfFlights().get(j)));
					if ((verticalDistanceBetweenFlights(airspace.getListOfFlights().get(i), airspace.getListOfFlights().get(j)) < this.gameOverVerticalSeparation)){
						System.out.println("crash");
						this.gameOverViolation = true;
						airspace.getListOfFlights().get(i).setVelocity(0);
						airspace.getListOfFlights().get(i).setTargetVelocity(0);
						airspace.getListOfFlights().get(j).setVelocity(0);
						airspace.getListOfFlights().get(j).setTargetVelocity(0);
						this.pointOfCrash.setLocation(airspace.getListOfFlights().get(i).getX(), airspace.getListOfFlights().get(i).getY());

					}
				}
			}
		}
	}
	
	
	public void update(AirspaceCompetitive airspace) {

		detectCargoPickUp(airspace);
		this.checkFlightOnFlightViolation(airspace);
		
	}

}
