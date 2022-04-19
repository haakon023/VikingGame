package group22.viking.game.factory;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import group22.viking.game.ECS.components.PlayerComponent;
import group22.viking.game.ECS.components.StateComponent;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.models.Assets;

public class PlayerFactory extends AbstractFactory {


    public PlayerFactory(PooledEngine engine) {
        super(engine);
    }

    public Entity createEntity(float x, float y, float z, Texture texture) {

        Entity entity = engine.createEntity();
        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        PlayerComponent plc = engine.createComponent(PlayerComponent.class);

        tc.position.set(x, y,z);
        state.set(StateComponent.STATE_NORMAL);

        tex.region = new TextureRegion(texture);

        entity.add(tc);
        entity.add(tex);
        entity.add(state);
        entity.add(plc);
        return entity;
    }

    public Entity createPlayerInScreenMiddle(int avatarId) {
        return createEntity(Gdx.graphics.getWidth() / 2,
                Gdx.graphics.getHeight() / 2,
                0,
                Assets.getTexture(Assets.getAvatar(avatarId)));
    }
}
