package group22.viking.game.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector2;


public abstract class Entity {

    Sprite sprite;

    Vector3 position; // 3D for rendering depth
    Vector2 size;
    Vector2 velocity;

    float speed;

    public Entity(Sprite sprite, Vector3 position, Vector2 size, Vector2 velocity, int speed) {
        this.sprite = sprite;
        this.position = position;
        this.size = size;
        this.velocity = velocity;
        this.speed = speed;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getSize() {
        return size;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}

