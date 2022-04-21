package group22.viking.game.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.ECS.components.TypeComponent;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;

public class TextureFactory extends AbstractFactory {

    Vector3 screenMiddle;

    public TextureFactory(PooledEngine engine) {
        super(engine);
        screenMiddle = new Vector3(VikingGame.SCREEN_WIDTH / 2, VikingGame.SCREEN_HEIGHT / 2, 0);
    }

    Entity create(Vector3 position, float scale, Texture texture) {
        return super.createEntity(TypeComponent.EntityType.TEXTURE)
                .add(engine.createComponent(TransformComponent.class)
                        .setPosition(position)
                        .setScale(new Vector2(scale, scale))
                )
                .add(engine.createComponent(TextureComponent.class)
                    .setTextureRegion(new TextureRegion(texture))
                );
    }

    public Entity createOceanback() {
        return create(
                new Vector3(0, 0, -6).add(screenMiddle),
                0.8F,
                Assets.getTexture(Assets.OCEAN_BACK)
        );
    }

    public Entity createOceantop() {
        return create(
                new Vector3(0, 0, -5).add(screenMiddle),
                0.8F,
                Assets.getTexture(Assets.OCEAN_TOP)
        );
    }

    public Entity createWavebottom() {
        return create(
                new Vector3(0, 0, -4).add(screenMiddle),
                0.6F,
                Assets.getTexture(Assets.WAVE_BOTTOM)
        );
    }

    public Entity createIsland() {
        return create(
                new Vector3(0, 0, -3).add(screenMiddle),
                0.6F,
                Assets.getTexture(Assets.ISLAND)
        );
    }

    public Entity createWavetop() {
        return create(
                new Vector3(0, -60, -2).add(screenMiddle),
                0.6F,
                Assets.getTexture(Assets.WAVE_TOP)
        );
    }

    public Entity createMonastery() {
        return create(
                new Vector3(0, 170, -1).add(screenMiddle),
                0.6F,
                Assets.getTexture(Assets.MONASTERY)
        );
    }

    public Entity createDefender(int avatarId) {
        return create(
                new Vector3(0, 120, 0).add(screenMiddle),
                0.7F,
                Assets.getTexture(Assets.getAvatar(avatarId))
        );
    }

    public Entity createHeathBarLeft() {
        return create(
                new Vector3(70, screenMiddle.y, 0),
                0.5F,
                Assets.getTexture(Assets.HEALTH_BAR_WRAPPER)
        );
    }

    public Entity createHeathFillingLeft() {
        return create(
                new Vector3(70, screenMiddle.y, 0),
                0.5F,
                Assets.getTexture(Assets.HEALTH_BAR_FILLING)
        );
    }

    public Entity createHeathBarRight() {
        return create(
                new Vector3(VikingGame.SCREEN_WIDTH - 70, screenMiddle.y, 50),
                0.5F,
                Assets.getTexture(Assets.HEALTH_BAR_WRAPPER)
        );
    }

    public Entity createHeathFillingRight() {
        return create(
                new Vector3(VikingGame.SCREEN_WIDTH - 70, screenMiddle.y, 50),
                0.5F,
                Assets.getTexture(Assets.HEALTH_BAR_FILLING)
        );
    }
}
