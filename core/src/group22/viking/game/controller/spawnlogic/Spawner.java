package group22.viking.game.controller.spawnlogic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import group22.viking.game.models.Entity;

public class Spawner extends Entity {
    private static final int SIZE = 20;
    public Spawner(Sprite sprite, Vector3 position, Vector2 size, Vector2 velocity, int speed) {
        super(new Sprite(), // TODO - This sprite could be invisible, but does not need to be.
                position,
                new Vector2(SIZE, SIZE),
                new Vector2(0,0),
                0);
    }
}
