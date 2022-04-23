package group22.viking.game.controller.states;

import com.badlogic.gdx.math.Vector2;

import group22.viking.game.ECS.systems.TutorialVikingSystem;
import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.factory.PowerUpFactory;
import group22.viking.game.factory.VikingFactory;
import group22.viking.game.powerups.IPowerUp;

public class OfflinePlayState extends AbstractPlayState{

    public int popUpCount;

    public OfflinePlayState(VikingGame game, Type type) {
        super(game, type);
        this.popUpCount = 2;
        if(type == Type.TUTORIAL){

            //switch systems
            this.engine.removeSystem(vikingSystem);
            this.vikingSystem = new TutorialVikingSystem(world, this);
            this.engine.addSystem(vikingSystem);

            this.collisionSystem.addTutorialReference(this);

            tutorialInit();
        }
    }

    private void tutorialInit(){

        renderABoat(new Vector2(0,0));
        //renderAPowerUp(new Vector2(800,100), new HealthPowerUp());
    }


    private void renderABoat(Vector2 position){
        VikingFactory vikingFactory = new VikingFactory(engine, world);
        engine.addEntity(vikingFactory.createShip(position.x, position.y));
    }

    public void renderAPowerUp(Vector2 position, IPowerUp iPowerUp){
        PowerUpFactory powerUpFactory = new PowerUpFactory(engine, world);
        engine.addEntity(powerUpFactory.createHealthPowerUp(position.x,position.y, iPowerUp));
    }

    @Override
    public void handleLocalDeath() {
        System.out.println("dead");
        //GameStateManager.getInstance().pop();
        // GameStateManager.getInstance().set(new GameOverState(game));
    }

    public void nextTutorialInterruption() {
        GameStateManager.getInstance().push(new TutorialInterruptState(game, this, popUpCount));
        popUpCount++;
    }
}
