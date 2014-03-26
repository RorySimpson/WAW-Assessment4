package competitive;

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




import states.PlayState;
import util.DeferredFile;

public class PlayCompetitiveState extends PlayState {


	private AirspaceCompetitive airspace;
	private boolean explosionOccurs = false;


	public PlayCompetitiveState(int state) {
		super(state);
		achievement = new Achievements();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		gameEnded = false;
		settingDifficulty = true;
		time = 0;
		airspace = new AirspaceCompetitive();
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



		//EntryPoints
		airspace.newEntryPoint(11, 200);
		airspace.newEntryPoint(11, 400);
		
		airspace.init(gc);
	}

	@Override
	public void drawEventMessage(GameContainer gc, Graphics g){

		boolean waitingToTakeOffAtAPL = false, waitingToTakeOffAtAPR = false, eruptionAboutToOccur  = false,
				waitingToLand = false;

		for(Flight flight: airspace.getListOfFlights()){


			if (flight.getFlightPlan().getEntryPoint() == airspace.getAirportRight().getEndOfRunway() && !flight.isTakingOff() 
					&& flight.getAltitude() ==0){
				waitingToTakeOffAtAPR = true;
			}

			if (flight.getFlightPlan().getCurrentRoute().get(0) == airspace.getAirportRight().getBeginningOfRunway()  &&
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

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		gameplayMusic.setVolume(1);
		g.setAntiAlias(true);



		// Checks whether the user is still choosing the difficulty



		//main game
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

		if (gameJustFinished)
		{	
			explosion.draw((float)airspace.getSeparationRules().getPointOfCrash().getX() -50, (float)airspace.getSeparationRules().getPointOfCrash().getY() -90 );
		}
		

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






	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		// Checks if the game has been retried and if it has resets the airspace

		if (gameEnded){

			airspace.resetAirspace();
			time = 0;
			gameEnded = false;
			airspace.getScore().resetScore();
		}


		//main game

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


		// Updating Airspace

		airspace.newCompetitiveFlight(gc);
		airspace.update(gc);
		if (airspace.getSeparationRules().getGameOverViolation() == true){
			

		}		
		
		if ((int)decMins >= 5){
			airspace.getSeparationRules().setGameOverViolation(false);
			airspace.resetAirspace();
			gameplayMusic.stop();
			gameEnded = true;
			sbg.enterState(stateContainer.Game.GAMEOVERSTATE);
			
		}

		Input input = gc.getInput();

		// Checking For Pause Screen requested in game

		if (input.isKeyPressed(Input.KEY_P)) {
			sbg.enterState(stateContainer.Game.PAUSECOOPSTATE);
		}			

		if (!gameplayMusic.playing()){
			//Loops gameplay music based on random number created in init

			gameplayMusic.loop(1.0f, 0.5f);
		}			
	}



	@Override
	public int getID() {
		return stateContainer.Game.PLAYCOMPETITIVESTATE;
	}

	public Airspace getAirspace() {
		return airspace;
	}

	public void setAirspace(AirspaceCompetitive airspace) {
		this.airspace = airspace;
	}

}