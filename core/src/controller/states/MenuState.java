package controller.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {
    private Texture background;
    private Texture tutorialPlayBtn;
    private Texture multiplayerPlayBtn;
    private Texture leaderboardBtn;
    private Texture muteSoundBtn;
    public MenuState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin(); //Rendering goes below here

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        tutorialPlayBtn.dispose();
        multiplayerPlayBtn.dispose();
        leaderboardBtn.dispose();
        muteSoundBtn.dispose();
    }
}
