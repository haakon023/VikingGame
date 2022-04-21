package group22.viking.game.controller.states;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;
import group22.viking.game.view.SoundManager;
import group22.viking.game.view.TutorialInterruptView;


public class TutorialInterruptState extends State {

    public TutorialInterruptState(VikingGame game) {
        super(new TutorialInterruptView(game.getBatch(), game.getCamera()), game);
        Gdx.input.setInputProcessor(view.getStage());
        addListenersToButtons();
    }

    @Override
    protected void handleInput() {

    }

    private void addListenersToButtons() {
        getView().getConfirmButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                SoundManager.buttonClickSound(getGame().getPreferences());
                GameStateManager.getInstance().pop();
            }
        });


    }

    public void update(float delta){

    }

    @Override
    public void dispose() {

    }

    private TutorialInterruptView getView() {
        return (TutorialInterruptView) view;
    }


}
