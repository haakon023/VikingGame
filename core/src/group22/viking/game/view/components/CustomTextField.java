package group22.viking.game.view.components;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;

public class CustomTextField extends TextField {
    public CustomTextField(String text, Vector2 position, Vector2 size) {
        super(text, initSkin());
        this.setWidth(size.x);
        this.setHeight(size.y);
        this.setPosition(position.x, position.y);

        this.addAction(sequence(
                alpha(0),
                parallel(fadeIn(0.5f) //,
                // moveBy(0,-20,.5f, Interpolation.pow5Out)
        )));
    }


    private static Skin initSkin() {
        Skin skin = new Skin();
        skin.addRegions(Assets.getTextureAtlas(Assets.UI_SKIN));
        // skin.add("default-font", VikingGame.font48);
        skin.add("default-font", Assets.FONT48); //add font as default-font in json file
        skin.load(Gdx.files.internal("ui/uiskin.json"));
        return skin;
    }
}
