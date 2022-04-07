package group22.viking.game.ECS.components;

import com.badlogic.ashley.core.Component;

public class TypeComponent implements Component {


    public EntityType EntityType;

    public enum EntityType {
        PLAYER,
        VIKING,
        Bullet
    }
}
