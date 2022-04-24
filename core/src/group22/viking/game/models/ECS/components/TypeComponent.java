package group22.viking.game.models.ECS.components;

import com.badlogic.ashley.core.Component;

public class TypeComponent implements Component {


    public EntityType entityType;

    public static enum EntityType {
        PLAYER,
        VIKING,
        BULLET,
        POWER_UP,
        TEXTURE
    }

    public TypeComponent set(EntityType entityType) {
        this.entityType = entityType;
        return this;
    }
}
