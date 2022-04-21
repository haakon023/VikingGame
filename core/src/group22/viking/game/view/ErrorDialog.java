package group22.viking.game.view;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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
        text("\n   An unexpected error has occurred.   " +
                "\n   Kindly contact support under:   " +
                "\n   alf.inge.wang@ntnu.no   " +
                "\n");
        button("\n    O.K.    \n");

        setSize(4000,4000);


    }

    @Override
    protected void result(Object object) {

    }



}
