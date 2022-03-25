package group22.viking.game.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

public abstract class Entity {

    Sprite sprite;

    float sizeX;
    float sizeY;
    float speed;

    Vector3 position;
    Vector3 direction;

}

