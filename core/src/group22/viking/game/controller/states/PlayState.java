package group22.viking.game.controller.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import group22.viking.game.ECS.InputController;
import group22.viking.game.ECS.RenderingSystem;
import group22.viking.game.ECS.components.PlayerComponent;
import group22.viking.game.ECS.PlayerControlSystem;
import group22.viking.game.ECS.components.StateComponent;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.controller.GameStateManager;

public class PlayState extends State implements Screen {
    private Texture muteSoundBtn;
    
    private RenderingSystem renderingSystem;
    private PlayerControlSystem playerControlSystem;
    private boolean initialized;
    
    private InputController inputController;
    
    private PooledEngine engine;
    
    
    public PlayState(GameStateManager gsm) {
        super(gsm);
        
        inputController = new InputController();
        
        engine = new PooledEngine();
        playerControlSystem = new PlayerControlSystem(inputController);
        
        engine.addSystem(playerControlSystem);
        
        CreatePlayer();
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        engine.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        //Not sure how to do this in a better way, with the setup we have with States that has the render method, which contains a SpriteBatch
        //Rendering system handles everything that has a TextureComponent and a transformComponent
        //or rewrite the state stuff    
        
        if(initialized)
            return;

        //Ideally I'd like to have this in the constructor, but the batch is being passed as parameter
        renderingSystem = new RenderingSystem(sb);
        
        engine.addSystem(renderingSystem);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputController);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    
    private Entity CreatePlayer()
    {
        Entity entity = engine.createEntity();
        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        PlayerComponent plc = engine.createComponent(PlayerComponent.class);

        float width = Gdx.graphics.getWidth();
        tc.position.set(width / 2, Gdx.graphics.getHeight() / 2,0);
        state.set(StateComponent.STATE_NORMAL); 
        
        tex.region = new TextureRegion(new Texture("badlogic.jpg"));
        
        entity.add(tc);
        entity.add(tex);
        entity.add(state);
        entity.add(plc);
        
        engine.addEntity(entity);
        return entity;
    }
}
