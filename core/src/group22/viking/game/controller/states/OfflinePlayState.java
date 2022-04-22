package group22.viking.game.controller.states;

import com.badlogic.gdx.math.Vector2;

import group22.viking.game.ECS.systems.TutorialCollisionSystem;
import group22.viking.game.ECS.systems.TutorialVikingSystem;
import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.factory.PowerUpFactory;
import group22.viking.game.factory.VikingFactory;
import group22.viking.game.powerups.HealthPowerUp;
import group22.viking.game.powerups.IPowerUp;
import group22.viking.game.view.SplashView;

public class OfflinePlayState extends AbstractPlayState{

    public Integer popUpCount;

    public OfflinePlayState(VikingGame game, Type type, Integer popUpCount) {
        super(game, type);
        this.popUpCount = popUpCount;
        if(type == Type.TUTORIAL){

            //switch systems
            this.engine.removeSystem(vikingSystem);
            this.vikingSystem = new TutorialVikingSystem(world, this);
            this.engine.addSystem(vikingSystem);
            this.engine.removeSystem(collisionSystem);
            this.collisionSystem = new TutorialCollisionSystem(world, this);
            this.engine.addSystem(collisionSystem);

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

}
