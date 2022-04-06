package group22.viking.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class View {

    protected SpriteBatch batch;

    public View(SpriteBatch batch) {
        this.batch = batch;
    }

    /**
     * call show() every time a view is shown again, e.g. after changing views
     */
    abstract void show();

    /**
     *
     * @param deltaTime
     */
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.34f, 0.44f, 0.53f, 1);

        batch.begin();
        drawElements(deltaTime);
        batch.end();
    }

    abstract void drawElements(float deltaTime);

    public void dispose() {}

}
