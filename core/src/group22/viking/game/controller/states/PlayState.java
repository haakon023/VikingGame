package group22.viking.game.controller.states;

import group22.viking.game.powerups.HealthPowerUp;
import group22.viking.game.ECS.utils.ColliderListener;
import group22.viking.game.ECS.systems.CollisionSystem;
import group22.viking.game.ECS.systems.HomingProjectileSystem;
import group22.viking.game.ECS.systems.LinearProjectileSystem;
import group22.viking.game.ECS.systems.PhysicsDebugSystem;
import group22.viking.game.ECS.systems.PhysicsSystem;
import group22.viking.game.controller.VikingGame;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.factory.PowerUpFactory;
import group22.viking.game.input.InputController;
import group22.viking.game.ECS.systems.RenderingSystem;
import group22.viking.game.ECS.systems.VikingSystem;
import group22.viking.game.ECS.utils.ZComparator;
import group22.viking.game.ECS.systems.PlayerControlSystem;
import group22.viking.game.factory.PlayerFactory;
import group22.viking.game.factory.TextureFactory;
import group22.viking.game.factory.VikingFactory;


import group22.viking.game.controller.firebase.FirebaseDocument;
import group22.viking.game.controller.firebase.Lobby;
import group22.viking.game.controller.firebase.OnCollectionUpdatedListener;
import group22.viking.game.controller.firebase.PlayerStatus;
import group22.viking.game.controller.firebase.PlayerStatusCollection;
import group22.viking.game.controller.firebase.Profile;
import group22.viking.game.models.Assets;
import group22.viking.game.view.PlayView;
import group22.viking.game.view.SoundManager;
import group22.viking.game.view.View;
import group22.viking.game.view.ViewComponentFactory;

public class PlayState extends State {



    public enum Type {
        TUTORIAL,
        PRACTICE,
        ONLINE
    } 

    private PlayerControlSystem playerControlSystem;
    private RenderingSystem renderingSystem;
    private HomingProjectileSystem homingProjectileSystem;
    private CollisionSystem collisionSystem;
    private PhysicsSystem physicsSystem;
    private LinearProjectileSystem linearProjectileSystem;


    private World world;
    
    public static World worldInstance;


    private boolean initialized;

    private InputController inputController;

    private PooledEngine engine;
    private Type type;

    private PlayerStatusCollection playerStatusCollection;

    public PlayState(VikingGame game, Type type) {
        super(Assets.playView, game);
        construct(type);
        beginGame(); //immediate begin

        SoundManager.playMusic(this, getGame().getPreferences());
    }

    public PlayState (VikingGame game, Lobby lobby) {
        super(Assets.playView, game);
        construct(Type.ONLINE);
        onlineInit(lobby);
        SoundManager.playMusic(this, getGame().getPreferences());
    }

    private void construct(Type type) {
        world = new World(new Vector2(0,0), true);
        world.setContactListener(new ColliderListener());
        

        this.type = type;
        this.inputController = new InputController();
        this.engine = new PooledEngine();
        this.playerControlSystem = new PlayerControlSystem(inputController, world);
        VikingSystem vikingSystem = new VikingSystem(world);
        this.renderingSystem = new RenderingSystem(game.getBatch(), new ZComparator());
        this.homingProjectileSystem = new HomingProjectileSystem();
        this.collisionSystem = new CollisionSystem(world);
        this.physicsSystem = new PhysicsSystem(world);
        this.linearProjectileSystem = new LinearProjectileSystem(world);

        this.engine.addSystem(playerControlSystem);
        this.engine.addSystem(physicsSystem);
        this.engine.addSystem(vikingSystem);
        this.engine.addSystem(renderingSystem);
        this.engine.addSystem(new PhysicsDebugSystem(world, renderingSystem.getCamera()));
        this.engine.addSystem(homingProjectileSystem);
        this.engine.addSystem(collisionSystem);
        this.engine.addSystem(linearProjectileSystem);

        Gdx.input.setInputProcessor(inputController);
        buildInitialEntities(engine);
    }

