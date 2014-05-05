package states;

//import java.awt.Font;
//import java.io.InputStream;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.DeferredFile;
//import org.newdawn.slick.util.ResourceLoader;


public class CreditsState extends BasicGameState {
	
	/* Images */
	private static Image
		menuButton, menuHover, menuBackground;
	//private static TrueTypeFont font;
	
	/* String is an array of array for better aligning */
	private String[][] credits;	//[section, line]

	
	/**
	 * Empty constructor for consistency 
	 * @param state - Takes a states
	 */
	public CreditsState(int state){
		
	}
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		{
			/* Deferred loading of images for better performances */
			LoadingList loading = LoadingList.get();

			loading.add(new DeferredFile("res/menu_graphics/new/menu_screen.png"){	
				public void loadFile(String filename) throws SlickException{
					menuBackground = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/menu_button.png"){
				public void loadFile(String filename) throws SlickException{
					menuButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/menu_hover.png"){
				public void loadFile(String filename) throws SlickException{
					menuHover = new Image(filename);
				}
			});
		}

		/* Credits text */
		credits = new String[][] {
				{"Music Assets",
					"\"Beachfront Celebration\" Kevin MacLeod (incompetech.com)",
					"Licensed under Creative Commons: By Attribution 3.0",
					"http://creativecommons.org/licenses/by/3.0/"
				},
				{"Images",
					"Loading screen plane created by Sallee Design",
					"http://salleedesign.com/resources/plane-psd/"
				},
				{"Font",
					"A love of thunder",
					"Downloaded from DaFont",
					"http://www.dafont.com/a-love-of-thunder.font"
				}
		};
	}

	/**
	 * Overriding the rendering method to render our own resources 
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException{
		
        /* Get mouse position */
		int posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
			//Fixing posY to reflect graphics coords
	
		/* Draw the background */
		menuBackground.draw(0,0);
		
		/* If mouse over the menu button show hover version */
		if (posX > 20 && posX < 136 && posY > 20 && posY < 66)
			menuHover.draw(20,20);
		else menuButton.draw(20,20);
		
		// Draw background panel
        //pale orange, semi-transparent
		g.setColor(new Color(250, 235, 215, 50));
		g.fillRoundRect (50, 230, 1100, 320, 5);
				
		{	// Draw credits screen
			g.setColor(Color.white);
			/* Starting y coordinate */
			int y = 240;

			for (String[] section: credits){
				for (String line: section){
					g.drawString(line, 60, y);
					/* Each new line at 15 px from the previous */
					y += 15;
				}
				/* Each new paragraph at 30 px from the previous */
				y += 30;
			}
		}
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
		
	}

	/**
	 * Overriding the getID method for better readability
	 */
	@Override
	public int getID(){
		return stateContainer.Game.CREDITSSTATE;
	}	
	
}
