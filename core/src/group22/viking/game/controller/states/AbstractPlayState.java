package group22.viking.game.controller.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import group22.viking.game.controller.ECS.systems.CollisionSystem;
import group22.viking.game.controller.ECS.systems.HomingProjectileSystem;
import group22.viking.game.controller.ECS.systems.LinearProjectileSystem;
import group22.viking.game.controller.ECS.systems.PhysicsSystem;
import group22.viking.game.controller.ECS.systems.PlayerControlSystem;
import group22.viking.game.controller.ECS.systems.RenderingSystem;
import group22.viking.game.controller.ECS.systems.VikingSystem;
import group22.viking.game.controller.ECS.utils.ColliderListener;
import group22.viking.game.controller.ECS.utils.ZComparator;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.firebase.collections.ProfileCollection;
import group22.viking.game.controller.ECS.factory.PlayerFactory;
import group22.viking.game.controller.ECS.factory.PowerUpFactory;
import group22.viking.game.controller.ECS.factory.TextureFactory;
import group22.viking.game.controller.ECS.factory.VikingFactory;
import group22.viking.game.controller.ECS.input.InputController;
import group22.viking.game.models.Assets;
import group22.viking.game.models.powerups.DamagePowerUp;
import group22.viking.game.models.powerups.HealthPowerUp;
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
    protected static CollisionSystem collisionSystem;
    protected static VikingSystem vikingSystem;

    protected static World world;

    private final InputController inputController;

    protected static PooledEngine engine;
    protected TextureFactory textureFactory;
    private final PowerUpFactory powerUpFactory;

    protected boolean isRendering;

    private float vikingWaveTimer;
    private float powerUpTimer;
    private float damagePowerUpTimer;

    private int cycle = 0;

    protected AbstractPlayState(VikingGame game, Type type) {
        super(Assets.playView, game);
        this.type = type;

        this.isRendering = false;

        this.profileCollection = game.getProfileCollection();

        this.inputController = new InputController();
        if (engine == null) {
            world = new World(new Vector2(0,0), true);
            world.setContactListener(new ColliderListener());
            engine = new PooledEngine(200, 10000, 200, 10000);
            playerControlSystem = new PlayerControlSystem(this, inputController, world);
            vikingSystem = new VikingSystem(world);
            renderingSystem = new RenderingSystem(game.getBatch(), new ZComparator());
            collisionSystem = new CollisionSystem(world);
            engine.addSystem(playerControlSystem);
            engine.addSystem(new PhysicsSystem(world));
            engine.addSystem(vikingSystem);
            engine.addSystem(renderingSystem);
            //engine.addSystem(new PhysicsDebugSystem(world, renderingSystem.getCamera()));
            engine.addSystem(new HomingProjectileSystem());
            engine.addSystem(collisionSystem);
            engine.addSystem(new LinearProjectileSystem(world));
        } else {
            engine.getSystem(PlayerControlSystem.class).reinitialize(inputController, this);
        }
        this.vikingWaveTimer = 0;

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
     * @param engine {Pooled Engine}
     */
    private void buildInitialEntities(PooledEngine engine) {
        // background
        engine.addEntity(textureFactory.createOceanBack());
        engine.addEntity(textureFactory.createOceanTop());
        engine.addEntity(textureFactory.createWaveBottom());
        engine.addEntity(textureFactory.createIsland());
        engine.addEntity(textureFactory.createWaveTop());
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
        SoundManager.playWaveBeginSound();
        VikingFactory vikingFactory = new VikingFactory(engine, world);
        cycle++;
        if (cycle % 6 == 5) {
            // special wave
            for (int i=0; i <= cycle / 2; i++) {
                engine.addEntity(vikingFactory.createSpecialShipAtEdge(VikingFactory.Edge.get(i)));
            }
            return;
        }
        // default wave
        for (int i=0; i <= cycle; i++) {
            engine.addEntity(vikingFactory.createDefaultShipAtEdge(VikingFactory.Edge.get(i)));
        }
    }

    private void spawnHealthPowerUp()
    {
        if(type == Type.TUTORIAL) return;
        double x = Math.random() * RenderingSystem.getMeterWidth();
        double y = Math.random() * RenderingSystem.getMeterHeight();

        double distanceToMiddle = Math.sqrt(Math.pow(x - RenderingSystem.getMeterWidth()/2, 2) +
                Math.pow(y - RenderingSystem.getMeterHeight()/2, 2));

        // don't spawn in screen middle
        if (distanceToMiddle < RenderingSystem.getMeterHeight()/4 ||
                distanceToMiddle > RenderingSystem.getMeterHeight()/2){
            spawnHealthPowerUp();
            return;
        }

        engine.addEntity(powerUpFactory.createHealthPowerUp( (float) x, (float) y, new HealthPowerUp()));
    }

    /**
     * created method for testing M4
     */
    private void spawnDamagePowerUp()
    {
        if(type == Type.TUTORIAL) return;
        double x = Math.random() * RenderingSystem.getMeterWidth();
        double y = Math.random() * RenderingSystem.getMeterHeight();

        double distanceToMiddle = Math.sqrt(Math.pow(x - RenderingSystem.getMeterWidth()/2, 2) +
                Math.pow(y - RenderingSystem.getMeterHeight()/2, 2));

        // don't spawn in screen middle
        if (distanceToMiddle < RenderingSystem.getMeterHeight()/4 ||
                distanceToMiddle > RenderingSystem.getMeterHeight()/2){
            spawnDamagePowerUp();
            return;
        }

        engine.addEntity(powerUpFactory.createDamagePowerUp( (float) x, (float) y, new DamagePowerUp()));
    }

    protected PlayView getView() {
        return (PlayView) view;
    }

    @Override
    public void reinitialize() {
        // no super call here
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
        vikingWaveTimer += deltaTime;
        powerUpTimer += deltaTime;
        damagePowerUpTimer += deltaTime;

        if (Math.round(vikingWaveTimer) >= 7) {
            spawnVikingWave();
            vikingWaveTimer = 0;
        }
        if (Math.round(powerUpTimer) >= 30)
        {
            spawnHealthPowerUp();
            powerUpTimer = 0;
        }
        if (Math.round(damagePowerUpTimer) >= 20)
        {
            spawnDamagePowerUp();
            damagePowerUpTimer = 0;
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
            world.destroyBody(body);
        }
    }

    public abstract void handleLocalDeath();


}
