package logicClasses;

public class ScoreTracking {

	/* Attributes */

	/* Defaults the variables needed for scoring */
	private int currentScore = 0;
	private int currentMultiplier = 1;
	private int progressionTowardsNextMultiplier = 0;
	private int waypointScore;

	// Constants for the scoring
	private static final int TIMESCORE = 2;
	private static final int FLIGHTPLANCHANGE = 10;
	private static final int FLIGHTLOST = 50;
	private static final int FLIGHTLOSTMUTLIPLIERREDUCTION = 200;
	private static final int WAYPOINTREACHEDMULIPLIERINCREASE = 200;
	private static final int MULTIPLIERINCREASEINTERVAL = 1000;

	private Achievements achievements;
	/* Initalise the attributes for the multiplier */
	private boolean negMult = false;
	private boolean multiplierInc = false;

	// CONSTRUCTOR
	/**
	 * Constructor that creates an achievements object because achievements
	 * change the score
	 */
	public ScoreTracking() {
		achievements = new Achievements();
	}

	// METHODS
	// Positive scoring
	public int updateWaypointScore(int closestDistance) {

		// checks to see if the plane is within 10 pixels
		if (closestDistance >= 0 && closestDistance <= 14) {
			// if yes, the score given is 100 points
			waypointScore = 100;
		}

		if (closestDistance >= 15 && closestDistance <= 28) {
			// 50 points
			waypointScore = 50;
		}

		if (closestDistance >= 29 && closestDistance <= 42) {
			// 20 points
			waypointScore = 20;
		}

		// once the distance and points are found, return the score
		return waypointScore;

	}

	/**
	 * Method to update score when a new value is gained
	 * 
	 * @param score
	 *            score
	 * @param bonusFlight
	 *            whether the plane was a bonus flight
	 * @return
	 */
	public int updateScore(int score, boolean bonusFlight) {

		// increase the current score by the score passed by parameter.
		// If the flight was a bonus flight award more points.
		if (bonusFlight) {
			return currentScore += (currentMultiplier * score) * 10;
		} else {
			return currentScore += currentMultiplier * score;
		}
	}

	/**
	 * Score achievements
	 * 
	 * @return the new score
	 */
	public String scoreAchievement() {
		String achievementScore = achievements.pointsAchievement(currentScore);
		return achievementScore;
	}

	/**
	 * Updates the score as the player keeps going
	 * 
	 * @return
	 */
	public int updateTimeScore() {
		currentScore += currentMultiplier * TIMESCORE;
		// Checks if the player has a score that warrants for an
		// achievement
		achievements.pointsAchievement(currentScore);
		return currentScore;
	}

	/**
	 * Penalises player for changing the flight plan
	 * 
	 * @return the new score
	 */
	public int reduceScoreOnFlightplanChange() {
		currentScore -= FLIGHTPLANCHANGE;
		achievements.changeFlightPlanAchievement();
		return currentScore;
	}

	/**
	 * Decreases the multipler when a flight was lost
	 * 
	 * @return
	 */
	public int reduceMultiplierOnFlightLost() {
		if (progressionTowardsNextMultiplier - FLIGHTLOSTMUTLIPLIERREDUCTION >= 0) {
			progressionTowardsNextMultiplier -= FLIGHTLOSTMUTLIPLIERREDUCTION;
			negMult = true;
		} else if (progressionTowardsNextMultiplier
				- FLIGHTLOSTMUTLIPLIERREDUCTION < 0
				&& currentMultiplier == 1) {
			progressionTowardsNextMultiplier = 0;
			negMult = true;
		} else if (progressionTowardsNextMultiplier
				- FLIGHTLOSTMUTLIPLIERREDUCTION < 0
				&& currentMultiplier != 1) {
			currentMultiplier -= 1;
			progressionTowardsNextMultiplier = 0;
			negMult = true;
		}
		return progressionTowardsNextMultiplier;
	}

	/**
	 * Increases the multpilier when a waypoint is passed
	 * 
	 * @return
	 */
	public int increaseMultiplierOnWaypointPassed() {
		progressionTowardsNextMultiplier += WAYPOINTREACHEDMULIPLIERINCREASE;
		negMult = false;
		if (progressionTowardsNextMultiplier > MULTIPLIERINCREASEINTERVAL) {
			currentMultiplier += 1;
			multiplierInc = true;
			progressionTowardsNextMultiplier = 0;

		}
		return progressionTowardsNextMultiplier;
	}

	/**
	 * Decreases score when a flight leaves the airspace without going through
	 * its exitpoint
	 * 
	 * @return
	 */
	public int reduceScoreOnFlightLost() {
		currentScore -= FLIGHTLOST;
		return currentScore;
	}

	/**
	 * Resets score and the multiplier to 0
	 */
	public void resetScore() {
		currentScore = 0;
		progressionTowardsNextMultiplier = 0;
		currentMultiplier = 1;
	}

	// Assists with achievement events
	public void procCompleteFlightAchieve() {
		achievements.completeFlightPlanAchievement();
	}

	public void procminsWithoutPlaneLossAchievement() {
		achievements.minsWithoutPlaneLossAchievement();
	}

	public boolean getNegMult() {
		return this.negMult;
	}

	public void setNegMult(boolean newVal) {
		this.negMult = newVal;
	}

	public int getScore() {
		return currentScore;
	}

	public String toString() {
		String s = Integer.toString(currentScore);
		return s;
	}

	public Achievements getAchievements() {
		return this.achievements;
	}

	public boolean getMultiplierInc() {
		return multiplierInc;
	}

	public void setMultiplierInc(boolean multiplierInc) {
		this.multiplierInc = multiplierInc;
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

	public void setProgressionTowardsNextMultiplier(
			int progressionTowardsNextMultiplier) {
		this.progressionTowardsNextMultiplier = progressionTowardsNextMultiplier;
	}
}