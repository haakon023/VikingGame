package group22.viking.game.controller.states;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.math.Interpolation;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.view.SplashView;


public class SplashState extends State {


    public SplashState(VikingGame game) {
        super(new SplashView(game.getBatch(), game.getCamera()), game);

        // set transition to change from SplashState to LoadingState
        ((SplashView) view).getGoatIcon().addAction(sequence(alpha(0),scaleTo(.1f,.1f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(2f,2f,2.5f,Interpolation.pow5),
                        moveTo(VikingGame.SCREEN_WIDTH/2-100, VikingGame.SCREEN_HEIGHT/2-100,2f,Interpolation.swing)),
                delay(1.5f), fadeOut(1.25f), run(createTransitionRunnable())));

        view.init();
    }

    @Override
    protected void handleInput() {

    }

    public void update(float delta){

    }

    @Override
    public void dispose() {

    }

    private Runnable createTransitionRunnable() {
        //runnable
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("transitioning");
                GameStateManager.getInstance().push(new LoadingState(game));
            }
        };
    }
}
