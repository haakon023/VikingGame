package group22.viking.game.controller.ECS.factory;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.models.ECS.components.PlayerComponent;
import group22.viking.game.models.ECS.components.TextureComponent;
import group22.viking.game.models.ECS.components.TransformComponent;
import group22.viking.game.models.ECS.components.TypeComponent;
import group22.viking.game.models.Assets;
import group22.viking.game.controller.ECS.systems.RenderingSystem;

public class TextureFactory extends AbstractFactory {

    private final ComponentMapper<TransformComponent> cmTransformComponent;
    private final ComponentMapper<TextureComponent> cmTextureComponent;

    public static final float HEALTH_BAR_SCALE = 0.6F;
    private static final float SCALE_AVATAR_HEAD = 0.35F;

    private final Vector3 screenMiddle;

    public TextureFactory(PooledEngine engine) {
        super(engine);
        screenMiddle = new Vector3(RenderingSystem.getMeterWidth() / 2,
                RenderingSystem.getMeterHeight() / 2, 0);

        this.cmTransformComponent = ComponentMapper.getFor(TransformComponent.class);
        this.cmTextureComponent = ComponentMapper.getFor(TextureComponent.class);
    }

    Entity create(Vector3 position, float scale, Texture texture) {
        return super.createEntity(TypeComponent.EntityType.TEXTURE)
                .add(engine.createComponent(TransformComponent.class)
                        .setPosition(position)
                        .setScale(new Vector2(scale, scale))
                        .deactivateRotation()
                )
                .add(engine.createComponent(TextureComponent.class)
                    .setTextureRegion(new TextureRegion(texture))
                );
    }

    public Entity createOceanBack() {
        return create(
                new Vector3(0, 0, -6).add(screenMiddle),
                1.0F,
                Assets.getTexture(Assets.OCEAN_BACK)
        );
    }

    public Entity createOceanTop() {
        return create(
                new Vector3(0, 0, -5).add(screenMiddle),
                1.0F,
                Assets.getTexture(Assets.OCEAN_TOP)
        );
    }

    public Entity createWaveBottom() {
        return create(
                new Vector3(0, RenderingSystem.pixelsToMeters(-70), -4).add(screenMiddle),
                0.6F,
                Assets.getTexture(Assets.WAVE_BOTTOM)
        );
    }

    public Entity createIsland() {
        return create(
                new Vector3(0, RenderingSystem.pixelsToMeters(-70), -3).add(screenMiddle),
                0.6F,
                Assets.getTexture(Assets.ISLAND)
        );
    }

    public Entity createWaveTop() {
        return create(
                new Vector3(0, RenderingSystem.pixelsToMeters(-120), -2).add(screenMiddle),
                0.6F,
                Assets.getTexture(Assets.WAVE_TOP)
        );
    }

    public Entity createMonastery() {
        return create(
                new Vector3(0, RenderingSystem.pixelsToMeters(100), -1).add(screenMiddle),
                0.6F,
                Assets.getTexture(Assets.MONASTERY)
        );
    }

    public Entity createDefender(int avatarId) {
        return create(
                new Vector3(0, RenderingSystem.pixelsToMeters(40), 0).add(screenMiddle),
                0.7F,
                Assets.getTexture(Assets.getAvatar(avatarId))
        );
    }

    public Entity createHealthBarLeft() {
        return create(
                new Vector3(RenderingSystem.pixelsToMeters(70), screenMiddle.y, 50),
                HEALTH_BAR_SCALE,
                Assets.getTexture(Assets.HEALTH_BAR_WRAPPER)
        );
    }

    public Entity createHealthFillingLeft() {
        return create(
                new Vector3(RenderingSystem.pixelsToMeters(70), screenMiddle.y, 49),
                HEALTH_BAR_SCALE,
                Assets.getTexture(Assets.HEALTH_BAR_FILLING)
        );
    }

    public Entity createAvatarHeadLeft(int avatarId) {
        return create(
                new Vector3(RenderingSystem.pixelsToMeters(70), RenderingSystem.pixelsToMeters(70), 50),
                SCALE_AVATAR_HEAD,
                Assets.getTexture(Assets.getAvatarHead(avatarId))
        );
    }

    public Entity createHealthBarRight() {
        return create(
                new Vector3(RenderingSystem.getMeterWidth() - RenderingSystem.pixelsToMeters(70), screenMiddle.y, 50),
                HEALTH_BAR_SCALE,
                Assets.getTexture(Assets.HEALTH_BAR_WRAPPER)
        );
    }

    public Entity createHealthFillingRight() {
        return create(
                new Vector3(RenderingSystem.getMeterWidth() - RenderingSystem.pixelsToMeters(70), screenMiddle.y, 49),
                HEALTH_BAR_SCALE,
                Assets.getTexture(Assets.HEALTH_BAR_FILLING)
        );
    }

    public Entity createAvatarHeadRight(int avatarId) {
        return create(
                new Vector3(RenderingSystem.getMeterWidth() - RenderingSystem.pixelsToMeters(70), RenderingSystem.pixelsToMeters(70), 50),
                SCALE_AVATAR_HEAD,
                Assets.getTexture(Assets.getAvatarHead(avatarId))
        );
    }

    public void updateHealthBar(Entity healthBar, float health) {
        if(health < 0) health = PlayerComponent.MAX_HEALTH; // default value
        TransformComponent transformComponent = cmTransformComponent.get(healthBar);
        TextureComponent textureComponent = cmTextureComponent.get(healthBar);

        transformComponent.scale.y = health / PlayerComponent.MAX_HEALTH * HEALTH_BAR_SCALE;

        transformComponent.position.y = (RenderingSystem.getMeterHeight() / 2) - //screen middle
                RenderingSystem.pixelsToMeters((1 - (health / PlayerComponent.MAX_HEALTH)) * // inverted health
                        (textureComponent.textureRegion.getRegionHeight() *
                                TextureComponent.RENDER_SCALE *
                                HEALTH_BAR_SCALE * // sprite size
                                0.5F)); // half sprite reduction
    }
}

