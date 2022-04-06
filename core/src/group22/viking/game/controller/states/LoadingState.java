package group22.viking.game.controller.states;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

import com.badlogic.gdx.math.MathUtils;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;
import group22.viking.game.view.LoadingView;
import group22.viking.game.view.SplashView;


public class LoadingState extends State {

    private float progress;

    public LoadingState(final VikingGame game){
        super(new LoadingView(game.getBatch(), game.getCamera()), game);

        Assets.load();

        ((LoadingView) view).show();
    }


    @Override
    protected void handleInput() {

    }

    /*@Override
    public void update(float dt) {

    }*/


    public void render(float deltaTime) {
        //TODO Needs to make progressbar smooth (lerp function does NOT work somehow)
        progress = MathUtils.lerp(progress, Assets.getProgress(), 0.1f);


        //once done loading all assets, go to menu screen
        if(Assets.update() && progress <= Assets.getProgress()-.001f){
            GameStateManager.getInstance(game).push(new MenuState(game));
        }

        view.render(deltaTime);
    }


    @Override
    public void dispose() {

    }

    public void update() {

    }

    public float updateProgressBar() {
        return Assets.getProgress();
    }



}
