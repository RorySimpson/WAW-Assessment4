package logicClasses;

public class Achievements {

	//FIELDS
	private int numberOfAchievements 			= 0;
	private static final int MAXACHIEVEMENTS 	= 9;
	private static final int ACHIEVEMENTTIME 	= 600000;
	private String achievementMessage 			= "";
	
	//Achievements gained booleans
	private boolean silverAchievementGained 			= false;
	private boolean goldAchievementGained 				= false;
	private boolean timeAchievementGained 				= false;
	private boolean noPlaneLossAchievementGained 		= false;
	private boolean planesLandedAchievementGained 		= false;
	private boolean flightPlanChangedAchievementGained 	= false;
		
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
	
	public void completeAchievement(){
		if (numberOfAchievements >= MAXACHIEVEMENTS){
			System.out.println("All Achievements Gained");
			//then display achievement
		}
		numberOfAchievements += 1;
	}
	
	public void timeAchievement(int gameTime){
		
		if (timeAchievementGained == false){
			if (gameTime >= ACHIEVEMENTTIME){
				//then display achievement
				System.out.println("Time Achievement Gained");
				completeAchievement();
				timeAchievementGained = true;
			}
		}
	}
	
	public String crashAchievement(int gameTime){
		
		if (gameTime <= 40000){
			//then display achievement
			achievementMessage = "Crash Achievement Gained";
			completeAchievement();
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
	
	public void completeFlightPlanAchievement(){
		//display achievement
		System.out.println("Complete Flight Plan Achievement Gained");
		completeAchievement();
	}
	
	public void minsWithoutPlaneLossAchievement(int timeWithoutLoss){
		if (noPlaneLossAchievementGained == false){
			if (timeWithoutLoss >= 10){
				//display achievement
				System.out.println("Time Without Losing Plane Achievement Gained");
				completeAchievement();
				noPlaneLossAchievementGained = true;
			}
		}
	}
	
	public void planesLandedAchievement(int planesLanded){
		if (planesLandedAchievementGained == false){
			if (planesLanded >= 10){
				//display achievement
				System.out.println("Landing Achievement Gained");
				completeAchievement();
				planesLandedAchievementGained = true;
			}
		}
	}
	
	//GETTERS AND SETTERS
	
	public int getNumberOfAchievements(){
		return numberOfAchievements;
	}
	
	public void setNumberOfAchievements(int noAchieved){
		numberOfAchievements += noAchieved;
	}
}
