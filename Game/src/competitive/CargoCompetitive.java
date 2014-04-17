package competitive;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;


import util.DeferredFile;

import java.awt.geom.Point2D;
import java.util.Random;

import logicClasses.Airspace;
import logicClasses.Flight;

public class CargoCompetitive {

	private Point2D location;
	private Flight currentHolder;
	Image scoreCoinImage;


	CargoCompetitive(){
		this.location = generateRandomCargoLocation();
		this.currentHolder = null;
	}
	
	public void init(GameContainer gc)throws SlickException{
		
		LoadingList loading = LoadingList.get();
		
		loading.add(new DeferredFile("res/graphics/new/coin.png"){
			public void loadFile(String filename) throws SlickException{
				scoreCoinImage = new Image(filename);
			}
		});
	}
	
	

	public Point2D generateRandomCargoLocation(){
		Point2D tempLocation = new Point2D.Float();
		Random rand = new Random();
		tempLocation.setLocation(rand.nextInt(1100) + 50, rand.nextInt(450) + 50);
		return tempLocation;
	}
	
	public void render(Graphics g, GameContainer gc){
		if(this.currentHolder == null){
			scoreCoinImage.drawCentered((float)location.getX(), (float)location.getY());
			//g.drawOval((float)location.getX(), (float)location.getY(), 10, 10);
		}
	}

	public Point2D getLocation() {
		return location;
	}

	public void setLocation(Point2D location) {
		this.location = location;
	}

	public Flight getCurrentHolder() {
		return currentHolder;
	}

	public void setCurrentHolder(Flight currentHolder) {
		this.currentHolder = currentHolder;
	}
}
