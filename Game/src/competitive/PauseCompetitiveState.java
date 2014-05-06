package competitive;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.lwjgl.input.Mouse;

import stateContainer.Game;
import util.DeferredFile;

public class PauseCompetitiveState extends BasicGameState {

	private static Image menuButton, menuButtonHover, quitButton, backButton,
			quitButtonHover, backButtonHover, background;
	// private static TrueTypeFont font;

	private int pageNumber;

	public PauseCompetitiveState(int state) {

	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbj)
			throws SlickException {
		pageNumber = 1;

		{
			LoadingList loading = LoadingList.get();

			loading.add(new DeferredFile("res/menu_graphics/new/versus.jpg") {
				public void loadFile(String filename) throws SlickException {
					background = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/back.png") {
				public void loadFile(String filename) throws SlickException {
					backButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/back_hover.png") {
				public void loadFile(String filename) throws SlickException {
					backButtonHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/menu_button.png") {
				public void loadFile(String filename) throws SlickException {
					menuButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/menu_hover.png") {
				public void loadFile(String filename) throws SlickException {
					menuButtonHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/quit_button.png") {
				public void loadFile(String filename) throws SlickException {
					quitButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/quit_hover.png") {
				public void loadFile(String filename) throws SlickException {
					quitButtonHover = new Image(filename);
				}
			});
		}

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		int posX = Mouse.getX(), posY = stateContainer.Game.MAXIMUMHEIGHT
				- Mouse.getY();

		// Render Pause Page 1

		background.draw(0, 0);

		// Render Back Button
		if (posX > 20 && posX < 40 && posY > 20 && posY < 40)
			backButtonHover.draw(20, 20);
		else
			backButton.draw(20, 20);

		// Render Quick Button
		if (posX > 1150 && posX < 1170 && posY > 550 && posY < 580)
			quitButtonHover.draw(1150, 550);
		else
			quitButton.draw(1150, 550);

		/* Menu button hovering */
		if (posX > 540 && posX < 720 && posY > 500 && posY < 600)
			menuButtonHover.draw(535, 553);
		else
			menuButton.draw(535, 553);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		int posX = Mouse.getX(), posY = stateContainer.Game.MAXIMUMHEIGHT
				- Mouse.getY();

		Input input = gc.getInput();

		if (input.isKeyPressed(Input.KEY_P)) {
			pageNumber = 1;
			sbg.enterState(stateContainer.Game.PLAYCOMPETITIVESTATE);
		}

		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			// Check if buttons are pressed

			// Selecting Back Button
			if (posX > 20 && posX < 40 && posY > 20 && posY < 40) {
				pageNumber = 1;
				sbg.enterState(stateContainer.Game.PLAYCOMPETITIVESTATE);
			}

			// Selecting Exit Button
			if (posX > 1150 && posX < 1170 && posY > 550 && posY < 580) {
				System.exit(0);
			}

			/* If user has pressed to go back to main menu */
			if (posX > 540 && posX < 720 && posY > 500 && posY < 600) {
				((Game) sbg).setGameEndedComp(true);
				((Game) sbg).setPreviousCompetitiveModeWinner("Draw");
				sbg.enterState(stateContainer.Game.GAMEOVERCOMPETITIVESTATE);
			}

		}

	}

	@Override
	public int getID() {
		return stateContainer.Game.PAUSECOMPETITIVESTATE;
	}
}