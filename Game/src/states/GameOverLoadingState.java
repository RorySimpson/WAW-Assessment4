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

	/* Images */
	private static Image gameOverBackground;

	/* Font */
	private TrueTypeFont font;

	/* Text */
	private String text;

	/*
	 * Empty constructor for consistency and so Eclipse doesn't complain
	 */
	public GameOverLoadingState(int state) {

	}

	/**
	 * Overriding the initialisation funciton to load our own resources
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		{
			/* Deferred loading for better performances */
			LoadingList loading = LoadingList.get();

			loading.add(new DeferredFile(
					"res/menu_graphics/new/gameover_screen.png") {
				public void loadFile(String filename) throws SlickException {
					gameOverBackground = new Image(filename);
				}
			});
		}
	}

	/**
	 * Overriding the enter state method to load the game over state
	 */
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		sbg.enterState(stateContainer.Game.GAMEOVERSTATE);
	}

	/**
	 * Overriding the render method to draw the game over background
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		gameOverBackground.draw(0, 0);
	}

	/**
	 * Overriding the update method for consistency
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
	}

	/**
	 * Overriding the getID method to get use more readable names for states
	 */
	@Override
	public int getID() {
		return stateContainer.Game.GAMEOVERLOADINGSTATE;
	}
}