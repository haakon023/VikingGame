package group22.viking.game.view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import group22.viking.game.models.Assets;


public class ViewComponentFactory {

    //TODO:
    // someone turn this into a proper factory pattern...


    public ImageButton createImageButton (
            TextureRegionDrawable profileTextureRegionDrawable,
            Vector2 position,
            Vector2 size)
    {
        ImageButton button = new ImageButton(profileTextureRegionDrawable);

        button.setSize(size.x, size.y);
        button.setPosition(position.x, position.y);

        return button;
    }

    public TextButton createTextButton(String text, Vector2 position, Vector2 size) {
        TextButton textButton = new TextButton(text, initSkin(), "default");
        textButton.setSize(size.x, size.y);
        textButton.setPosition(position.x, position.y);

        return textButton;
    }

    public TextField createTextField(String text, Vector2 position, Vector2 size) {
        TextField textField = new TextField(text, initSkin(), "default");
        textField.setSize(size.x, size.y);
        textField.setPosition(position.x, position.y);

        return textField;
    }

    private Skin initSkin() {
        Skin skin = new Skin();
        skin.addRegions(Assets.getTextureAtlas(Assets.UI_SKIN));
        skin.add("default-font", Assets.FONT48); //add font as default-font in json file
        skin.load(Gdx.files.internal("ui/uiskin.json"));
        return skin;
    }




}
