package group22.viking.game.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Attacker extends Entity {
    public float attackRange;

    public Attacker(Sprite sprite, Vector3 position, Vector2 size, Vector2 velocity, int speed) {
        super(new Sprite(),
                position,
                new Vector2(40,50),
                velocity, speed);
    }
}
