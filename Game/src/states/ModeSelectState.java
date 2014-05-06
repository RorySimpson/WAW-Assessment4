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

public class ModeSelectState extends BasicGameState
	{

	
	/* Images */
	private static Image background, single, versus, coop, howto,
						 singleHover, versusHover, coopHover, howtoHover;
	private HoverImage
		singleImage, versusImage, coopImage,
		howtoSingle, howtoCoop, howtoVersus;

	private boolean mouseBeenReleased;
	
	/**
	 * Empty constructor for consistency 
	 * @param state - Takes a states
	 */
	public ModeSelectState(int state){
		this.mouseBeenReleased = false;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
                /* Deferred loading of images for better performances */
			LoadingList loading = LoadingList.get();

			loading.add(new DeferredFile("res/menu_graphics/new/selectmode_background.jpg"){	
				public void loadFile(String filename) throws SlickException{
					background = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/singleplayer.JPG"){	
				public void loadFile(String filename) throws SlickException{
					single = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/singleplayer_hover.JPG"){	
				public void loadFile(String filename) throws SlickException{
					singleHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/versus.JPG"){	
				public void loadFile(String filename) throws SlickException{
					versus = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/versus_hover.JPG"){	
				public void loadFile(String filename) throws SlickException{
					versusHover = new Image(filename);
				}
			});


			loading.add(new DeferredFile("res/menu_graphics/new/coop.JPG"){	
				public void loadFile(String filename) throws SlickException{
					coop = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/coop_hover.JPG"){	
				public void loadFile(String filename) throws SlickException{
					coopHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/howto.png"){	
				public void loadFile(String filename) throws SlickException{
					howto = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/howto_hover.png"){	
				public void loadFile(String filename) throws SlickException{
					howtoHover = new Image(filename);
				}
			});

        loading.add(new DeferredResource(){
			public String getDescription() {
				return "set up mode state buttons";
			}

			/* Load all the hover images */
			public void load(){
				singleImage = new HoverImage(single, singleHover, 120, 170);
				coopImage = new HoverImage(coop, coopHover, 470, 170);
				versusImage = new HoverImage(versus, versusHover, 820, 170);
				howtoSingle = new HoverImage(howto, howtoHover, 140, 540);
				howtoCoop = new HoverImage(howto, howtoHover, 490, 540);
				howtoVersus = new HoverImage(howto, howtoHover, 840, 540);
			}
		});

        };
	/**
	 * Overriding the rendering method to render our own resources 
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException{
		
        /* Get mouse position */
		int posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT - Mouse.getY();
			//Fixing posY to reflect graphics coords
	
		/* Draw the background */
		background.draw(0,0);

		/* Render the image */
		singleImage.render(posX, posY);
		coopImage.render(posX, posY);
		versusImage.render(posX, posY);

		howtoSingle.render(posX, posY);
		howtoCoop.render(posX, posY);
		howtoVersus.render(posX, posY);
	}

	/**
	 * Overriding the update method to update our resources 
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
	
        /* Get mouse position */
		int posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
			//Fixing posY to reflect graphics coords
	
		/* If Menu button has been pressed, go back to menu state */
		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			
			if (posX > 20 && posX < 136 && posY > 20 && posY < 66) {
				sbg.enterState(stateContainer.Game.MENUSTATE);
			}
		}

		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {	
			if(mouseBeenReleased){	//button first pressed
				mouseBeenReleased = false;
				
				if (singleImage.isMouseOver(posX, posY)) {
					sbg.enterState(stateContainer.Game.PLAYSTATE);
				}
				
				if (versusImage.isMouseOver(posX, posY)) {
					sbg.enterState(stateContainer.Game.PLAYCOMPETITIVESTATE);
				} 

				if (coopImage.isMouseOver(posX, posY)) {
					sbg.enterState(stateContainer.Game.PLAYCOOPSTATE);
				}

				if (howtoVersus.isMouseOver(posX, posY)) {
					sbg.enterState(stateContainer.Game.VSSTATE);
				}

				if (howtoSingle.isMouseOver(posX, posY)) {
					sbg.enterState(stateContainer.Game.SINGLESTATE);
				}

				if (howtoCoop.isMouseOver(posX, posY)) {
					sbg.enterState(stateContainer.Game.COOPSTATE);
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
	 * Overriding the getID method for better readability
	 */
	@Override
	public int getID(){
		return stateContainer.Game.MODESTATE;
	}	
}