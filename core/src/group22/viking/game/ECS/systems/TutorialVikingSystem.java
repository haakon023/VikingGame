package group22.viking.game.ECS.systems;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.ECS.components.VikingComponent;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.states.OfflinePlayState;
import group22.viking.game.controller.states.TutorialPlayState;
import group22.viking.game.powerups.HealthPowerUp;

public class TutorialVikingSystem extends VikingSystem{

    private TutorialPlayState tutorialPlayState;
    public TutorialVikingSystem(World world, TutorialPlayState tutorialPlayState) {
        super(world);
        this.tutorialPlayState = tutorialPlayState;
    }

    protected void processEntity(com.badlogic.ashley.core.Entity entity, float deltaTime) {
        super.processEntity(entity, deltaTime);

        VikingComponent viking = cmViking.get(entity);

        if(viking.getHealth() <= 0){
            System.out.println("SUNK!");

            tutorialPlayState.nextInterruption();

            tutorialPlayState.createTutorialPowerUp(new Vector2(VikingGame.SCREEN_WIDTH - 500,700), new HealthPowerUp());

        }
    }



}
