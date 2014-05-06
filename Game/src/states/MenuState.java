package states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.DeferredFile;
import util.HoverImage;


public class MenuState extends BasicGameState {

	/* Images */
	private static Image 
		menuBackground,
		playButton, quitButton, creditsButton, controlsButton, scoreButton, achieveButton,
		playHover, quitHover, creditsHover, controlsHover, scoreHover, achieveHover;
	
	/* Hover images */
	private HoverImage
		play, quit, credits, controls, score, achieve;
	
	/* Boolean to check for buttons clicking */
	private boolean mouseBeenReleased;

	
	/**
	 * Menu state constructor
	 * @param state - takes a state
	 */
	public MenuState(int state) {
		this.mouseBeenReleased = false;
	}
	

	/**
	 * Overriding the initialise method
	 */
	@Override 
	public void init(GameContainer gc, StateBasedGame sbg) 
			throws SlickException {
		LoadingList loading = LoadingList.get();

		/* Deferred loading of all the images for better performance */
		loading.add(new DeferredFile(
				"res/menu_graphics/new/menu_screen.png"){
			public void loadFile(String filename) throws SlickException{
				menuBackground = new Image(filename);
			}
		});

		loading.add(new DeferredFile(
				"res/menu_graphics/new/play_button.png"){
			public void loadFile(String filename) throws SlickException{
				playButton = new Image(filename);
			}
		});

		loading.add(new DeferredFile(
				"res/menu_graphics/new/play_hover.png"){
			public void loadFile(String filename) throws SlickException{
				playHover = new Image(filename);
			}
		});

		loading.add(new DeferredFile(
				"res/menu_graphics/new/quit_button.png"){
			public void loadFile(String filename) throws SlickException{
				quitButton = new Image(filename);
			}
		});

		loading.add(new DeferredFile(
				"res/menu_graphics/new/quit_hover.png"){
			public void loadFile(String filename) throws SlickException{
				quitHover = new Image(filename);
			}
		});

		loading.add(new DeferredFile(
				"res/menu_graphics/new/credits.png"){
			public void loadFile(String filename) throws SlickException{
				creditsButton = new Image(filename);
			}
		});

		loading.add(new DeferredFile(
				"res/menu_graphics/new/credits_hover.png"){
			public void loadFile(String filename) throws SlickException{
				creditsHover = new Image(filename);
			}
		});

		loading.add(new DeferredFile(
				"res/menu_graphics/new/controls.png"){
			public void loadFile(String filename) throws SlickException{
				controlsButton = new Image(filename);
			}
		});

		loading.add(new DeferredFile(
				"res/menu_graphics/new/controls_hover.png"){
			public void loadFile(String filename) throws SlickException{
				controlsHover = new Image(filename);
			}
		});
		
		loading.add(new DeferredFile(
				"res/menu_graphics/new/highscores.png"){
			public void loadFile(String filename) throws SlickException{
				scoreButton = new Image(filename);
			}
		});
		
		loading.add(new DeferredFile(
				"res/menu_graphics/new/highscores_hover.png"){
			public void loadFile(String filename) throws SlickException{
				scoreHover = new Image(filename);
			}
		});

		loading.add(new DeferredFile(
				"res/menu_graphics/new/achievements.png"){
			public void loadFile(String filename) throws SlickException{
				achieveButton = new Image(filename);
			}
		});
		
		loading.add(new DeferredFile(
				"res/menu_graphics/new/achievements_hover.png"){
			public void loadFile(String filename) throws SlickException{
				achieveHover = new Image(filename);
			}
		});
		
		loading.add(new DeferredResource(){
			public String getDescription() {
				return "set up menuState buttons";
			}

			/* Load all the hover images */
			public void load(){
				play = new HoverImage(playButton, playHover, 439, 349);
				quit = new HoverImage(quitButton, quitHover, 1078, 539);
				credits = new HoverImage(creditsButton, creditsHover, 20, 537);
				controls = new HoverImage(controlsButton, controlsHover, 490, 533);
				score = new HoverImage(scoreButton, scoreHover, 223, 540);
				achieve = new HoverImage(achieveButton, achieveHover, 765, 540);
			}
		});

	}

	/**
	 * Overriding the render method
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
        /* Get mouse position */
		int	posX = Mouse.getX(),
			posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
		// Mapping Mouse coords onto graphics coords
		
		menuBackground.draw(0,0);

		// Draw all the buttons
		play.render(posX, posY);
		quit.render(posX, posY);
		credits.render(posX, posY);
		controls.render(posX, posY);
		score.render(posX, posY);
		achieve.render(posX, posY);
	}

	/**
	 * Overriding the update method
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		/* Get mouse position */
		int	posX = Mouse.getX(),
			posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
        	// Mapping Mouse coords onto graphics coords

		/* Detect buttons pressed */
		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {	
			if(mouseBeenReleased){	//button first pressed
				mouseBeenReleased = false;
				
				if (play.isMouseOver(posX, posY)) {
					sbg.enterState(stateContainer.Game.MODESTATE);
				}
				
				if (controls.isMouseOver(posX, posY)) {
					sbg.enterState(stateContainer.Game.CONTROLSSTATE);
				} 

				if (quit.isMouseOver(posX, posY)) {
					System.exit(0);
				}

				if (credits.isMouseOver(posX, posY)) {	
					sbg.enterState(stateContainer.Game.CREDITSSTATE);
				}
				
				if (score.isMouseOver(posX, posY)) {	
					sbg.enterState(stateContainer.Game.SCORESTATE);
				}
				
				if (achieve.isMouseOver(posX,  posY)){
					sbg.enterState(stateContainer.Game.ACHIEVEVIEWERSTATE);
				}
			}
			/* else mouse is dragged*/
		}	
        // Mouse just released without pressing a button
		else if (!mouseBeenReleased){	
			mouseBeenReleased = true;
		}
	}

	/**
	 * More readable state IDs
	 */
	public int getID() {
		return stateContainer.Game.MENUSTATE;
	}
}