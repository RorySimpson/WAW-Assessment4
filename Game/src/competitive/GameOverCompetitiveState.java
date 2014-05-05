package competitive;



import stateContainer.Game;
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


public class GameOverCompetitiveState extends BasicGameState {
	
	private static Image 
		quitButton, menuButton, playAgainButton,   
		quitHover, menuHover, playAgainHover,
		gameOverBackground;
	
	

	
	private String winner;
	
	public GameOverCompetitiveState(int state) {
		
		
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

			loading.add(new DeferredFile("res/menu_graphics/new/playagain_button.png"){
				public void loadFile(String filename) throws SlickException{
					playAgainButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/quit_button.png"){
				public void loadFile(String filename) throws SlickException{
					quitButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/menu_button.png"){
				public void loadFile(String filename) throws SlickException{
					menuButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/playagain_hover.png"){
				public void loadFile(String filename) throws SlickException{
					playAgainHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/quit_hover.png"){
				public void loadFile(String filename) throws SlickException{
					quitHover = new Image(filename);
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
		
		gameOverBackground.draw(0,0);
		
		winner = ((Game)sbg).getPreviousCompetitiveModeWinner();
		if(winner!=null){
			if(winner == "Draw"){
				g.drawString(winner, 450, 500);
			}
			else{
				g.drawString(winner + " has won ", 450, 500);
			}
		}
		
		
		int	posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
		//Fixing posY to reflect graphics coords
		
		// Menu Button
		if (posX > 728 && posX < 844 && posY > 380 && posY < 426)
			menuHover.draw(728,380);
		else menuButton.draw(728,380);
		 
		// Play Again Button
		if (posX > 354 && posX < 582 && posY > 380 && posY < 424)
			playAgainHover.draw(354,380);
		else playAgainButton.draw(354,380);
		
		// Exit Button
		if ((posX > 1150 && posX < 1170) && (posY > 550 && posY < 580))
			quitHover.draw(1148,556);
		else quitButton.draw(1148,556);
	
		g.setColor(Color.white);
		

	}
	

	

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException {


	    	
		int posX = Mouse.getX(),
			posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
		
		
		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)){
			
			// Selecting Play Again
			if(posX>354&&posX<582&&posY>380&&posY<424) {
				sbg.enterState(stateContainer.Game.PLAYCOMPETITIVESTATE);
			}
			
			// Selecting Menu
			if(posX>728&&posX<844&&posY>380&&posY<426) { // 116 46
				sbg.enterState(stateContainer.Game.MENUSTATE);
			}
			
			// Selecting the exit button
			if((posX > 1150 && posX < 1170) && (posY > 550 && posY < 580)) {
				System.exit(0);
			}
		}
		
	}

	@Override
	public int getID() {
		return stateContainer.Game.GAMEOVERCOMPETITIVESTATE;
	}
}
