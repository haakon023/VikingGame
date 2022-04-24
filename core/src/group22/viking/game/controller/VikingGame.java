package group22.viking.game.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import group22.viking.game.controller.firebase.PlayerStatusCollection;
import group22.viking.game.controller.firebase.FirebaseInterface;
import group22.viking.game.controller.firebase.LobbyCollection;
import group22.viking.game.controller.firebase.ProfileCollection;
import group22.viking.game.controller.states.SplashState;
import group22.viking.game.models.Assets;


public class VikingGame extends Game {

	public static final String PREFERENCES_PROFILE_KEY = "local-profile-id";
	public static final String PREFERENCES_SOUND_KEY = "sound_preference";

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Preferences preferences;


	public float SCREEN_WIDTH;
	public float SCREEN_HEIGHT;

	public GameStateManager gsm;			//TODO: or private?

	private final ProfileCollection profileCollection;
	private final PlayerStatusCollection playerStatusCollection;
	private final LobbyCollection lobbyCollection;
	// TODO more collections

	private boolean isOnline;

	public static Logger logger;

	private static VikingGame instance;

	public static VikingGame getInstance(FirebaseInterface firebaseInterface, Preferences preferences) {
		if(instance == null) {
			instance = new VikingGame(firebaseInterface, preferences);
		}
		return instance;
	}

	public static VikingGame getInstance() {
		return instance;
	}
	
	private VikingGame(FirebaseInterface firebaseInterface, Preferences preferences) {
		this.preferences = preferences;
		this.playerStatusCollection = new PlayerStatusCollection(firebaseInterface);
		this.profileCollection = new ProfileCollection(firebaseInterface);
		this.lobbyCollection = new LobbyCollection(firebaseInterface);

		this.isOnline = firebaseInterface.isOnline();

		instance = this;

		logger = Logger.getLogger(VikingGame.class.getName());

		logger.log(Level.INFO, "online: " + isOnline);
	}

	@Override
	public void create () {
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		batch = new SpriteBatch();

		gsm = GameStateManager.getInstance();
		gsm.push(new SplashState(this));
	}



	@Override
	public void render () {
		gsm.render(Gdx.graphics.getDeltaTime());
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

	public PlayerStatusCollection getPlayerStatusCollection() {
		return playerStatusCollection;
	}

	public LobbyCollection getLobbyCollection() {
		return lobbyCollection;
	}

	public ProfileCollection getProfileCollection() {
		return profileCollection;
	}

	public Preferences getPreferences() {
		return preferences;
	}


}