    /**
     * Build background and Defender
     *
     * @param engine
     */
    private void buildInitialEntities(PooledEngine engine) {
        // background
        TextureFactory textureFactory = new TextureFactory(engine);
        engine.addEntity(textureFactory.createOceanback());
        engine.addEntity(textureFactory.createOceantop());
        engine.addEntity(textureFactory.createWavebottom());
        engine.addEntity(textureFactory.createIsland());
        engine.addEntity(textureFactory.createWavetop());
        engine.addEntity(textureFactory.createMonastery());

        PowerUpFactory powerUpFactory = new PowerUpFactory(engine, world);

        engine.addEntity(powerUpFactory.createHealthPowerUp(VikingGame.SCREEN_WIDTH - 600,VikingGame.SCREEN_HEIGHT - 100, new HealthPowerUp()));

        // Defender
        engine.addEntity(textureFactory.createDefender(
                (int) game.getProfileCollection().getLocalPlayerProfile().getAvatarId()
        ));
        PlayerFactory playerFactory = new PlayerFactory(engine);
        engine.addEntity(playerFactory.createRotatingWeapon());

        // health bars
        engine.addEntity(textureFactory.createHeathBarLeft());
        engine.addEntity(textureFactory.createHeathBarRight());
        engine.addEntity(textureFactory.createHeathFillingLeft());
        engine.addEntity(textureFactory.createHeathFillingRight());

        // TODO put code in wave logic:
        VikingFactory vikingFactory = new VikingFactory(engine, world);
        engine.addEntity(vikingFactory.createShip(0,0));
        engine.addEntity(vikingFactory.createShip(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT));
    }

    private void onlineInit(final Lobby lobby) {
        this.playerStatusCollection = game.getPlayerStatusCollection();

        initOpponent(lobby.isHost() ?
                game.getProfileCollection().getHostProfile() :
                game.getProfileCollection().getGuestProfile());

        playerStatusCollection.createOwnStatus(
                lobby.getOwnId(),
                lobby.getOpponentId(),
                new OnCollectionUpdatedListener() {
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        addOpponentListener(lobby);
                        beginGame();
                    }

                    @Override
                    public void onFailure() {
                        ViewComponentFactory.createErrorDialog().show(getView().getStage());
                    }
                }
        );
    }

    private void beginGame() {

    }

    private void addOpponentListener(Lobby lobby) {
        playerStatusCollection.addListenerToOpponentStatus(
                lobby.getOwnId(),
                lobby.getOpponentId(),
                new OnCollectionUpdatedListener() {
                    @Override
                    public void onSuccess(FirebaseDocument document) {
                        PlayerStatus opponent = (PlayerStatus) document;
                        if(opponent.isDead()) {
                            // TODO end game
                            return;
                        }
                        displayOpponentHealth(opponent.getHealth());
                    }

                    @Override
                    public void onFailure() {
                        ViewComponentFactory.createErrorDialog().show(getView().getStage());
                    }
                }
        );
    }

    private PlayView getView() {
        return (PlayView) view;

    }

    /**
     * Display opponent avatar and name.
     *
     * @param profile {Profile} opponent profile
     */
    private void initOpponent(Profile profile) {
        // TODO gui call
        // profile.getName(); // name
        // profile.getAvatarId(); // avatar id
    }

    private void displayOpponentHealth(long health) {
        // TODO gui call
    }

    /**
     * Reduce own health. Different behavior depending on type (online vs offline).
     *
     * @param damage {long}
     * @return {long} new health
     */
    private long reduceOwnHealth(long damage) {
        if(type == Type.ONLINE) {
            return playerStatusCollection.reduceOwnHealth(damage);
        }
        // TODO offline functionality
        return 1L;
    }

    private void notifyOpponentOverCompletedWave() {
        if(type != Type.ONLINE) return;
        playerStatusCollection.waveCompleted();
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
