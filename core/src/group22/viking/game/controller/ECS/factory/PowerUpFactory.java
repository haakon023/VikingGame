package group22.viking.game.controller.ECS.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.controller.ECS.systems.RenderingSystem;
import group22.viking.game.models.powerups.IPowerUp;
import group22.viking.game.models.ECS.components.B2dBodyComponent;
import group22.viking.game.models.ECS.components.PowerUpComponent;
import group22.viking.game.models.ECS.components.TextureComponent;
import group22.viking.game.models.ECS.components.TransformComponent;
import group22.viking.game.models.ECS.components.TypeComponent;
import group22.viking.game.controller.ECS.utils.BodyFactory;
import group22.viking.game.models.Assets;

public class PowerUpFactory extends AbstractFactory {


    private final World world;

    public PowerUpFactory(PooledEngine engine, World world){
        super(engine);
        this.world = world;
    }

    Entity create(Vector3 position, float scale, Texture texture, IPowerUp powerUp, float hitBoxRadius) {
        Entity entity = super.createEntity(TypeComponent.EntityType.POWER_UP);
        Body body = BodyFactory.getInstance(world).makeCirclePolyBody(
                position.x, position.y, hitBoxRadius, BodyDef.BodyType.DynamicBody, false);
        Filter filter = new Filter();
        filter.categoryBits = BodyFactory.POWER_UP_ENTITY;
        filter.maskBits = BodyFactory.BULLET_ENTITY;
        body.getFixtureList().first().setFilterData(filter);
        return entity
                .add(engine.createComponent(TransformComponent.class)
                        .init(position, new Vector2(scale, scale), false)
                )
                .add(engine.createComponent(TextureComponent.class)
                        .init(new TextureRegion(texture))
                )
                .add(engine.createComponent(PowerUpComponent.class)
                        .init(powerUp) // TODO
                )
                .add(engine.createComponent(B2dBodyComponent.class)
                        .init(body, entity)
                );
    }

    public Entity createFasterShootingPowerUp(float x, float y, IPowerUp powerUp) {
        return create(
                new Vector3(x, y, 0),
                1.0F,
                Assets.getTexture(Assets.ENERGY_POTION),
                powerUp,
                RenderingSystem.pixelsToMeters(100F)
        );
    }

    public Entity createHealthPowerUp(float x, float y, IPowerUp powerUp) {
        return create(
                new Vector3(x, y, 0),
                1.0F,
                Assets.getTexture(Assets.HEALTH_POTION),
                powerUp,
                RenderingSystem.pixelsToMeters(100F)
        );
    }
}
