package group22.viking.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.states.MenuState;
import group22.viking.game.models.Assets;
import group22.viking.game.view.components.CustomTextButton;

public class LeaderboardScreen implements Screen {

    private Stage stage;

    private Skin skin;

    //buttons
    private TextButton exitButton;

    private VikingGame game;


    /*
    constructor, do not load any actual files like pngs here. Instead do it in the show method
    */
    public LeaderboardScreen(VikingGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT,game.getCamera()));
    }

    @Override
    public void show() {
        System.out.println("Profile Settings");

        //delegate input Events to all Actors
        Gdx.input.setInputProcessor(stage);

        //skin
        this.skin = new Skin();
        this.skin.addRegions(Assets.getTextureAtlas("ui/uiskin.atlas"));
        this.skin.add("default-font",Assets.FONT48); //add font as default-font in json file
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));

        initButtons();
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

        Assets.FONT48.draw(game.getBatch(), "Leaderboard State", 20,80);
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

    private void initButtons() {

        /*
        exitButton = new CustomTextButton("<", new Vector2(50, 150), new Vector2(150,VikingGame.SCREEN_HEIGHT-300));
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance(game).push(new MenuState(game));
            }
        });
        */

        stage.addActor(exitButton);
    }
}
