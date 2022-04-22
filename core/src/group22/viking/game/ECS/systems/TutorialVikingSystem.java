package group22.viking.game.ECS.systems;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import group22.viking.game.ECS.components.VikingComponent;
import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.states.OfflinePlayState;
import group22.viking.game.controller.states.TutorialInterruptState;
import group22.viking.game.powerups.HealthPowerUp;

public class TutorialVikingSystem extends VikingSystem{

    OfflinePlayState offlinePlayState;
    public TutorialVikingSystem(World world, OfflinePlayState offlinePlayState) {
        super(world);
        this.offlinePlayState = offlinePlayState;
    }

    protected void processEntity(com.badlogic.ashley.core.Entity entity, float deltaTime) {
        super.processEntity(entity, deltaTime);

        VikingComponent viking = cmViking.get(entity);

        if(viking.getHealth() <= 0){
            System.out.println("SUNK!");

            offlinePlayState.nextTutorialInterruption();

            offlinePlayState.renderAPowerUp(new Vector2(VikingGame.SCREEN_WIDTH-300,100), new HealthPowerUp());

        }
    }



}
