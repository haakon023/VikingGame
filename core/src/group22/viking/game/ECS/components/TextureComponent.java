package group22.viking.game.ECS.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import group22.viking.game.controller.VikingGame;

public class TextureComponent implements Component {
    public static float RENDER_SCALE = VikingGame.SCREEN_WIDTH / 3000F; // 3000 is with of background
    public TextureRegion textureRegion = null;

    public TextureComponent setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        return this;
    }
}
