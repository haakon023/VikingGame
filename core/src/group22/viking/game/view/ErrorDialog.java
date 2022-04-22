package group22.viking.game.view;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import group22.viking.game.models.Assets;

public class ErrorDialog extends Dialog {

    public ErrorDialog(String title, String content, Skin skin) {
        super(title, skin);
        text("\n  " + content + "  \n");
        button("\n    O.K.    \n");
    }

    @Override
    protected void result(Object object) {

    }



}
