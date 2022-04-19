package group22.viking.game;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.lang.annotation.Target;

import group22.viking.game.controller.VikingGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// In KITKAT (4.4) and next releases, hide the virtual buttons
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			hideVirtualButtons();
			hideActionBar();
		}

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new VikingGame(new AndroidInterfaceClass(), this.getPreferences("my-config")), config);
	}

	@TargetApi(19)
	private void hideVirtualButtons() {
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
						| View.SYSTEM_UI_FLAG_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
	}

	@TargetApi(16)
	private void hideActionBar() {
		// Hide the action bar: https://developer.android.com/training/system-ui/status#java
		ActionBar actionBar = getActionBar();
		actionBar.hide();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			// In KITKAT (4.4) and next releases, hide the virtual buttons
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				hideVirtualButtons();
				hideActionBar();
			}
		}
	}
}
