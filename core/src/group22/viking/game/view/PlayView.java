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
        float zoom = 1.35F;
        factory.createTexture(
                Assets.OCEANBACK,
                new Vector3(VikingGame.SCREEN_WIDTH / 2,VikingGame.SCREEN_HEIGHT / 2,-1),
                new Vector2(
                        VikingGame.SCREEN_HEIGHT / Assets.getTexture(Assets.OCEANBACK).getHeight() * zoom,
                        VikingGame.SCREEN_WIDTH / Assets.getTexture(Assets.OCEANBACK).getWidth() * zoom)
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
