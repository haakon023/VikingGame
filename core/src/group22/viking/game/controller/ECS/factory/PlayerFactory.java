package group22.viking.game.controller.ECS.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.models.ECS.components.PlayerComponent;
import group22.viking.game.models.ECS.components.StateComponent;
import group22.viking.game.models.ECS.components.TextureComponent;
import group22.viking.game.models.ECS.components.TransformComponent;
import group22.viking.game.models.ECS.components.TypeComponent;
import group22.viking.game.firebase.collections.PlayerStatusCollection;
import group22.viking.game.models.Assets;
import group22.viking.game.controller.ECS.systems.RenderingSystem;

public class PlayerFactory extends AbstractFactory {


    public PlayerFactory(PooledEngine engine) {
        super(engine);
    }

    Entity create(Vector3 position, float scale, Texture texture,
                  Entity relatedHealthBar, PlayerStatusCollection playerStatusCollection) {
        return super.createEntity(TypeComponent.EntityType.PLAYER)
                .add(engine.createComponent(TransformComponent.class)
                        .setPosition(position)
                        .setScale(new Vector2(scale, scale))
                )
                .add(engine.createComponent(TextureComponent.class)
                        .setTextureRegion(new TextureRegion(texture))
                )
                .add(engine.createComponent(StateComponent.class)
                        .set(StateComponent.STATE_NORMAL)
                )
                .add(engine.createComponent(PlayerComponent.class)
                        .init()
                        .setHealthBar(relatedHealthBar)
                        .setPlayerStatusCollection(playerStatusCollection)
                );
    }

    public Entity createRotatingWeapon(Entity relatedHealthBar, PlayerStatusCollection playerStatusCollection) {
        return create(
                new Vector3(RenderingSystem.getMeterWidth() / 2,
                        RenderingSystem.getMeterHeight() / 2 + RenderingSystem.pixelsToMeters(70),
                        0),
                1.0F,
                Assets.getTexture(Assets.BOW),
                relatedHealthBar,
                playerStatusCollection
        );
    }


}
