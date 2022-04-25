package group22.viking.game.controller.ECS.factory;

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

import group22.viking.game.controller.ECS.systems.RenderingSystem;
import group22.viking.game.controller.ECS.utils.BodyFactory;
import group22.viking.game.models.ECS.components.B2dBodyComponent;
import group22.viking.game.models.ECS.components.CollisionComponent;
import group22.viking.game.models.ECS.components.TextureComponent;
import group22.viking.game.models.ECS.components.TransformComponent;
import group22.viking.game.models.ECS.components.TypeComponent;
import group22.viking.game.models.ECS.components.VikingComponent;
import group22.viking.game.models.Assets;
import group22.viking.game.models.firebase.documents.Lobby;

public class VikingFactory extends AbstractFactory {

    public enum Edge {
        LEFT(0), RIGHT(1), TOP(2), BOTTOM(3);

        private final int value;

        Edge(int value) {
            this.value = value;
        }

        public static Edge get(int i) {
            for (Edge edge : Edge.values()) {
                if (edge.value == i % 4) {
                    return edge;
                }
            }
            return null;
        }
    };

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
                        .init(position, new Vector2(scale, scale),false)
                )
                .add(engine.createComponent(TextureComponent.class)
                        .init(new TextureRegion(texture))
                )
                .add(engine.createComponent(VikingComponent.class)
                        .init()
                )
                .add(engine.createComponent(B2dBodyComponent.class)
                        .init(body, entity)
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
                        .init(position, new Vector2(scale, scale), false)
                )
                .add(engine.createComponent(TextureComponent.class)
                        .init(new TextureRegion(texture))
                )
                .add(engine.createComponent(VikingComponent.class)
                        .init(health,
                                damage,
                                VikingComponent.DEFAULT_ATTACK_RATE,
                                speed,
                                reward)
                )
                .add(engine.createComponent(B2dBodyComponent.class)
                        .init(body, entity)
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

    public Entity createDefaultShipAtEdge(Edge edge) {
        Vector2 position = getRandomPositionOnEdge(edge);
        return createDefaultShip(position.x, position.y);
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

    public Entity createSpecialShipAtEdge(Edge edge) {
        Vector2 position = getRandomPositionOnEdge(edge);
        return createSpecialShip(position.x, position.y);
    }

    private Vector2 getRandomPositionOnEdge(Edge edge) {
        float x = (float) Math.random();
        float y = (float) Math.random();
        switch (edge) {
            case TOP:
                y = 0;
                break;
            case LEFT:
                x = 0;
                break;
            case RIGHT:
                x = 1;
                break;
            case BOTTOM:
                y = 1;
                break;
        }
        return new Vector2(x * RenderingSystem.getMeterWidth(),
                y * RenderingSystem.getMeterHeight());
    }

}
