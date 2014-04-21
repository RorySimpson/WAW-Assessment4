package states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import util.DeferredFile;
import util.HoverImage;
import logicClasses.Achievements;
import stateContainer.Game;



public class AchieveViewerState extends BasicGameState{

	/* Images */
	public static Image
		menuBackground, menuButton, menuHover,
		silverUnachieved, silverImg,
		goldUnachieved, goldImg,
		timeUnachieved, timeImg,
		noPlanesLostUnachieved, noPlanesLostImg,
		planesLandedUnachieved, planesLandedImg,
		flightPlanChangedUnachieved, flightPlanChangedImg,
		crashUnachieved, crashImg,
		completeFlightPlanUnachieved, completeFlightPlanImg,
		allAchievedUnachieved, allAchievedImg;
	
	/* Hover images */
	private HoverImage
		menuReturn, silverAchieve, goldAchieve, timeAchieve, noPlanesLostAchieve, planesLandedAchieve,
		flightPlanChangedAchieve, crashAchieve, completeFlightPlanAchieve, allAchievedAchieve;
	
	/* Copy of achievements pulled from game container */
	private Achievements
		currentAchieved;
	
	/* Check if mouse has been released */
	private boolean mouseBeenReleased;
	
	/* Empty constructor for consistency */
	public AchieveViewerState(int state){
		
	}

	/**
	 * Override the initialisation method to load our resources
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		{
			/* Deferred loading of resources for better performances */
			LoadingList loading = LoadingList.get();

			//background img
			loading.add(new DeferredFile(
					"res/menu_graphics/new/menu_screen.png"){	
				public void loadFile(String filename) throws SlickException{
					menuBackground = new Image(filename);
				}
			});

			//menu return hoverButton img
			loading.add(new DeferredFile(
					"res/menu_graphics/new/menu_button.png"){
				public void loadFile(String filename) throws SlickException{
					menuButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/menu_hover.png"){
				public void loadFile(String filename) throws SlickException{
					menuHover = new Image(filename);
				}
					
			});
			
			//gold achievement img
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/goldAchieved.png"){
				public void loadFile(String filename) throws SlickException{
					goldImg = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/goldUnachieved.png"){
				public void loadFile(String filename) throws SlickException{
					goldUnachieved = new Image(filename);
				}
			});
			
