package logicClasses;

public class Achievements {

	//FIELDS
	private int numberOfAchievements 			= 0;
	private static final int MAXACHIEVEMENTS 	= 8;
	private static final int ACHIEVEMENTTIME 	= 600000;
	private String achievementMessage 			= "";
	
	//Achievements gained booleans
	private boolean silverAchievementGained 			= false;
	private boolean goldAchievementGained 				= false;
	private boolean timeAchievementGained 				= false;
	private boolean noPlaneLossAchievementGained 		= false;
	private boolean planesLandedAchievementGained 		= false;
	private boolean flightPlanChangedAchievementGained 	= false;
	private boolean crashAchievementGained 				= false;
	private boolean completeFlightPlanAchievementGained = false;
	
	private boolean allAchievementsEarned				= false;
	private boolean achievementGained                   = false; //bool that is set/deset to render onscreen achievement box
		
	//CONSTRUCTOR
	public Achievements(){

	}
	
	//METHODS
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
	
	//used to increment total achievement count, sets bool used for rendering achievement box
	public void completeAchievement(){
		numberOfAchievements += 1;
		achievementGained = true;
		
		if (numberOfAchievements == MAXACHIEVEMENTS){
			//then display achievement
			allAchievementsEarned = true;
			
		}
		
	}
	
	public String timeAchievement(int gameTime){
		
		if (timeAchievementGained == false){
			if (gameTime >= ACHIEVEMENTTIME){
				//then display achievement
				achievementMessage = "Time Achievement Gained";
				completeAchievement();
				timeAchievementGained = true;
			}
		}
		
		return achievementMessage;
	}
	
	public String crashAchievement(int gameTime){
		
		if (gameTime <= 40000 && crashAchievementGained == false){
			//then display achievement
			achievementMessage = "Crash Achievement Gained";
			completeAchievement();
			crashAchievementGained = true;
		}
		
		return achievementMessage;
	}
	
	public String changeFlightPlanAchievement(){
		if (flightPlanChangedAchievementGained == false){
			//display achievement
			achievementMessage = "Change Flight Plan Achievement Gained";
			completeAchievement();
			flightPlanChangedAchievementGained = true;
		}
		return achievementMessage;
	}
	
	//Never Called!!
	public String completeFlightPlanAchievement(){
		if (completeFlightPlanAchievementGained == false){
		//display achievement
		achievementMessage = "Complete Flight Plan Achievement Gained";
		completeAchievement();
		completeFlightPlanAchievementGained = true;
		}
		return achievementMessage;
	}
	
	//Never Called!!
	public String minsWithoutPlaneLossAchievement(int timeWithoutLoss){
		if (noPlaneLossAchievementGained == false){
			if (timeWithoutLoss >= 10){
				//display achievement
				achievementMessage = "Time Without Losing Plane Achievement Gained";
				completeAchievement();
				noPlaneLossAchievementGained = true;
			}
		}
		return achievementMessage;
	}
	
	//Never Called!!
	public String planesLandedAchievement(int planesLanded){
		if (planesLandedAchievementGained == false){
			if (planesLanded >= 10){
				//display achievement
				achievementMessage = "Landing Achievement Gained";
				completeAchievement();
				planesLandedAchievementGained = true;
			}
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
}
