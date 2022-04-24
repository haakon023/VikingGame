package group22.viking.game.models.ECS.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

public class LinearProjectileComponent implements Component {
    private Vector3 direction;
    private float speed = 100;

    public static final float MAX_TIME_ALIVE = 2F; // in seconds
    public float timeAlive;

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
}
