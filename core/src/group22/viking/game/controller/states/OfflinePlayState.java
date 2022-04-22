package group22.viking.game.controller.states;

import group22.viking.game.controller.VikingGame;

public class OfflinePlayState extends AbstractPlayState{

    public Integer popUpCount;

    public OfflinePlayState(VikingGame game, Type type, Integer popUpCount) {
        super(game, type);
        this.popUpCount = popUpCount;
    }
}
