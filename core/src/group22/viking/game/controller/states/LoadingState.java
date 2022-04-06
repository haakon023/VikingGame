package group22.viking.game.controller.states;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;
import group22.viking.game.view.LoadingView;


public class LoadingState extends State {

    public LoadingState(final VikingGame game){
        super(new LoadingView(game.getBatch()), game);

        Assets.load();

    }


    @Override
    protected void handleInput() {

    }

    /*@Override
    public void update(float dt) {

    }*/


    public void render(float deltaTime) {}


    @Override
    public void dispose() {

    }
}
