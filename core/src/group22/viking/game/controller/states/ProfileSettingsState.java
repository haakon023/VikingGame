package group22.viking.game.controller.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.ProfileScreen;


public class ProfileSettingsState extends State {

    public ProfileSettingsState(final VikingGame game) {
        super(new ProfileScreen(game));
    }


    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }


    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {

    }



}
