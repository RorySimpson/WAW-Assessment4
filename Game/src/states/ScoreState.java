package states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import logicClasses.Connection;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import util.DeferredFile;

public class ScoreState extends BasicGameState {
	
	private static Image menuButton, menuHover, menuBackground;
	private Connection connection = new Connection();
	private List<String> scores = new ArrayList<String>();
	
	private TrueTypeFont
        	titleFont = new TrueTypeFont(new Font(Font.SANS_SERIF, Font.BOLD, 36), false);
	
	//CONSTRUCTOR
	public ScoreState(int scorestate){

	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg){
		scores = connection.getScores();
	}
	
	//METHODS
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		{
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
	}
		
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException{
		
		int posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
			//Fixing posY to reflect graphics coords
	
		menuBackground.draw(0,0);
		
		if (posX>20 && posX< 136 && posY>20 && posY<66)
			menuHover.draw(20,20);
		else menuButton.draw(20,20);
		
		//draw background panel
		g.setColor(new Color(250, 235, 215, 50));	//pale orange, semi-transparent
		g.fillRoundRect (50, 230, 1100, 320, 5);
		
		//DRAW SCORES
		titleFont.drawString((float)(500),(float)(230),"High Scores!",Color.white);
		
		//Iterate through the hashMap and print out each key => value pair
		g.setColor(Color.white);
		int y = 300;
		
		for (String s : scores) {
			String[] parts = s.split(":");
			g.drawString(parts[0],500,y);
		    g.drawString(parts[1],700,y);
		    y += 25;
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
	
		int posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
			//Fixing posY to reflect graphics coords
	
		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			
			if (posX>20 && posX<136 && posY>20 && posY<66) {
			    	connection.clearData();
				sbg.enterState(stateContainer.Game.MENUSTATE);
			}
		}	
		
	}

	@Override
	public int getID(){
		return stateContainer.Game.SCORESTATE;
	}	
	
}
