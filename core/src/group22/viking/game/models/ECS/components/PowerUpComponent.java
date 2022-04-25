package group22.viking.game.models.ECS.components;

import com.badlogic.ashley.core.Component;

import group22.viking.game.models.powerups.IPowerUp;

public class PowerUpComponent implements Component {
    private IPowerUp powerUp;
    
    public IPowerUp getPowerUp()
    {
        return powerUp;
    }

    public PowerUpComponent init(IPowerUp powerUp) {
        this.powerUp = powerUp;
        return this;
    }
}
