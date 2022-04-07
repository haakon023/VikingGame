package group22.viking.game.controller.states;

import group22.viking.game.ECS.EntityFactory;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;
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
import group22.viking.game.ECS.VikingSystem;
import group22.viking.game.ECS.ZComparator;
import group22.viking.game.ECS.components.PlayerComponent;
import group22.viking.game.ECS.PlayerControlSystem;
import group22.viking.game.ECS.components.StateComponent;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.ECS.components.VikingComponent;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.view.PlayView;

public class PlayState extends State {

    public enum Type {
        TUTORIAL,
        PRACTICE,
        ONLINE
    } 

    private Texture muteSoundBtn;
    
    //private RenderingSystem renderingSystem;
    private PlayerControlSystem playerControlSystem;
    private RenderingSystem renderingSystem;

    private boolean initialized;
    
    private InputController inputController;

    private PooledEngine engine;
    private EntityFactory entityFactory;
    
    private Type type;
    
    //TODO: OUR REFACTORED CONSTRUCTOR
    // public PlayState(VikingGame game, Type type) {             //TODO: GameStateManager gsm, 
        //super(new PlayView(game.getBatch(), game.getCamera()), game);                           //TODO: GSM necessary here?


    public PlayState(GameStateManager gsm) {
        super(gsm);

        System.out.println("PLAYSTATE CONSTRUCTOR ");

        this.type = type;


        //TODO:
        // this.entityFactory = new EntityFactory(engine);
        // this.renderingSystem = new RenderingSystem(game.getBatch());

        
        inputController = new InputController();
        
        engine = new PooledEngine();
        playerControlSystem = new PlayerControlSystem(inputController);
        VikingSystem vikingSystem = new VikingSystem();
        
        engine.addSystem(playerControlSystem);
        engine.addSystem(vikingSystem);

        CreatePlayer();
        CreateViking();
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
        renderingSystem = new RenderingSystem(sb, new ZComparator());
        
        engine.addSystem(renderingSystem);

        initialized = true;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputController);
    }

        this.engine.addSystem(playerControlSystem);
        this.engine.addSystem(renderingSystem);
        Gdx.input.setInputProcessor(inputController);           //TODO: is it fine to put it here? (before: in show())

        Entity player = entityFactory.createPlayer();
        ((PlayView) view).buildBackground(entityFactory);
    }

    @Override
    protected void handleInput() {

    }


    @Override
    public void reinitialize() {
        view.runInitialAnimations();
        Gdx.input.setInputProcessor(inputController);           //TODO: is it fine to put it here? (before: in show())

        engine.addSystem(playerControlSystem);
        engine.addSystem(renderingSystem);
    }

    public void pause() {
        engine.removeSystem(playerControlSystem);
        engine.removeSystem(renderingSystem);
    }

    @Override
    public void render(float deltaTime) {
        engine.update(deltaTime);
        //do here NOT use the screen render system
        //screen.render(deltaTime);
    }

    @Override
    public void dispose() {
        //REVIEW: remove the renderingSystem once the state is not used anymore
        engine.removeSystem(renderingSystem);
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

    private Entity CreateViking()
    {
        Entity entity = engine.createEntity();
        TransformComponent tc = engine.createComponent(TransformComponent.class);
        TextureComponent tex = engine.createComponent(TextureComponent.class);
        VikingComponent vc = engine.createComponent(VikingComponent.class);

        tc.position.set(0, 0,0);

        tex.region = new TextureRegion(new Texture("badlogic.jpg"));

        entity.add(tc);
        entity.add(tex);
        entity.add(vc);

        engine.addEntity(entity);
        return entity;
    }
}
