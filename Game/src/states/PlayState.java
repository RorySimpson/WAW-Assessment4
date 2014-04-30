package states;

import java.awt.Font;
import java.io.InputStream;

import logicClasses.Achievements;
import logicClasses.Airspace;
import logicClasses.Controls;
import logicClasses.Flight;
import stateContainer.Game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import events.Tornado;
import util.DeferredFile;


/* The PlayState is the game play state */
public class PlayState extends BasicGameState {
    /* All the images */
	protected static Image 
		easyButton, mediumButton,hardButton,  
		easyHover, mediumHover, hardHover,  
		backgroundImage, difficultyBackground,
		statusBarImage, clockImage, windImage,
		flightIcon,messageBoxImage, scoreCoinImage,
		cursorImg, achievementBox, soundOffImage, 
		soundOnImage, pauseImage, moonImage, sunImage,
		dayTimeImage;


    /* Explosion animation for crashes */
	protected Animation explosion;

    /* Sound and music for entertainment */
	protected static Sound endOfGameSound;
	protected static Music gameplayMusic;

    /* Custom fonts */
	protected static TrueTypeFont
		font;
	protected  static TrueTypeFont panelFont;	

    /* Timer  */
	protected float time;
	protected String stringTime;
	protected int counter = 0;

    /* Airspace object */
	protected Airspace airspace;

    /* Booleans to help out with the game events */
	protected boolean settingDifficulty;
	protected boolean gameEnded;
	
	
    /* Achievements objects */
	protected Achievements achievement;
    /* This will be used to display the achievement gained */
	protected String achievementMessage = "";
	
	protected float currentCoord = 600;
	protected float targetCoord;

    /* A game over delay to allow the explosion to appear before displaying 
     * the game over screen */


    /* A synchroniser to decide how long should the achievements text appear for */
	protected int synch = 180;

    /* Storing the colours to facilitate the day/night cycle */
	protected int red = 255, blue = 255, green = 255;
	protected Color brightness = new Color(red, green, blue);
	/* Constants to dictate how low should the brightness go to */
	protected static final int MAXIMUMBRIGHTNESS = 255;
	protected static final int MINIMUMBRIGHTNESS = 25;

    /* How long should the day last */
	protected int countdownToLightReduction = 40;

    /* Default booleans for music and day/night */
	protected boolean musicPaused = false, increasingBrightness = false;
	
    /* Constructor to create a new Achievements object */
	public PlayState(int state) {
		achievement = new Achievements();
	}
	
