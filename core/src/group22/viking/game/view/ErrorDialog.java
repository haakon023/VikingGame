package group22.viking.game.view;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import group22.viking.game.models.Assets;

public class ErrorDialog extends Dialog {

    public ErrorDialog(String title, Skin skin) {
        super(title, skin);
    }

    public ErrorDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    public ErrorDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    {
        text("\n   " + Assets.t("error_dialog_line1") + "   " +
                "\n   " + Assets.t("error_dialog_line2") + "   " +
                "\n   " + Assets.t("error_dialog_mail") + "   " +
                "\n");
        button("\n    " + Assets.t("error_dialog_confirmation") + "    \n");

        setSize(4000,4000);
    }

    @Override
    protected void result(Object object) {

    }



}
