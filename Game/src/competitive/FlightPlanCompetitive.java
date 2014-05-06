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

	private AirspaceCompetitive airspace;
	private FlightCompetitive flight;

	public FlightPlanCompetitive(AirspaceCompetitive airspace,
			FlightCompetitive flight, boolean competitive) {
		super(airspace, flight, competitive);
		this.currentRoute = new ArrayList<Point>();
		this.currentRoute
				.add(airspace.getAirportRight().getBeginningOfRunway());
		this.currentRoute.add(airspace.getAirportRight().getEndOfRunway());
		this.airspace = airspace;
		this.flight = flight;

	}

	/**
	 * assignEntryPoint: assigns an entrypoint to a new flight.
	 * 
	 * @param airspace
	 */

	public void assignEntryPoint(AirspaceCompetitive airspace) {

		// If flight is for player 1, assign left hand side entrypoint
		if (flight.isPlayer2()) {
			flight.setX(airspace.getListOfEntryPoints().get(0).getX());
			flight.setY(airspace.getListOfEntryPoints().get(0).getY());
			entryPoint = airspace.getListOfEntryPoints().get(0);
		}

		// If flight is for player 2, assign right hand side entrypoint
		else {
			flight.setX(airspace.getListOfEntryPoints().get(1).getX());
			flight.setY(airspace.getListOfEntryPoints().get(1).getY());
			entryPoint = airspace.getListOfEntryPoints().get(1);
		}
	}

	/**
	 * update: updates all flight plan logic
	 */
	public void update() {

		if (currentRoute.size() == 0) {
			return;
		}

		// If flight is at next waypoint, remove that waypoint from the
		// plan.
		if (flight.checkIfFlightAtWaypoint(currentRoute.get(0))) {
			currentRoute.remove(0);
		}
	}

}
