package group22.viking.game.controller.states;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;
import group22.viking.game.view.SoundManager;


public class TutorialInterruptState extends AbstractInformationOverlayState {

    TutorialPlayState tutorialPlayState;

    public TutorialInterruptState(VikingGame game, TutorialPlayState offlinePlayState, int popUpCount) {
        super(game);
        this.tutorialPlayState = offlinePlayState;

        setViewTexts(popUpCount);
        switch (popUpCount){
            case 0:
            case 1:
            case 2:
                addListenerPopOneState();
                break;
            case 3:
                addListenerPopAndNextTutorialInterruption();
                break;
            case 4:
                addListenerPopTwoStates();
                break;
        }
    }

    protected void addListenerPopOneState() {
        getView().getConfirmButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                SoundManager.buttonClickSound();
                GameStateManager.getInstance().pop();
            }
        });
    }

    protected void addListenerPopTwoStates() {
        getView().getConfirmButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                SoundManager.buttonClickSound();
                GameStateManager.getInstance().pop();
                GameStateManager.getInstance().pop();
            }
        });
    }

    protected void addListenerPopAndNextTutorialInterruption() {
        getView().getConfirmButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                SoundManager.buttonClickSound();
                GameStateManager.getInstance().pop();
                tutorialPlayState.nextInterruption();
                //tutorialPlayState.popUpCount++;
            }
        });
    }

    private void setViewTexts(int popUpCount) {
        if (popUpCount >= 5) return;
        getView().setTexts(
                Assets.t("tutorial_header" + popUpCount),
                Assets.t("tutorial_content" + popUpCount)
        );
    }

}




