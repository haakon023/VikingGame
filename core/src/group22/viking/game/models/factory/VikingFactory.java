package group22.viking.game.models.factory;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
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
import group22.viking.game.models.ECS.components.TextureComponent;
import group22.viking.game.models.ECS.components.TransformComponent;
import group22.viking.game.models.ECS.components.TypeComponent;
import group22.viking.game.models.ECS.components.VikingComponent;
import group22.viking.game.models.Assets;

public class VikingFactory extends AbstractFactory {

    private final World world;

    public VikingFactory(PooledEngine engine, World world) {
        super(engine);
        this.world = world;
    }

    Entity createDefault(Vector3 position, float scale, Texture texture, float hitBoxRadius) {
        Entity entity = super.createEntity(TypeComponent.EntityType.VIKING);
        Body body = BodyFactory.getInstance(world).makeCirclePolyBody(
                position.x, position.y, hitBoxRadius, BodyDef.BodyType.DynamicBody, false);
        Filter filter = new Filter();
        filter.categoryBits = BodyFactory.VIKING_ENTITY;
        filter.maskBits = BodyFactory.BULLET_ENTITY;
        body.getFixtureList().first().setFilterData(filter);
        return entity
                .add(engine.createComponent(TransformComponent.class)
                        .setPosition(position)
                        .setScale(new Vector2(scale, scale))
                        .deactivateRotation()
                )
                .add(engine.createComponent(TextureComponent.class)
                        .setTextureRegion(new TextureRegion(texture))
                )
                .add(engine.createComponent(VikingComponent.class))
                .add(engine.createComponent(B2dBodyComponent.class)
                        .setBody(body, entity)
                )
                .add(engine.createComponent(CollisionComponent.class));
    }

    Entity createCustom(
            Vector3 position,
            float scale,
            Texture texture,
            float hitBoxRadius,
            float health,
            long reward,
            float speed,
            float damage
    ) {
        Entity entity = super.createEntity(TypeComponent.EntityType.VIKING);
        Body body = BodyFactory.getInstance(world).makeCirclePolyBody(
                position.x, position.y, hitBoxRadius, BodyDef.BodyType.DynamicBody, false);
        Filter filter = new Filter();
        filter.categoryBits = BodyFactory.VIKING_ENTITY;
        filter.maskBits = BodyFactory.BULLET_ENTITY;
        body.getFixtureList().first().setFilterData(filter);
        return entity
                .add(engine.createComponent(TransformComponent.class)
                        .setPosition(position)
                        .setScale(new Vector2(scale, scale))
                )
                .add(engine.createComponent(TextureComponent.class)
                        .setTextureRegion(new TextureRegion(texture))
                )
                .add(engine.createComponent(VikingComponent.class)
                        .setHealth(health)
                        .setScoreReward(reward)
                        .setSpeed(speed)
                        .setDamage(damage)
                )
                .add(engine.createComponent(B2dBodyComponent.class)
                        .setBody(body, entity)
                )
                .add(engine.createComponent(CollisionComponent.class));
    }




    public Entity createDefaultShip(float x, float y) {
        return createDefault(
                new Vector3(x, y, 0),
                0.5F,
                Assets.getTexture(Assets.VIKING_SHIP),
                RenderingSystem.pixelsToMeters(150F)
        );
    }

    public Entity createSpecialShip(float x, float y) {
        return createCustom(
                new Vector3(x, y, 0),
                0.5F,
                Assets.getTexture(Assets.VIKING_SHIP_SPECIAL),
                RenderingSystem.pixelsToMeters(150F),
                300,
                20,
                6,
                75
        );
    }



}
