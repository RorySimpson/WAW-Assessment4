package competitive;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import logicClasses.Airspace;
import logicClasses.Flight;
import logicClasses.FlightPlan;
import logicClasses.Point;

public class FlightPlanCompetitive extends FlightPlan {

	public FlightPlanCompetitive(Airspace airspace, FlightCompetitive flight, boolean competitive){
		super(airspace, flight, competitive);
		this.currentRoute = new ArrayList <Point>() ; 
		this.currentRoute.add(airspace.getAirportRight().getBeginningOfRunway());
		this.currentRoute.add(airspace.getAirportRight().getEndOfRunway());
		
		
	}
	
	public void assignEntryPoint(Airspace airspace){
		if(((FlightCompetitive)flight).isPlayer2()){
			flight.setX(airspace.getListOfEntryPoints().get(0).getX()); 
			flight.setY(airspace.getListOfEntryPoints().get(0).getY());
			entryPoint = airspace.getListOfEntryPoints().get(0);
		}
		
		else{
			flight.setX(airspace.getListOfEntryPoints().get(1).getX()); 
			flight.setY(airspace.getListOfEntryPoints().get(1).getY());
			entryPoint = airspace.getListOfEntryPoints().get(1);
		}
	}
	
	public void update(){
		
		if(currentRoute.size() == 0){
			return;
		}
		
		if(((FlightCompetitive)flight).checkIfFlightAtWaypoint(currentRoute.get(0))){
			currentRoute.remove(0);
		}
	}
	

	

	

	
	
}
