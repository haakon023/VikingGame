package group22.viking.game.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Defender extends Entity {

    public Defender(Sprite sprite, Vector3 position, Vector2 size, Vector2 velocity, int speed) {
        super(sprite, position, size, velocity, speed);
    }
}
