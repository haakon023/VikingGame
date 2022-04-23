package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;
import group22.viking.game.view.InformationOverlayView;
import group22.viking.game.view.SoundManager;

public abstract class AbstractInformationOverlayState extends State{

    protected ClickListener confirmButtonClickListener;

    protected AbstractInformationOverlayState(VikingGame game) {
        super(Assets.informationOverlayView, game);
        //addListenersToButtons();
        Gdx.input.setInputProcessor(view.getStage());
        SoundManager.mumbleSound(getGame().getPreferences());
    }

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

    protected InformationOverlayView getView() {
        return (InformationOverlayView) view;
    }

    @Override
    public void dispose() {
        super.dispose();
        if(confirmButtonClickListener != null){
            getView().getConfirmButton().removeListener(confirmButtonClickListener);
        }

    }
}
