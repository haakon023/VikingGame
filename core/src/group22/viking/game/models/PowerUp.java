package group22.viking.game.models;

import com.badlogic.gdx.math.Vector3;

public class PowerUp extends Entity{
    private float duration;
    private Effect effect;

    public PowerUp(float duration, Effect effect) {
        this.duration = duration;
        this.effect = effect;
    }

    public Vector3 getPosition() {
        return position;
    }

    public float getSpeed() {
        return speed;
    }

    public float getDuration() {
        return duration;
    }

    public Effect getEffect() {
        return effect;
    }

} 