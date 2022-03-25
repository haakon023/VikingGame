package group22.viking.game.controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import group22.viking.game.controller.states.LoadingState;
import group22.viking.game.controller.states.MenuState;
import group22.viking.game.controller.states.SplashState;

public class VikingGame extends Game {

	public OrthographicCamera camera;
	public SpriteBatch batch;

	public static float SCREEN_WIDTH;
	public static float SCREEN_HEIGHT;

	public AssetManager assets;

	//Screens to dispose of them
	public SplashState splashState;
	public LoadingState loadingState;
	public MenuState menuState;


	@Override
	public void create () {
		assets = new AssetManager();
		SCREEN_WIDTH=Gdx.graphics.getWidth();
		SCREEN_HEIGHT=Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT);
		batch = new SpriteBatch();

		splashState = new SplashState(this);
		loadingState = new LoadingState(this);
		menuState = new MenuState(this);


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
}
