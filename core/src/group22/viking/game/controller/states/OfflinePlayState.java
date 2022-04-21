package group22.viking.game.controller.states;

import group22.viking.game.controller.VikingGame;

public class OfflinePlayState extends AbstractPlayState{

    public Integer popUpCount;

    public enum Type {
        TUTORIAL,
        PRACTICE
    }

    private Type type;

    public OfflinePlayState(VikingGame game, Type type, Integer popUpCount) {
        super(game);
        this.type = type;
        this.popUpCount = popUpCount;
    }
}
