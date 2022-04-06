package group22.viking.game.controller.states;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.controller.GameStateManager;

public abstract class State {       //TODO: implements Screen ; or has screen?
    //protected OrthographicCamera cam; Do we want this?
    protected Vector3 mouse;
    protected Screen screen;

    protected State(Screen screen){
        //cam = new OrthographicCamera(); Do we want this?
        mouse = new Vector3();
        this.screen = screen;
    }

    protected abstract void handleInput();

    public abstract void render(float deltaTime);

    public abstract void dispose();

    public Screen getScreen() {
        return screen;
    }
}
