package group22.viking.game.controller.states;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;

public class GameOverState extends AbstractInformationOverlayState{

    protected GameOverState(VikingGame game, boolean win) {
        super(game);
        setViewTexts(win);
    }

    private void setViewTexts(boolean win) {
        getView().setTexts(
                Assets.t(win ? "game_over_title_win" : "game_over_title_lost"),
                Assets.t(win ? "game_over_content_win" : "game_over_content_lost")
        );
    }
}
