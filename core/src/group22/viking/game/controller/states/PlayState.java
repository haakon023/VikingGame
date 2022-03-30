package group22.viking.game.controller.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.PlayScreen;

public class PlayState extends State {

    public enum Type {
        TUTORIAL,
        PRACTICE,
        ONLINE
    }

    private Type type;

    public PlayState(final VikingGame game, Type type) {
        super(new PlayScreen(game));
        this.type = type;
    }


    @Override
    protected void handleInput() {

    }

    public void update(float delta){

    }

    @Override
    public void render(SpriteBatch sb) {

    }


    @Override
    public void dispose() {
    }



}
