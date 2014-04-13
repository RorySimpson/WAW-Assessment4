package competitive;

import java.awt.geom.Point2D;
import java.util.Random;
import java.util.ArrayList;

import events.*;
import logicClasses.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


public class SeparationRulesCompetitive extends SeparationRules {
	
	protected final static int CARGOPICKUPDISTANCE = 15;
	private ArrayList<CrashCompetitive> listOfActiveCrashes;
	
	
	public SeparationRulesCompetitive(int difficultyVal){
		super(difficultyVal);
		this.gameOverLateralSeparation = 30;
		this.gameOverVerticalSeparation = 200;
		this.listOfActiveCrashes = new ArrayList<CrashCompetitive>();
		 
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
		

		for (int i = 0; i < airspace.getListOfFlights().size(); i++){

			for (int j = i+1; j < airspace.getListOfFlights().size(); j++){
				
				if ((lateralDistanceBetweenFlights(airspace.getListOfFlights().get(i), airspace.getListOfFlights().get(j)) < this.gameOverLateralSeparation)){
					
					if ((verticalDistanceBetweenFlights(airspace.getListOfFlights().get(i), airspace.getListOfFlights().get(j)) < this.gameOverVerticalSeparation)){
						if((airspace.getCargo().getCurrentHolder() == airspace.getListOfFlights().get(i)) || (airspace.getCargo().getCurrentHolder() == airspace.getListOfFlights().get(i))){
							airspace.getCargo().setCurrentHolder(null);
							airspace.getCargo().getLocation().setLocation(100, 100); // Change this when flights can disappear.
						}
						this.gameOverViolation = true;
						airspace.getListOfFlights().get(i).setVelocity(0);
						airspace.getListOfFlights().get(i).setTargetVelocity(0);
						airspace.getListOfFlights().get(j).setVelocity(0);
						airspace.getListOfFlights().get(j).setTargetVelocity(0);
						listOfActiveCrashes.add(new CrashCompetitive(airspace.getListOfFlights().get(i), airspace.getListOfFlights().get(j), new Point2D.Double(airspace.getListOfFlights().get(i).getX(), airspace.getListOfFlights().get(i).getY())));
					}
				}
			}
		}
	}
	
	public void removeCrash(AirspaceCompetitive airspace, CrashCompetitive crash){
		
		Flight flight1 = crash.getFlight1();
		Flight flight2 = crash.getFlight2();
	
		
		if(airspace.getListOfFlightsPlayer1().contains(flight1)) {
			airspace.getListOfFlightsPlayer1().remove(flight1);
		}
		
		else if(airspace.getListOfFlightsPlayer2().contains(flight1)) {
			airspace.getListOfFlightsPlayer2().remove(flight1);
		}
		
		if(airspace.getListOfFlightsPlayer1().contains(flight2)) {
			airspace.getListOfFlightsPlayer1().remove(flight2);
		}
		
		else if(airspace.getListOfFlightsPlayer2().contains(flight2)) {
			airspace.getListOfFlightsPlayer2().remove(flight2);
		}
		
		airspace.getListOfFlightsInAirspace().remove(flight1);
		airspace.getListOfFlightsInAirspace().remove(flight2);
		
		Flight selected1 = ((ControlsCompetitive) airspace.getControls()).getSelectedFlight1();
		Flight selected2 = ((ControlsCompetitive) airspace.getControls()).getSelectedFlight2();
		
		// If flight was selected, de-select it
		if (!(airspace.getListOfFlightsInAirspace().contains(selected1))) {
			((ControlsCompetitive) airspace.getControls()).setSelectedFlight1(null);

		}
		
		if (!(airspace.getListOfFlightsInAirspace().contains(selected2))) {
			((ControlsCompetitive) airspace.getControls()).setSelectedFlight2(null);

		}
		
		listOfActiveCrashes.remove(crash);
		
	}
	
	
	public void update(AirspaceCompetitive airspace) {
		
		for(int i = 0; i < listOfActiveCrashes.size(); i++){
			listOfActiveCrashes.get(i).setCountdownTillRemoval(listOfActiveCrashes.get(i).getCountdownTillRemoval()-1);
			if(listOfActiveCrashes.get(i).getCountdownTillRemoval() <= 0){
				removeCrash(airspace, listOfActiveCrashes.get(i));
			}
		}

		detectCargoPickUp(airspace);
		this.checkFlightOnFlightViolation(airspace);
		
	}

	public ArrayList<CrashCompetitive> getListOfActiveCrashes() {
		return listOfActiveCrashes;
	}

	public void setListOfActiveCrashes(
			ArrayList<CrashCompetitive> listOfActiveCrashes) {
		this.listOfActiveCrashes = listOfActiveCrashes;
	}

}
