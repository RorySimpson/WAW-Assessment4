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

	public FlightPlanCompetitive(Airspace airspace, Flight flight, boolean competitive){
		super(airspace, flight, competitive);
		this.currentRoute = new ArrayList <Point>() ; 
		this.currentRoute.add(airspace.getAirportRight().getBeginningOfRunway());
		this.currentRoute.add(airspace.getAirportRight().getEndOfRunway());
		
	}
	
	@Override
	public int generateVelocity() {
		Random rand = new Random();
		
		int	min = flight.getMinVelocity(),
			max = flight.getMaxVelocity();
		return (rand.nextInt(min) + (max -min));
	}
	
	
}
