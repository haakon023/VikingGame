package group22.viking.game.view.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 *  Common combinations: (48, 5) for nearly everything; (100, 10) for lobby score
 *
 */
public class Font extends BitmapFont {

    public BitmapFont font;

    private static FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto.ttf"));
    private static FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    public Font(int fontSize, int letterSpacing) {
        parameter.size = fontSize;
        parameter.color = Color.WHITE;
        parameter.spaceX = letterSpacing; //letter spacing

        font = generator.generateFont(parameter);
    }


}