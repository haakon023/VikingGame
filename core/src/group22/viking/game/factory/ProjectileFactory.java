package group22.viking.game.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.ECS.components.HomingProjectileComponent;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.models.Assets;

public class ProjectileFactory extends AbstractFactory {

    public ProjectileFactory(PooledEngine engine) {
        super(engine);
    }

    @Override
    Entity createEntity(float x, float y, float z, Texture texture) {
        Entity entity = engine.createEntity();

        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);
        HomingProjectileComponent hpc = engine.createComponent(HomingProjectileComponent.class );

        tc.position.set(new Vector3(x,y,z));
        tc.scale.set(0.3f, 0.3f);
        tex.region = new TextureRegion(texture);

        hpc.setSpeed(200);

        entity.add(tc);
        entity.add(tex);
        entity.add(hpc);

        return entity;
    }

    public Entity createProjectile(float x, float y){
        return createEntity(x,y,0, Assets.getTexture(Assets.ARROW_SPRITE));
    }
}
