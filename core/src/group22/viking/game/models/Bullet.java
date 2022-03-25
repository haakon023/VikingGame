package group22.viking.game.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Bullet extends Entity{
    private final static int BULLET_SIZE = 5;
    private int damage;

    public Bullet(Vector3 position, Vector2 direction, int damage, int speed) {
        super(new Sprite(), // TODO load texture
                position,
                new Vector2(BULLET_SIZE, BULLET_SIZE),
                direction,
                speed
                );
        this.damage = damage;
    }


    public int getDamage() {
        return damage;
    }

}
