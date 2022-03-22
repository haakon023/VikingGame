package group22.viking.game.models;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface Entity {
    int health = 0;
    int damage = 0;
    Sprite sprite = null;
    float attackSpeed = 0;
}
