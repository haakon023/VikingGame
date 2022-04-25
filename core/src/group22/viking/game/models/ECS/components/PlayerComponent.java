package group22.viking.game.models.ECS.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import group22.viking.game.firebase.collections.PlayerStatusCollection;

public class PlayerComponent implements Component{

    private PlayerStatusCollection playerStatusCollection;

    public static final long MAX_HEALTH = 1000;
    private long health = MAX_HEALTH;

    public Entity healthBar = null;

    public float fireRate = 0.2f; // shoot every half second
    public float attackDamage = 50;

    public PlayerComponent init() {
        this.health = MAX_HEALTH;
        return this;
    }

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

        // keep value in borders
        if (health > MAX_HEALTH) health = MAX_HEALTH;
        if (health < 0) health = 0;

        if (playerStatusCollection != null) {
            playerStatusCollection.sendHealth(health);
        }
    }
    public long getHealth() {
        return health;
    }

    public void addAttackDamage(float additionalAttackDamage) {
        if (attackDamage < 150) {
            this.attackDamage += additionalAttackDamage;
        }
    }

    public PlayerComponent setHealthBar(Entity healthBar) {
        this.healthBar = healthBar;
        return this;
    }

    public PlayerComponent setPlayerStatusCollection(PlayerStatusCollection playerStatusCollection) {
        this.playerStatusCollection = playerStatusCollection;
        return this;
    }

    public boolean isDead() {
        return health == 0;
    }
}
