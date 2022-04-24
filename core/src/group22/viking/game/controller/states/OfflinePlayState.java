package group22.viking.game.controller.states;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;

public class OfflinePlayState extends AbstractPlayState{

    public int popUpCount;

    public OfflinePlayState(VikingGame game, Type type) {
        super(game, type);
    }

    @Override
    public void handleLocalDeath() {
        GameStateManager.getInstance().set(new GameOverState(game, false));
    }
}
