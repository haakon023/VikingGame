package group22.viking.game.ECS.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import group22.viking.game.controller.firebase.PlayerStatusCollection;

public class PlayerComponent implements Component{

    private PlayerStatusCollection playerStatusCollection;

    public static final long MAX_HEALTH = 100;
    private long health = MAX_HEALTH;

    public Entity healthBar = null;

    public float fireRate = 0.2f; // shoot every half second
    public float attackDamage = 50;

    /**
     * Increase or reduce health, but follow health rules. Send to opponent if online mode (not null)
     *
     * @param amount damage if negative
     */
    public void addToHealth(float amount)
    {
        // no rise from death
        if(health == 0) return;

        // modify health
        health += amount;
        if (playerStatusCollection != null) {
            playerStatusCollection.sendHealth(health);
        }

        // keep value in borders
        if (health > MAX_HEALTH) health = MAX_HEALTH;
        if (health < 0) health = 0;
    }
    public long getHealth() {
        return health;
    }

    public PlayerComponent setHealthBar(Entity healthBar) {
        this.healthBar = healthBar;
        return this;
    }

    public PlayerComponent setPlayerStatusCollection(PlayerStatusCollection playerStatusCollection) {
        this.playerStatusCollection = playerStatusCollection;
        return this;
    }
}
