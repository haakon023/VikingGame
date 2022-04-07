package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.ProfileSettingsView;


public class ProfileSettingsState extends State {

    public ProfileSettingsState(final VikingGame game) {
        super(new ProfileSettingsView(game.getBatch(), game.getCamera()), game);
        Gdx.input.setInputProcessor(view.getStage());
        addListenersToButtons();

        System.out.println("PROFILE STATE CREATED");
    }


    @Override
    protected void handleInput() {

    }

    public void update(float delta){

    }

    @Override
    public void dispose() {

    }

    private void addListenersToButtons() {
        ((ProfileSettingsView) view).getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                System.out.println("EXIT BUTTON CLICKED");
                GameStateManager.getInstance(game).pop();
            }
        });


    }



}
