package group22.viking.game.ECS.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.ECS.components.B2dBodyComponent;
import group22.viking.game.ECS.components.LinearProjectileComponent;
import group22.viking.game.ECS.components.TransformComponent;

public class LinearProjectileSystem extends IteratingSystem {

    // rotation offset depends on rotation in resource image
    private static float ROTATION_OFFSET = -130;

    private ComponentMapper<LinearProjectileComponent> linearProjectileComponentMapper;
    private ComponentMapper<B2dBodyComponent> bodyComponentComponentMapper;
    private ComponentMapper<TransformComponent> transformComponentMapper;
    private World world;


    public LinearProjectileSystem(World world) {
        super(Family.all(LinearProjectileComponent.class).get());
        this.world = world;
        bodyComponentComponentMapper = ComponentMapper.getFor(B2dBodyComponent.class);
        linearProjectileComponentMapper = ComponentMapper.getFor(LinearProjectileComponent.class);
        transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2dBodyComponent b2dBodyComponent = bodyComponentComponentMapper.get(entity);
        LinearProjectileComponent linearProjectileComponent = linearProjectileComponentMapper.get(entity);
        TransformComponent transformComponent = transformComponentMapper.get(entity);


        linearProjectileComponent.timeAlive += deltaTime;
        //i have no clue why the projectile is moving so fucking sloooow
        b2dBodyComponent.body.applyLinearImpulse(
                new Vector2(linearProjectileComponent.getDirection().x, linearProjectileComponent.getDirection().y).nor().scl(-10000*4),
                new Vector2(linearProjectileComponent.getDirection().x, linearProjectileComponent.getDirection().y).nor(),
                true);
        transformComponent.rotation = calculateAngle(linearProjectileComponent.getDirection());

        if(linearProjectileComponent.timeAlive > linearProjectileComponent.maxTimeAlive){
            linearProjectileComponent.timeAlive = 0;
            world.destroyBody(b2dBodyComponent.body);
            getEngine().removeEntity(entity);
        }

    }

    private float calculateAngle(Vector3 direction)
    {
        float radians = (float)Math.atan2(direction.x, direction.y);
        return radians * MathUtils.radiansToDegrees * -1 + ROTATION_OFFSET;
    }
}
