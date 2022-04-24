package group22.viking.game.controller.states;

import com.badlogic.gdx.math.Vector2;

import group22.viking.game.ECS.systems.TutorialVikingSystem;
import group22.viking.game.ECS.systems.VikingSystem;
import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.factory.PowerUpFactory;
import group22.viking.game.factory.VikingFactory;
import group22.viking.game.powerups.IPowerUp;

public class TutorialPlayState extends OfflinePlayState {

    public TutorialPlayState(VikingGame game) {
        super(game, Type.TUTORIAL);
        this.popUpCount = 2;

        //switch systems
        engine.removeSystem(vikingSystem);
        vikingSystem = new TutorialVikingSystem(world, this);
        engine.addSystem(vikingSystem);

        collisionSystem.addTutorialReference(this);

        init();
    }

    private void init(){
        createTutorialBoat(new Vector2(0,0));
    }


    private void createTutorialBoat(Vector2 position){
        VikingFactory vikingFactory = new VikingFactory(engine, world);
        engine.addEntity(vikingFactory.createDefaultShip(position.x, position.y));
    }

    public void createTutorialPowerUp(Vector2 position, IPowerUp iPowerUp){
        PowerUpFactory powerUpFactory = new PowerUpFactory(engine, world);
        engine.addEntity(powerUpFactory.createHealthPowerUp(position.x,position.y, iPowerUp));
    }

    public void nextInterruption() {
        GameStateManager.getInstance().push(new TutorialInterruptState(game, this, popUpCount));
        popUpCount++;
    }

    @Override
    public void dispose() {
        super.dispose();

        engine.removeSystem(vikingSystem);
        vikingSystem = new VikingSystem(world);
        engine.addSystem(vikingSystem);
        collisionSystem.addTutorialReference(null);
    }
}
