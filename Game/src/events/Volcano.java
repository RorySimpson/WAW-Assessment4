package events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logicClasses.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import java.awt.geom.Point2D;

public class Volcano {

	private int countdownTillNextEruption = 3600;
	private List<VolcanoProjectile> listOfProjectilesLaunched;
	public static final Point2D.Double VOLCANOPOSITION = new Point2D.Double(
			600, 300);
	private Image volcanoImage;

	public Volcano() {

		this.listOfProjectilesLaunched = new ArrayList<VolcanoProjectile>();
	}

	/**
	 * eruption: Creates projectiles to be released during eruption.
	 */
	public void eruption(GameContainer gc) throws SlickException {

		Random rand = new Random();

		// Generate a random number of projectiles to be launched.
		// Between 2 and 5 projectiles will be launched
		int numberOfProjectilesToBeLaunched = rand.nextInt(4) + 2;

		// Create projectiles
		for (int i = 0; i < numberOfProjectilesToBeLaunched; i++) {
			listOfProjectilesLaunched.add(new VolcanoProjectile());
			listOfProjectilesLaunched.get(listOfProjectilesLaunched.size() - 1)
					.init(gc);
		}
	}

	/**
	 * checkIfProjectilesHasLeftAirspace: Checks whether the projectiles
	 * launched from the volcano have left the airspace.
	 */
	public boolean checkIfProjectileHasLeftAirspace(VolcanoProjectile projectile) {
		// x and y must be within these bounds to be within screen space
		// Not quite the same with withinAirspace method
		if (projectile.getX() > 1220 || projectile.getX() < -20
				|| projectile.getY() > 620 || projectile.getY() < -20) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * init: Initialises all the resources required for the volcano class.
	 * 
	 * @param gc
	 *            GameContainer
	 * @throws SlickException
	 */

	public void init(GameContainer gc) throws SlickException {
		volcanoImage = new Image("res/graphics/new/volcano.png");
	}

	/**
	 * render: Render all of the graphics for the volcano
	 * 
	 * @param g
	 *            Graphics
	 * @param gc
	 *            GameContainer
	 * 
	 * @throws SlickException
	 */

	public void render(Graphics g, GameContainer gc) throws SlickException {

		volcanoImage.drawCentered((float) VOLCANOPOSITION.x,
				(float) VOLCANOPOSITION.y);

		for (VolcanoProjectile projectile : listOfProjectilesLaunched) {
			projectile.render(g, gc);
		}

	}

	/**
	 * update: Update all logic in the volcano class
	 * 
	 * @param gc
	 *            GameContainer
	 */

	public void update(GameContainer gc) throws SlickException {

		// If it's time for an eruption, let all hell break loose
		if (countdownTillNextEruption == 0) {
			countdownTillNextEruption = 3600;
			eruption(gc);
		}

		// Otherwise reduce countdown
		else {
			countdownTillNextEruption -= 1;

		}

		// Update the state of the volcano projectiles
		for (int i = 0; i < listOfProjectilesLaunched.size(); i++) {
			VolcanoProjectile projectile = listOfProjectilesLaunched.get(i);
			projectile.update();
			if (checkIfProjectileHasLeftAirspace(projectile)) {
				listOfProjectilesLaunched.remove(projectile);
			}

		}

	}

	public List<VolcanoProjectile> getListOfProjectilesLaunched() {
		return listOfProjectilesLaunched;
	}

	public void setListOfProjectilesLaunched(
			List<VolcanoProjectile> listOfProjectilesLaunched) {
		this.listOfProjectilesLaunched = listOfProjectilesLaunched;
	}

	public int getCountdownTillNextEruption() {
		return countdownTillNextEruption;
	}

	public void setCountdownTillNextEruption(int countdownTillNextEruption) {
		this.countdownTillNextEruption = countdownTillNextEruption;
	}

}
