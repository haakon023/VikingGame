package group22.viking.game.models.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.models.ECS.systems.RenderingSystem;
import group22.viking.game.models.ECS.utils.BodyFactory;
import group22.viking.game.models.ECS.components.B2dBodyComponent;
import group22.viking.game.models.ECS.components.CollisionComponent;
import group22.viking.game.models.ECS.components.HomingProjectileComponent;
import group22.viking.game.models.ECS.components.LinearProjectileComponent;
import group22.viking.game.models.ECS.components.TextureComponent;
import group22.viking.game.models.ECS.components.TransformComponent;
import group22.viking.game.models.ECS.components.TypeComponent;
import group22.viking.game.models.Assets;

public class ProjectileFactory extends AbstractFactory {

    private final World world;

    public ProjectileFactory(PooledEngine engine, World world)
    {
        super(engine);
        this.world = world;
    }

    Entity create(Vector3 position, float scale, Texture texture) {
        return super.createEntity(TypeComponent.EntityType.BULLET)
                .add(engine.createComponent(TransformComponent.class)
                        .setPosition(position)
                        .setScale(new Vector2(scale, scale))
                )
                .add(engine.createComponent(TextureComponent.class)
                        .setTextureRegion(new TextureRegion(texture))
                );
    }

    public Entity createProjectile(float x, float y){
        Entity entity = create(
                new Vector3(x, y, 0),
                0.3F,
                Assets.getTexture(Assets.ARROW_SPRITE)
        );
        return entity.add(engine.createComponent(HomingProjectileComponent.class).setSpeed(200));
    }

    public Entity createLinearProjectile(float x, float y)
    {
        Entity entity = create(
                new Vector3(RenderingSystem.pixelsToMeters(x),
                        RenderingSystem.pixelsToMeters(y), 0),
                0.3F,
                Assets.getTexture(Assets.ARROW_SPRITE)
        );
        Body body = BodyFactory.getInstance(world).makeCirclePolyBody(
                x, y, 10, BodyDef.BodyType.DynamicBody, false);
        Filter filter = new Filter();
        filter.categoryBits = BodyFactory.BULLET_ENTITY;
        filter.maskBits = BodyFactory.VIKING_ENTITY | BodyFactory.POWER_UP_ENTITY;
        body.getFixtureList().first().setFilterData(filter);

        return entity
                .add(engine.createComponent(B2dBodyComponent.class)
                        .setBody(body, entity)
                )
                .add(engine.createComponent(LinearProjectileComponent.class)
                        .setSpeed(100)
                )
                .add(engine.createComponent(CollisionComponent.class));
    }
}
