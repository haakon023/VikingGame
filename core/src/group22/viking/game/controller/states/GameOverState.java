package group22.viking.game.controller.states;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;
import group22.viking.game.view.SoundManager;

public class GameOverState extends AbstractInformationOverlayState{

    protected GameOverState(VikingGame game, boolean win) {
        super(game);
        setViewTexts(win);
        addListenersToButtons();
    }

    private void addListenersToButtons() {
        confirmButtonClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                SoundManager.buttonClickSound();
                GameStateManager.getInstance().pop();
            }
        };
        getView().getConfirmButton().addListener(confirmButtonClickListener);
    }

    private void setViewTexts(boolean win) {
        getView().setTexts(
                Assets.t(win ? "game_over_title_win" : "game_over_title_lost"),
                Assets.t(win ? "game_over_content_win" : "game_over_content_lost")
        );
    }
}
