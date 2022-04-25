package group22.viking.game.models.powerups;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import group22.viking.game.models.ECS.components.PlayerComponent;

public class DamagePowerUp implements IPowerUp {

    private final ComponentMapper<PlayerComponent> playerMapper;

    public DamagePowerUp() {
        playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    public void givePowerUp(Entity entity) {
        PlayerComponent player = playerMapper.get(entity);
        player.addAttackDamage(25);
    }
}
