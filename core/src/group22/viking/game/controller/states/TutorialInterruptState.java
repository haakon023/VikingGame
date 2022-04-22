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

    public TutorialInterruptState(VikingGame game, int popUpCount) {
        super(game);
        System.out.println("TUTORIAL INTERRUPTION STATE: " + popUpCount);
        setViewTexts(popUpCount);
    }

    private void setViewTexts(int popUpCount) {
        getView().setTexts(
                Assets.t("tutorial_header" + popUpCount),
                Assets.t("tutorial_content" + popUpCount)
        );
    }

}




