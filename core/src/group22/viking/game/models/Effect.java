package group22.viking.game.models;

public class Effect {
    private float shootingSpeedEffect;
    private float duration;

    public Effect(float shootingSpeedEffect, float duration) {
        this.shootingSpeedEffect = shootingSpeedEffect;
        this.duration = duration;
    }

    public float getShootingSpeedEffect() {
        return shootingSpeedEffect;
    }

    /* Not sure if we need to set it other than when its created
    public void setShootingSpeedEffect(float shootingSpeedEffect) {
        this.shootingSpeedEffect = shootingSpeedEffect;
    }
    */
    

    public float getDuration() {
        return duration;
    }

    /* Not sure if we need to set it other than when its created
    public void setDuration(float duration) {
        this.duration = duration;
    }
    */
    
}
