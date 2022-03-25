package group22.viking.game.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class PowerUp extends Entity{
    private static final int SIZE = 20;

    private float duration;
    private Effect effect;

    public PowerUp(Vector3 position, Effect effect, float duration) {
        super(new Sprite(), // TODO
                position,
                new Vector2(SIZE, SIZE),
                new Vector2(0,0),
                0);
        this.effect = effect;
        this.duration = duration;
    }

    public float getDuration() {
        return duration;
    }

    public Effect getEffect() {
        return effect;
    }

} 