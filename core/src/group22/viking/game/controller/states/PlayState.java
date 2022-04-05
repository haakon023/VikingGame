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
import group22.viking.game.ECS.VikingSystem;
import group22.viking.game.ECS.ZComparator;
import group22.viking.game.ECS.components.PlayerComponent;
import group22.viking.game.ECS.PlayerControlSystem;
import group22.viking.game.ECS.components.PowerUpComponent;
import group22.viking.game.ECS.components.StateComponent;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.ECS.components.VikingComponent;
import group22.viking.game.controller.GameStateManager;
import group22.viking.game.factory.PlayerFactory;
import group22.viking.game.factory.PowerUpFactory;
import group22.viking.game.factory.VikingFactory;

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
        VikingSystem vikingSystem = new VikingSystem();
        
        engine.addSystem(playerControlSystem);
        engine.addSystem(vikingSystem);


        VikingFactory vikingFactory = new VikingFactory(engine);
        Entity viking =  vikingFactory.createEntity(0,0,0, new Texture("badlogic.jpg"));
        PlayerFactory playerFactory = new PlayerFactory(engine);
        float width = Gdx.graphics.getWidth();
        Entity player =  playerFactory.createEntity(width / 2, Gdx.graphics.getHeight() / 2,0, new Texture("badlogic.jpg"));
        engine.addEntity(player);
        engine.addEntity(viking);

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
        renderingSystem = new RenderingSystem(sb, new ZComparator());
        
        engine.addSystem(renderingSystem);

        initialized = true;
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
    

}
