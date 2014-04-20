package states;

import java.awt.Font;
import java.io.InputStream;

import onlineLogicClasses.OnlineAirspace;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import util.DeferredFile;

public class OnlinePlayState extends PlayState {
	
	
	/**
	 * TODO (not sure what's this for v)
	 * Constructor for OnlinePlayState
	 * @param state Takes a state and calls its constructor
	 */
	public OnlinePlayState(int state) {

		super(state);

	}

	/**
	 * Overriding the initialisation method
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) 
			throws SlickException {

		/* Resets the options to start the game */
		gameEnded = false;
		settingDifficulty = true;
		time = 0;

		/* Creates an airspace object */
		airspace = new OnlineAirspace();
		/* The displayed string for time is empty at the beginning */
		this.stringTime="";


		gc.setAlwaysRender(true);
		gc.setUpdateOnlyWhenVisible(true);

		// Font
		{
			LoadingList loading = LoadingList.get();

			loading.add(new DeferredFile("res/GoodDog/GoodDog.otf"){
				public void loadFile(String filename) {
					InputStream inputStream = ResourceLoader.
							getResourceAsStream(filename);

					try {
						Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
						font = new TrueTypeFont(awtFont.deriveFont(20f), true);
						panelFont = new TrueTypeFont(awtFont.deriveFont(14f), true);

                /* Catch error if font can't load */
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			// Music
			loading.add(new DeferredFile(
					"res/music/new/Beachfront Celebration.ogg"){
				public void loadFile(String filename) throws SlickException{
					gameplayMusic = new Music(filename);
				}
			});



			loading.add(new DeferredFile(
					"res/music/new/Big Explosion.ogg"){
				public void loadFile(String filename) throws SlickException{
					endOfGameSound = new Sound(filename);
				}
			});

			//Images
			loading.add(new DeferredFile(
					"res/graphics/new/control_bar_vertical.png"){
				public void loadFile(String filename) throws SlickException{
					statusBarImage = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/graphics/clock.png"){
				public void loadFile(String filename) throws SlickException{
					clockImage = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/graphics/new/wind_indicator.png"){
				public void loadFile(String filename) throws SlickException{
					windImage = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/graphics/new/control_bar_plane.png"){		
				public void loadFile(String filename) throws SlickException{	
					flightIcon = new Image(filename);
				}	
			});		

			loading.add(new DeferredFile(
					"res/graphics/new/background.png"){
				public void loadFile(String filename) throws SlickException{
					backgroundImage = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/difficulty.png"){
				public void loadFile(String filename) throws SlickException{
					difficultyBackground = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/easy.png"){
				public void loadFile(String filename) throws SlickException{
					easyButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/easy_hover.png"){ 
				public void loadFile(String filename) throws SlickException{
					easyHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/medium.png"){
				public void loadFile(String filename) throws SlickException{
					mediumButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/medium_hover.png"){
				public void loadFile(String filename) throws SlickException{
					mediumHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/hard.png"){
				public void loadFile(String filename) throws SlickException{
					hardButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/menu_graphics/new/hard_hover.png"){
				public void loadFile(String filename) throws SlickException{
					hardHover = new Image(filename);
				}
			});	

			loading.add(new DeferredFile(
					"res/graphics/new/achivementBox.png"){
				public void loadFile(String filename) throws SlickException{
					achievementBox = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/graphics/new/messageBox.png"){
				public void loadFile(String filename) throws SlickException{
					messageBoxImage = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/graphics/new/soundOff.png"){
				public void loadFile(String filename) throws SlickException{
					soundOffImage = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/graphics/new/soundOn.png"){
				public void loadFile(String filename) throws SlickException{
					soundOnImage = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/graphics/new/pause.png"){
				public void loadFile(String filename) throws SlickException{
					pauseImage = new Image(filename);
				}
			});

			loading.add(new DeferredFile(
					"res/graphics/new/coin.png"){
				public void loadFile(String filename) throws SlickException{
					scoreCoinImage = new Image(filename);
				}
			});


			/* Loads the explosion spritesheet */
			SpriteSheet sheet = new SpriteSheet(
					"res/graphics/explosion.png", 128, 128);

			/* Creates the explosion */
			explosion = new Animation();
			explosion.setAutoUpdate(true);

			/* Adds all the frames in the spritesheet */
			int spriteNumber = 0;

			for(int col=0;col<9;col++)
			{
				explosion.addFrame(sheet.getSprite(spriteNumber,0), 100);
				spriteNumber++;
			}
		}

		// Initialise the airspace object;
		// Waypoints
		airspace.newWaypoint( 350, 150, "A");
		airspace.newWaypoint( 400, 470, "B");
		airspace.newWaypoint( 650,  60, "C");
		airspace.newWaypoint( 800, 320, "D");
		airspace.newWaypoint( 600, 418, "E");
		airspace.newWaypoint( 500, 220, "F");
		airspace.newWaypoint( 950, 188, "G");
		airspace.newWaypoint(1050, 272, "H");
		airspace.newWaypoint( 900, 420, "I");
		airspace.newWaypoint( 240, 250, "J");


		// EntryPoints
		airspace.newEntryPoint( 11, 400);
		airspace.newEntryPoint(1200, 200);
		airspace.newEntryPoint( 600,   0);
		airspace.getListOfEntryPoints().add(airspace
								.getAirportLeft().getEndOfRunway());
		airspace.getListOfEntryPoints().add(airspace
								.getAirportRight().getEndOfRunway());

		// Exit Points
		airspace.newExitPoint( 800,   0, "1");
		airspace.newExitPoint( 11, 200, "2");
		airspace.newExitPoint(1200, 300, "3");
		airspace.getListOfExitPoints().add(airspace.getAirportLeft()
								.getBeginningOfRunway());
		airspace.getListOfExitPoints().add(airspace.getAirportRight()
								.getBeginningOfRunway());

		/* Initialise airspace */
		airspace.init(gc);
	}

         
	/**
	 * Override the enter state method
	 */
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		
		((OnlineAirspace)airspace).enter();
		
	}
	
	/**
	 * Override the getID() method to return a more readable result
	 */
	public int getID() {
		return stateContainer.Game.ONLINEPLAYSTATE;
	}
}
