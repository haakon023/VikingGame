package group22.viking.game.controller.states;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.View;

public abstract class State {       //TODO: implements Screen ; or has screen?
    //protected OrthographicCamera cam; Do we want this?
    protected Vector3 mouse;
    protected VikingGame game;
    protected View view;

    protected State(View view, VikingGame game){
        //cam = new OrthographicCamera(); Do we want this?
        mouse = new Vector3();
        this.game = game;
        this.view = view;
    }

    protected abstract void handleInput();

    public abstract void render(float deltaTime);

    public abstract void dispose();

}
