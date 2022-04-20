package group22.viking.game.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.ECS.BodyFactory;
import group22.viking.game.ECS.components.B2dBodyComponent;
import group22.viking.game.ECS.components.CollisionComponent;
import group22.viking.game.ECS.components.HomingProjectileComponent;
import group22.viking.game.ECS.components.LinearProjectileComponent;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.ECS.components.TypeComponent;
import group22.viking.game.models.Assets;

public class ProjectileFactory extends AbstractFactory {

    private final World world;

    public ProjectileFactory(PooledEngine engine, World world)
    {
        super(engine);
        this.world = world;
    }

    @Override
    Entity createEntity(float x, float y, float z, Texture texture) {
        Entity entity = engine.createEntity();

        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);
        TypeComponent tyc = engine.createComponent(TypeComponent.class  );

        tyc.entityType = TypeComponent.EntityType.BULLET;

        tc.position.set(new Vector3(x,y,z));
        tc.scale.set(0.3f, 0.3f);
        tex.region = new TextureRegion(texture);

        entity.add(tyc) ;
        entity.add(tc);
        entity.add(tex);

        return entity;
    }

    public Entity createProjectile(float x, float y){
        Entity entity = createEntity(x,y,0, Assets.getTexture(Assets.ARROW_SPRITE));
        HomingProjectileComponent hpc = engine.createComponent(HomingProjectileComponent.class );
        hpc.setSpeed(200);
        entity.add(hpc);
        return entity;
    }

    public Entity createLinearProjectile(float x, float y)
    {
        Entity entity = createEntity(x,y,0, Assets.getTexture(Assets.ARROW_SPRITE));

        B2dBodyComponent b2d = engine.createComponent(B2dBodyComponent.class);

        b2d.body = BodyFactory.getInstance(world).makeCirclePolyBody(x, y, 1f, BodyDef.BodyType.DynamicBody, false);
        b2d.body.setUserData(entity);

        entity.add(engine.createComponent(CollisionComponent.class));
        entity.add(b2d);

        LinearProjectileComponent lpc = engine.createComponent(LinearProjectileComponent.class);
        lpc.setSpeed(700);
        entity.add(lpc);
        return entity;
    }
}
