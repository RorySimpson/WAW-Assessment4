package events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logicClasses.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import java.awt.geom.Point2D;

public class Volcano {
	
	private int countdownTillNextEruption = 3600;
	private List <VolcanoProjectile> listOfProjectilesLaunched;
	public static final Point2D.Double VOLCANOPOSITION = new Point2D.Double(0,0);
	
	
	
	public Volcano(){
		
		this.listOfProjectilesLaunched = new ArrayList<VolcanoProjectile>();
	}
	

	public void eruption(){
		
		Random rand = new Random();
		int numberOfProjectilesToBeLaunched = rand.nextInt(4) + 1;
		
		for(int i = 0; i < numberOfProjectilesToBeLaunched; i++){
			listOfProjectilesLaunched.add(new VolcanoProjectile());
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
	
	public void render(){
		
		for (VolcanoProjectile projectile : listOfProjectilesLaunched){
			projectile.render();
		}
		
	}
	
	public void update(){
		
		if (countdownTillNextEruption != 0){
			countdownTillNextEruption -= 1;
		}
		
		else{
			eruption();
			countdownTillNextEruption = 3600;
		}
		
		for (VolcanoProjectile projectile : listOfProjectilesLaunched){
			projectile.update();
			if (checkIfProjectileHasLeftAirspace(projectile)){
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


}
