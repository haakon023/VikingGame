package group22.viking.game.models.ECS.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.models.Assets;


public class PhysicsDebugSystem extends IteratingSystem {

    private final Box2DDebugRenderer debugRenderer;
    private final World world;
    private final OrthographicCamera camera;

    public PhysicsDebugSystem(World world, OrthographicCamera camera){
        super(Family.all().get());
        debugRenderer = Assets.box2DDebugRenderer;
        this.world = world;
        this.camera = camera;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        debugRenderer.render(world, camera.combined);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}