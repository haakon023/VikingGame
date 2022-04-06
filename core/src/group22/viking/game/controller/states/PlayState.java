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
import group22.viking.game.ECS.components.PlayerComponent;
import group22.viking.game.ECS.components.PlayerControlSystem;
import group22.viking.game.ECS.components.StateComponent;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;

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
    
    public PlayState(VikingGame game, Type type) {             //TODO: GameStateManager gsm, 
        super(new PlayView(game.getBatch(), game.getCamera()), game);                           //TODO: GSM necessary here?
        this.type = type;

        // super(gsm);
        
        this.inputController = new InputController();

        this.engine = new PooledEngine();
        this.entityFactory = new EntityFactory(engine);
        this.playerControlSystem = new PlayerControlSystem(inputController);
        this.renderingSystem = new RenderingSystem(game.getBatch());

        this.engine.addSystem(playerControlSystem);
        this.engine.addSystem(renderingSystem);
        //Gdx.input.setInputProcessor(inputController);           //TODO: is it fine to put it here? (before: in show())

        Entity player = entityFactory.createPlayer();
        ((PlayScreen) screen).buildBackground(entityFactory);
    }

    @Override
    protected void handleInput() {

    }


    //@Override
    public void show() {
        screen.show();
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
}
