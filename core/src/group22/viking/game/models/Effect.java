package group22.viking.game.models;

public class Effect {
    private float shootingFrequencyEffect;
    private float duration;

    public Effect(float shootingFrequencyEffect, float duration) {
        this.shootingFrequencyEffect = shootingFrequencyEffect;
        this.duration = duration;
    }

    public float getShootingFrequencyEffect() {
        return shootingFrequencyEffect;
    }

    public float getDuration() {
        return duration;
    }
    
}
