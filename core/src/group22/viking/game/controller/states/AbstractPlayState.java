package group22.viking.game.controller.states;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;

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

    private float vikingWaveTimer;
    private float powerUpTimer;


    private int cycle = 1;



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
        VikingFactory vikingFactory = new VikingFactory(engine, world);
        for (int i=0; i < Math.round(cycle); i++)
        {
            double randomX = Math.random();
            double randomY = Math.random();
            if (cycle %2 == 0 && cycle < 10)
            {
                engine.addEntity(vikingFactory.createDefaultShip(
                        (float) (VikingGame.getInstance().SCREEN_WIDTH * randomX),
                        VikingGame.getInstance().SCREEN_HEIGHT
                ));
                engine.addEntity(vikingFactory.createDefaultShip(
                        (VikingGame.getInstance().SCREEN_WIDTH),
                        (float) (VikingGame.getInstance().SCREEN_HEIGHT * randomY)
                ));
            }
            else if (cycle < 4)
            {
                engine.addEntity(vikingFactory.createDefaultShip(
                        (float) (VikingGame.getInstance().SCREEN_WIDTH * randomX),
                        0
                ));
                engine.addEntity(vikingFactory.createDefaultShip(
                        0,
                        (float) (VikingGame.getInstance().SCREEN_HEIGHT * randomY)
                ));
            }
            else
            {
                engine.addEntity(vikingFactory.createDefaultShip(
                        (float) (VikingGame.getInstance().SCREEN_WIDTH * randomX),
                        VikingGame.getInstance().SCREEN_HEIGHT
                ));
                engine.addEntity(vikingFactory.createDefaultShip(
                        (VikingGame.getInstance().SCREEN_WIDTH),
                        (float) (VikingGame.getInstance().SCREEN_HEIGHT * randomY)
                ));
                engine.addEntity(vikingFactory.createDefaultShip(
                        (float) (VikingGame.getInstance().SCREEN_WIDTH * randomX),
                        0
                ));
                engine.addEntity(vikingFactory.createDefaultShip(
                        0,
                        (float) (VikingGame.getInstance().SCREEN_HEIGHT * randomY)
                ));
            }

        }
        cycle++;
    }

    private void spawnPowerUp()
    {
        if(type == Type.TUTORIAL) return;
        double x = Math.random() * VikingGame.getInstance().SCREEN_WIDTH;
        double y = Math.random() * VikingGame.getInstance().SCREEN_HEIGHT;

        double distanceToMiddle = Math.sqrt(Math.pow(x - VikingGame.getInstance().SCREEN_WIDTH/2, 2) +
                Math.pow(y - VikingGame.getInstance().SCREEN_HEIGHT/2, 2));

        // don't spawn in screen middle
        if (distanceToMiddle < VikingGame.getInstance().SCREEN_HEIGHT/4 ||
                distanceToMiddle > VikingGame.getInstance().SCREEN_HEIGHT/2){
            spawnPowerUp();
            return;
        }

        engine.addEntity(powerUpFactory.createHealthPowerUp( (float) x, (float) y, new HealthPowerUp()));
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

        if (Math.round(vikingWaveTimer) >= 10) {
            spawnVikingWave();
            vikingWaveTimer = 0;
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
