package group22.viking.game.ECS;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.ECS.components.B2dBodyComponent;
import group22.viking.game.ECS.components.CollisionComponent;
import group22.viking.game.ECS.components.PlayerComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.ECS.components.TypeComponent;
import group22.viking.game.ECS.components.VikingComponent;

public class CollisionSystem extends IteratingSystem {

    ComponentMapper<CollisionComponent> componentMapper;
    ComponentMapper<VikingComponent> vikingMapper;
    ComponentMapper<PlayerComponent> playerMapper;
    private World world;

    public CollisionSystem(World world) {
        super(Family.all(CollisionComponent.class, TransformComponent.class).get());
        this.world = world;
        componentMapper = ComponentMapper.getFor(CollisionComponent.class);
        vikingMapper = ComponentMapper.getFor(VikingComponent.class);
        playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    protected void processEntity(com.badlogic.ashley.core.Entity entity, float deltaTime) {
        // get player collision component
        CollisionComponent cc = componentMapper.get(entity);

        TypeComponent thisType = entity.getComponent(TypeComponent.class);
        PlayerComponent pc = playerMapper.get(getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get()).first());
        com.badlogic.ashley.core.Entity collidedEntity = cc.collisionEntity;
        if(thisType.entityType == TypeComponent.EntityType.BULLET){
            if(collidedEntity != null){
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if(type != null){
                    switch(type.entityType){
                        case VIKING:
                            //do player hit enemy thing
                            VikingComponent vc = vikingMapper.get(collidedEntity);
                            vc.DealDamage(pc.AttackDamage);
                            destroyEntity(entity);
                            break;
                        case POWER_UP:
                            //give player the powerup
                            //PlayerComponent pc = playerMapper.get(collidedEntity);
                            break;
                        case BULLET:
                            System.out.println("colliding with bullet");
                    }
                    cc.collisionEntity = null; // collision handled reset component
                }
            }
        }
    }

    private void destroyEntity(Entity entity) {
        world.destroyBody(entity.getComponent(B2dBodyComponent.class).body);
        getEngine().removeEntity(entity);
    }
}
