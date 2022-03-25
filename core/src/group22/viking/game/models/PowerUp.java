package group22.viking.game.models;

import com.badlogic.gdx.math.Vector2;

public class PowerUp extends Entity{
    private Vector2 position;
    private Vector2 velocity;
    private float duration;
    private Effect effect;

    public PowerUp(Vector2 position, Vector2 velocity, float duration, Effect effect) {
        this.position = position;
        this.velocity = velocity;
        this.duration = duration;
        this.effect = effect;
    }

    public Vector2 getPosition() {
        return position;
    }

    /* Not sure if we need to set it other than when its created
    public void setPosition(Vector2 position) {
        this.position = position;
    }
    */

    public Vector2 getVelocity() {
        return velocity;
    }

    /* Not sure if we need to set it other than when its created
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    */

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getDuration() {
        return duration;
    }

    /* Not sure if we need to set it other than when its created
    public void setDuration(float duration) {
        this.duration = duration;
    }
    */
    

    public Effect getEffect() {
        return effect;
    }

    /* Not sure if we need to set it other than when its created
    public void setEffect(Effect effect) {
        this.effect = effect;
    }
    */
    
} 