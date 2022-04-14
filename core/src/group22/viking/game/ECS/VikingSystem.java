package group22.viking.game.ECS;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.ECS.components.PlayerComponent;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.ECS.components.VikingComponent;
import group22.viking.game.models.Player;

public class VikingSystem extends IteratingSystem {
    
    private ComponentMapper<TransformComponent> cmTransform;
    private ComponentMapper<VikingComponent> cmViking;
    private ComponentMapper<TextureComponent> cmTexture;
    
    public VikingSystem() {
        super(Family.all(VikingComponent.class, TransformComponent.class, TextureComponent.class).get());
        
        cmTransform = ComponentMapper.getFor(TransformComponent.class);
        cmViking = ComponentMapper.getFor(VikingComponent.class);
        cmTexture = ComponentMapper.getFor(TextureComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Entity player = getEngine().getEntitiesFor(Family.one( PlayerComponent.class).get()).first();

        TransformComponent playerTransform = player.getComponent(TransformComponent.class);
        Vector3 playerPosition = playerTransform.position;
        
        TransformComponent vikingTransform = cmTransform.get(entity);
        TextureComponent vikingTexture = cmTexture.get(entity);
        VikingComponent viking = cmViking.get(entity);

        viking.setTimeSinceLastAttack(viking.getTimeSinceLastAttack() + deltaTime);

        double distance = Math.sqrt((playerPosition.x - vikingTransform.position.x) * (playerPosition.y - vikingTransform.position.y));
        float vikingSize = vikingTexture.region.getRegionWidth() / 2f;
        if(distance > vikingSize) {
            Vector3 direction = new Vector3(playerPosition.x - vikingTransform.position.x, playerPosition.y - vikingTransform.position.y, 0).nor();
            vikingTransform.position.mulAdd(direction, 100 * deltaTime);
            return;
        }

        dealDamage(player.getComponent(PlayerComponent.class), viking);
    }

    private void dealDamage(PlayerComponent player, VikingComponent viking)
    {
        if(viking.getAttackRate() <= viking.getTimeSinceLastAttack())
        {
            player.dealDamage(viking.getDamage());
            viking.setTimeSinceLastAttack(0);
        }
    }
}
