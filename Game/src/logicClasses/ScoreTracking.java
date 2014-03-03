package logicClasses;

public class ScoreTracking {
	
	private int currentScore = 0;
	private int currentMultiplier = 1;
	private int progressionTowardsNextMultiplier = 0;
	
	private int waypointScore;
	private static final int TIMESCORE = 2;		//constant for the time scoring
	private static final int FLIGHTPLANCHANGE = 10;
	private static final int FLIGHTLOST = 50;
	private static final int FLIGHTLOSTMUTLIPLIERREDUCTION = 200;
	private static final int WAYPOINTREACHEDMULIPLIERINCREASE = 200;
	private static final int MULTIPLIERINCREASEINTERVAL = 1000;
	private Achievements achievements;
	private boolean negMult = false;
	private boolean multiplierInc = false;
		
	//CONSTRUCTOR
	public ScoreTracking() {
		achievements = new Achievements();
	}
	
	//METHODS
	// Positive scoring
	public int updateWaypointScore(int closestDistance){
					
		if (closestDistance >= 0 && closestDistance <= 14){		//checks to see if the plane is within 10 pixels
			waypointScore = 100;								//if yes, the score given is 100 points
		}
					
		if (closestDistance >= 15 && closestDistance <= 28){	
			waypointScore = 50;
		}
					
		if (closestDistance >= 29 && closestDistance <= 42){
			waypointScore = 20;
		}
			
		return waypointScore;									//once the distance and points are found, return the score
					
	}
		
	public int updateScore(int score){
		return currentScore+= currentMultiplier * score;			//increase the current score by the score passed by parameter
	}
	
	public String scoreAchievement(){
		String achievementScore = achievements.pointsAchievement(currentScore);
		return achievementScore;
	}
	
	public int updateTimeScore(){
		currentScore +=  currentMultiplier * TIMESCORE;
		achievements.pointsAchievement(currentScore);
		return currentScore;
	}
	
	//Negative Scoring
	public int reduceScoreOnFlightplanChange(){
		currentScore -= FLIGHTPLANCHANGE;
		achievements.changeFlightPlanAchievement();
		return currentScore;
	}
	
	public int reduceMultiplierOnFlightLost(){
		if (progressionTowardsNextMultiplier - FLIGHTLOSTMUTLIPLIERREDUCTION >= 0){
			progressionTowardsNextMultiplier -= FLIGHTLOSTMUTLIPLIERREDUCTION;
			negMult = true;	
		}
		else if(progressionTowardsNextMultiplier - FLIGHTLOSTMUTLIPLIERREDUCTION < 0 && currentMultiplier == 1){
			progressionTowardsNextMultiplier = 0;
			negMult = true;
		}
		else if(progressionTowardsNextMultiplier - FLIGHTLOSTMUTLIPLIERREDUCTION < 0 && currentMultiplier != 1){
			currentMultiplier -= 1;
			progressionTowardsNextMultiplier = 0;
			negMult = true;
		}
		return progressionTowardsNextMultiplier;
	}
	
	public static float getMultiplierincreaseinterval() {
		return MULTIPLIERINCREASEINTERVAL;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	public int getCurrentMultiplier() {
		return currentMultiplier;
	}

	public void setCurrentMultiplier(int currentMultiplier) {
		this.currentMultiplier = currentMultiplier;
	}

	public float getProgressionTowardsNextMultiplier() {
		return progressionTowardsNextMultiplier;
	}

	public void setProgressionTowardsNextMultiplier(int progressionTowardsNextMultiplier) {
		this.progressionTowardsNextMultiplier = progressionTowardsNextMultiplier;
	}

	public int increaseMultiplierOnWaypointPassed(){
		progressionTowardsNextMultiplier +=  WAYPOINTREACHEDMULIPLIERINCREASE;
		negMult = false;
		if (progressionTowardsNextMultiplier > MULTIPLIERINCREASEINTERVAL ){
			currentMultiplier += 1;
			multiplierInc = true;
			progressionTowardsNextMultiplier = 0;
			
		}
		return progressionTowardsNextMultiplier;
	}
	
	
	public int reduceScoreOnFlightLost(){
		currentScore -= FLIGHTLOST;
		return currentScore;
	}
	
	public void resetScore(){
		currentScore = 0;
		progressionTowardsNextMultiplier = 0;
		currentMultiplier = 1;
	}
	
	public boolean getNegMult(){
		return this.negMult;
	}
	
	public void setNegMult(boolean newVal){
		this.negMult = newVal;
	}
	
	public int getScore(){
		return currentScore;
	}
	
	public String toString(){
		String s = Integer.toString(currentScore);
		return s;
	}
	
	public Achievements getAchievements(){
		return this.achievements;
	}	
	
	public boolean getMultiplierInc(){
		return multiplierInc;
	}
	
	public void setMultiplierInc(boolean multiplierInc){
		this.multiplierInc = multiplierInc;
	}
}
