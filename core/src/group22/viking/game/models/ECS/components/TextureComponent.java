package group22.viking.game.models.ECS.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.ECS.systems.RenderingSystem;

public class TextureComponent implements Component {
    public static float RENDER_SCALE = VikingGame.getInstance().SCREEN_WIDTH / 3000F; // 3000 is width of background
    public TextureRegion textureRegion = null;

    public TextureComponent init(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        return this;
    }
}
