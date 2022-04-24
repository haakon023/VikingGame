package group22.viking.game.view;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import group22.viking.game.controller.GameStateManager;

public class ErrorDialog extends Dialog {

    private boolean popState;

    public ErrorDialog(String title, String content, Skin skin) {
        super(title, skin);
        text("\n  " + content + "  \n");
        button("\n    O.K.    \n");
        this.popState = false;
    }

    @Override
    protected void result(Object object) {
        if(popState) {
            GameStateManager.getInstance().pop();
        }
    }

    public ErrorDialog popState() {
        this.popState = true;
        return this;
    }
}
