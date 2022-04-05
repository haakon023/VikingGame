package group22.viking.game.ECS;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import group22.viking.game.ECS.components.CollisionComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.ECS.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {

    ComponentMapper<CollisionComponent> componentMapper;

    public CollisionSystem() {
        super(Family.all(CollisionComponent.class, TransformComponent.class).get());
        componentMapper = ComponentMapper.getFor(CollisionComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get player collision component
        CollisionComponent cc = componentMapper.get(entity);

        Entity collidedEntity = cc.collisionEntity;
        if(collidedEntity != null){
            TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
            if(type != null){
                switch(type.EntityType){
                    case VIKING:
                        //do player hit enemy thing
                        System.out.println("player hit enemy");
                        break;
                }
                cc.collisionEntity = null; // collision handled reset component
            }
        }
    }
}
