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
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.firebase.FirebaseDocument;
import group22.viking.game.controller.firebase.OnCollectionUpdatedListener;
import group22.viking.game.controller.firebase.Profile;
import group22.viking.game.controller.firebase.ProfileCollection;
import group22.viking.game.models.Assets;
import group22.viking.game.view.LeaderboardView;
import group22.viking.game.view.SoundManager;
import group22.viking.game.view.TutorialInterruptView;

public class TutorialInterruptState extends State {


    public TutorialInterruptState(VikingGame game) {
        super(new TutorialInterruptView(game.getBatch(), game.getCamera()), game);
        Gdx.input.setInputProcessor(view.getStage());
        addListenersToButtons();
        SoundManager.mumbleSound(getGame().getPreferences());
    }

    @Override
    protected void handleInput() {

    }


    public void update(float dt) {

    }

    private TutorialInterruptView getView() {
        return (TutorialInterruptView) view;
    }

    private void addListenersToButtons() {
        getView().getConfirmButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.buttonClickSound(getGame().getPreferences());
                GameStateManager.getInstance().pop();
            }
        });


    }
}




