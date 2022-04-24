package group22.viking.game.ECS.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

public class LinearProjectileComponent implements Component {
    private Vector3 direction;
    private float speed = 500;

    public float getSpeed() {
        return speed;
    }

    public LinearProjectileComponent setSpeed(float value)
    {
        speed = value;
        return this;
    }

    public void setDirection(Vector3 direction) {
        this.direction = direction;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public float timeAlive;

    public float maxTimeAlive = 8; //6 secounds
}
