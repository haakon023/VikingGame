package group22.viking.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import group22.viking.game.controller.firebase.GameCollection;
import group22.viking.game.controller.firebase.FirebaseInterface;
import group22.viking.game.controller.firebase.LobbyCollection;
import group22.viking.game.controller.firebase.ProfileCollection;
import group22.viking.game.controller.states.SplashState;
import group22.viking.game.models.Assets;


public class VikingGame extends Game {

	private OrthographicCamera camera;
	private SpriteBatch batch;

	public static float SCREEN_WIDTH;
	public static float SCREEN_HEIGHT;

	public GameStateManager gsm;			//TODO: or private?

	private final ProfileCollection profileCollection;
	private final GameCollection gameCollection;
	private final LobbyCollection lobbyCollection;
	// TODO more collections

	public static VikingGame instance;
	
	public VikingGame(FirebaseInterface firebaseInterface, Preferences preferences) {
		this.gameCollection = new GameCollection(firebaseInterface);
		this.profileCollection = new ProfileCollection(firebaseInterface, preferences);
		this.lobbyCollection = new LobbyCollection(firebaseInterface);
		
		instance = this;
	}

	@Override
	public void create () {
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT);
		batch = new SpriteBatch();

		gsm = GameStateManager.getInstance();
		gsm.push(new SplashState(this));

		// TODO: Remove Test / example
		System.out.println(Assets.LANGUAGE.get("app_name"));
	}


	@Override
	public void render () {
		// first, update the data
		//gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(Gdx.graphics.getDeltaTime());
		// then render the screen via PlayerStatus
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

	public GameCollection getGameCollection() {
		return gameCollection;
	}

	public LobbyCollection getLobbyCollection() {
		return lobbyCollection;
	}

	public ProfileCollection getProfileCollection() {
		return profileCollection;
	}
}
