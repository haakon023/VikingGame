package group22.viking.game.view;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.factory.TextureFactory;
import group22.viking.game.models.Assets;

public class PlayView extends View {


    public PlayView(SpriteBatch batch, Camera camera) {
        super(batch, camera);
    }

    @Override
    public void init() {

    }

    public void buildBackground(PooledEngine engine) {
        TextureFactory factory = new TextureFactory(engine);

        engine.addEntity(factory.createOceanback());
        engine.addEntity(factory.createOceantop());
        engine.addEntity(factory.createWavebottom());
        engine.addEntity(factory.createIsland());
        engine.addEntity(factory.createWavetop());
        engine.addEntity(factory.createMonastery());
    }

    @Override
    public void runInitialAnimations() {

    }

    /**
     * Will not be used.
     *
     * @param deltaTime time difference
     */
    @Override
    void drawElements(float deltaTime) {
        System.out.println("FALSE");
    }
}
