package group22.viking.game.controller.ECS.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.models.ECS.components.B2dBodyComponent;
import group22.viking.game.models.ECS.components.CollisionComponent;
import group22.viking.game.models.ECS.components.PlayerComponent;
import group22.viking.game.models.ECS.components.PowerUpComponent;
import group22.viking.game.models.ECS.components.TransformComponent;
import group22.viking.game.models.ECS.components.TypeComponent;
import group22.viking.game.models.ECS.components.VikingComponent;
import group22.viking.game.controller.states.OnlinePlayState;
import group22.viking.game.controller.states.TutorialPlayState;

public class CollisionSystem extends IteratingSystem {

    private final ComponentMapper<CollisionComponent> componentMapper;
    private final ComponentMapper<VikingComponent> vikingMapper;
    private final ComponentMapper<PlayerComponent> playerMapper;
    private final ComponentMapper<PowerUpComponent> powerUpMapper;

    private final World world;
    private TutorialPlayState tutorialPlayState;
    private OnlinePlayState onlinePlayState;

    public CollisionSystem(World world) {
        super(Family.all(CollisionComponent.class, TransformComponent.class).get());
        this.world = world;
        componentMapper = ComponentMapper.getFor(CollisionComponent.class);
        vikingMapper = ComponentMapper.getFor(VikingComponent.class);
        playerMapper = ComponentMapper.getFor(PlayerComponent.class);
        powerUpMapper = ComponentMapper.getFor(PowerUpComponent.class);
    }

    public void addTutorialReference(TutorialPlayState tutorialPlayState) {
        this.tutorialPlayState = tutorialPlayState;
    }

    public void addOnlineReference(OnlinePlayState onlinePlayState) {
        this.onlinePlayState = onlinePlayState;
    }

    @Override
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
                            if(onlinePlayState != null) onlinePlayState.addScore(vc.getScoreReward());
                            vc.dealDamage(pc.attackDamage);
                            destroyEntity(entity);
                            break;
                        case POWER_UP:
                            PowerUpComponent powerComponent = powerUpMapper.get(collidedEntity);
                            powerComponent.getPowerUp().givePowerUp(player);
                            destroyEntity(collidedEntity);
                            destroyEntity(entity);
                            if(tutorialPlayState != null) tutorialPlayState.nextInterruption();
                            break;
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
