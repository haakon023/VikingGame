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
import group22.viking.game.models.Assets;

public class VikingGame extends Game {

	private OrthographicCamera camera;
	private SpriteBatch batch;

	public static float SCREEN_WIDTH;
	public static float SCREEN_HEIGHT;

	public GameStateManager gsm;

	public static BitmapFont font48;		//TODO: JUST FOR TESTING PURPOSES


	@Override
	public void create () {
		SCREEN_WIDTH=Gdx.graphics.getWidth();
		SCREEN_HEIGHT=Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT);
		batch = new SpriteBatch();

		initFont();

		gsm = GameStateManager.getInstance(this);
		gsm.push(new SplashState(this));
	}

	//TODO: JUST FOR TESTING PURPOSES:
	public void initFont() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		//customizing fonts
		parameter.size = 48;
		parameter.color = Color.WHITE;
		parameter.spaceX = 5; //letter spacing
		font48 = generator.generateFont(parameter);
	}


	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		Assets.dispose();
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
}
