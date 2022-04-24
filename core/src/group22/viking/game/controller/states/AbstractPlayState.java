package group22.viking.game.controller.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;

import group22.viking.game.controller.spawnlogic.Spawner;
import group22.viking.game.controller.spawnlogic.SpawnerController;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

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

    public enum Type {
        TUTORIAL,
        PRACTICE,
        ONLINE
    }

    private final Type type;

    protected ProfileCollection profileCollection;

    private static PlayerControlSystem playerControlSystem;
    private static RenderingSystem renderingSystem;
    private static HomingProjectileSystem homingProjectileSystem;
    protected static CollisionSystem collisionSystem;
    private static PhysicsSystem physicsSystem;
    private static LinearProjectileSystem linearProjectileSystem;
    protected static VikingSystem vikingSystem;

    protected static World world;

    private InputController inputController;

    protected static PooledEngine engine;
    protected TextureFactory textureFactory;
    private PowerUpFactory powerUpFactory;

    protected boolean isRendering;

    private float time;
    private float powerUpTimer;

    private final SpawnerController spawnerController;

    protected AbstractPlayState(VikingGame game, Type type) {
        super(Assets.playView, game);
        this.type = type;

        this.isRendering = false;

        this.profileCollection = game.getProfileCollection();

        this.inputController = new InputController();
        if (engine == null) {
            world = new World(new Vector2(0,0), true);
            world.setContactListener(new ColliderListener());
            engine = new PooledEngine();
            playerControlSystem = new PlayerControlSystem(this, inputController, world);
            vikingSystem = new VikingSystem(world);
            renderingSystem = new RenderingSystem(game.getBatch(), new ZComparator());
            homingProjectileSystem = new HomingProjectileSystem();
            collisionSystem = new CollisionSystem(world);
            physicsSystem = new PhysicsSystem(world);
            linearProjectileSystem = new LinearProjectileSystem(world);
            engine.addSystem(playerControlSystem);
            engine.addSystem(physicsSystem);
            engine.addSystem(vikingSystem);
            engine.addSystem(renderingSystem);
            engine.addSystem(new PhysicsDebugSystem(world, renderingSystem.getCamera()));
            engine.addSystem(homingProjectileSystem);
            engine.addSystem(collisionSystem);
            engine.addSystem(linearProjectileSystem);
        } else {
            engine.getSystem(PlayerControlSystem.class).updateInputController(inputController);
        }
        this.time = 0;
        this.spawnerController = new SpawnerController(4);

        Gdx.input.setInputProcessor(inputController);

        textureFactory = new TextureFactory(engine);
        powerUpFactory = new PowerUpFactory(engine, world);
        buildInitialEntities(engine);

        SoundManager.playMusic(this);

        this.isRendering = true;
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
        engine.addEntity(playerFactory.createRotatingWeapon(healthBar,
                type == Type.ONLINE ? game.getPlayerStatusCollection() : null));

        // first Vikings
        spawnVikingWave();
    }

    private void spawnVikingWave()
    {
        if(type == Type.TUTORIAL) return;
        int amountToSpawnPerSpawner = spawnerController.amountOfAttackersToSpawnForEachSpawner(Math.round(time));
        VikingFactory vikingFactory = new VikingFactory(engine, world);
        System.out.println("amount: "+ amountToSpawnPerSpawner*spawnerController.getSpawners().size()); //For testing
        for (int i=0; i < spawnerController.getSpawners().size(); i++)
        {
            Spawner spawner = spawnerController.getSpawners().get(i);
            for (int j=0; j < amountToSpawnPerSpawner; j++)
            {
                System.out.println(spawner.getPosition() + " | position of ship");
                engine.addEntity(vikingFactory.createShip(spawner.getPosition().x, spawner.getPosition().y));
            }
        }
    }

    private void spawnPowerUp()
    {
        if(type == Type.TUTORIAL) return;
        float randomX = (float) Math.random();
        float randomY = (float) Math.random();

        // don't spawn in screen middle
        if (randomX > 0.4 && randomX < 0.6 &&
                randomY > 0.4 && randomY < 0.6){
            spawnPowerUp();
            return;
        }

        engine.addEntity(powerUpFactory.createHealthPowerUp( VikingGame.SCREEN_WIDTH * randomX, VikingGame.SCREEN_HEIGHT*randomY, new HealthPowerUp()));
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

        isRendering = true;
    }

    public void pause() {
        this.isRendering = false;
        engine.removeSystem(playerControlSystem);
        engine.removeSystem(renderingSystem);
    }

    @Override
    public void render(float deltaTime) {
        if (!isRendering) return;
        time += deltaTime;
        powerUpTimer += deltaTime;
        if (Math.round(time) >= 30) {
            spawnVikingWave();
            time = 0;
        }
        if (Math.round(powerUpTimer) >= 45)
        {
            spawnPowerUp();
            powerUpTimer = 0;
        }
        engine.update(deltaTime);
        //do here NOT use the stage-view render system
    }

    @Override
    public void dispose() {
        this.isRendering = false;
        // reset engine
        engine.removeAllEntities();
        engine.clearPools();

        world.clearForces();
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        for(Body body : bodies){
            System.out.println("DELETE BODY");
            world.destroyBody(body);
        }
    }

    public abstract void handleLocalDeath();


}
