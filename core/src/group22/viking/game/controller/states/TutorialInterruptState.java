package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;
import group22.viking.game.view.SoundManager;
import group22.viking.game.view.InformationOverlayView;


public class TutorialInterruptState extends AbstractInformationOverlayState {

    public TutorialInterruptState(VikingGame game, Integer popUpCount) {
        super(game);
        Gdx.input.setInputProcessor(view.getStage());
        addListenersToButtons();
        setViewTexts(popUpCount);
    }

    private void addListenersToButtons() {
        getView().getConfirmButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                SoundManager.buttonClickSound();
                GameStateManager.getInstance().pop();
            }
        });
    }

    private void setViewTexts(int popUpCount) {
        getView().setTexts(
                Assets.t("tutorial_header" + popUpCount),
                Assets.t("tutorial_content" + popUpCount)
        );
    }


}
