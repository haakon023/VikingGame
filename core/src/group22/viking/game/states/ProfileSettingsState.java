package group22.viking.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ProfileSettingsState extends State{
    private Texture muteSoundBtn;
    public ProfileSettingsState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin(); //Render stuff goes below here

        sb.end();
    }

    @Override
    public void dispose() {

    }
}
