package group22.viking.game.view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import group22.viking.game.models.Assets;


public class ViewComponentFactory {

    //TODO:
    // someone turn this into a proper factory pattern...

    private final static FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto.ttf"));
    private final static FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    public static final Vector2 VERY_SMALL_BUTTON_SIZE = new Vector2(120, 120);
    public static final Vector2 SMALL_BUTTON_SIZE = new Vector2(150, 150);
    public static final Vector2 BIG_BUTTON_SIZE =  new Vector2(700, 150);
    public static final Vector2 PROFILE_IMAGE_SIZE = new Vector2(500, 500);

    public static ImageButton createImageButton (
            TextureRegionDrawable profileTextureRegionDrawable,
            Vector2 position,
            Vector2 size)
    {
        ImageButton button = new ImageButton(profileTextureRegionDrawable);

        button.setSize(size.x, size.y);
        button.setPosition(position.x, position.y);

        return button;
    }

    public static ErrorDialog createErrorDialog(){
        return new ErrorDialog("", createSkin48());
    }

    public static TextButton createTextButton(String text, Vector2 position, Vector2 size) {
        TextButton textButton = new TextButton(text, createSkin48(), "default");
        textButton.setSize(size.x, size.y);
        textButton.setPosition(position.x, position.y);

        return textButton;
    }

    public static TextField createTextField(String text, Vector2 position, Vector2 size) {
        TextField textField = new TextField(text, createSkin48(), "default");
        textField.setSize(size.x, size.y);
        textField.setPosition(position.x, position.y);

        return textField;
    }

    public static Label createLabel48(String text, Vector2 position) {
        Label label = new Label(text, createSkin48());
        label.setPosition(position.x, position.y);

        return label;
    }

    public static Label createLabel100(String text, Vector2 position) {
        Label label = new Label(text, createSkin100());
        label.setPosition(position.x, position.y);

        return label;
    }

    private static Skin createSkin48() {
        Skin skin = new Skin(Assets.getTextureAtlas(Assets.UI_SKIN));
        skin.add("default-font", Assets.FONT48); //add font as default-font in json file
        skin.load(Gdx.files.internal("ui/uiskin.json"));

        return skin;
    }

    private static Skin createSkin100() {
        Skin skin = new Skin(Assets.getTextureAtlas(Assets.UI_SKIN));
        skin.add("default-font", Assets.FONT100); //add font as default-font in json file
        skin.load(Gdx.files.internal("ui/uiskin.json"));

        return skin;
    }

    private static Skin createSkin28() {
        Skin skin = new Skin(Assets.getTextureAtlas(Assets.UI_SKIN));
        skin.add("default-font", Assets.FONT28); //add font as default-font in json file
        skin.load(Gdx.files.internal("ui/uiskin.json"));

        return skin;
    }


    public static Action createFadeInAction() {
        return sequence(
                alpha(0),
                moveBy(0,20,0f, Interpolation.linear),
                parallel(
                        fadeIn(.5f),
                        moveBy(0,-20,.5f, Interpolation.pow5Out)
                )
        );
    }

    public static BitmapFont generateFont(int fontSize, int letterSpacing) {
        parameter.size = fontSize;
        parameter.color = Color.WHITE;
        parameter.spaceX = letterSpacing; //letter spacing

        return generator.generateFont(parameter);
    }



}
