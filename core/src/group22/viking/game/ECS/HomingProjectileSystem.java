package group22.viking.game.ECS;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.ECS.components.HomingProjectileComponent;
import group22.viking.game.ECS.components.TransformComponent;

public class HomingProjectileSystem extends IteratingSystem {

    private ComponentMapper<HomingProjectileComponent> cmProjectile;
    private ComponentMapper<TransformComponent> cmTransform;

    public HomingProjectileSystem() {
        super(Family.all(HomingProjectileComponent.class).get());
        cmProjectile = ComponentMapper.getFor(HomingProjectileComponent.class);
        cmTransform = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HomingProjectileComponent projectileComponent = cmProjectile.get(entity);
        TransformComponent transformComponent = cmTransform.get(entity);
        TransformComponent targetTransform = cmTransform.get(projectileComponent.getTarget());



        double distance = Math.sqrt((targetTransform.position.x - transformComponent.position.x) * (targetTransform.position.y - transformComponent.position.y));
        if(distance > 2) {
            Vector3 direction = new Vector3(targetTransform.position.x - transformComponent.position.x, targetTransform.position.y - transformComponent.position.y, 0).nor();

            transformComponent.position.mulAdd(direction, projectileComponent.getSpeed() * deltaTime);
            return;
        }
        getEngine().removeEntity(entity);
    }
}
