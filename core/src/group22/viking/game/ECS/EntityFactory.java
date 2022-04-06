package group22.viking.game.ECS;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.ECS.components.PlayerComponent;
import group22.viking.game.ECS.components.StateComponent;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;

public class EntityFactory {

    private PooledEngine engine;

    public EntityFactory(PooledEngine engine) {
        this.engine = engine;
    }
    public Entity createPlayer()
    {
        Entity entity = engine.createEntity();
        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        PlayerComponent plc = engine.createComponent(PlayerComponent.class);

        float test = Gdx.graphics.getWidth();
        tc.position.set(test / 2, Gdx.graphics.getHeight() / 2,0);
        state.set(StateComponent.STATE_NORMAL);

        tex.region = new TextureRegion(new Texture("img/OceanBack.png"));

        entity.add(tc);
        entity.add(tex);
        entity.add(state);
        entity.add(plc);

        engine.addEntity(entity);

        return entity;

    }

    public Entity createTexture(String imagePath, Vector3 position, Vector2 scale)
    {
        Entity entity = engine.createEntity();
        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);

        float test = Gdx.graphics.getWidth();
        tc.position.set(position);
        tc.scale.set(scale);

        tex.region = new TextureRegion(new Texture(imagePath));

        entity.add(tc);
        entity.add(tex);

        engine.addEntity(entity);

        return entity;
    }

}
