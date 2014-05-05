package logicClasses;

public class Achievements {

	//FIELDS
	/* Default number of achievements */
	private int numberOfAchievements 			= 0;
	/* Constant holding the maximum number of achievements */
	private static final int MAXACHIEVEMENTS 	= 8; //not including all achivements earned
	/* How much should the user play to get the time achievement */
	private static final int ACHIEVEMENTTIME 	= 60000;
	/* Empty achievement message */
	private String achievementMessage 			= "";
	
	//Achievements gained booleans initiated to false
	private boolean silverAchievementGained 			= false;
	private boolean goldAchievementGained 				= false;
	private boolean timeAchievementGained 				= false;
	private boolean noPlaneLossAchievementGained 		= false;
	private boolean planeLandedAchievementGained 		= false;
	private boolean flightPlanChangedAchievementGained 	= false;
	private boolean crashAchievementGained 				= false;
	private boolean completeFlightPlanAchievementGained = false;
	
	private boolean allAchievementsEarned				= false;
	// Bool that is set/unset to render on-screen achievement box
	private boolean achievementGained                   = false; 
		
	//CONSTRUCTOR
	public Achievements(){

	}
	
	//METHODS
	/**
	 * Display the achievements text for the achievmeents related 
	 * to number of points
	 * @param pointsTotal How many points the user has at the time
	 * @return the message
	 */
	public String pointsAchievement(int pointsTotal){
		
		if (silverAchievementGained == false){
			if (pointsTotal >= 1000){
				//then display silver achievement
				achievementMessage = "Silver Achievement Gained";
				
				silverAchievementGained = true;
				completeAchievement();
			}
		}
		
		if (goldAchievementGained == false){
			if (pointsTotal >= 2000){
				//then display gold achievement
				achievementMessage = "Gold Achievement Gained";
				
				goldAchievementGained = true;
				completeAchievement();
			}	
		}
		
		return achievementMessage;
	}
	
	/**
	 * Used to increment total achievement count
	 */
	public void completeAchievement(){
		/* Keep count of achievements */
		numberOfAchievements += 1;
		achievementGained = true;
		
        // Display achievement
		if (numberOfAchievements == MAXACHIEVEMENTS){
			allAchievementsEarned = true;
			
		}
	}
	
	/**
	 * Time achievement for playing for a certain amount of time 
	 * @param gameTime How long the player has been played for
	 * @return the message
	 */
	public String timeAchievement(int gameTime){
		
		if (timeAchievementGained == false){
			if (gameTime >= ACHIEVEMENTTIME){
				// Then display achievement
				achievementMessage = "Time Achievement Gained";
				completeAchievement();
				timeAchievementGained = true;
			}
		}

		return achievementMessage;
	}
	
	/**
	 * Achievement for crashing 
	 * @return the message
	 */
	public String crashAchievement(){
		
		if (crashAchievementGained == false){
			// Display achievement
			achievementMessage = "Crash Achievement Gained";
			completeAchievement();
			crashAchievementGained = true;
		}
		
		return achievementMessage;
	}
	
	/**
	 * Achievement for changing the flight plan 
	 * @return the message
	 */
	public String changeFlightPlanAchievement(){
		if (flightPlanChangedAchievementGained == false){
			//display achievement
			achievementMessage = "Change Flight Plan Achievement Gained";
			completeAchievement();
			flightPlanChangedAchievementGained = true;
		}
		return achievementMessage;
	}
	
	/**
	 * Achievement for ocmpleting a flight plan 
	 * @return the message
	 */
	public String completeFlightPlanAchievement(){
		if (completeFlightPlanAchievementGained == false){
            //display achievement
            achievementMessage = "Complete Flight Plan Achievement Gained";
            completeAchievement();
            completeFlightPlanAchievementGained = true;
		}
		return achievementMessage;
	}
	
	/**
	 * Minutes passed without  losing a plane
	 * @return
	 */
	public String minsWithoutPlaneLossAchievement(){
		if (noPlaneLossAchievementGained == false){
            //display achievement
            achievementMessage = "Time Without Losing Plane Achievement Gained";
            completeAchievement();
            noPlaneLossAchievementGained = true;
		}
		return achievementMessage;
	}
	
	
	/**
	 * achievment for landing a plane 
	 * @return the message
	 */
	public String planeLandedAchievement(){
		if (planeLandedAchievementGained == false){
            achievementMessage = "Landing Achievement Gained";
            completeAchievement();
            planeLandedAchievementGained = true;
		}
		return achievementMessage;
	}
	
	//GETTERS AND SETTERS

	public int getNumberOfAchievements(){
		return numberOfAchievements;
	}
	
	public void setNumberOfAchievements(int noAchieved){
		numberOfAchievements += noAchieved;
	}
	
	public boolean getAchievementGained(){
		return achievementGained;
	}
	
	public void setAchievementGained(boolean achievementGained){
		this.achievementGained = achievementGained;
	}
	
	public boolean getSilverAchievementGained(){
		return silverAchievementGained;
	}
	
	public boolean getGoldAchievementGained(){
		return goldAchievementGained; 	
	}
	
	public boolean getTimeAchievementGained(){
		return timeAchievementGained;
	}
	
	public boolean getNoPlaneLossAchievementGained(){
		return noPlaneLossAchievementGained;
	}
	
	public boolean getPlaneLandedAchievementGained(){
		return planeLandedAchievementGained;
	}
	
	public boolean getFlightPlanChangedAchievementGained(){
		return flightPlanChangedAchievementGained;
	}
	
	public boolean getCrashAchievementGained(){
		return crashAchievementGained;
	}
	
	public boolean getCompleteFlightPlanAchievementGained(){
		return completeFlightPlanAchievementGained;
	}
	
	public boolean getAllAchievementsEarned(){
		return allAchievementsEarned;
	}
}
