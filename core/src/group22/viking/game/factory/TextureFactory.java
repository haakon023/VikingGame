package group22.viking.game.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;

public class TextureFactory extends AbstractFactory {

    Vector3 screenMiddle;

    public TextureFactory(PooledEngine engine) {
        super(engine);
        screenMiddle = new Vector3(VikingGame.SCREEN_WIDTH / 2, VikingGame.SCREEN_HEIGHT / 2, 0);
    }

    @Override
    Entity createEntity(float x, float y, float z, Texture texture) {
        return null;
    }

    Entity createEntity(float x, float y, float z, float w, float h, Texture texture) {
        Entity entity = engine.createEntity();
        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);

        tc.position.set(x,y,z);
        tc.scale.set(w, h);
        tex.region = new TextureRegion(texture);

        entity.add(tc);
        entity.add(tex);
        return entity;
    }

    Entity createEntity(Vector3 position, float scale, Texture texture) {
        return createEntity(
                position.x,
                position.y,
                position.z,
                VikingGame.SCREEN_WIDTH / texture.getWidth() * scale,
                VikingGame.SCREEN_HEIGHT / texture.getHeight() * scale,
                texture);
    }

    public Entity createOceanback() {
        return createEntity(
                new Vector3(0, 0, -6).add(screenMiddle),
                1.5F,
                Assets.getTexture(Assets.OCEANBACK)
        );
    }

    public Entity createOceantop() {
        return createEntity(
                new Vector3(0, 0, -5).add(screenMiddle),
                1.5F,
                Assets.getTexture(Assets.OCEANTOP)
        );
    }

    public Entity createWavebottom() {
        return createEntity(
                new Vector3(0, 0, -4).add(screenMiddle),
                0.4F,
                Assets.getTexture(Assets.WAVEBOTTOM)
        );
    }

    public Entity createIsland() {
        return createEntity(
                new Vector3(0, 0, -3).add(screenMiddle),
                0.3F,
                Assets.getTexture(Assets.ISLAND)
        );
    }

    public Entity createWavetop() {
        return createEntity(
                new Vector3(0, -76, -2).add(screenMiddle),
                0.3F,
                Assets.getTexture(Assets.WAVETOP)
        );
    }

    public Entity createMonastery() {
        return createEntity(
                new Vector3(0, 200, -1).add(screenMiddle),
                0.2F,
                Assets.getTexture(Assets.MONASTERY)
        );
    }
}
