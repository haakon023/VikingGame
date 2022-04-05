package group22.viking.game.controller.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.LoadingScreen;


public class LoadingState extends State {

    public LoadingState(final VikingGame game){
        super(new LoadingScreen(game));

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
