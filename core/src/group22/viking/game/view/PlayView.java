package group22.viking.game.view;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * There is no real view for play state, because the ECS does its job there.
 * So this is only a dummy class.
 */
public class PlayView extends View {

    public PlayView(SpriteBatch batch, Camera camera) {
        super(batch, camera);
    }

    @Override
    public void init() {}

    @Override
    public void runInitialAnimations() {}

    @Override
    void drawElements(float deltaTime) {}
}
