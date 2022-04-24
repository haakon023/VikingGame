package group22.viking.game.powerups;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import group22.viking.game.ECS.components.PlayerComponent;

public class HealthPowerUp implements IPowerUp{

    private final ComponentMapper<PlayerComponent> playerMapper;
    
    public HealthPowerUp() {
        playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    public void givePowerUp(Entity entity) {
        PlayerComponent player = playerMapper.get(entity);
        player.addToHealth(25);
    }
}
