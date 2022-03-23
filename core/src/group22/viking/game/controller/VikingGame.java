package group22.viking.game.controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import group22.viking.game.controller.firebase.FirebaseInterface;
import group22.viking.game.controller.states.MenuState;

public class VikingGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private GameStateManager gsm;
	private I18NBundle language;
	FirebaseInterface _FBIC;

	public VikingGame(FirebaseInterface FBIC) { _FBIC = FBIC; }

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		gsm.push(new MenuState(gsm));

		// test Firebase:
		_FBIC.someFunction();
		_FBIC.FirstFireBaseTest();
		_FBIC.SetOnValueChangedListener();
		_FBIC.SetValueInDb("message", "this is new text");
		_FBIC.SetValueInDb("message2", "wow, create a new one");

		// create language bundle
		// Locale locale = new Locale(Locale.getDefault().getLanguage() , Locale.getDefault().getCountry());
		language = I18NBundle.createBundle(Gdx.files.internal("i18n/app"), Locale.getDefault());

		// TODO: Remove Test / example
		System.out.println(language.get("app_name"));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
