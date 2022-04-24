package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import group22.viking.game.input.InputController;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.View;

public abstract class State {
    //protected OrthographicCamera cam; Do we want this?
    protected VikingGame game;
    protected View view;

    protected State(View view, VikingGame game){
        this.game = game;
        this.view = view;

        Gdx.input.setInputProcessor(view.getStage());
    }

    public void reinitialize() {
        view.runInitialAnimations();
        Gdx.input.setInputProcessor(view.getStage());
    }

    public void pause() {}

    public void render(float deltaTime) {
        view.render(deltaTime);
    }

    public abstract void dispose();

    public VikingGame getGame() {
        return game;
    }
}
