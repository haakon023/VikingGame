package group22.viking.game.view;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.ECS.EntityFactory;
import group22.viking.game.models.Assets;

public class PlayView extends View {


    public PlayView(SpriteBatch batch, Camera camera) {
        super(batch);
    }

    @Override
    public void init() {

    }

    public void buildBackground(EntityFactory factory) {
        factory.createTexture(
                Assets.getTexture(Assets.OCEANBACK),
                new Vector3(0,0,-6),
                1.5F
        );
        factory.createTexture(
                Assets.getTexture(Assets.OCEANTOP),
                new Vector3(0,0,-5),
                1.5F
        );
        factory.createTexture(
                Assets.getTexture(Assets.WAVEBOTTOM),
                new Vector3(0,0,-4),
                0.4F
        );
        factory.createTexture(
                Assets.getTexture(Assets.ISLAND),
                new Vector3(0,0,-3),
                0.3F
        );
        factory.createTexture(
                Assets.getTexture(Assets.WAVETOP),
                new Vector3(0,-76,-2),
                0.3F
        );
        factory.createTexture(
                Assets.getTexture(Assets.MONASTERY),
                new Vector3(0,200,-1),
                0.2F
        );
        // ...

    }

    @Override
    public void runInitialAnimations() {

    }

    /**
     * Will not be used.
     *
     * @param deltaTime
     */
    @Override
    void drawElements(float deltaTime) {
        System.out.println("FALSE");
    }
}
