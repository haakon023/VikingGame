package group22.viking.game.ECS.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.ECS.components.CollisionComponent;
import group22.viking.game.ECS.components.PlayerComponent;
import group22.viking.game.ECS.components.PowerUpComponent;
import group22.viking.game.ECS.components.TypeComponent;
import group22.viking.game.ECS.components.VikingComponent;
import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.states.OfflinePlayState;
import group22.viking.game.controller.states.TutorialInterruptState;

public class TutorialCollisionSystem extends CollisionSystem{

    OfflinePlayState offlinePlayState;
    public TutorialCollisionSystem(World world, OfflinePlayState offlinePlayState) {
        super(world);
        this.offlinePlayState = offlinePlayState;
    }

    protected void processEntity(com.badlogic.ashley.core.Entity entity, float deltaTime) {
        // get player collision component
        CollisionComponent cc = componentMapper.get(entity);
        TypeComponent thisType = entity.getComponent(TypeComponent.class);

        Entity player = getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get()).first();
        PlayerComponent pc = playerMapper.get(player);
        com.badlogic.ashley.core.Entity collidedEntity = cc.collisionEntity;
        if(thisType.entityType == TypeComponent.EntityType.BULLET){
            if(collidedEntity != null){
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if(type != null){
                    switch(type.entityType){
                        case VIKING:
                            //do player hit enemy thing
                            VikingComponent vc = vikingMapper.get(collidedEntity);
                            vc.DealDamage(pc.attackDamage);
                            destroyEntity(entity);
                            break;
                        case POWER_UP:
                            PowerUpComponent powerComponent = powerUpMapper.get(collidedEntity);
                            powerComponent.getPowerUp().givePowerUp(player);
                            GameStateManager.getInstance().push(new TutorialInterruptState(offlinePlayState.getGame(),
                                    offlinePlayState.popUpCount+1));
                            System.out.println("SecondScreen: " + offlinePlayState.popUpCount);
                            destroyEntity(collidedEntity);
                            destroyEntity(entity);
                            break;
                    }
                    cc.collisionEntity = null; // collision handled reset component
                }
            }
        }
    }


}
