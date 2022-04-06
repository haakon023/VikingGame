package group22.viking.game.controller.states;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.PlayScreen;

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
import group22.viking.game.ECS.components.PlayerControlSystem;
import group22.viking.game.ECS.components.StateComponent;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;

public class PlayState extends State implements Screen {

    public enum Type {
        TUTORIAL,
        PRACTICE,
        ONLINE
    } 

    private Texture muteSoundBtn;
    
    private RenderingSystem renderingSystem;
    private PlayerControlSystem playerControlSystem;
    private boolean initialized;
    
    private InputController inputController;

    private PlayScreen playScreen;

    private PooledEngine engine;
    
    private Type type;
    
    public PlayState(VikingGame game, Type type) {             //TODO: GameStateManager gsm, 
        super(null);                           //TODO: GSM necessary here?
        this.type = type;
        // super(gsm);
        
        inputController = new InputController();

        playScreen = new PlayScreen(game, this);

        engine = new PooledEngine();

        playerControlSystem = new PlayerControlSystem(inputController);
        engine.addSystem(playerControlSystem);

       // Gdx.input.setInputProcessor(inputController);           //TODO: is it fine to put it here? (before: in show())
        // renderingSystem = new RenderingSystem(new SpriteBatch());
        renderingSystem = new RenderingSystem(game.getBatch());     // get game's SpriteBatch
        game.setScreen(this);
        engine.addSystem(renderingSystem);
        createPlayer();
        createTexture("img/OceanBack.png");

    }

    @Override
    protected void handleInput() {

    }

    /*public void update(float dt) {
        engine.update(dt);
    }*/

    /*@Override
    public void render(SpriteBatch sb) {
        //Not sure how to do this in a better way, with the setup we have with States that has the render method, which contains a SpriteBatch
        //Rendering system handles everything that has a TextureComponent and a transformComponent
        //or rewrite the state stuff    
        
        if(initialized)
            return;

        //Ideally I'd like to have this in the constructor, but the batch is being passed as parameter
        renderingSystem = new RenderingSystem(sb);
        
        engine.addSystem(renderingSystem);
        initialized = true;
    }*/

    @Override
    public void show() {
        playScreen.show();
        Gdx.input.setInputProcessor(inputController);           //TODO: is it fine to put it here? (before: in show())

    }

    @Override
    public void render(float delta) {
        engine.update(delta);
        //playScreen.render(delta);
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
        //REVIEW: remove the renderingSystem once the state is not used anymore
        // engine.removeSystem(engine.getSystem(RenderingSystem.class));
        engine.removeSystem(renderingSystem);
    }
    
    public void createPlayer()
    {
        Entity entity = engine.createEntity();
        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        PlayerComponent plc = engine.createComponent(PlayerComponent.class);

        float test = Gdx.graphics.getWidth();
        tc.position.set(test / 2, Gdx.graphics.getHeight() / 2,0);
        state.set(StateComponent.STATE_NORMAL); 
        
        tex.region = new TextureRegion(new Texture("img/WizardSprite.png"));
        // tex.region = new TextureRegion(new Texture("img/OceanBack.png"));

        entity.add(tc);
        entity.add(tex);
        entity.add(state);
        entity.add(plc);
        
        engine.addEntity(entity);
        
    }

    public void createTexture(String img)
    {
        Entity entity = engine.createEntity();
        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);

        float test = Gdx.graphics.getWidth();
        tc.position.set(0, 0,-1);

        tex.region = new TextureRegion(new Texture(img));

        entity.add(tc);
        entity.add(tex);

        engine.addEntity(entity);

    }
    
}
