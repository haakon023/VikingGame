package group22.viking.game.controller.states;

import group22.viking.game.controller.VikingGame;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import group22.viking.game.ECS.InputController;
import group22.viking.game.ECS.RenderingSystem;
import group22.viking.game.ECS.VikingSystem;
import group22.viking.game.ECS.ZComparator;
import group22.viking.game.ECS.PlayerControlSystem;
import group22.viking.game.factory.PlayerFactory;
import group22.viking.game.factory.TextureFactory;
import group22.viking.game.factory.VikingFactory;


import group22.viking.game.view.PlayView;

public class PlayState extends State {

    public enum Type {
        TUTORIAL,
        PRACTICE,
        ONLINE
    } 

    private Texture muteSoundBtn;
    
    private PlayerControlSystem playerControlSystem;
    private RenderingSystem renderingSystem;

    private boolean initialized;
    
    private InputController inputController;

    private PooledEngine engine;

    private Type type;

    public PlayState(VikingGame game, Type type) {
        super(new PlayView(game.getBatch(), game.getCamera()), game);

        System.out.println("PLAYSTATE CONSTRUCTOR");

        this.type = type;

        this.inputController = new InputController();
        this.engine = new PooledEngine();
        this.playerControlSystem = new PlayerControlSystem(inputController);
        VikingSystem vikingSystem = new VikingSystem();
        this.renderingSystem = new RenderingSystem(game.getBatch(), new ZComparator());

        this.engine.addSystem(playerControlSystem);
        this.engine.addSystem(vikingSystem);
        this.engine.addSystem(renderingSystem);

        Gdx.input.setInputProcessor(inputController);

        buildInitialEntities(engine);
    }

    /**
     * Build background and Defender
     *
     * @param engine
     */
    private void buildInitialEntities(PooledEngine engine) {
        TextureFactory textureFactory = new TextureFactory(engine);
        engine.addEntity(textureFactory.createOceanback());
        engine.addEntity(textureFactory.createOceantop());
        engine.addEntity(textureFactory.createWavebottom());
        engine.addEntity(textureFactory.createIsland());
        engine.addEntity(textureFactory.createWavetop());
        engine.addEntity(textureFactory.createMonastery());

        PlayerFactory playerFactory = new PlayerFactory(engine);
        engine.addEntity(playerFactory.createPlayerInScreenMiddle(0));

        // TODO put code in wave logic:
        VikingFactory vikingFactory = new VikingFactory(engine);
        engine.addEntity(vikingFactory.createShip(0,0));
        engine.addEntity(vikingFactory.createShip(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT));
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
}
