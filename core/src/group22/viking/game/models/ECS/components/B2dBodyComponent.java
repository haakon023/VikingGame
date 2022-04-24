package group22.viking.game.models.ECS.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;

public class B2dBodyComponent implements Component {
    public Body body;

    public B2dBodyComponent setBody(Body body, Entity entity) {
        this.body = body;
        this.body.setUserData(entity);
        return this;
    }
}
