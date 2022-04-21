package group22.viking.game.ECS.components;

import com.badlogic.ashley.core.Component;

public class HomingProjectileComponent implements Component {

    private float speed;
    private com.badlogic.ashley.core.Entity target;


    public HomingProjectileComponent setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public void setTarget(com.badlogic.ashley.core.Entity target) {
        this.target = target;
    }

    public float getSpeed() {
        return speed;
    }

    public com.badlogic.ashley.core.Entity getTarget() {
        return target;
    }


}
