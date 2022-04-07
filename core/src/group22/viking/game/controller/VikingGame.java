package group22.viking.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import group22.viking.game.controller.firebase.FirebaseGameCollection;
import group22.viking.game.controller.firebase.FirebaseInterface;
import group22.viking.game.controller.firebase.FirebaseProfileCollection;
import group22.viking.game.controller.states.PlayState;
import group22.viking.game.controller.states.SplashState;
import group22.viking.game.models.Assets;

public class VikingGame extends Game {

	private OrthographicCamera camera;
	private SpriteBatch batch;

	public static float SCREEN_WIDTH;
	public static float SCREEN_HEIGHT;

	public GameStateManager gsm;			//TODO: or private?

	private FirebaseProfileCollection firebaseProfileCollection;
	private FirebaseGameCollection firebaseGameCollection;
	// TODO more collections

	public static VikingGame instance;
	
	public VikingGame(FirebaseInterface firebaseInterface) {
		this.firebaseGameCollection = new FirebaseGameCollection(firebaseInterface);
		this.firebaseProfileCollection = new FirebaseProfileCollection(firebaseInterface);
		// TODO more collections
		
		instance = this;
	}

	@Override
	public void create () {
		SCREEN_WIDTH=Gdx.graphics.getWidth();
		SCREEN_HEIGHT=Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT);
		batch = new SpriteBatch();

		gsm = GameStateManager.getInstance(this);
		gsm.push(new SplashState(this));

		// gsm.push(new MenuState(gsm,
		//		firebaseProfileCollection,
		//		firebaseGameCollection));
		
		// Test Firestore:
		// new MenuState(gsm, firebaseProfileCollection, firebaseGameCollection).testFirestore();

		// TODO: Remove Test / example
		System.out.println(Assets.LANGUAGE.get("app_name"));
	}


	@Override
	public void render () {
		// first, update the data
		//gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(Gdx.graphics.getDeltaTime());
		// then render the screen via Game
		// super.render();
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
