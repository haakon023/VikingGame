package group22.viking.game.ECS;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.ECS.components.PlayerComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.ECS.components.VikingComponent;

public class VikingSystem extends IteratingSystem {
    
    private ComponentMapper<TransformComponent> cmTransform;
    private ComponentMapper<VikingComponent> cmViking;
    
    public VikingSystem() {
        super(Family.all(VikingComponent.class, TransformComponent.class).get());
        
        cmTransform = ComponentMapper.getFor(TransformComponent.class);
        cmViking = ComponentMapper.getFor(VikingComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Entity player = getEngine().getEntitiesFor(Family.one( PlayerComponent.class).get()).first();

        TransformComponent playerTransform = player.getComponent(TransformComponent.class);
        Vector3 playerPosition = playerTransform.position;
        
        TransformComponent vikingTransform = entity.getComponent(TransformComponent.class);
        double distance = Math.sqrt((playerPosition.x - vikingTransform.position.x) * (playerPosition.y - vikingTransform.position.y));
        System.out.println(distance + " | distance");    
        if(distance > RenderingSystem.PixelsToMeters(vikingTransform.scale.x)) {
            Vector3 direction = new Vector3(playerPosition.x - vikingTransform.position.x, playerPosition.y - vikingTransform.position.y, 0).nor();
            vikingTransform.position.mulAdd(direction, 100 * deltaTime);
        }

        
    }
}
