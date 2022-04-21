package group22.viking.game.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.powerups.IPowerUp;
import group22.viking.game.ECS.components.B2dBodyComponent;
import group22.viking.game.ECS.components.PowerUpComponent;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.ECS.components.TypeComponent;
import group22.viking.game.ECS.utils.BodyFactory;
import group22.viking.game.models.Assets;

public class PowerUpFactory extends AbstractFactory {


    private final World world;

    public PowerUpFactory(PooledEngine engine, World world){
        super(engine);
        this.world = world;
    }

    public Entity createEntity(float x, float y, float z, Texture texture) {
        Entity entity = engine.createEntity();
        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);
        PowerUpComponent vc = engine.createComponent(PowerUpComponent.class);
        B2dBodyComponent b2d = engine.createComponent(B2dBodyComponent.class);


        TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
        typeComponent.entityType = TypeComponent.EntityType.POWER_UP;

        tc.position.set(x,y,z);

        tex.region = new TextureRegion(texture);

        b2d.body = BodyFactory.getInstance(world).makeCirclePolyBody(x,y, tex.region.getRegionWidth() / 2, BodyDef.BodyType.StaticBody, true);
        b2d.body.setUserData(entity);
        
        entity.add(tc);
        entity.add(tex);
        entity.add(vc);
        entity.add(typeComponent);
        entity.add(b2d);
        
        return entity;
    }
    
    public Entity createHealthPowerUp(float x, float y, IPowerUp powerUp)
    {
        Entity entity = createEntity(x,y,0, Assets.getTexture(Assets.BAD_LOGIC));
        entity.getComponent(PowerUpComponent.class).setPowerUp(powerUp);
        
        return entity;
    }
}
