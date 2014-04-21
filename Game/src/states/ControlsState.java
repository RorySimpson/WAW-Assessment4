package states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.DeferredFile;


public class ControlsState extends BasicGameState {
		
	/* Images */
	private static Image 
		nextPageButton, previousPageButton, backButton, quitButton, 
		nextPageHover, previousPageHover, backHover, quitHover,
		controlsBackgroundPage1, controlsBackgroundPage2;
	
	/* Page number (Controls has 2 pages of text) */
	private int pageNumber;
    
	/**
	 * Empty constructor for consistency
	 * @param state - take a state
	 */
	public ControlsState(int state){
		
	}
	
	
	/**
	 * Overriding the initialisation method to load our resources  
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
        /* Starts with page 1 */
		pageNumber = 1;

		{
			/* Deferred loading for better performances */
			LoadingList loading = LoadingList.get();

			loading.add(new DeferredFile(
					"res/menu_graphics/new/controls1.png"){
				public void loadFile(String filename) throws SlickException{
					controlsBackgroundPage1 = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/controls2.png"){
				public void loadFile(String filename) throws SlickException{
					controlsBackgroundPage2 = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/back.png"){
				public void loadFile(String filename) throws SlickException{
					backButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/back_hover.png"){
				public void loadFile(String filename) throws SlickException{
					backHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/next_page.png"){
				public void loadFile(String filename) throws SlickException{
					nextPageButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/next_page_hover.png"){
				public void loadFile(String filename) throws SlickException{
					nextPageHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/previous_page.png"){
				public void loadFile(String filename) throws SlickException{
					previousPageButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/previous_page_hover.png"){
				public void loadFile(String filename) throws SlickException{ 
					previousPageHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/quit_button.png"){
				public void loadFile(String filename) throws SlickException{
					quitButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/quit_hover.png"){
				public void loadFile(String filename) throws SlickException{
					quitHover = new Image(filename);
				}
			});
		}
			
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException{
		
        /* Get mouse position */
		int	posX = Mouse.getX(),
			posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
			//Fixing posY to reflect graphics coords
		
		/* If first page */
		if (pageNumber == 1) {
			
			/* Draw the firs page */
			controlsBackgroundPage1.draw(0,0);
			
			/* If next page button is hovered, display the hovered version */
			if (posX > 1030 && posX < 1193 && posY > 280 && posY < 315)
				nextPageHover.draw(1030,280);
			else nextPageButton.draw(1030,280);
			
			/* If quit button is hovered, display the hovered version */
			if (posX > 1150 && posX < 1170 && posY > 550 && posY < 580)
				quitHover.draw(1148,556);
			else quitButton.draw(1148,556);
			
		}
		
		/* If it's on the second page */
		else if (pageNumber == 2){
			controlsBackgroundPage2.draw(0,0);
			
			/* If previous page button is hovered, display the hovered version */
			if (posX > 30 && posX < 241 && posY > 280 && posY < 315)
				previousPageHover.draw(30,280);
			else previousPageButton.draw(30,280);
			
			//menuButton.draw(1050, 20);
			
			/* If quit button is hovered, display the hovered version */
			if (posX > 1150 && posX < 1170 && posY > 550 && posY < 580)
				quitHover.draw(1148,556);
			else quitButton.draw(1148,556);	
		}
		
        /* If back button is hovered, display the hovered version */
		if (posX > 20 && posX < 40 && posY > 20 && posY < 40)
			backHover.draw(20,20);
		else backButton.draw(20,20);
	}

	/**
	 * Overriding  the update method to update our resources
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

        /* Get mouse position */
		int	posX = Mouse.getX(),
			posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();

		/* Detect if user clicks a button */
		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			
			/* If menu button is pressed, go to menu */
			if (posX > 20 && posX < 40 && posY > 20 && posY < 40) {
				pageNumber = 1;
				sbg.enterState(stateContainer.Game.MENUSTATE);
			}
			
			/* If quit button is pressed, exit game */
			if (posX > 1150 && posX < 1170 && posY > 550 && posY < 580) {
				System.exit(0);
			}

			/* If user pressed next page from page 1, go to page 2*/
			if (pageNumber == 1){
				if (posX > 1030 && posX < 1193 && posY > 280 && posY < 315) {
					pageNumber = 2;
				}
			}

			/* If user pressed prev page from page 2, go to page 1*/
			if (pageNumber == 2){
				if (posX > 30 && posX < 241 && posY > 280 && posY < 315) {
					pageNumber = 1;
				}
			}
		}
	}

	/**
	 *  Override the getID method for better readibility
	 */
	@Override
	public int getID(){
		return stateContainer.Game.CONTROLSSTATE;
	}
	
}
