package competitive;

import java.util.Random;
import logicClasses.*;

public class FlightCompetitive extends Flight {
	
	private int velocity;
	
	public FlightCompetitive(Airspace airspace, Boolean competitive){
		super(airspace, competitive);
		velocity = generateVelocity();
		
	}
	
	
	/**
	 * generateVelocity: Creates a velocity from a range of values
	 */

	public int generateVelocity() {
		Random rand = new Random();
		
		
		int	min = this.getMinVelocity(),
			max = this.getMaxVelocity();
		return (rand.nextInt(min) + (max -min));
	}

}
