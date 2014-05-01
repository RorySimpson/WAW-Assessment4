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





import stateContainer.Game;
import states.PlayState;
import util.DeferredFile;

public class PlayCompetitiveState extends PlayState {


	private AirspaceCompetitive airspace;
	private boolean explosionOccurs = false;
	private float decMins, decSecs, secondsOccured;
	private int secs;
	protected float time;


	public PlayCompetitiveState(int state) {
		super(state);
		achievement = new Achievements();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		// Configure variable for start of game

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
			
			
			
			// Configuring Sprite Sheet For Explosion
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

		//Adding EntryPoints
		airspace.newEntryPoint(1200, 100);
		airspace.newEntryPoint(11, 150);
		
		// Initialising the airspace
		airspace.init(gc);
		airspace.createAndSetSeparationRules();
	}



	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		
		// Configuring Music
		gameplayMusic.setVolume(1);
	
		// Set font for the rest of the render
		g.setAntiAlias(true);
		g.setFont(font);



		// Day Night Cycle Logic
		
		if(countdownToLightReduction != 0){
			countdownToLightReduction --;
		}
		else{

			if(red == 40 && blue == 40 && green == 40){
				increasingBrightness = true;	
			}

			else if(red == 255  && blue == 255 && green == 255){
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
		


		// Drawing Airspace and elements within it
		g.setColor(Color.white);
		airspace.render(g, gc);
		statusBarImage.draw(0,0);
		messageBoxImage.draw(11,560);

		// Drawing Clock and Time
		g.setColor(Color.white);
		clockImage.draw(6,565);
		g.drawString(stringTime, 31, 570);

		// Drawing Score
		g.setColor(Color.cyan);
		g.drawString("Player 1:    " + Integer.toString(airspace.getPlayer1Score()), 440, 573 );
		g.setColor(Color.red);
		g.drawString("Player 2:    " + Integer.toString(airspace.getPlayer2Score()), 650, 573 );
		g.setColor(Color.white);
		
		

		// Drawing Pause Button and Mute

		if(gameplayMusic.playing()){
			soundOnImage.draw(1160, 565);
		}
		else{
			soundOffImage.draw(1160, 565);
		}

		pauseImage.draw(1120,565);
		
		// Render Time in Multiplier Bar. Completely filled when gameover.
		g.setColor(Color.cyan);
		g.fillRect(0, 600 - 600 *(secondsOccured / 300), 11, 600 *(secondsOccured / 300));
		g.setColor(Color.white);




		Input input = gc.getInput();
		
		
		// Render Explosions
		g.setWorldClip(11, 0, Game.MAXIMUMWIDTH -11, Game.MAXIMUMHEIGHT-40);
		for (CrashCompetitive crash : airspace.getSeparationRules().getListOfActiveCrashes()){
			explosion.draw((float)crash.getPointOfCrash().getX() - 50, (float)crash.getPointOfCrash().getY() - 90 );
		}
		g.setWorldClip(0, 0, Game.MAXIMUMWIDTH, Game.MAXIMUMHEIGHT);

		

	}	


	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		 /* Get mouse position */
		int posX = Mouse.getX();
		int posY = stateContainer.Game.MAXIMUMHEIGHT -Mouse.getY();

		
		Input input = gc.getInput();

		// Checks if the game has been retried and if it has resets the airspace

		if (gameEnded){

			airspace.resetAirspace();
			airspace.init(gc);
			time = 0;
			gameEnded = false;
			airspace.getScore().resetScore();
		}


		// Updating Clock and Time

		time += delta;
		achievement.timeAchievement((int) time);
		decMins = time/1000/60;
		int mins = (int) decMins;
		decSecs=decMins-mins;
		
		secondsOccured = time/1000;
		secs = Math.round(decSecs*60);

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
		
		// Game ends after 5 minutes of playing.
		if ((int)decMins >= 5){
			
			if(airspace.getPlayer1Score() > airspace.getPlayer2Score()){
				((Game)sbg).setPreviousCompetitiveModeWinner("Player 1");
			}
			
			if(airspace.getPlayer1Score() == airspace.getPlayer2Score()){
				((Game)sbg).setPreviousCompetitiveModeWinner("Draw");
			}
			
			
			else{
				((Game)sbg).setPreviousCompetitiveModeWinner("Player 2");
			}
			
			airspace.getSeparationRules().setGameOverViolation(false);
			airspace.resetAirspace();
			gameplayMusic.stop();
			gameEnded = true;
			sbg.enterState(stateContainer.Game.GAMEOVERCOMPETITIVESTATE);
			
		}
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            /* If user pressed pause */
			if((posX>1120&&posX<1165) && (posY>565&&posY<600)) {
				sbg.enterState(stateContainer.Game.PAUSECOMPETITIVESTATE);				
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
		


		// Checking For Pause Screen requested in game

		if (input.isKeyPressed(Input.KEY_P)) {
			sbg.enterState(stateContainer.Game.PAUSECOMPETITIVESTATE);
		}			

		if (!gameplayMusic.playing()&& (!musicPaused)){
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
