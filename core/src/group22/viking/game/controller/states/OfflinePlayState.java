package group22.viking.game.controller.states;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;

public class OfflinePlayState extends AbstractPlayState{

    public Integer popUpCount;

    public OfflinePlayState(VikingGame game, Type type, Integer popUpCount) {
        super(game, type);
        this.popUpCount = popUpCount;
    }

    @Override
    public void handleLocalDeath() {
        // GameStateManager.getInstance().pop();
        GameStateManager.getInstance().set(new GameOverState(game));
    }
}
