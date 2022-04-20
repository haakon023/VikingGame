package group22.viking.game.ECS.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class LinearProjectileComponent implements Component {
    private Vector3 direction;
    private float speed;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float value)
    {
        speed = value;
    }

    public void setDirection(Vector3 direction) {
        this.direction = direction;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public float timeAlive;

    public float maxTimeAlive = 20; //6 secounds
}
