package stateContainer;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import states.*;

public class Game extends StateBasedGame {

	public static final String NAME = "When Planes Collide";
	
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
		PAUSECOOPSTATE = 9;
	
	public static final int 
		MAXIMUMWIDTH = 1200, MAXIMUMHEIGHT = 600;
	
	private int currentScore;


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
	}
	
	

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
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