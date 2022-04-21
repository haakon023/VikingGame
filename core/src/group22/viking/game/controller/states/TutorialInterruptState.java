package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.SoundManager;
import group22.viking.game.view.TutorialInterruptView;

public class TutorialInterruptState extends State {

    public TutorialInterruptState(VikingGame game, Integer popUpCount) {
        super(new TutorialInterruptView(game.getBatch(), game.getCamera(), popUpCount), game);
        Gdx.input.setInputProcessor(view.getStage());
        addListenersToButtons();
        SoundManager.mumbleSound(getGame().getPreferences());
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

    @Override
    public void dispose() {

    }

    private TutorialInterruptView getView() {
        return (TutorialInterruptView) view;
    }

}




