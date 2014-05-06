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

	/* Title of the game */
	public static final String NAME = "Expect Turbulence";
	
	/* More readable state names */
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
	
	/* Window size */
	public static final int 
		MAXIMUMWIDTH = 1200, MAXIMUMHEIGHT = 600;
	
	/* Score */
	private int currentScore;
	/* Store the versus winner */
	private String previousCompetitiveModeWinner;
	/* Achievements object */
	private Achievements achievements = new Achievements();
	/* Whether the game ended */
	private boolean gameEnded;
	private boolean gameEndedCoop;
	private boolean gameEndedComp;


	/**
	 * Adds all states to a container 
	 * @param NAME The game's title
	 */

	public Game(String NAME) {
		super(NAME);
	}

	/**
	 * Create all the states
	 */
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
	
	// SETTERS AND GETTERS

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

	public boolean isGameEndedCoop() {
		return gameEndedCoop;
	}

	public void setGameEndedCoop(boolean gameEndedCoop) {
		this.gameEndedCoop = gameEndedCoop;
	}

	public boolean isGameEndedComp() {
		return gameEndedComp;
	}

	public void setGameEndedComp(boolean gameEndedComp) {
		this.gameEndedComp = gameEndedComp;
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