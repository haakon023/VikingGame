package group22.viking.game.controller.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.controller.GameStateManager;

public abstract class State {
    //protected OrthographicCamera cam; Do we want this?
    protected Vector3 mouse;
    protected GameStateManager gsm;

    protected State(GameStateManager gsm){
        this.gsm = gsm;
        //cam = new OrthographicCamera(); Do we want this?
        mouse = new Vector3();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}
