package coop;

import java.awt.Font;
import java.io.InputStream;

import logicClasses.Achievements;
import logicClasses.Airspace;
import logicClasses.Controls;
import logicClasses.Flight;

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





import stateContainer.Game;
import states.PlayState;
import util.DeferredFile;

public class PlayCoopState extends PlayState {
	
	protected float time;


	private AirspaceCoop airspace;


	public PlayCoopState(int state) {
		super(state);
		achievement = new Achievements();
	}
	/**
	 * init: Initialises all the resources required for the PlayCoopState class.
	 * @param gc GameContainer
	 * @throws SlickException
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		gameEnded = false;
		settingDifficulty = true;
		time = 0;
		airspace = new AirspaceCoop();
		this.stringTime="";
		
		gc.setAlwaysRender(true);
		gc.setUpdateOnlyWhenVisible(true);
		// Set mouse cursor
		//gc.setMouseCursor("res/graphics/cross.png",12,12);
	
		
		// Font
		
		{
			LoadingList loading = LoadingList.get();
			
			loading.add(new DeferredFile("res/GoodDog/GoodDog.otf"){
				public void loadFile(String filename) {
					InputStream inputStream = ResourceLoader.getResourceAsStream(filename);
					try {
						Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
						font = new TrueTypeFont(awtFont.deriveFont(20f), true);
						panelFont = new TrueTypeFont(awtFont.deriveFont(14f), true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			// Music
			loading.add(new DeferredFile("res/music/new/Beachfront Celebration.ogg"){
				public void loadFile(String filename) throws SlickException{
					gameplayMusic = new Music(filename);
				}
			});
			
			loading.add(new DeferredFile("res/music/new/Big Explosion.ogg"){
				public void loadFile(String filename) throws SlickException{
					endOfGameSound = new Sound(filename);
				}
			});

			//Images
			loading.add(new DeferredFile("res/graphics/new/control_bar_vertical.png"){
				public void loadFile(String filename) throws SlickException{
					statusBarImage = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/graphics/clock.png"){
				public void loadFile(String filename) throws SlickException{
					clockImage = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile("res/graphics/new/wind_indicator.png"){
				public void loadFile(String filename) throws SlickException{
					windImage = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile("res/graphics/new/control_bar_plane.png"){		
				public void loadFile(String filename) throws SlickException{	
					flightIcon = new Image(filename);
				}	
			});		

			loading.add(new DeferredFile("res/graphics/new/background.png"){
				public void loadFile(String filename) throws SlickException{
					backgroundImage = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/difficulty.png"){
				public void loadFile(String filename) throws SlickException{
					difficultyBackground = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/easy.png"){
				public void loadFile(String filename) throws SlickException{
					easyButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/easy_hover.png"){ 
				public void loadFile(String filename) throws SlickException{
					easyHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/medium.png"){
				public void loadFile(String filename) throws SlickException{
					mediumButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/medium_hover.png"){
				public void loadFile(String filename) throws SlickException{
					mediumHover = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/hard.png"){
				public void loadFile(String filename) throws SlickException{
					hardButton = new Image(filename);
				}
			});

			loading.add(new DeferredFile("res/menu_graphics/new/hard_hover.png"){
				public void loadFile(String filename) throws SlickException{
					hardHover = new Image(filename);
				}
			});	
			
			loading.add(new DeferredFile("res/graphics/new/achivementBox.png"){
				public void loadFile(String filename) throws SlickException{
					achievementBox = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile("res/graphics/new/messageBox.png"){
				public void loadFile(String filename) throws SlickException{
					messageBoxImage = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile("res/graphics/new/soundOff.png"){
				public void loadFile(String filename) throws SlickException{
					soundOffImage = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile("res/graphics/new/soundOn.png"){
				public void loadFile(String filename) throws SlickException{
					soundOnImage = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile("res/graphics/new/pause.png"){
				public void loadFile(String filename) throws SlickException{
					pauseImage = new Image(filename);
				}
			});
			
			loading.add(new DeferredFile("res/graphics/new/coin.png"){
				public void loadFile(String filename) throws SlickException{
					scoreCoinImage = new Image(filename);
				}
			});
			
			
			SpriteSheet sheet = new SpriteSheet("res/graphics/explosion.png", 128, 128);
	        explosion = new Animation();
	        explosion.setAutoUpdate(true);
	        
	        int spriteNumber = 0;
	      
            for(int col=0;col<9;col++)
            {
               explosion.addFrame(sheet.getSprite(spriteNumber,0), 100);
               spriteNumber++;
            }
         
	         
			
		}
		
		
		
		//initialise the airspace object;
		//Waypoints
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
		airspace.getListOfEntryPoints().add(airspace.getAirportLeft().getEndOfRunway());
		airspace.getListOfEntryPoints().add(airspace.getAirportRight().getEndOfRunway());
		
		// Exit Points
		airspace.newExitPoint( 800,   0, "1");
		airspace.newExitPoint( 11, 200, "2");
		airspace.newExitPoint(1200, 300, "3");
		airspace.getListOfExitPoints().add(airspace.getAirportLeft().getBeginningOfRunway());
		airspace.getListOfExitPoints().add(airspace.getAirportRight().getBeginningOfRunway());
				
	    airspace.init(gc);
	}
	
	/**
	 * drawEventMessage: Draws relevant messages regarding the game state in the bottom bar.
	 */
	
