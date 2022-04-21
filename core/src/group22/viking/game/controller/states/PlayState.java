package group22.viking.game.controller.states;

import group22.viking.game.ECS.HomingProjectileSystem;
import group22.viking.game.controller.VikingGame;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;

import group22.viking.game.ECS.InputController;
import group22.viking.game.ECS.RenderingSystem;
import group22.viking.game.ECS.VikingSystem;
import group22.viking.game.ECS.ZComparator;
import group22.viking.game.ECS.PlayerControlSystem;
import group22.viking.game.controller.spawnlogic.Spawner;
import group22.viking.game.controller.spawnlogic.SpawnerController;
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

public class PlayState extends State {




    public enum Type {
        TUTORIAL,
        PRACTICE,
        ONLINE
    } 

    private PlayerControlSystem playerControlSystem;
    private RenderingSystem renderingSystem;
    private HomingProjectileSystem homingProjectileSystem;


    private boolean initialized;

    private InputController inputController;

    private PooledEngine engine;

    private Type type;

    private PlayerStatusCollection playerStatusCollection;

    private float time;

    private SpawnerController spawnerController;

    public PlayState(VikingGame game, Type type) {
        super(Assets.playView, game);
        construct(type);
        beginGame(); //immediate begin
    }

    public PlayState (VikingGame game, Lobby lobby) {
        super(Assets.playView, game);
        construct(Type.ONLINE);
        onlineInit(lobby);
    }

    private void construct(Type type) {
        System.out.println("PLAYSTATE CONSTRUCTOR");
        this.type = type;
        this.inputController = new InputController();
        this.engine = new PooledEngine();
        this.playerControlSystem = new PlayerControlSystem(inputController);
        VikingSystem vikingSystem = new VikingSystem();
        this.renderingSystem = new RenderingSystem(game.getBatch(), new ZComparator());
        this.homingProjectileSystem = new HomingProjectileSystem();
        this.time = 0;
        this.spawnerController = new SpawnerController(3);

        this.engine.addSystem(playerControlSystem);
        this.engine.addSystem(vikingSystem);
        this.engine.addSystem(renderingSystem);
        this.engine.addSystem(homingProjectileSystem);

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
                        // TODO network error
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
                        // TODO network error
                    }
                }
        );
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

    private void spawnVikings()
    {
        int amountToSpawnPerSpawner = spawnerController.amountOfAttackersToSpawnForEachSpawner(Math.round(time));
        VikingFactory vikingFactory = new VikingFactory(engine);
        System.out.println("amount: "+ amountToSpawnPerSpawner*spawnerController.getSpawners().size()); //For testing
        for (int i=0; i < spawnerController.getSpawners().size(); i++)
        {
            Spawner spawner = spawnerController.getSpawners().get(i);
            for (int j=0; j < amountToSpawnPerSpawner; j++)
            {
                engine.addEntity(vikingFactory.createShip(spawner.getPosition().x, spawner.getPosition().y));
            }
        }
        time = 0;
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
        time += deltaTime;
        if (Math.round(time) == 30) spawnVikings();
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
