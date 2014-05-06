package coop;

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

public class GameOverCoopState extends BasicGameState {

	private static Image quitButton, menuButton, playAgainButton, quitHover,
			menuHover, playAgainHover, gameOverBackground;

	private Achievements achievement;
	private Connection connection;

	private boolean newHighScore, textBoxCleared, success;
	private TextField nameTextField;

	private TrueTypeFont font;

	private String text;

	public GameOverCoopState(int state) {
		achievement = new Achievements();
		connection = new Connection();
	}

	/**
	 * init: Initialises all the resources required for the GameOverCoopState
	 * class.
	 * 
	 * @param gc
	 *            GameContainer
	 * @throws SlickException
	 */

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		{
			LoadingList loading = LoadingList.get();

			loading.add(new DeferredFile(
					"res/menu_graphics/new/gameover_screen.png") {
				public void loadFile(String filename) throws SlickException {
					gameOverBackground = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/playagain_button.png") {
				public void loadFile(String filename) throws SlickException {
					playAgainButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/quit_button.png") {
				public void loadFile(String filename) throws SlickException {
					quitButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/menu_button.png") {
				public void loadFile(String filename) throws SlickException {
					menuButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/playagain_hover.png") {
				public void loadFile(String filename) throws SlickException {
					playAgainHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/quit_hover.png") {
				public void loadFile(String filename) throws SlickException {
					quitHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/menu_hover.png") {
				public void loadFile(String filename) throws SlickException {
					menuHover = new Image(filename);
				}
			});
		}
	}

	/**
	 * enter: Setup for state upon entering the state.
	 */
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {

		// Check to see if a new global high score has been set (in top
		// 10 scores)
		int score = ((Game) sbg).getCurrentScore();
		int lowestScore = connection.getLowestScore();
		connection.clearData();
		if (score >= lowestScore) {
			Font awtFont = new Font("Courier", Font.BOLD, 15); // Setting
																// up
																// fonts
																// used
																// in
																// text
																// boxes
			font = new TrueTypeFont(awtFont, false);
			newHighScore = true;
			nameTextField = new TextField(gc, font, 520, 150, 200, 23);
			nameTextField.isAcceptingInput();
			nameTextField.setMaxLength(15);
			nameTextField.setText("Whats your name?");
		}
	}

	/**
	 * render: Render all of the graphics in the GameOverCoopState class
	 * 
	 * @param g
	 *            Graphics
	 * @param gc
	 *            GameContainer
	 * 
	 * @throws SlickException
	 */

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		gameOverBackground.draw(0, 0);

		// Display Score
		g.drawString("Score: ", 600, 500);
		g.drawString(Integer.toString(((Game) sbg).getCurrentScore()), 655, 500);

		int posX = Mouse.getX();
		// Fixing posY to reflect graphics coords
		int posY = stateContainer.Game.MAXIMUMHEIGHT - Mouse.getY();

		// Handles mouse effects on buttons
		if (posX > 728 && posX < 844 && posY > 380 && posY < 426)
			menuHover.draw(728, 380);
		else
			menuButton.draw(728, 380);

		if (posX > 354 && posX < 582 && posY > 380 && posY < 424)
			playAgainHover.draw(354, 380);
		else
			playAgainButton.draw(354, 380);

		if ((posX > 1150 && posX < 1170) && (posY > 550 && posY < 580))
			quitHover.draw(1148, 556);
		else
			quitButton.draw(1148, 556);

		g.setColor(Color.white);

		// Display text box to enter identifier for high score
		if (newHighScore == true) {
			g.setColor(new Color(250, 235, 215, 50)); // pale
														// orange,
														// semi-transparent
			g.fillRoundRect(50, 50, 1100, 150, 5);
			g.setColor(Color.white);
			g.drawString("Congratulations! You have set a new highscore!", 450,
					70);
			g.drawString(
					"Enter your name below to be added to the When Planes Collide Leaderboard!",
					280, 90);
			nameTextField.render(gc, g);
		}

		// Display successful high score submission message
		if (success == true) {
			g.setColor(new Color(250, 235, 215, 50)); // pale
														// orange,
														// semi-transparent
			g.fillRoundRect(50, 50, 1100, 150, 5);
			g.setColor(Color.white);
			g.drawString("Thanks for submitting your highscore!", 450, 70);
			g.drawString("You can see the leaderboard from the main menu!",
					400, 90);
		}
	}

	/**
	 * updateTextBox: Updates and handles the text box used to enter in
	 * identifiers for highscores.
	 */

	public void updateTextBox(Input input, StateBasedGame sbg) {
		text = nameTextField.getText();
		if (nameTextField.hasFocus()) {

			if (!textBoxCleared) {
				nameTextField.setText("");
				textBoxCleared = true;
			}

			// When the enter key is pressed retrieve its text and
			// reset the textbox
			if (input.isKeyDown(Input.KEY_ENTER)) {
				nameTextField.setFocus(false);
				connection.sendNewScore(text, ((Game) sbg).getCurrentScore());
				success = true;
				newHighScore = false;
			}
		} else {
			if (text == "") {
				nameTextField.setText("Whats your name?");
			}

		}

	}

	/**
	 * update: Update all logic in the GameOverCoopState class
	 * 
	 * @param gc
	 *            GameContainer
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		if (newHighScore == true) {
			Input input = gc.getInput();
			updateTextBox(input, sbg);
		}

		int posX = Mouse.getX(), posY = stateContainer.Game.MAXIMUMHEIGHT
				- Mouse.getY();

		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {

			// Press Play Again button to return to the game screen
			if (posX > 354 && posX < 582 && posY > 380 && posY < 424) {
				success = false;
				newHighScore = false;
				textBoxCleared = false;
				sbg.enterState(stateContainer.Game.PLAYCOOPSTATE);
			}

			// Press Menu button to return to the menu screen
			if (posX > 728 && posX < 844 && posY > 380 && posY < 426) { // 116
																		// 46
				success = false;
				newHighScore = false;
				textBoxCleared = false;
				sbg.enterState(stateContainer.Game.MENUSTATE);
			}

			// Press quit button to exit game
			if ((posX > 1150 && posX < 1170) && (posY > 550 && posY < 580)) {
				System.exit(0);
			}
		}

	}

	@Override
	public int getID() {
		return stateContainer.Game.GAMEOVERCOOPSTATE;
	}
}
