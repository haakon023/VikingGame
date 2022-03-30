package group22.viking.game.controller.states;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.SplashScreen;


public class SplashState extends State {

    public SplashState(VikingGame game) {
        super(new SplashScreen(game));
    }

    @Override
    protected void handleInput() {

    }

    public void update(float delta){

    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {

    }
}
