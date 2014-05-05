package stateContainer;

import org.newdawn.slick.AppGameContainer;



import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import competitive.*;
import coop.*;
import states.*;
import competitive.PlayCompetitiveState;
import logicClasses.Achievements;

public class Game extends StateBasedGame {

	public static final String NAME = "Expect Turbulence";
	
	public static final int 
		SPLASHSTATE = 0,
		MENUSTATE = 1,
	 	PLAYSTATE = 2,
		GAMEOVERSTATE = 3,
		PAUSESTATE = 4,
		CREDITSSTATE = 5,
		CONTROLSSTATE = 6,
		SCORESTATE = 7,
		PLAYCOOPSTATE = 8,
		PAUSECOOPSTATE = 9,
		ACHIEVEVIEWERSTATE = 10,
		PLAYCOMPETITIVESTATE = 12,
		GAMEOVERLOADINGSTATE = 13,
		PAUSECOMPETITIVESTATE = 14,
		GAMEOVERCOMPETITIVESTATE = 15,
		GAMEOVERCOOPSTATE = 16,
		MODESTATE = 17;
	

	
	public static final int 
		MAXIMUMWIDTH = 1200, MAXIMUMHEIGHT = 600;
	
	private int currentScore;
	private String previousCompetitiveModeWinner;
	private Achievements achievements = new Achievements();
	private boolean gameEnded;


	/**
	 * Adds all states to a container 
	 * @param NAME The game's title
	 */

	public Game(String NAME) {
		super(NAME);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		
		addState(new SplashState(SPLASHSTATE));
		enterState(SPLASHSTATE);
		addState(new MenuState(MENUSTATE));
		addState(new PlayState(PLAYSTATE));
		addState(new GameOverState(GAMEOVERSTATE));
		addState(new PauseState(PAUSESTATE));
		addState(new CreditsState(CREDITSSTATE));
		addState(new ControlsState(CONTROLSSTATE));
		addState(new ScoreState(SCORESTATE));
		addState(new PlayCoopState(PLAYCOOPSTATE));
		addState(new PauseCoopState(PAUSECOOPSTATE));
		addState(new AchieveViewerState(ACHIEVEVIEWERSTATE));
		addState(new PlayCompetitiveState(PLAYCOMPETITIVESTATE));
		addState(new GameOverLoadingState(GAMEOVERLOADINGSTATE));
		addState(new PauseCompetitiveState(PAUSECOMPETITIVESTATE));
		addState(new GameOverCompetitiveState(GAMEOVERCOMPETITIVESTATE));
		addState(new GameOverCoopState(GAMEOVERCOOPSTATE));
		addState(new ModeSelectState(MODESTATE));
	}
	
	public String getPreviousCompetitiveModeWinner() {
		return previousCompetitiveModeWinner;
	}

	public void setPreviousCompetitiveModeWinner(
			String previousCompetitiveModeWinner) {
		this.previousCompetitiveModeWinner = previousCompetitiveModeWinner;
	}

	public void setAchievements(Achievements newAchievements){
		achievements  = newAchievements;
	}
	
	public Achievements getAchievements(){
		return achievements;
	}
	

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}
	

	public boolean isGameEnded() {
		return gameEnded;
	}

	public void setGameEnded(boolean gameEnded) {
		this.gameEnded = gameEnded;
	}

	public static void main(String[] args) {
		
		AppGameContainer appgc;
		
//	    fLogger.info("Showing the main window.");
		try {
			appgc = new AppGameContainer(new Game(NAME));
			appgc.setDisplayMode(MAXIMUMWIDTH, MAXIMUMHEIGHT, false);
			appgc.setTargetFrameRate(60);
			appgc.setIcon("res/graphics/icon.png");
			appgc.start();
			
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		
		
	}	
}