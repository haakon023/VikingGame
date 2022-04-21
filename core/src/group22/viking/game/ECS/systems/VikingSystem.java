package group22.viking.game.ECS.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.ECS.components.B2dBodyComponent;
import group22.viking.game.ECS.components.HomingProjectileComponent;
import group22.viking.game.ECS.components.PlayerComponent;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.ECS.components.VikingComponent;
import group22.viking.game.factory.ProjectileFactory;

public class VikingSystem extends IteratingSystem {

    private final World world;
    private ComponentMapper<TransformComponent> cmTransform;
    private ComponentMapper<VikingComponent> cmViking;
    private ComponentMapper<TextureComponent> cmTexture;
    private ComponentMapper<B2dBodyComponent> cmBody;

    private ProjectileFactory projectileFactory;
    
    public VikingSystem(World world) {
        super(Family.all(VikingComponent.class, TransformComponent.class, TextureComponent.class).get());
        this.world = world;
        cmTransform = ComponentMapper.getFor(TransformComponent.class);
        cmViking = ComponentMapper.getFor(VikingComponent.class);
        cmTexture = ComponentMapper.getFor(TextureComponent.class);
        cmBody = ComponentMapper.getFor(B2dBodyComponent.class);
    }

    @Override
    protected void processEntity(com.badlogic.ashley.core.Entity entity, float deltaTime) {
        com.badlogic.ashley.core.Entity player = getEngine().getEntitiesFor(Family.one( PlayerComponent.class).get()).first();

        TransformComponent playerTransform = cmTransform.get(player);
        Vector3 playerPosition = playerTransform.position;

        TransformComponent vikingTransform = cmTransform.get(entity);
        TextureComponent vikingTexture = cmTexture.get(entity);
        VikingComponent viking = cmViking.get(entity);

        B2dBodyComponent b2d = cmBody.get(entity);

        if(viking.getHealth() <= 0) {
            world.destroyBody(entity.getComponent(B2dBodyComponent.class).body);
            getEngine().removeEntity(entity);
            return;
        }
        
        viking.setTimeSinceLastAttack(viking.getTimeSinceLastAttack() + deltaTime);

        double distance = distance(new Vector2(playerPosition.x, playerPosition.y), new Vector2(vikingTransform.position.x, vikingTransform.position.y));

        float vikingSize = vikingTexture.region.getRegionWidth() / 2f;
        if(distance > vikingSize) {
            Vector3 direction = new Vector3(playerPosition.x - vikingTransform.position.x, playerPosition.y - vikingTransform.position.y, 0).nor();
            b2d.body.setLinearVelocity(new Vector2(direction.x, direction.y).scl( 1000));
            return;
        }
        
        b2d.body.setLinearVelocity(0,0);

        if (dealDamage(player.getComponent(PlayerComponent.class), viking)) {
            SpawnProjectile(vikingTransform, player);
        }
    }

    double distance(Vector2 object1, Vector2 object2){
        return Math.sqrt(Math.pow((object2.x - object1.x), 2) + Math.pow((object2.y - object1.y), 2));
    }

    private boolean dealDamage(PlayerComponent player, VikingComponent viking)
    {
        if (!(viking.getAttackRate() <= viking.getTimeSinceLastAttack())) {
            return false;
        }
        player.modifyHealth(-viking.getDamage());
        viking.setTimeSinceLastAttack(0);
        return true;
    }

    private void SpawnProjectile(TransformComponent vikingTransform, com.badlogic.ashley.core.Entity target)
    {
        if(projectileFactory == null)
            projectileFactory = new ProjectileFactory((PooledEngine) getEngine(), world);

        com.badlogic.ashley.core.Entity projectile = projectileFactory.createProjectile(vikingTransform.position.x, vikingTransform.position.y);
        projectile.getComponent(HomingProjectileComponent.class).setTarget(target);
        getEngine().addEntity(projectile);
    }


}
