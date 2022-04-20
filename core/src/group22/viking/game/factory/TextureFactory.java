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

    Entity createEntity(float x, float y, float z, float scale, Texture texture) {
        Entity entity = engine.createEntity();
        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);

        tc.position.set(x,y,z);
        tc.scale.scl(scale);
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
                scale,
                texture);
    }

    public Entity createOceanback() {
        return createEntity(
                new Vector3(0, 0, -6).add(screenMiddle),
                0.8F,
                Assets.getTexture(Assets.OCEAN_BACK)
        );
    }

    public Entity createOceantop() {
        return createEntity(
                new Vector3(0, 0, -5).add(screenMiddle),
                0.8F,
                Assets.getTexture(Assets.OCEAN_TOP)
        );
    }

    public Entity createWavebottom() {
        return createEntity(
                new Vector3(0, 0, -4).add(screenMiddle),
                0.6F,
                Assets.getTexture(Assets.WAVE_BOTTOM)
        );
    }

    public Entity createIsland() {
        return createEntity(
                new Vector3(0, 0, -3).add(screenMiddle),
                0.6F,
                Assets.getTexture(Assets.ISLAND)
        );
    }

    public Entity createWavetop() {
        return createEntity(
                new Vector3(0, -60, -2).add(screenMiddle),
                0.6F,
                Assets.getTexture(Assets.WAVE_TOP)
        );
    }

    public Entity createMonastery() {
        return createEntity(
                new Vector3(0, 170, -1).add(screenMiddle),
                0.6F,
                Assets.getTexture(Assets.MONASTERY)
        );
    }

    public Entity createDefender(int avatarId) {
        return createEntity(
                new Vector3(0, 120, 0).add(screenMiddle),
                0.7F,
                Assets.getTexture(Assets.getAvatar(avatarId))
        );
    }
}
