package group22.viking.game.controller.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.ECS.systems.CollisionSystem;
import group22.viking.game.ECS.systems.HomingProjectileSystem;
import group22.viking.game.ECS.systems.LinearProjectileSystem;
import group22.viking.game.ECS.systems.PhysicsDebugSystem;
import group22.viking.game.ECS.systems.PhysicsSystem;
import group22.viking.game.ECS.systems.PlayerControlSystem;
import group22.viking.game.ECS.systems.RenderingSystem;
import group22.viking.game.ECS.systems.VikingSystem;
import group22.viking.game.ECS.utils.ColliderListener;
import group22.viking.game.ECS.utils.ZComparator;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.firebase.ProfileCollection;
import group22.viking.game.factory.PlayerFactory;
import group22.viking.game.factory.PowerUpFactory;
import group22.viking.game.factory.TextureFactory;
import group22.viking.game.factory.VikingFactory;
import group22.viking.game.input.InputController;
import group22.viking.game.models.Assets;
import group22.viking.game.powerups.HealthPowerUp;
import group22.viking.game.view.PlayView;
import group22.viking.game.view.SoundManager;

public abstract class AbstractPlayState extends State{

    protected ProfileCollection profileCollection;

    private PlayerControlSystem playerControlSystem;
    private RenderingSystem renderingSystem;
    private HomingProjectileSystem homingProjectileSystem;
    private CollisionSystem collisionSystem;
    private PhysicsSystem physicsSystem;
    private LinearProjectileSystem linearProjectileSystem;
    private VikingSystem vikingSystem;

    private World world;

    public static World worldInstance;


    private InputController inputController;

    protected PooledEngine engine;
    protected TextureFactory textureFactory;

    protected AbstractPlayState(VikingGame game) {
        super(Assets.playView, game);

        this.profileCollection = game.getProfileCollection();

        world = new World(new Vector2(0,0), true);
        world.setContactListener(new ColliderListener());

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

        textureFactory = new TextureFactory(engine);
        buildInitialEntities(engine);

        SoundManager.playMusic(this, getGame().getPreferences());
    }

    /**
     * Build background and Defender
     *
     * @param engine
     */
    private void buildInitialEntities(PooledEngine engine) {
        // background
        engine.addEntity(textureFactory.createOceanback());
        engine.addEntity(textureFactory.createOceantop());
        engine.addEntity(textureFactory.createWavebottom());
        engine.addEntity(textureFactory.createIsland());
        engine.addEntity(textureFactory.createWavetop());
        engine.addEntity(textureFactory.createMonastery());

        PowerUpFactory powerUpFactory = new PowerUpFactory(engine, world);

        engine.addEntity(powerUpFactory.createHealthPowerUp(VikingGame.SCREEN_WIDTH - 600,VikingGame.SCREEN_HEIGHT - 100, new HealthPowerUp()));

        // health bars
        Entity healthBar = textureFactory.createHealthFillingLeft();
        engine.addEntity(healthBar);
        engine.addEntity(textureFactory.createHealthBarLeft());
        engine.addEntity(textureFactory.createAvatarHeadLeft(
                (int) profileCollection.getLocalPlayerProfile().getAvatarId())
        );

        // Defender
        engine.addEntity(textureFactory.createDefender(
                (int) profileCollection.getLocalPlayerProfile().getAvatarId()
        ));
        PlayerFactory playerFactory = new PlayerFactory(engine);
        engine.addEntity(playerFactory.createRotatingWeapon(healthBar));

        // TODO put code in wave logic:
        VikingFactory vikingFactory = new VikingFactory(engine, world);
        engine.addEntity(vikingFactory.createShip(0,0));
        engine.addEntity(vikingFactory.createShip(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT));
    }

    protected PlayView getView() {
        return (PlayView) view;
    }

    @Override
    public void reinitialize() {
        view.runInitialAnimations();
        Gdx.input.setInputProcessor(inputController);

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
        //do here NOT use the stage-view render system
    }

    @Override
    public void dispose() {
        // reset engine
        engine.removeAllEntities();
        engine.removeSystem(renderingSystem);
        engine.removeSystem(playerControlSystem);
        engine.removeSystem(physicsSystem);
        engine.removeSystem(vikingSystem);
        engine.removeSystem(renderingSystem);
        engine.removeSystem(homingProjectileSystem);
        engine.removeSystem(collisionSystem);
        engine.removeSystem(linearProjectileSystem);
    }


}
