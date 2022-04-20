package group22.viking.game.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.ECS.BodyFactory;
import group22.viking.game.ECS.components.B2dBodyComponent;
import group22.viking.game.ECS.components.CollisionComponent;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.ECS.components.TypeComponent;
import group22.viking.game.ECS.components.VikingComponent;
import group22.viking.game.models.Assets;

public class VikingFactory extends AbstractFactory {


    private final World world;

    public VikingFactory(PooledEngine engine, World world) {
        super(engine);
        this.world = world;
    }

    public Entity createEntity(float x, float y, float z, Texture texture) {
        Entity entity = engine.createEntity();
        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);
        VikingComponent vc = engine.createComponent(VikingComponent.class);
        B2dBodyComponent b2d = engine.createComponent(B2dBodyComponent.class);
        b2d.body = BodyFactory.getInstance(world).makeCirclePolyBody(x,y, 250, BodyDef.BodyType.DynamicBody, false);
        b2d.body.setUserData(entity);

        TypeComponent tyc = engine.createComponent(TypeComponent.class);
        CollisionComponent cc = engine.createComponent(CollisionComponent.class);
        tyc.entityType = TypeComponent.EntityType.VIKING;
        tc.position.set(x,y,z);

        tex.region = new TextureRegion(texture);

        entity.add(tyc);
        entity.add(cc);
        entity.add(b2d);
        entity.add(tc);
        entity.add(tex);
        entity.add(vc);
        return entity;
    }

    public Entity createShip(float x, float y) {
        return createEntity(x, y, 0, Assets.getTexture(Assets.VIKING_SHIP));
    }
}
