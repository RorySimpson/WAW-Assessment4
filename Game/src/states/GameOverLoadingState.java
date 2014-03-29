package states;
import java.awt.Font;

import logicClasses.Achievements;
import logicClasses.Connection;
import stateContainer.Game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.DeferredFile;


public class GameOverLoadingState extends BasicGameState {
	
	private static Image 
		gameOverBackground;
	
	private TrueTypeFont font;
	
	private String text;
	
	public GameOverLoadingState(int state) {
	    
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
				throws SlickException {
		
		{
			LoadingList loading = LoadingList.get();
			
			loading.add(new DeferredFile("res/menu_graphics/new/gameover_screen.png"){
				public void loadFile(String filename) throws SlickException{
					gameOverBackground = new Image(filename);
				}
			});
		}
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg){
	    sbg.enterState(stateContainer.Game.GAMEOVERSTATE);
	}
		
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
				throws SlickException{
		
		gameOverBackground.draw(0,0);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException {
	}

	@Override
	public int getID() {
		return stateContainer.Game.GAMEOVERLOADINGSTATE;
	}
}
