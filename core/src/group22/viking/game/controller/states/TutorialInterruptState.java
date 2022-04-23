package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;
import group22.viking.game.view.SoundManager;
import group22.viking.game.view.InformationOverlayView;


public class TutorialInterruptState extends AbstractInformationOverlayState {

    OfflinePlayState offlinePlayState;

    public TutorialInterruptState(VikingGame game, OfflinePlayState offlinePlayState, int popUpCount) {
        super(game);
        this.offlinePlayState = offlinePlayState;
        if(popUpCount < 3){
            addListenersToButtons();

        }else if(popUpCount == 3){
            System.out.println("POPUP ADDED" + popUpCount);
            showNextSlide();
        }else if(popUpCount ==4){
            System.out.println("RETURN ADDED" + popUpCount);
            returnToMenu();
        }

        setViewTexts(popUpCount);

    }

    @Override
    protected void addListenersToButtons() {
        confirmButtonClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                SoundManager.buttonClickSound(getGame().getPreferences());
                GameStateManager.getInstance().pop();
            }
        };
        getView().getConfirmButton().addListener(confirmButtonClickListener);

    }

    protected void returnToMenu() {
        confirmButtonClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                SoundManager.buttonClickSound(getGame().getPreferences());
                GameStateManager.getInstance().pop();
                GameStateManager.getInstance().pop();

            }
        };
        getView().getConfirmButton().addListener(confirmButtonClickListener);
    }

    protected void showNextSlide() {
        confirmButtonClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                SoundManager.buttonClickSound(getGame().getPreferences());
                GameStateManager.getInstance().pop();
                offlinePlayState.nextTutorialInterruption();
                offlinePlayState.popUpCount++;
            }
        };
        getView().getConfirmButton().addListener(confirmButtonClickListener);

    }

    private void setViewTexts(int popUpCount) {
        if(popUpCount <5){
            getView().setTexts(
                    Assets.t("tutorial_header" + popUpCount),
                    Assets.t("tutorial_content" + popUpCount)
            );
        }



    }

}




