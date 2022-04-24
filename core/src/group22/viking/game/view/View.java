package group22.viking.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.VikingGame;

public abstract class View {

    protected SpriteBatch batch;
    protected final Stage stage;


    public View(SpriteBatch batch, Camera camera) {
        this.batch = batch;
        this.stage = new Stage(new FitViewport(VikingGame.getInstance().SCREEN_WIDTH, VikingGame.getInstance().SCREEN_HEIGHT, camera));
    }

    /**
     * Initialize view
     */
    public abstract void init();

    public abstract void runInitialAnimations();

    /**
     * Render the view.
     *
     * @param deltaTime time difference to previous rendering.
     */
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.34f, 0.44f, 0.53f, 1f);

        batch.begin();
        drawElements(deltaTime);
        batch.end();
    }

    /**
     * Called by render method: draw view elements.
     *
     * @param deltaTime time difference to previous rendering.
     */
    abstract void drawElements(float deltaTime);

    public void dispose() {}

    public Stage getStage() {
        return stage;
    }
}
