package group22.viking.game.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 *  Common combinations: (48, 5) for nearly everything; (100, 10) for lobby score
 *
 */
public class FontGenerator {

    private static FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto.ttf"));
    private static FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    public static BitmapFont generateFont(int fontSize, int letterSpacing) {
        parameter.size = fontSize;
        parameter.color = Color.WHITE;
        parameter.spaceX = letterSpacing; //letter spacing

        return generator.generateFont(parameter);
    }


}