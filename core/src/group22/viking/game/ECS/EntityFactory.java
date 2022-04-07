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
import group22.viking.game.ECS.components.VikingComponent;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;

public class EntityFactory {

    private PooledEngine engine;
    private Vector3 screenMiddle;
    private Vector2 screenSize;


    public EntityFactory(PooledEngine engine) {
        this.engine = engine;
        this.screenMiddle = new Vector3(VikingGame.SCREEN_WIDTH / 2, VikingGame.SCREEN_HEIGHT / 2, 0);
        this.screenSize = new Vector2(VikingGame.SCREEN_WIDTH, VikingGame.SCREEN_HEIGHT);
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
        tc.scale.scl(0.5f);

        tex.region = new TextureRegion(Assets.getTexture(Assets.WIZARDSPRITE));

        entity.add(tc);
        entity.add(tex);
        entity.add(state);
        entity.add(plc);

        engine.addEntity(entity);

        return entity;
    }

    public Entity createViking(Vector2 spawnPosition)
    {
        Entity entity = engine.createEntity();
        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);
        VikingComponent vc = engine.createComponent(VikingComponent.class);

        tc.position.set(spawnPosition.x, spawnPosition.y,0);

        tex.region = new TextureRegion(new Texture(Assets.VIKINGSHIP));

        entity.add(tc);
        entity.add(tex);
        entity.add(vc);

        engine.addEntity(entity);

        return entity;
    }

    public Entity createTexture(Texture texture, Vector3 position, float scale)
    {
        Entity entity = engine.createEntity();
        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);

        tc.position.set(screenMiddle);
        tc.position.add(position);

        tc.scale.set(
                screenSize.x / texture.getWidth() * scale,
                screenSize.y / texture.getHeight() * scale);

        tex.region = new TextureRegion(texture);

        entity.add(tc);
        entity.add(tex);

        engine.addEntity(entity);

        return entity;
    }

}
