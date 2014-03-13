package onlineLogicClasses;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import logicClasses.Airspace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import client.Client;

public class OnlineAirspace extends Airspace {

	Client client;
	
	public void enter() {
		
		client = new Client();
		client.start();
		
	}

	public void init(GameContainer gc) throws SlickException {

		this.controls.init(gc);
		this.airportLeft.init(gc);
		this.airportRight.init(gc);
		this.eventController.init(gc);
		

		for (int i = 0; i < this.listOfWaypoints.size(); i++) { // Initialising waypoints
			this.listOfWaypoints.get(i).init(gc);
		}

		for (int i = 0; i < this.listOfExitPoints.size(); i++) { // Initailising exit points
			this.listOfExitPoints.get(i).init(gc);
		}

		for (int i = 0; i < this.listOfEntryPoints.size(); i++) { // Initialising entry point
			this.listOfEntryPoints.get(i).init(gc);
		}

	}
	@Override
	public void update(GameContainer gc) throws SlickException {

		


		this.numberOfGameLoopsSinceLastFlightAdded++;
		this.numberOfGameLoops++;
		if (this.numberOfGameLoops >= this.numberOfGameLoopsWhenDifficultyIncreases) {
			this.increaseDifficulty();

		}

		for (int i = 0; i < this.listOfFlightsInAirspace.size(); i++) {
			this.listOfFlightsInAirspace.get(i).update(score);
			if(this.listOfFlightsInAirspace.get(i).getFlightPlan().getCurrentRoute().size()==0) {
				this.removeSpecificFlight(i);
			}
			else if (this.checkIfFlightHasLeftAirspace(this.getListOfFlights().get(i))) {
				if(this.getListOfFlights().get(i).isControllable()){
					score.reduceScoreOnFlightLost();
					score.reduceMultiplierOnFlightLost();

				}
				this.removeSpecificFlight(i);
			}

		}

		this.eventController.update(gc, this);
		this.separationRules.update(this);
		this.controls.update(gc, this);
	}
	

}