			//silver achievement img
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/silverAchieved.png"){
				public void loadFile(String filename) throws SlickException{
					silverImg = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/silverUnachieved.png"){
				public void loadFile(String filename) throws SlickException{
					silverUnachieved = new Image(filename);
				}
			});
			
			//time achievement img
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/timeAchieved.png"){
				public void loadFile(String filename) throws SlickException{
					timeImg = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/timeUnachieved.png"){
				public void loadFile(String filename) throws SlickException{
					timeUnachieved = new Image(filename);
				}
			});
			
			//no planes lost achievement img
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/noPlanesLostAchieved.png"){
				public void loadFile(String filename) throws SlickException{
					noPlanesLostImg = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/noPlanesLostUnachieved.png"){
				public void loadFile(String filename) throws SlickException{
					noPlanesLostUnachieved= new Image(filename);
				}
			});
			
			//planes landed achievement img
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/planesLandedAchieved.png"){
				public void loadFile(String filename) throws SlickException{
					planesLandedImg = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/planesLandedUnachieved.png"){
				public void loadFile(String filename) throws SlickException{
					planesLandedUnachieved = new Image(filename);
				}
			});
			
			//flight plan changed achievement img
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/flightPlanChangedAchieved.png"){
				public void loadFile(String filename) throws SlickException{
					flightPlanChangedImg = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/flightPlanChangedUnachieved.png"){
				public void loadFile(String filename) throws SlickException{
					flightPlanChangedUnachieved = new Image(filename);
				}
			});
			
			//all achievements achievement img
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/allAchieved.png"){
				public void loadFile(String filename) throws SlickException{
					allAchievedImg = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/allAchievedUnachieved.png"){
				public void loadFile(String filename) throws SlickException{
					allAchievedUnachieved = new Image(filename);
				}
			});
			
			//crash achievement img
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/crashAchieved.png"){
				public void loadFile(String filename) throws SlickException{
					crashImg = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/crashUnachieved.png"){
				public void loadFile(String filename) throws SlickException{
					crashUnachieved = new Image(filename);
				}
			});
			
			//complete flight plan img
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/completeFlightPlanAchieved.png"){
				public void loadFile(String filename) throws SlickException{
					completeFlightPlanImg = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile(
					"res/menu_graphics/achievements/completeFlightPlanUnachieved.png"){
				public void loadFile(String filename) throws SlickException{
					completeFlightPlanUnachieved = new Image(filename);
				}
			});
			
			
			loading.add(new DeferredResource(){
				public String getDescription() {
					return "set up AchieveViewerState buttons";
				}

				/* Load all the images */
				public void load(){
					menuReturn = new HoverImage(menuButton, menuHover, 20, 20);
					silverAchieve = new HoverImage(silverUnachieved, silverImg, 240, 240);
					goldAchieve = new HoverImage(goldUnachieved, goldImg, 320, 240);
					timeAchieve = new HoverImage(timeUnachieved, timeImg, 400, 240);
					noPlanesLostAchieve = new HoverImage(noPlanesLostUnachieved, noPlanesLostImg, 480, 240);
					planesLandedAchieve = new HoverImage(planesLandedUnachieved, planesLandedImg, 560, 240);
					flightPlanChangedAchieve = new HoverImage(flightPlanChangedUnachieved, flightPlanChangedImg, 640,240);
					crashAchieve = new HoverImage (crashUnachieved, crashImg, 720, 240);
					completeFlightPlanAchieve = new HoverImage (completeFlightPlanUnachieved, completeFlightPlanImg, 800, 240);
					allAchievedAchieve = new HoverImage (allAchievedUnachieved, allAchievedImg, 880, 240);

				}
			});
		}

		
		/*try {
			Font awtFont = new Font("Courier New", Font.PLAIN, 20);
			font = new TrueTypeFont(awtFont, false);
		} catch(Exception e){
			e.printStackTrace();
		}*/

		/*credits = new String[][] {
				{"Music Assets",
					"\"Jarvic 8\" Kevin MacLeod (incompetech.com)",
					"Licensed under Creative Commons: By Attribution 3.0",
					"http://creativecommons.org/licenses/by/3.0/"
				},
				{"Images",
					"Loading screen plane created by Sallee Design",
					"http://salleedesign.com/resources/plane-psd/"
				},
				{"Font",
					"A love of thunder",
					"Downloaded from DaFont",
					"http://www.dafont.com/a-love-of-thunder.font"
				}
		};*/
	}
	

	/**
	 * Override the rendering method to render our resouces
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
        /* Get mouse position */
		int posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
			//Fixing posY to reflect graphics coords
	
		/* Draw the menu background */
		menuBackground.draw(0,0);
		
		//draw hover buttons in correct locations
		menuReturn.render(posX, posY);
		silverAchieve.render(posX, posY);
		goldAchieve.render(posX, posY);
		timeAchieve.render(posX, posY);
		noPlanesLostAchieve.render(posX, posY);
		planesLandedAchieve.render(posX, posY);
		flightPlanChangedAchieve.render(posX, posY); 
		crashAchieve.render(posX, posY); 
		completeFlightPlanAchieve.render(posX, posY); 
		allAchievedAchieve.render(posX, posY);
		
		//draw background panel
		g.setColor(new Color(250, 235, 215, 50));	//pale orange, semi-transparent
		g.fillRoundRect (50, 330, 1100, 220, 5);
		g.fillRoundRect (235, 235, 730, 90, 2);
		
	}

	/**
	 * Override the update method to update our resources
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
        /* Get achievements objects */
		currentAchieved = ((Game)sbg).getAchievements();
		
		/* Get mouse position */
		int	posX = Mouse.getX(),
			posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
				// Mapping Mouse coords onto graphics coords
		
		/* If menu button is pressed, go to menu */
		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {	
			if(mouseBeenReleased){	//button first pressed
				mouseBeenReleased = false;
				
				if (menuReturn.isMouseOver(posX, posY)){
					sbg.enterState(stateContainer.Game.MENUSTATE);
				}
			}
			/* else mouse is dragged*/
		}	
        /* Mouse just released */
		else if (!mouseBeenReleased){	
			mouseBeenReleased = true;
		}
	}

	/**
	 * Override the getID method for better readability
	 */
	@Override
	public int getID() {
		return stateContainer.Game.ACHIEVEVIEWERSTATE;
	}
}
