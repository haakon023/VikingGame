package group22.viking.game.models.ECS.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.controller.states.AbstractPlayState;
import group22.viking.game.models.factory.TextureFactory;
import group22.viking.game.models.input.InputController;
import group22.viking.game.models.ECS.components.LinearProjectileComponent;
import group22.viking.game.models.ECS.components.PlayerComponent;
import group22.viking.game.models.ECS.components.TransformComponent;
import group22.viking.game.models.factory.ProjectileFactory;

public class PlayerControlSystem extends IteratingSystem {

    private final static float ROTATION_OFFSET = 180;

    private final ComponentMapper<PlayerComponent> cmPlayerComponent;
    private final ComponentMapper<TransformComponent> cmTransformComponent;

    private final TextureFactory textureFactory;

    private InputController inputController;
    private ProjectileFactory projectileFactory;
    private final World world;
    private AbstractPlayState state;

    private float timeSinceFired = 0;

    public PlayerControlSystem(AbstractPlayState state, InputController inputController, World world) {
        super(Family.all(PlayerComponent.class, TransformComponent.class).get());
        this.world = world;
        this.state = state;
        this.cmPlayerComponent = ComponentMapper.getFor(PlayerComponent.class);
        this.cmTransformComponent = ComponentMapper.getFor(TransformComponent.class);
        this.textureFactory = new TextureFactory((PooledEngine) getEngine());
        this.inputController = inputController;
    }

    public void reinitialize(InputController inputController, AbstractPlayState state) {
        this.inputController = inputController;
        this.state = state;
    }

    @Override
    protected void processEntity(com.badlogic.ashley.core.Entity entity, float deltaTime) {
        PlayerComponent pComp = cmPlayerComponent.get(entity);
        TransformComponent tComp = cmTransformComponent.get(entity);

        // offline and online
        if(pComp.isDead()){
            state.handleLocalDeath();
            return;
        }

        textureFactory.updateHealthBar(pComp.healthBar, pComp.getHealth());

        timeSinceFired += deltaTime;
        if(inputController.isMouse1Down) {
            Vector2 pos = RenderingSystem.pixelsToMeters(new Vector2(inputController.mouseLocation));
            tComp.rotation = calculateAngle(pos, new Vector2(tComp.position.x, tComp.position.y));

            if(timeSinceFired > pComp.fireRate)
                shootBullet(tComp, getLookVector(pos, new Vector2(tComp.position.x, tComp.position.y)));
        }
    }

    private void shootBullet(TransformComponent playerTransform, Vector2 direction)
    {
        if(projectileFactory == null)
            projectileFactory = new ProjectileFactory((PooledEngine) getEngine(), world);

        direction = direction.mulAdd(new Vector2(0,0), -1);

        com.badlogic.ashley.core.Entity projectile = projectileFactory.createLinearProjectile(playerTransform.position.x, playerTransform.position.y);
        projectile.getComponent(LinearProjectileComponent.class).setDirection(new Vector3(direction.x, direction.y, 0));
        getEngine().addEntity(projectile);

        timeSinceFired = 0;
    }

    private float calculateAngle(Vector2 mousePos, Vector2 playerPos)
    {
        float radians = (float)Math.atan2(mousePos.x - playerPos.x, mousePos.y - playerPos.y);
        //add 90 degrees offset to correct the angle
        return radians * MathUtils.radiansToDegrees - ROTATION_OFFSET;
    }

    private Vector2 getLookVector(Vector2 mousePos, Vector2 playerPos)
    {
        return new Vector2(playerPos.x - mousePos.x, mousePos.y - playerPos.y).nor();
    }
}
