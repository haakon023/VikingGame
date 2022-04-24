package group22.viking.game.models.ECS.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TransformComponent implements Component {
    public final Vector3 position = new Vector3();
    public final Vector2 scale = new Vector2(1.0f, 1.0f);
    public float rotation = 0.0f;
    public boolean isRotating = false;
    public boolean isHidden = false;

    public TransformComponent setPosition(Vector3 position) {
        this.position.set(position);
        return this;
    }

    public TransformComponent setScale(Vector2 scale) {
        this.scale.set(scale);
        return this;
    }

    public TransformComponent activateRotation() {
        this.isRotating = true;
        return this;
    }

    public TransformComponent deactivateRotation() {
        this.isRotating = false;
        return this;
    }
}
