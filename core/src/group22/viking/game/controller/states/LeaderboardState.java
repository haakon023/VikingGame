package group22.viking.game.controller.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.LeaderboardScreen;
import group22.viking.game.view.LeaderboardView;


public class LeaderboardState extends State {



    public LeaderboardState(VikingGame game) {
        super(new LeaderboardView(game.getBatch()), game);
    }

    @Override
    protected void handleInput() {

    }

    /*@Override
    public void update(float dt) {

    }*/

    @Override
    public void render(float deltaTime) {

    }

    @Override
    public void dispose() {
    }


}
