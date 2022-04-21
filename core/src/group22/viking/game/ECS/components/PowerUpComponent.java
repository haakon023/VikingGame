package group22.viking.game.ECS.components;

import com.badlogic.ashley.core.Component;

import group22.viking.game.powerups.IPowerUp;

public class PowerUpComponent implements Component {
    private IPowerUp powerUp;
    
    public IPowerUp getPowerUp()
    {
        return powerUp;
    }

    public PowerUpComponent setPowerUp(IPowerUp powerUp) {
        this.powerUp = powerUp;
        return this;
    }
}
