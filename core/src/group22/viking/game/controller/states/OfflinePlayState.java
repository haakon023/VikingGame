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
    }

    @Override
    public void handleLocalDeath() {
        System.out.println("dead");
        GameStateManager.getInstance().set(new GameOverState(game, false));
    }
}
