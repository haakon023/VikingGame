package group22.viking.game.view.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;

public class CustomTextButton extends TextButton {

    public static BitmapFont font48;

    private enum ListenerType {
        CHANGE_SCREEN,
        SUBMIT,
        MUTE,
        PROFILE_IMAGE,
        EXIT
    }

    public CustomTextButton(String text, Vector2 position, Vector2 size) {
        super(text, initSkin(), "default");
        this.setSize(size.x, size.y);
        this.setPosition(position.x, position.y);
        this.addAction(
                sequence(alpha(0),
                parallel(fadeIn(0.5f) //,
                //moveBy(0,-20,.5f, Interpolation.pow5Out)
                )));
    }

    private static Skin initSkin() {
        Skin skin = new Skin();
        skin.addRegions(Assets.getTextureAtlas(Assets.UI_SKIN));
        skin.add("default-font", VikingGame.font48);
        // skin.add("default-font", Assets.FONT48); //add font as default-font in json file
        skin.load(Gdx.files.internal("ui/uiskin.json"));
        return skin;
    }


}
