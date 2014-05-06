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

	/* Attributes used by this state */
	private static Image menuButton, menuHover, menuBackground;
	/* Connection for connecting to the High Scores database */
	private Connection connection = new Connection();
	/* An array of all scores of the users for communication with the server */
	private List<String> scores = new ArrayList<String>();

	/* Load custom font */
	private TrueTypeFont titleFont = new TrueTypeFont(new Font(Font.SANS_SERIF,
			Font.BOLD, 36), false);

	// CONSTRUCTOR
	/* Empty constructor for preventing some Eclipse warnings */
	public ScoreState(int scorestate) {

	}

	/*
	 * Overwriting the enter method to force the state to fetch the scores as
	 * soon as it has loaded
	 */
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		scores = connection.getScores();
	}

	// METHODS
	/* Overriding initialisation */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		{
			/* Use deferred loading for better performance */
			LoadingList loading = LoadingList.get();

			/* The menu screen image */
			loading.add(new DeferredFile(
					"res/menu_graphics/new/menu_screen.png") {
				public void loadFile(String filename) throws SlickException {
					menuBackground = new Image(filename);
				}
			});

			/* The menu button image */
			loading.add(new DeferredFile(
					"res/menu_graphics/new/menu_button.png") {
				public void loadFile(String filename) throws SlickException {
					menuButton = new Image(filename);
				}
			});

			/* The menu hover image */
			loading.add(new DeferredFile("res/menu_graphics/new/menu_hover.png") {
				public void loadFile(String filename) throws SlickException {
					menuHover = new Image(filename);
				}
			});
		}
	}

	/* Overriding the rendering method */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		/* Get the mouse position */
		int posX = Mouse.getX();
		// Fixing posY to reflect graphics coords
		int posY = stateContainer.Game.MAXIMUMHEIGHT - Mouse.getY();

		menuBackground.draw(0, 0);

		/* Detect mouse hover */
		if (posX > 20 && posX < 136 && posY > 20 && posY < 66)
			menuHover.draw(20, 20);
		else
			menuButton.draw(20, 20);

		// Draw background panel
		g.setColor(new Color(250, 235, 215, 50)); // pale orange,
													// semi-transparent
		g.fillRoundRect(50, 230, 1100, 320, 5);

		// DRAW SCORES
		titleFont.drawString((float) (500), (float) (230), "High Scores!",
				Color.white);

		// Iterate through the hashMap and print out each key => value
		// pair
		g.setColor(Color.white);
		int y = 300;

		for (String s : scores) {
			String[] parts = s.split(":::");
			g.drawString(parts[0], 500, y);
			g.drawString(parts[1], 700, y);
			y += 25;
		}
	}

	/*
	 * Override the update method to handle pressing the menu button to go back
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		int posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT - Mouse.getY();
		// Fixing posY to reflect graphics coords

		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {

			if (posX > 20 && posX < 136 && posY > 20 && posY < 66) {
				connection.clearData();
				sbg.enterState(stateContainer.Game.MENUSTATE);
			}
		}

	}

	/* Override the getID() method for better readability */
	@Override
	public int getID() {
		return stateContainer.Game.SCORESTATE;
	}

}