    /* Overriding the enter state method so Eclipse
     *  doesn't prompt us with warnings */
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		
	}


    /* Overriding the initialisation method */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
        /* Default values for when the game started */
		gameEnded = false;
		settingDifficulty = true;

        /* Time starts at 0 */
		time = 0;

        /* New airspace object */
		airspace = new Airspace();
		this.stringTime = "";
		
		
        /* Start rendering */
		gc.setAlwaysRender(true);
		gc.setUpdateOnlyWhenVisible(true);

		// Font options
		
		{
            /* Deferred loading for better performances */
			LoadingList loading = LoadingList.get();
			
            /* Load the font */
			loading.add(new DeferredFile("res/GoodDog/GoodDog.otf"){
				public void loadFile(String filename) {
					InputStream inputStream = ResourceLoader.
												getResourceAsStream(filename);
					try {
						Font awtFont = Font.createFont(Font.TRUETYPE_FONT, 
															inputStream);
                        /* Different font sizes */
						font = new TrueTypeFont(awtFont.
														deriveFont(20f), true);
						panelFont = new TrueTypeFont(awtFont.
														deriveFont(14f), true);
                    /* Catch any error that might arise */
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
            /* Load the music */
			loading.add(new DeferredFile("res/music/new/Beachfront Celebration.ogg"){
				public void loadFile(String filename) throws SlickException{
					gameplayMusic = new Music(filename);
				}
			});
			

			
            /* Load the explosion sound */
			loading.add(new DeferredFile("res/music/new/Big Explosion.ogg"){
				public void loadFile(String filename) throws SlickException{
					endOfGameSound = new Sound(filename);
				}
			});

            /* Load all the needed images */
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

			loading.add(new DeferredFile(
					"res/graphics/new/moon.png"){
				public void loadFile(String filename) throws SlickException{
					moonImage = new Image(filename);
                        }
				});

			loading.add(new DeferredFile(
					"res/graphics/new/sun.png"){
				public void loadFile(String filename) throws SlickException{
					sunImage = new Image(filename);
				}
			});
			
			
            /* Load the explosion and set it up */
			SpriteSheet sheet = new SpriteSheet(
					"res/graphics/explosion.png", 128, 128); // Size

            /* Set up the explosion */
	        explosion = new Animation();
	        explosion.setAutoUpdate(true);
	        
            /* Find the number of sprites in the spritesheet */
	        int spriteNumber = 0;
	      
            for(int col = 0; col < 9; col++){
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
		
	
		//EntryPoints
		airspace.newEntryPoint( 11, 400);
		airspace.newEntryPoint(1200, 200);
		airspace.newEntryPoint( 600,   0);
		airspace.getListOfEntryPoints().add(airspace.getAirportLeft()
													.getEndOfRunway());
		airspace.getListOfEntryPoints().add(airspace.getAirportRight()
                                                    .getEndOfRunway());
		
		// Exit Points
		airspace.newExitPoint( 800,   0, "1");
		airspace.newExitPoint(  11, 200, "2");
		airspace.newExitPoint(1200, 300, "3");
		airspace.getListOfExitPoints().add(airspace.getAirportLeft()
                                                .getBeginningOfRunway());
		airspace.getListOfExitPoints().add(airspace.getAirportRight()
                                                .getBeginningOfRunway());
				
	    airspace.init(gc);
	}
	
	/* Method to draw the events messages at the bottom of the screen */
	public void drawEventMessage(GameContainer gc, Graphics g){
		
		/* Setting up the event booleans */
		boolean waitingToTakeOffAtAPL = false, waitingToTakeOffAtAPR = false,
				eruptionAboutToOccur  = false, waitingToLand = false;
		
		/* Loop through flighs to check if any needs to land or take off
		 * and signal to display the message */
		for(Flight flight: airspace.getListOfFlights()){
			
			/* Check if there's a plane waiting to take off 
			 * at the left airport */
			if (flight.getFlightPlan().getEntryPoint() 
					== airspace.getAirportLeft().getEndOfRunway() 
						&& !flight.isTakingOff() 
				&& flight.getAltitude() ==0){
				waitingToTakeOffAtAPL = true;
			}
			
			/* Check if there's a plane waiting to take off 
			 * at the right airport */
			if (flight.getFlightPlan().getEntryPoint() 
					== airspace.getAirportRight().getEndOfRunway() 
                        && !flight.isTakingOff() 
                            && flight.getAltitude() ==0){
					waitingToTakeOffAtAPR = true;
			}
			
			/* Check if a plane needs to land at either of the airports */
			if ((flight.getFlightPlan().getCurrentRoute().get(0)
					== airspace.getAirportRight().getBeginningOfRunway() 
						|| flight.getFlightPlan().getCurrentRoute().get(0)
						  == airspace.getAirportLeft().getBeginningOfRunway())
						  && !flight.isLanding()){
					waitingToLand = true;
			}
		}
		
		/* Warning about eruptions */
		if(airspace.getEventController().getVolcano().getCountdownTillNextEruption() < 600){
			eruptionAboutToOccur = true;
		}
		
		/* The actual warning messages */
		if(eruptionAboutToOccur){
			g.setColor(Color.red);
			g.drawString("Volcano's ready to blow!!!!!", 500, 570);
			g.setColor(Color.white);
		}
		
		else if(waitingToLand){
			g.drawString("Flight wants to land", 500, 570);
			
		}
		
		else if(waitingToTakeOffAtAPL){
			g.drawString("Flight awaiting take off at APL", 500, 570);
			
		}
		
		else if(waitingToTakeOffAtAPR){
			g.drawString("Flight awaiting take off at APR", 500, 570);
		}
		
		
	}
	
	/* Override the rendering method to render our resources */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
        /* Start the music */
		gameplayMusic.setVolume(1);
		g.setAntiAlias(true);
		
		
		
		// Checks whether the user is still choosing the difficulty
		if(settingDifficulty){

			int posX = Mouse.getX();
            //Fixing posY to reflect graphics coords
			int posY= stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();

			difficultyBackground.draw(0,0);
			

			/* Check if the user is hovering the buttons 
			 * in order to render different images */
			if (posX>100 && posX<216 && posY>300 && posY<354)
				easyHover.draw(100,300);
			else easyButton.draw(100,300);
			
			if (posX>100 && posX<284 && posY>400 && posY<454)
				mediumHover.draw(100,400);
			else mediumButton.draw(100,400);
			
			if (posX>100 && posX<227 && posY>500 && posY<554)
				hardHover.draw(100,500);
			else hardButton.draw(100,500);
		}
		
		else {
			/* MAIN GAME */

			// Set font for the rest of the rendering
			g.setFont(font);

			/* Accommodate day/night transition */
			if(countdownToLightReduction != 0){
				countdownToLightReduction --;

			}
			/* Check if it's night or day. 25 is the 
			 * minimum value that we drop to */
			else {
				/* If it's complete night, start lighting up 
				 * we only check for red because all the other colours
				 * are kept equal to red */
				if(red == MINIMUMBRIGHTNESS){
					increasingBrightness = true;	
				}
				
				/* Stop lighting up if the colours are fully saturated */
				else if(red == MAXIMUMBRIGHTNESS){
					increasingBrightness = false;
				}
				
				if (red == MINIMUMBRIGHTNESS || red == MAXIMUMBRIGHTNESS){
					/* If maximum or minimum brightness, don't change 
					 * brightness for 20 seconds. This is done in order 
					 * to have some delay between transitions */
					countdownToLightReduction = 60 * 20; 
				}
				else {
					countdownToLightReduction = 40;
				}
				
				/* Morning - add brightness */
				if(increasingBrightness){
					red ++;
					blue ++;
					green ++;
					brightness = new Color( red , blue, green);
				}
				/* Evening - decrease brightness */
				else{
					red --;
					blue --;
					green --;
					brightness = new Color( red , blue, green);
				}
			}
			
			
			/* Draw the background and achievement box */
			backgroundImage.draw(0,0, brightness);
			messageBoxImage.draw(11,560);
			
			
			// Drawing Airspace and elements within it
			g.setColor(Color.white);
			airspace.render(g, gc);
			statusBarImage.draw(0,0);
					
			// Drawing Clock and Time
			g.setColor(Color.white);
			clockImage.draw(6,565);
			g.drawString(stringTime, 31, 570);

			// Drawing moon and sun
			dayTimeImage = (red > (MAXIMUMBRIGHTNESS - MINIMUMBRIGHTNESS)/2) ? sunImage:moonImage;
			String dayTimeString = (dayTimeImage == sunImage) ? "Daytime":"Nighttime";

			dayTimeImage.draw(160,570);
			g.drawString(dayTimeString, 190, 570);

			// Drawing Score
			scoreCoinImage.draw(90, 573);
			g.drawString(airspace.getScore().toString(), 110, 570);
			if (airspace.getScore().getCurrentMultiplier() != 1){
				g.drawString("x" + String.valueOf(airspace.getScore()
						.getCurrentMultiplier()), 200,570);
			}
			
			// Drawing Pause Button and Mute
			if(gameplayMusic.playing()){
				soundOnImage.draw(1160, 565);
			}
			else{
				soundOffImage.draw(1160, 565);
			}
			
			pauseImage.draw(1120,565);
			
			drawEventMessage(gc,g);
						
			// Compute the multiplier algorithm
			// TODO I still have to figure out what's happening here
			if(airspace.getScore().getProgressionTowardsNextMultiplier() != 0){
				
				this.targetCoord = 600 - (float) (0.6 * airspace.getScore()
						.getProgressionTowardsNextMultiplier()); 
				
				/*  */
				if (this.currentCoord != this.targetCoord 
						&& airspace.getScore().getNegMult() == false){
					g.setColor(Color.cyan);
					g.fillRect(0, this.currentCoord, 11, (600-this.currentCoord));
					g.setColor(Color.white);
					this.currentCoord --;
				}

				else if (this.currentCoord != this.targetCoord 
						&& airspace.getScore().getNegMult() == true){
					g.setColor(Color.cyan);
					g.fillRect(0, this.currentCoord, 11, (600-this.currentCoord));
					g.setColor(Color.white);
					this.currentCoord ++;
					if (this.currentCoord == this.targetCoord) { airspace.getScore().setNegMult(false); }
				}

				else {
					g.setColor(Color.cyan);
					g.fillRect(0, this.currentCoord, 11, (600-this.currentCoord));
					g.setColor(Color.white);
				}
			}

			else {
				if (airspace.getScore().getMultiplierInc()){
					airspace.getScore().setMultiplierInc(false);
					this.currentCoord = 600;
				}

				else if (this.currentCoord != 600){
					g.setColor(Color.cyan);
					g.fillRect(0, this.currentCoord, 11, (600-this.currentCoord));
					g.setColor(Color.white);
					this.currentCoord ++;
				}

				else {
					;
				}
			}
			
			// Segmenting multiplier bar
			g.setColor(Color.black);
			g.drawLine(0, 120, 10, 120);
			g.drawLine(0, 240, 10, 240);
			g.drawLine(0, 360, 10, 360);
			g.drawLine(0, 480, 10, 480);
			
			Input input = gc.getInput();
			
			 
			// Drawing Achievements
			if (airspace.getScore().getAchievements().getAchievementGained()){
				
				/* Draw the box first */
				achievementBox.draw(900, 0);
				
				/* Draw the text */
				g.drawString(airspace.getScore().scoreAchievement(), 
						stateContainer.Game.MAXIMUMWIDTH -font.getWidth
						(airspace.getScore().scoreAchievement()) -10, 30);
				g.drawString(achievementMessage, 
						stateContainer.Game.MAXIMUMWIDTH -10 -font.getWidth
						(achievementMessage), 40);

				/* Decrease the synchroniser to make the text disappear
				 * after a certain amount of time */
				synch --;
			
				/* Erase the text and restart the synchroniser */
				if (synch == 0){
                    airspace.getScore().getAchievements().setAchievementGained(false);
                    synch = 180;
				}
			}
		}	
	}
				
							
	/* Override the update method */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
        /* Get mouse position */
		int posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();

		/* Get user input */
		Input input = gc.getInput();
		
		
		// Checks if the game has been retried and if it has resets the airspace
		if (gameEnded){
			airspace.resetAirspace();
			airspace.init(gc);
	    	time = 0;

	    	gameEnded = false;
	    	settingDifficulty = true;

	    	airspace.getScore().resetScore();
		}
		
		
		
		// SIM: Checks whether the user is still choosing the difficulty
		if(settingDifficulty){
			if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				if((posX>100&&posX<216) && (posY>300&&posY<354)) {
					airspace.setDifficultyValueOfGame(1);
					airspace.getControls().setDifficultyValueOfGame(Controls.EASY);
					airspace.createAndSetSeparationRules();

					settingDifficulty = false;			
				}
				
				
				if((posX>100&&posX<284) && (posY>400&&posY<454)) {
					airspace.setDifficultyValueOfGame(2);
					airspace.getControls().setDifficultyValueOfGame(Controls.NORMAL);
					airspace.createAndSetSeparationRules();

					settingDifficulty = false;	
				}
				
				
				if((posX>100&&posX<227) && (posY>500&&posY<554)) {
					airspace.setDifficultyValueOfGame(3);
					airspace.getControls().setDifficultyValueOfGame(Controls.HARD);
					airspace.createAndSetSeparationRules();

					settingDifficulty = false;
				}	
			}
		}
		
		else {	

			/* MAIN GAME */

			// Updating Clock and Time
			time += delta;

			/* Check if user deserves an achievement for staying
			 * long enough in the game */
			achievement.timeAchievement((int) time);

			/* For computing the  time in a readable format */
			float decMins=time/1000/60;
			int mins = (int) decMins;
			float decSecs=decMins-mins;
			int secs = Math.round(decSecs*60);
				
			/* The strings used to display the time */
			String stringMins="";
			String stringSecs="";

			/* Calculate the time */
			if(secs >= 60){
				secs -= 60;
				mins += 1;
				// {!} should do +60 score every minute(possibly) 
				//     - after 3 minutes adds on 2 less points every time?
				airspace.getScore().updateTimeScore();
			}

			/* Adjust the time format to accommodate for 1 digit times */
			if(mins<10) {
				stringMins="0"+mins;
			}
			else {
				stringMins=String.valueOf(mins);
			}
			if(secs<10) {
				stringSecs="0"+secs;
			}
			else {
				stringSecs=String.valueOf(secs);
			}
						
			this.stringTime=stringMins+":"+stringSecs;
						
						
			// Updating Airspace
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                /* If user pressed pause */
				if((posX>1120&&posX<1165) && (posY>565&&posY<600)) {
					sbg.enterState(stateContainer.Game.PAUSESTATE);				
				}
				
				/* If user pressed the music button */
				else if((posX>1160&&posX<1200) && (posY>565&&posY<600)) {
					
					if(gameplayMusic.playing()){
						gameplayMusic.pause();
						musicPaused = true;
					}
					
					else{
						gameplayMusic.resume();
						musicPaused = false;
					}
				}
			}

			/* Call the updates */
			airspace.newFlight(gc );
			airspace.update(gc );

			/* If there is a crash */
			if (airspace.getSeparationRules().getGameOverViolation()){
                //pass the game time as of game over into the crashAchievement
				achievementMessage = achievement.crashAchievement((int) time); 
				airspace.getSeparationRules().setGameOverViolation(false);

				/* Set the achievements */
				((Game)sbg).setAchievements(airspace.getScore().getAchievements());
				((Game)sbg).setCurrentScore(airspace.getScore( ).getCurrentScore());

				/* Stop the music and play the game over sound */
				gameplayMusic.stop();
				endOfGameSound.play();
				
				gameEnded = true;

				/* Lights up for next game session */
				red = 255;
				blue = 255;
				green = 255;
				brightness = new Color( red , blue, green);
				countdownToLightReduction = 40;
				
				/* Switch states to Game Over screen */
				sbg.enterState(stateContainer.Game.GAMEOVERLOADINGSTATE);
				currentCoord = 600;
				targetCoord = 600;
				synch = 180;
                }
			
		
			// Checking For Pause Screen requested in game
			if (input.isKeyPressed(Input.KEY_P)) {
				sbg.enterState(stateContainer.Game.PAUSESTATE);
			}			
			
			/* Checking for music being paused from within the game */
			if (input.isKeyPressed(Input.KEY_M)) {
				if (!musicPaused)
				{
					gameplayMusic.pause();
					musicPaused = true;
				}
				else
				{
					gameplayMusic.resume();
					musicPaused = false;
				}
			
			}
			
			//Keep this to demo tornadoes
			
/*			if (input.isKeyPressed(Input.KEY_X)){
				Tornado tornado = new Tornado(airspace);
				tornado.init(gc);
				tornado.attack();
				airspace.getEventController().getListOfTornados().add(tornado);
			}*/
			
			if (!gameplayMusic.playing() && (!musicPaused) ){
				//Loops gameplay music based on random number created in init (what?)
				gameplayMusic.loop(1.0f, 0.5f);
			}			
		}
	}


	@Override
	public int getID() {
		return stateContainer.Game.PLAYSTATE;
	}

	public Airspace getAirspace() {
		return airspace;
	}

	public void setAirspace(Airspace airspace) {
		this.airspace = airspace;
	}
}
