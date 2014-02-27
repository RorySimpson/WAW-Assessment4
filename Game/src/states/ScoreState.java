package states;


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
import java.net.*;
import java.util.ArrayList;
import java.io.*;

import util.DeferredFile;



public class ScoreState extends BasicGameState {
	
	private static Image
		menuButton, menuHover, menuBackground;
	//private static TrueTypeFont font;
	
	private String[][] credits;	//[section, line]
	private String scoreString;

	
	public ScoreState(int state){
		
	}
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg){
		connection();
	}
	
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
		
		credits = new String[][] {
				{"Scores",
					"COMING SOON!",
				},
		};
	}
	
	public void connection(){
		URL address;
		
		try {
			address = new URL("http://teamwaw.co.uk/whenPlanesCollide/connection.php");
			//URLConnection yc = oracle.openConnection();        Server 403s's when using URL connection so we are now using http
			HttpURLConnection httpcon = (HttpURLConnection) address.openConnection(); 
			httpcon.addRequestProperty("User-Agent", "WhenPlanesCollide"); 
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	        httpcon.getInputStream()));
	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	        	
	        scoreString = scoreString + inputLine;
	       in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
				
		{	//draw credits screen
			g.setColor(Color.white);
			int y = 240;
			for (String[] section: credits){
				for (String line: section){
					//font.drawString(60, y, line);
					g.drawString(line, 60, y);
					y += 15;
				}
				y += 30;
			}
		g.drawString(scoreString, 100, 300);
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
				sbg.enterState(stateContainer.Game.MENUSTATE);
			}
		}	
		
	}

	@Override
	public int getID(){
		return stateContainer.Game.SCORESTATE;
	}	
	
}
