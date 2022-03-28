package group22.viking.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import group22.viking.game.controller.states.LeaderboardState;
import group22.viking.game.controller.states.LoadingState;
import group22.viking.game.controller.states.LobbyState;
import group22.viking.game.controller.states.MenuState;
import group22.viking.game.controller.states.PlayState;
import group22.viking.game.controller.states.ProfileSettingsState;
import group22.viking.game.controller.states.SplashState;

public class VikingGame extends Game {

	public OrthographicCamera camera;
	public SpriteBatch batch;

	public static float SCREEN_WIDTH;
	public static float SCREEN_HEIGHT;

	public AssetManager assets;

	//font
	public BitmapFont font48;
	public BitmapFont font100;

	//Screens to dispose of them
	public SplashState splashState;
	public LoadingState loadingState;
	public MenuState menuState;
	public PlayState playState;
	public LobbyState lobbyState;
	public ProfileSettingsState profileSettingsState;
	public LeaderboardState leaderboardState;


	@Override
	public void create () {
		assets = new AssetManager();
		SCREEN_WIDTH=Gdx.graphics.getWidth();
		SCREEN_HEIGHT=Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT);
		batch = new SpriteBatch();

		initFonts();


		splashState = new SplashState(this);
		loadingState = new LoadingState(this);
		menuState = new MenuState(this);
		playState = new PlayState(this);
		lobbyState = new LobbyState(this);
		profileSettingsState = new ProfileSettingsState(this);
		leaderboardState = new LeaderboardState(this);

		//FIRST SCREEN WHEN STARTING APP
		this.setScreen(splashState);

		//gsm = new GameStateManager();
		//gsm.push(new MenuState(gsm));
		//Gdx.gl.glClearColor(0.34f, 0.44f, 0.53f, 1);
	}


	@Override
	public void render () {
		super.render();
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assets.dispose();
		splashState.dispose();
		loadingState.dispose();
		menuState.dispose();

	}

	private void initFonts() {

		//font48
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		//customizing fonts
		parameter.size = 48;
		parameter.color = Color.WHITE;
		parameter.spaceX = 5; //letter spacing
		font48 = generator.generateFont(parameter);


		//font100
		FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		//customizing fonts
		parameter2.size = 100;
		parameter2.color = Color.WHITE;
		parameter2.spaceX = 10; //letter spacing

		font100 = generator2.generateFont(parameter2);


	}



}
