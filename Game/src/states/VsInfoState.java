package states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.DeferredFile;
import util.HoverImage;

public class VsInfoState extends BasicGameState {

	/* Images */
	private static Image background, backButton, backButtonHover;

	/* Boolean to check for buttons clicking */
	private boolean mouseBeenReleased;

	private HoverImage back;

	/* Constructor so Eclipse doesn't complain */
	public VsInfoState(int state) {
		this.mouseBeenReleased = false;
	}

	/**
	 * Overriding the initialisation method
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbj)
			throws SlickException {

		/* Deferred loading of all the needed images */
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
			loading.add(new DeferredResource() {
				public String getDescription() {
					return "set up menuState buttons";
				}

				/* Load all the hover images */
				public void load() {
					back = new HoverImage(backButton, backButtonHover, 70, 70);
				}
			});
		}
	}

	/**
	 * Override the rendering method
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		/* Get the mouse position */
		int posX = Mouse.getX(), posY = stateContainer.Game.MAXIMUMHEIGHT
				- Mouse.getY();

		background.draw(0, 0);

		back.render(posX, posY);
	}

	/**
	 * Override the update method
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		/* Get mouse position */
		int posX = Mouse.getX(), posY = stateContainer.Game.MAXIMUMHEIGHT
				- Mouse.getY();

		/* Get user input */
		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if (mouseBeenReleased) { // button first pressed
				mouseBeenReleased = false;

				if (back.isMouseOver(posX, posY)) {
					sbg.enterState(stateContainer.Game.MODESTATE);
				}
			}
			/* else mouse is dragged */
		}
		// Mouse just released without pressing a button
		else if (!mouseBeenReleased) {
			mouseBeenReleased = true;
		}
	}

	/**
	 * Better state ID
	 */
	@Override
	public int getID() {
		return stateContainer.Game.VSSTATE;
	}
}
