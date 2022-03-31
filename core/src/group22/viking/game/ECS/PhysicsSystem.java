package group22.viking.game.ECS;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import group22.viking.game.ECS.components.CollisionComponent;
import group22.viking.game.ECS.components.TransformComponent;

public class PhysicsSystem extends IteratingSystem {
    
    
    
    public PhysicsSystem() {
        super(Family.all(CollisionComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        
    }
}
