package group22.viking.game.controller.states;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;
import group22.viking.game.view.InformationOverlayView;
import group22.viking.game.view.SoundManager;

public abstract class AbstractInformationOverlayState extends State{

    protected AbstractInformationOverlayState(VikingGame game) {
        super(Assets.informationOverlayView, game);
        SoundManager.mumbleSound();
    }

    protected InformationOverlayView getView() {
        return (InformationOverlayView) view;
    }

    @Override
    public void dispose() {
        removeAllNonDefaultListeners(getView().getConfirmButton());
    }
}
