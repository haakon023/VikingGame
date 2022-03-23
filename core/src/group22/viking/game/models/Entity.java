package group22.viking.game.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

public class Entity {
    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    String name;
    Sprite sprite;

    float attackSpeed;
    float sizeX;
    float sizeY;
    float speed;
    
    Vector3 position;
    Vector3 direction;
}
