package group22.viking.game.ECS;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import group22.viking.game.ECS.components.CollisionComponent;
import group22.viking.game.ECS.components.PlayerComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.ECS.components.TypeComponent;
import group22.viking.game.ECS.components.VikingComponent;

public class CollisionSystem extends IteratingSystem {

    ComponentMapper<CollisionComponent> componentMapper;
    ComponentMapper<VikingComponent> vikingMapper;
    ComponentMapper<PlayerComponent> playerMapper;

    public CollisionSystem() {
        super(Family.all(CollisionComponent.class, TransformComponent.class).get());
        componentMapper = ComponentMapper.getFor(CollisionComponent.class);
        vikingMapper = ComponentMapper.getFor(VikingComponent.class);
    }

    @Override
    protected void processEntity(com.badlogic.ashley.core.Entity entity, float deltaTime) {
        // get player collision component
        CollisionComponent cc = componentMapper.get(entity);

        TypeComponent thisType = entity.getComponent(TypeComponent.class);

        com.badlogic.ashley.core.Entity collidedEntity = cc.collisionEntity;
        if(thisType.entityType == TypeComponent.EntityType.Bullet){
            if(collidedEntity != null){
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if(type != null){
                    switch(type.entityType){
                        case VIKING:
                            //do player hit enemy thing
                            VikingComponent vc = vikingMapper.get(collidedEntity);
                            vc.DealDamage(50);
                            System.out.println("hiii");
                            break;
                        case POWER_UP:
                            //give player the powerup
                            //PlayerComponent pc = playerMapper.get(collidedEntity);
                            break;
                        case Bullet:
                            System.out.println("colliding with bullet");
                    }
                    cc.collisionEntity = null; // collision handled reset component
                }
            }
        }
    }
}