	@Override
	public void drawEventMessage(GameContainer gc, Graphics g){

		boolean waitingToTakeOffAtAPL = false, waitingToTakeOffAtAPR = false, eruptionAboutToOccur  = false,
				waitingToLand = false;

		for(Flight flight: airspace.getListOfFlights()){

			if (flight.getFlightPlan().getEntryPoint() == airspace.getAirportLeft().getEndOfRunway() && !flight.isTakingOff() 
					&& flight.getAltitude() ==0){
				waitingToTakeOffAtAPL = true;
			}

			if (flight.getFlightPlan().getEntryPoint() == airspace.getAirportRight().getEndOfRunway() && !flight.isTakingOff() 
					&& flight.getAltitude() ==0){
				waitingToTakeOffAtAPR = true;
			}

			if ((flight.getFlightPlan().getCurrentRoute().get(0) == airspace.getAirportRight().getBeginningOfRunway() || 
					flight.getFlightPlan().getCurrentRoute().get(0) == airspace.getAirportLeft().getBeginningOfRunway()) &&
					!flight.isLanding()){
				waitingToLand = true;
			}




		}

		if(airspace.getEventController().getVolcano().getCountdownTillNextEruption() < 600){
			eruptionAboutToOccur = true;
		}

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
	
	/**
	 * render: Render all of the graphics in the PlayCoopState class
	 * @param g Graphics
	 * @param gc GameContainer
	 * 
	 * @throws SlickException
	 */
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		gameplayMusic.setVolume(1);
		g.setAntiAlias(true);
		
		
		
		// Checks whether the user is still choosing the difficulty
		
		if(settingDifficulty){

			int posX = Mouse.getX();
			int posY= stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();
				//Fixing posY to reflect graphics coords

			difficultyBackground.draw(0,0);
			

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
		
		else{	//main game
			//set font for the rest of the render
			g.setFont(font);
			
			// Drawing Side Images
			
			
			
			if(countdownToLightReduction != 0){
				countdownToLightReduction --;
			}
			else{
				
				if(red == 40){
					increasingBrightness = true;	
				}
				
				else if(red == 255){
					increasingBrightness = false;
				}
				
				

				if(increasingBrightness){
					red ++;
					blue ++;
					green ++;
				}
				else{
					red --;
					blue --;
					green --;
				}
				
				
				brightness = new Color( red , blue, green);
				countdownToLightReduction = 40;
			}
			
			
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
			
			// Drawing Score
			scoreCoinImage.draw(90, 573);
			g.drawString(airspace.getScore().toString(), 110, 570);
			if (airspace.getScore().getCurrentMultiplier() != 1){
				g.drawString("x" + String.valueOf(airspace.getScore().getCurrentMultiplier()), 200,570);
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
						
	
			

			
			// Multiplier
			if(airspace.getScore().getProgressionTowardsNextMultiplier() != 0){
				
				this.targetCoord = 600 - (float) (0.6 * airspace.getScore().getProgressionTowardsNextMultiplier()); 
				
				if (this.currentCoord != this.targetCoord && airspace.getScore().getNegMult() == false){
					g.setColor(Color.cyan);
					g.fillRect(0, this.currentCoord, 11, (600-this.currentCoord));
					g.setColor(Color.white);
					this.currentCoord --;
				}
				else if (this.currentCoord != this.targetCoord && airspace.getScore().getNegMult() == true){
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
				
				achievementBox.draw(900, 0);
				
				g.drawString(airspace.getScore().scoreAchievement(), 
						stateContainer.Game.MAXIMUMWIDTH -font.getWidth(airspace.getScore().scoreAchievement()) -10, 30);
				g.drawString(achievementMessage, 
						stateContainer.Game.MAXIMUMWIDTH -10 -font.getWidth(achievementMessage), 40);
				synch --;
			
				if (synch == 0){
				airspace.getScore().getAchievements().setAchievementGained(false);
				synch = 180;
				}
			}
		}	
		
		
	}
	
	/**
	 * update: Update all logic in the PlayCoopState class
	 * @param gc GameContainer
	 */

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		 /* Get mouse position */
		int posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();

		/* Get user input */
		Input input = gc.getInput();
		

		// Checks if the game has been retried and if it has resets the airspace

		if (((Game)sbg).isGameEndedCoop()){
			airspace.resetAirspace();
			airspace.init(gc);
	    	time = 0;
	    	/* Lights up for next game session */
			red = 255;
			blue = 255;
			green = 255;
			brightness = new Color( red , blue, green);
			countdownToLightReduction = 40;

	    	((Game)sbg).setGameEndedCoop(false); 
	    	settingDifficulty = true;

	    	airspace.getScore().resetScore();
		}



		// Checks whether the user is still choosing the difficulty

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

		else{	//main game

			// Updating Clock and Time

			time += delta;
			achievement.timeAchievement((int) time);
			float decMins=time/1000/60;
			int mins = (int) decMins;
			float decSecs=decMins-mins;

			int secs = Math.round(decSecs*60);

			String stringMins="";
			String stringSecs="";
			if(secs>=60){
				secs -= 60;
				mins+=1;
				// {!} should do +60 score every minute(possibly) 
				//     - after 3 minutes adds on 2 less points every time?
				airspace.getScore().updateTimeScore();
			}
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
			
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                /* If user pressed pause */
				if((posX>1120&&posX<1165) && (posY>565&&posY<600)) {
					sbg.enterState(stateContainer.Game.PAUSECOOPSTATE);				
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



			// Updating Airspace

			airspace.newCoopFlight(gc);
			airspace.update(gc);
			if (airspace.getSeparationRules().getGameOverViolation() == true){
				achievement.crashAchievement();
				airspace.getSeparationRules().setGameOverViolation(false);
				gameplayMusic.stop();
				endOfGameSound.play();
				gameEnded = true;
				
				/* Set the achievements */
				((Game)sbg).setAchievements(airspace.getScore().getAchievements());
				((Game)sbg).setCurrentScore(airspace.getScore( ).getCurrentScore());


				
				/* Switch states to Game Over screen */
				sbg.enterState(stateContainer.Game.GAMEOVERCOOPSTATE);
				currentCoord = 600;
				targetCoord = 600;
				synch = 180;

			}					


			// Checking For Pause Screen requested in game

			if (input.isKeyPressed(Input.KEY_P)) {
				sbg.enterState(stateContainer.Game.PAUSECOOPSTATE);
			}			

			if (!gameplayMusic.playing() && (!musicPaused)){
				//Loops gameplay music based on random number created in init

				gameplayMusic.loop(1.0f, 0.5f);
			}			
		}
	}


	@Override
	public int getID() {
		return stateContainer.Game.PLAYCOOPSTATE;
	}

	public Airspace getAirspace() {
		return airspace;
	}

	public void setAirspace(AirspaceCoop airspace) {
		this.airspace = airspace;
	}
}
