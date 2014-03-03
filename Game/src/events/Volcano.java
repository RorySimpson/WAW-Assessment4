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
	private List <VolcanoProjectile> listOfProjectilesLaunched;
	public static final Point2D.Double VOLCANOPOSITION = new Point2D.Double(600,300);
	private Image volcanoImage;
	
	
	
	public Volcano(){
		
		this.listOfProjectilesLaunched = new ArrayList<VolcanoProjectile>();
	}
	

	public void eruption(GameContainer gc) throws SlickException{
		
		Random rand = new Random();
		int numberOfProjectilesToBeLaunched = rand.nextInt(4) + 1;
		
		for(int i = 0; i < numberOfProjectilesToBeLaunched; i++){
			listOfProjectilesLaunched.add(new VolcanoProjectile());
			listOfProjectilesLaunched.get(listOfProjectilesLaunched.size()-1).init(gc);
		}
	}
	
	public boolean checkIfProjectileHasLeftAirspace(VolcanoProjectile projectile){
		// x and y must be within these bounds to be within screen space
				// Not quite the same with withinAirspace method
				if (projectile.getX() > 1220 || projectile.getX() < -20|| projectile.getY() > 620 || projectile.getY() < -20) { 
					return true;
				}
				else 
				{
					return false;
				}
	}
	
	public void init (GameContainer gc) throws SlickException{
		
	}
	
	public void render(Graphics g, GameContainer gc) throws SlickException{
		
		for (VolcanoProjectile projectile : listOfProjectilesLaunched){
			projectile.render(g, gc);
		}
		
	}
	
	public void update(GameContainer gc) throws SlickException{
		
		if (countdownTillNextEruption == 0){
			countdownTillNextEruption = 3600;
			eruption(gc);
		}
		
		else{
			countdownTillNextEruption -= 1;
			
		}
		
		for(int i = 0; i < listOfProjectilesLaunched.size(); i++){
			VolcanoProjectile projectile = listOfProjectilesLaunched.get(i);
			projectile.update();
			if (checkIfProjectileHasLeftAirspace(projectile)){
				listOfProjectilesLaunched.remove(projectile);
			}
			
		}
		
//		for (VolcanoProjectile projectile : listOfProjectilesLaunched){
//			projectile.update();
//			if (checkIfProjectileHasLeftAirspace(projectile)){
//				listOfProjectilesLaunched.remove(projectile);
//			}
//			
//		}
	}
	
	public List<VolcanoProjectile> getListOfProjectilesLaunched() {
		return listOfProjectilesLaunched;
	}

	public void setListOfProjectilesLaunched(
			List<VolcanoProjectile> listOfProjectilesLaunched) {
		this.listOfProjectilesLaunched = listOfProjectilesLaunched;
	}


}
