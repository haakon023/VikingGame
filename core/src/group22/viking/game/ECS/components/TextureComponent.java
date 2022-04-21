package group22.viking.game.ECS.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureComponent implements Component {
    public TextureRegion textureRegion = null;

    public TextureComponent setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        return this;
    }
}
