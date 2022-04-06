package group22.viking.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.ECS.EntityFactory;
import group22.viking.game.controller.VikingGame;
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
                new Vector3(0,0,-5),
                1.5F
        );
        factory.createTexture(
                Assets.getTexture(Assets.OCEANTOP),
                new Vector3(0,0,-4),
                1.5F
        );
        factory.createTexture(
                Assets.getTexture(Assets.WAVEBOTTOM),
                new Vector3(0,0,-4),
                1.5F
        );
        factory.createTexture(
                Assets.getTexture(Assets.ISLAND),
                new Vector3(0,0,-4),
                1.5F
        );
        factory.createTexture(
                Assets.getTexture(Assets.WAVETOP),
                new Vector3(0,0,-4),
                1.5F
        );
        factory.createTexture(
                Assets.getTexture(Assets.MONASTERY),
                new Vector3(0,200,-4),
                1.5F
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
