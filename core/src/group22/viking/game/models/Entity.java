package group22.viking.game.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

public abstract class Entity {

    String name;
    Sprite sprite;

    float attackSpeed;
    float sizeX;
    float sizeY;
    float speed;
    
    Vector3 position;
    Vector3 direction;

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }
}