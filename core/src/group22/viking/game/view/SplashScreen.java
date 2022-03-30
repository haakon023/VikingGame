package group22.viking.game.view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.states.LoadingState;
import group22.viking.game.models.Assets;

public class SplashScreen implements Screen {

    private Stage stage;
    private Image goatIcon;
    private int counter = 0;
    private VikingGame game;

    /*
    constructor, do not load any actual files like pngs here. Instead do it in the show method
    */
    public SplashScreen(VikingGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT, game.getCamera()));
    }

    @Override
    public void show() {

        System.out.println("SPLASH");

        //delegate input Events to all Actors
        Gdx.input.setInputProcessor(stage);

        //runnable
        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("transitioning");
                //TODO:
                GameStateManager.getInstance(game).push(new LoadingState(game));
            }
        };

        //Goat image
        goatIcon = new Image(new Texture("img/GoatIcon.png"));
        goatIcon.setWidth(200);
        goatIcon.setHeight(200);
        goatIcon.setOrigin(goatIcon.getWidth()/2,goatIcon.getHeight()/2);

        goatIcon.setPosition(VikingGame.SCREEN_WIDTH/2-100,VikingGame.SCREEN_HEIGHT+100);
        goatIcon.addAction(sequence(alpha(0),scaleTo(.1f,.1f),
                parallel(fadeIn(2f, Interpolation.pow2),
                        scaleTo(2f,2f,2.5f,Interpolation.pow5),
                        moveTo(VikingGame.SCREEN_WIDTH/2-100, VikingGame.SCREEN_HEIGHT/2-100,2f,Interpolation.swing)),
                delay(1.5f), fadeOut(1.25f), run(transitionRunnable)));

        stage.addActor(goatIcon);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.34f, 0.44f, 0.53f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        //calls draw for every actor it contains
        stage.draw();

        //BEGIN
        game.getBatch().begin();
        counter++;
        // Assets.FONT48.draw(game.getBatch(), "Version: 1.0.0", 20,80);
        VikingGame.font48.draw(game.getBatch(), "Version: 1.0.0", 20,80);
        game.getBatch().end();
    }

    public void update(float delta){
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
