package group22.viking.game.ECS;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import group22.viking.game.ECS.components.B2dBodyComponent;
import group22.viking.game.ECS.components.LinearProjectileComponent;

public class LinearProjectileSystem extends IteratingSystem {


    private ComponentMapper<LinearProjectileComponent> pCm;
    private ComponentMapper<B2dBodyComponent> bCm;


    public LinearProjectileSystem() {
        super(Family.all(LinearProjectileComponent.class).get());
        bCm = ComponentMapper.getFor(B2dBodyComponent.class);
        pCm = ComponentMapper.getFor(LinearProjectileComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2dBodyComponent b2d = bCm.get(entity);
        LinearProjectileComponent lpc = pCm.get(entity);


        lpc.timeAlive += deltaTime;
        b2d.body.setLinearVelocity(new Vector2(lpc.getDirection().x, lpc.getDirection().y).nor().scl(-110000));

        if(lpc.timeAlive > lpc.maxTimeAlive){
            lpc.timeAlive = 0;
            getEngine().removeEntity(entity);
        }

    }
}
