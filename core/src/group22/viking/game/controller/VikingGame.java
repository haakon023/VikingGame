package group22.viking.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import group22.viking.game.controller.states.SplashState;
import group22.viking.game.models.Assets;

public class VikingGame extends Game {

	private OrthographicCamera camera;
	private SpriteBatch batch;

	public static float SCREEN_WIDTH;
	public static float SCREEN_HEIGHT;

	public GameStateManager gsm;


	@Override
	public void create () {
		SCREEN_WIDTH=Gdx.graphics.getWidth();
		SCREEN_HEIGHT=Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT);
		batch = new SpriteBatch();

		gsm = GameStateManager.getInstance(this);
		gsm.push(new SplashState(this));
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
