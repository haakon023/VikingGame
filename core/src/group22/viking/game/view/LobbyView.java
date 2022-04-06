package group22.viking.game.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LobbyView extends View {

    public LobbyView(SpriteBatch batch) {
        super(batch);

        this.init();
    }

    @Override
    public void init() {


        runInitialAnimations();
    }

    @Override
    public void runInitialAnimations() {

    }

    @Override
    void drawElements(float deltaTime) {

    }
}
