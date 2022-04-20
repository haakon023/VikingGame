package group22.viking.game.ECS;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.ECS.components.B2dBodyComponent;
import group22.viking.game.ECS.components.LinearProjectileComponent;

public class LinearProjectileSystem extends IteratingSystem {


    private ComponentMapper<LinearProjectileComponent> pCm;
    private ComponentMapper<B2dBodyComponent> bCm;
    private World world;


    public LinearProjectileSystem(World world) {
        super(Family.all(LinearProjectileComponent.class).get());
        this.world = world;
        bCm = ComponentMapper.getFor(B2dBodyComponent.class);
        pCm = ComponentMapper.getFor(LinearProjectileComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2dBodyComponent b2d = bCm.get(entity);
        LinearProjectileComponent lpc = pCm.get(entity);


        lpc.timeAlive += deltaTime;
        //i have no clue why the projectile is moving so fucking sloooow
        b2d.body.applyLinearImpulse(new Vector2(lpc.getDirection().x, lpc.getDirection().y).nor().scl(-10000), new Vector2(lpc.getDirection().x, lpc.getDirection().y).nor(), true);

        if(lpc.timeAlive > lpc.maxTimeAlive){
            lpc.timeAlive = 0;
            world.destroyBody(b2d.body);
            getEngine().removeEntity(entity);
        }

    }
}
