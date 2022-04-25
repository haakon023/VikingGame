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

    public TransformComponent init(
            Vector3 position,
            Vector2 scale,
            boolean isRotating
    ) {
        this.position.set(position);
        this.scale.set(scale);
        this.rotation = 0F;
        this.isRotating = isRotating;
        this.isHidden = false;
        return this;
    }
}
