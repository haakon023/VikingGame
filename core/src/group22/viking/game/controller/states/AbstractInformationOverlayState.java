package group22.viking.game.controller.states;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;

public abstract class AbstractInformationOverlayState extends State{

    protected AbstractInformationOverlayState(VikingGame game) {
        super(Assets.informationOverlayView, game);


    }
}
