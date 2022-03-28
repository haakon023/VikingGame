package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.VikingGame;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashState implements Screen {

    private Stage stage;
    private final VikingGame vikingGame;
    private Image goatIcon;
    private int counter = 0;


    /*
    constructor, do not load any actual files like pngs here. Instead do it in the show method
    */
    public SplashState(final VikingGame vikingGame) {
        this.vikingGame = vikingGame;
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT,vikingGame.camera));
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
                vikingGame.setScreen(vikingGame.loadingState);
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
        vikingGame.batch.begin();
        counter++;
        vikingGame.font48.draw(vikingGame.batch, "Version: 1.0.0", 20,80);
        vikingGame.batch.end();
    }

    public void update(float delta){

        //calls the act Method of any actor that is added to the stage
        stage.act(delta);
        //if(counter == 400){
           // vikingGame.setScreen(new LoadingState(vikingGame));
       // }


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
