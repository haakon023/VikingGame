package group22.viking.game.ECS.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class PlayerComponent implements Component{
    public static final float MAX_HEALTH = 100;
    private float health = MAX_HEALTH;

    public Entity healthBar = null;

    public float fireRate = 0.2f; // shoot every half secound
    public float attackDamage = 50;
    
    public void modifyHealth(float amount)
    {
        if(health == 0) return;
        health += amount;
        if (health > MAX_HEALTH) health = MAX_HEALTH;
        if (health < 0) health = 0;
    }
    public float getHealth() {
        return health;
    }

    public PlayerComponent setHealthBar(Entity healthBar) {
        this.healthBar = healthBar;
        return this;
    }


}
