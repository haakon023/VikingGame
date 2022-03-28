package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.VikingGame;

public class PlayState implements Screen {

    private Stage stage;
    private final VikingGame vikingGame;



    /*
    constructor, do not load any actual files like pngs here. Instead do it in the show method
    */
    public PlayState(final VikingGame vikingGame) {
        this.vikingGame = vikingGame;
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT,vikingGame.camera));
    }



    @Override
    public void show() {

        System.out.println("PLAY");

        //delegate input Events to all Actors
        Gdx.input.setInputProcessor(stage);

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
        vikingGame.font48.draw(vikingGame.batch, "Play State", 20,80);
        vikingGame.batch.end();
    }

    public void update(float delta){

        //calls the act Method of any actor that is added to the stage
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
