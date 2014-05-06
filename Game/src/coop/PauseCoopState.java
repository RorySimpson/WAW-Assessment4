package coop;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.lwjgl.input.Mouse;

import stateContainer.Game;
import util.DeferredFile;

public class PauseCoopState extends BasicGameState {
	
	private static Image
		menuButton, menuButtonHover, quitButton, backButton,
		quitButtonHover, backButtonHover,
		pauseBackgroundPage1;
	//private static TrueTypeFont font;
	
	public PauseCoopState(int state) {
		
	}

	/**
	 * init: Initialises all the resources required for the PauseCoopState class.
	 * @param gc GameContainer
	 * @throws SlickException
	 */
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbj) throws SlickException {
		{
			LoadingList loading = LoadingList.get();

			loading.add(new DeferredFile("res/menu_graphics/new/coop.jpg"){
				public void loadFile(String filename) throws SlickException{
					pauseBackgroundPage1 = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/back.png"){
				public void loadFile(String filename) throws SlickException{
					backButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/back_hover.png"){
				public void loadFile(String filename) throws SlickException{
					backButtonHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/menu_button.png"){
				public void loadFile(String filename) throws SlickException{
					menuButton = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile("res/menu_graphics/new/menu_hover.png"){
				public void loadFile(String filename) throws SlickException{
					menuButtonHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/quit_button.png"){
				public void loadFile(String filename) throws SlickException{
					quitButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/quit_hover.png"){
				public void loadFile(String filename) throws SlickException{
					quitButtonHover = new Image(filename);
				}
			});
		}
	}
	
	/**
	 * render: Render all of the graphics in the PauseCoopState class
	 * @param g Graphics
	 * @param gc GameContainer
	 * 
	 * @throws SlickException
	 */
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		int	posX = Mouse.getX(),
			posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
		
        pauseBackgroundPage1.draw(0,0);
		
		if (posX>20 && posX<40 && posY>20 && posY<40) 
			backButtonHover.draw(20,20);
		else backButton.draw(20,20);
		
		if (posX>1150 && posX<1170 && posY>550 && posY<580) 
			quitButtonHover.draw(1150,550);
		else quitButton.draw(1150,550);
		
		/* Menu button hovering */
		if (posX>520 && posX<700 && posY>500 && posY < 600)
			menuButtonHover.draw(535,533);
		else menuButton.draw(535,533);
	}
	
	/**
	 * update: Update all logic in the PauseCoopState class
	 * @param gc GameContainer
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		int	posX = Mouse.getX(),
			posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
		
		Input input = gc.getInput();
		
		// Press 'P' to go back to game.
		if(input.isKeyPressed(Input.KEY_P)) {
			sbg.enterState(stateContainer.Game.PLAYCOOPSTATE);
		}
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			
			
			// Press back to return to game
			if (posX>20 && posX<40 && posY>20 && posY<40) {					
				sbg.enterState(stateContainer.Game.PLAYCOOPSTATE);
			}		
			
			// Press exit button to close game
			if (posX>1150 && posX<1170 && posY>550 && posY<580) {
				System.exit(0);
			}
			
			/* If user has pressed to go back to main menu */
			if (posX>520 && posX<700 && posY>500 && posY < 600){
				((Game)sbg).setGameEndedCoop(true);
				sbg.enterState(stateContainer.Game.GAMEOVERCOOPSTATE);
			}
		}
	}
	
	@Override
	public int getID(){
		return stateContainer.Game.PAUSECOOPSTATE;
	}
}