package group22.viking.game.controller.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.LobbyScreen;
import group22.viking.game.view.LobbyView;


public class LobbyState extends State {


    public LobbyState(final VikingGame game) {
        super(new LobbyView(game.getBatch()), game);
    }


    @Override
    protected void handleInput() {

    }

    /*@Override
    public void update(float dt) {

    }*/

    @Override
    public void render(float deltaTime) {}



    @Override
    public void dispose() {

    }


}
