package group22.viking.game.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import group22.viking.game.ECS.components.PowerUpComponent;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.ECS.components.VikingComponent;
import group22.viking.game.models.PowerUp;

public class PowerUpFactory extends AbstractFactory {


    public PowerUpFactory(PooledEngine engine){
        super(engine);
    }

    public Entity createEntity(float x, float y, float z, Texture texture) {
        Entity entity = engine.createEntity();
        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);
        PowerUpComponent vc = engine.createComponent(PowerUpComponent.class);

        tc.position.set(x,y,z);

        tex.region = new TextureRegion(texture);

        entity.add(tc);
        entity.add(tex);
        entity.add(vc);
        return entity;
    }
}
