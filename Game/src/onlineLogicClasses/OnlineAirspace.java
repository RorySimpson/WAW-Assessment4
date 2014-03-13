package onlineLogicClasses;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import logicClasses.Airspace;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class OnlineAirspace extends Airspace{
@Override
public void update(GameContainer gc) throws SlickException {

	try {
		Socket client = new Socket("localhost",6789);
		ObjectInputStream is = new ObjectInputStream(client.getInputStream());
		ArrayList<Integer> ints = (ArrayList<Integer>) is.readObject();
		for(int i : ints) {
			System.out.println(i);
		}
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

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
