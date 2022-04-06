package group22.viking.game.controller.states;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.ProfileSettingsView;


public class ProfileSettingsState extends State {

    public ProfileSettingsState(final VikingGame game) {
        super(new ProfileSettingsView(game.getBatch()), game);
    }


    @Override
    protected void handleInput() {

    }

    /*@Override
    public void update(float dt) {

    }*/


    @Override
    public void render(float deltaTime) {

    }

    @Override
    public void dispose() {

    }



}
