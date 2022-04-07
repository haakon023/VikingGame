package group22.viking.game.ECS.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component{
    private float health = 100;

    public float getHealth() {
        return health;
    }

    public void dealDamage(float damageAmount)
    {
        this.health -= damageAmount;
    }
}
