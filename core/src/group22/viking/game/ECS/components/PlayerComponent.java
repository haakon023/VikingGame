package group22.viking.game.ECS.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component{
    private float health = 100;

    public float fireRate = 0.2f; // shoot every half secound
    public float AttackDamage = 50;
    
    public void modifyHealth(float amount)
    {
        health += amount;
    }
    public float getHealth() {
        return health;
    }


}
