package states;

//import java.awt.Rectangle;
import java.io.IOException;

//import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public final class SplashState extends BasicGameState {

	/* Attributes */
	private static Image 
		splash, indicator;
	/*private static Color
		loadBaseColor = Color.white, loadFillColor = Color.black;*/
	private static LoadingList loading = LoadingList.get();
	
	
	public SplashState(int state){
		
	}

	/* Override the default initialisation */
	@Override
	public void init(GameContainer gc, StateBasedGame s)
			throws SlickException {
        /* load the graphics */
		splash = new Image("res/graphics/new/startup_bg.jpg");
		indicator = new Image("res/graphics/new/startup_plane.png");
	}
    /* Override the default rendering method */
	@Override
	public void render(GameContainer gc, StateBasedGame s, Graphics g)
			throws SlickException {
        /* Draw the background image and get the loading bar working 
         * to properly display the amount of remaining resources to load  */	
		g.drawImage(splash, 0, 0);
		indicator.drawCentered(900 -((600 * loading.getRemainingResources()) 
				/ loading.getTotalResources()), 390);
	}
	
	/* Override the default update method */
	@Override
	public void update(GameContainer gc, StateBasedGame s, int delta)
			throws SlickException {

        /* Enter the menu when loading is done  */	
		if (loading.getRemainingResources() == 0){	//finished loading
			gc.setShowFPS(false);
			s.enterState(stateContainer.Game.MENUSTATE);
		}
        /* Inform the user if any error occurred while loading */
		else {
			DeferredResource next = loading.getNext();
			try {
				next.load();
			} catch (IOException e) {
				System.out.println("Failed loading:\t" +next.getDescription());
				e.printStackTrace();
			}	
		}		
	}

	/* Use names for states instead of IDs for readability  */
	@Override
	public int getID() {
		return stateContainer.Game.SPLASHSTATE;
	}	
	
}
