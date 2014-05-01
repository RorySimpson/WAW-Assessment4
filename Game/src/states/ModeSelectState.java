package states;
import java.awt.Font;
import util.DeferredFile;
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
import org.newdawn.slick.TrueTypeFont;

public class ModeSelectState extends BasicGameState
	{

	
	/* Images */
	private static Image img;
	private static TrueTypeFont font1,font2;

	
	private String text;

	
	/**
	 * Empty constructor for consistency 
	 * @param state - Takes a states
	 */
	public ModeSelectState(int state){
		
	}
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
                /* Deferred loading of images for better performances */
			LoadingList loading = LoadingList.get();

            Font font1 = new Font("Comic Sans MS", Font.BOLD, 24);
            font2 = new TrueTypeFont(font1, false);

		text = "P to Play, C to Cooperate, O to pretend you play Online, V for Versus";

        };
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
		// modeBackground.draw(0,0);

		g.setFont(font2);
        g.setColor(Color.red);
		g.drawString(text,250,250);
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

		Input input = gc.getInput();
		if(input.isKeyPressed(Input.KEY_P)) {
			sbg.enterState(stateContainer.Game.PLAYSTATE);
		}
		if(input.isKeyPressed(Input.KEY_C)) {
			sbg.enterState(stateContainer.Game.PLAYCOOPSTATE);
		}
		if(input.isKeyPressed(Input.KEY_O)) {
			sbg.enterState(stateContainer.Game.ONLINEPLAYSTATE);
		}
		
		if(input.isKeyPressed(Input.KEY_V)) {
			sbg.enterState(stateContainer.Game.PLAYCOMPETITIVESTATE);
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
