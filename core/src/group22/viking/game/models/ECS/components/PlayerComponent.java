package group22.viking.game.models.ECS.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import group22.viking.game.firebase.collections.PlayerStatusCollection;

public class PlayerComponent implements Component{

    private PlayerStatusCollection playerStatusCollection;

    public static final long MAX_HEALTH = 1000;
    private long health = MAX_HEALTH;

    public Entity healthBar = null;

    public static final float INITIAL_FIRE_RATE = 0.2F;
    public float fireRate = INITIAL_FIRE_RATE;
    public float attackDamage = 100;

    public PlayerComponent init(Entity healthBar, PlayerStatusCollection playerStatusCollection) {
        this.health = MAX_HEALTH;
        this.healthBar = healthBar;
        this.fireRate = INITIAL_FIRE_RATE;
        this.playerStatusCollection = playerStatusCollection;
        this.attackDamage = 100;
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

    public boolean isDead() {
        return health == 0;
    }
}
