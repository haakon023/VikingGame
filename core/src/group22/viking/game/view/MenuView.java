package group22.viking.game.view;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuView extends View {
    public MenuView(SpriteBatch batch, Camera camera) {
        super(batch);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        super.render(deltaTime);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    void drawElements(float deltaTime) {
        System.out.println("DRAW ELEMENTS");
    }
}
