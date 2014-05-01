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


public class GameOverState extends BasicGameState {
	
	/* Images */
	private static Image 
		quitButton, menuButton, playAgainButton,   
		quitHover, menuHover, playAgainHover,
		gameOverBackground;
	
	/* Creating achievements and connection objects to be used in this class */
	private Achievements achievement;
	private Connection connection;
	
	/* To aid recording new high scores */
	private boolean newHighScore, textBoxCleared, success;
	private TextField nameTextField;
	
	/* Using a TrueType font */
	private TrueTypeFont font;
	
	/* Text input from the user to register their name for high scores */
	private String text;
	
	/**
	 * GameOverState constructor that creates achievments and connection objects
	 * @param state
	 */
	public GameOverState(int state) {
		achievement = new Achievements();
		connection = new Connection();
	}
	
	/**
	 * Overriding the initalise method to load our resources
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
				throws SlickException {
		
		{
			/* Prepare deferred loading for better performances */
			LoadingList loading = LoadingList.get();
			
			/* Load all the images */
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
	
	/**
	 * Override the enter state method
	 */
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg){

		// Check to see if a new global high score has been set (top 10 scores)
		int score = ((Game)sbg).getCurrentScore();
		int lowestScore = connection.getLowestScore();

		connection.clearData();

		/* If current score bigger than the lowest score */
		if (score >= lowestScore){
			// Setting up fonts used in text boxes
			Font awtFont = new Font("Courier", Font.BOLD, 15); 
			font = new TrueTypeFont(awtFont, false);

			/* High score has been achieved */
			newHighScore = true;

			/* Prompt user to input his name */
			nameTextField = new TextField(gc, font, 520, 150, 200, 23);
			nameTextField.isAcceptingInput();
			nameTextField.setMaxLength(15); // Done for consistent looks
			nameTextField.setText("What's your name?");
		}
	}
	
	/**
	 * Overriding the rendering method to render our resources
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
				throws SlickException{
		
		gameOverBackground.draw(0,0);
		
		/* Display score */
		g.drawString("Score: ", 600,500);
		g.drawString(Integer.toString(((Game)sbg).getCurrentScore()), 655, 500);
		
		/* Get mouse position */
		int	posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
			//Fixing posY to reflect graphics coords
		
		/* Check for buttons hovering */
		if (posX > 728 && posX < 844 && posY > 380 && posY < 426)
			menuHover.draw(728,380);
		else menuButton.draw(728,380);
		
		if (posX > 354 && posX < 582 && posY > 380 && posY < 424)
			playAgainHover.draw(354,380);
		else playAgainButton.draw(354,380);
		
		if ((posX > 1150 && posX < 1170) && (posY > 550 && posY < 580))
			quitHover.draw(1148,556);
		else quitButton.draw(1148,556);

		g.setColor(Color.white);
		
		/* If user got a new high score */
		if(newHighScore == true){
            //pale orange, semi-transparent
			g.setColor(new Color(250, 235, 215, 50));	
			g.fillRoundRect (50, 50, 1100, 150, 5);
			g.setColor(Color.white);

			g.drawString("Congratulations! You have set a new highscore!", 450, 70);
			g.drawString("Enter your name below to be added to "
					+ "the When Planes Collide Leaderboard!", 280, 90);

			nameTextField.render(gc, g);
		}
		
		/* If the high score has been submitted successfully */
		if(success == true){
            //pale orange, semi-transparent
		    g.setColor(new Color(250, 235, 215, 50));	
		    g.fillRoundRect (50, 50, 1100, 150, 5);
		    g.setColor(Color.white);

		    g.drawString("Thanks for submitting your highscore!", 450, 70);
		    g.drawString("You can see the leaderboard from the main menu!", 400, 90);
		}
	}
	
	/**
	 * Method to update the user input text box
	 * @param input - player's name
	 * @param sbg - StateBasedGame to be able to communicate with other states
	 */
	public void updateTextBox(Input input,StateBasedGame sbg){
		/* To get player's name */
	    text = nameTextField.getText();

	    /* If the player is submitting his high score */
	    if (nameTextField.hasFocus()) {
                /* Clear text box */
                if(!textBoxCleared){
                    nameTextField.setText("");
                    textBoxCleared = true;
                }
                
                /* When the enter key is pressed retrieve its 
                text and reset the textbox */
                if (input.isKeyDown(Input.KEY_ENTER)) {
                    nameTextField.setFocus(false);
                    connection.sendNewScore(text,((Game)sbg).getCurrentScore());

                    success = true;
                    newHighScore = false;
                }
	    }
	    else{
                /* Default message before user focuses the text box */
                if(text == ""){
                    nameTextField.setText("Whats your name?");
                }
		//nameTextField.setText(text);
	    }

	}
	

	/**
	 * Override the update method to update our resources
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

        /* If a new high score has been, let user write his name */
        if(newHighScore == true){
                Input input = gc.getInput();
                updateTextBox(input,sbg);
        }
	    	
        /* Get mouse position */
		int posX = Mouse.getX(),
			posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
		

		/* Detect buttons pressed */
		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)){
            /* Play button */
			if(posX > 354 && posX < 582 && posY > 380 && posY < 424) {
				success = false;
				newHighScore = false;
				textBoxCleared = false;
				sbg.enterState(stateContainer.Game.PLAYSTATE);
			}
			
			/* Menu button */
			if(posX > 728 && posX < 844 && posY > 380 && posY < 426) { // 116 46
				success = false;
				newHighScore = false;
				textBoxCleared = false;
				sbg.enterState(stateContainer.Game.MENUSTATE);
			}
			
			/* Quit button */
			if((posX > 1150 && posX < 1170) && (posY > 550 && posY < 580)) {
				System.exit(0);
			}
		}
	}

	/**
	 * More readable getID()
	 */
	@Override
	public int getID() {
		return stateContainer.Game.GAMEOVERSTATE;
	}
}
