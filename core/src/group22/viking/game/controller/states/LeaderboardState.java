package group22.viking.game.controller.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.LeaderboardScreen;


public class LeaderboardState extends State {



    public LeaderboardState(VikingGame game) {
        super(new LeaderboardScreen(game));
    }

    @Override
    protected void handleInput() {

    }

    /*@Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {

    }*/

    @Override
    public void dispose() {
    }


}
