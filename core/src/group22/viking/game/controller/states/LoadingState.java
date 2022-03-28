package group22.viking.game.controller.states;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.w3c.dom.Text;

import group22.viking.game.controller.VikingGame;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class LoadingState implements Screen {

    private final VikingGame vikingGame;
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private float progress;

    private Image vikingHeader;
    private Image stopHeader;

    //TODO ADD ALL FUTURE GAME ASSETS HERE (MUST BE ADDED TO ASSET MANAGER)
    private void queueAssets() {

        //image files
        //main menu
        vikingGame.assets.load("img/castle.png", Texture.class);
        vikingGame.assets.load("img/GoatIcon.png", Texture.class);
        vikingGame.assets.load("img/stopHeader.png", Texture.class);
        vikingGame.assets.load("img/vikingHeader.png", Texture.class);
        vikingGame.assets.load("img/vikingShip.png", Texture.class);
        vikingGame.assets.load("img/waveDark.png", Texture.class);
        vikingGame.assets.load("img/waveMedium.png", Texture.class);
        vikingGame.assets.load("img/waveLight.png", Texture.class);
        vikingGame.assets.load("img/waveVeryLight.png", Texture.class);
        //character sprites
        vikingGame.assets.load("img/KnightSprite.png", Texture.class);
        vikingGame.assets.load("img/KnightSpriteHead.png", Texture.class);
        vikingGame.assets.load("img/WarriorWomanSprite.png", Texture.class);
        vikingGame.assets.load("img/WarriorWomanSpriteHead.png", Texture.class);
        vikingGame.assets.load("img/WizardSprite.png", Texture.class);
        vikingGame.assets.load("img/WizardSpriteHead.png", Texture.class);

        //ui files
        vikingGame.assets.load("ui/uiskin.atlas", TextureAtlas.class);


    }

    /*
    constructor, do not load any actual files like pngs here. Instead do it in the show method
    */
    public LoadingState(final VikingGame vikingGame){
        this.vikingGame = vikingGame;
        this.shapeRenderer = new ShapeRenderer();
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT,vikingGame.camera));
        Gdx.gl.glClearColor(0.34f, 0.44f, 0.53f, 1);

    }



    @Override
    public void show() {
        System.out.println("LOADING");//for debug

        //delegate input Events to all Actors
        Gdx.input.setInputProcessor(stage);

        vikingHeader = new Image(new Texture("img/vikingHeader.png"));
        vikingHeader.setPosition(VikingGame.SCREEN_WIDTH/2-430,VikingGame.SCREEN_HEIGHT/2);
        vikingHeader.setWidth(660);
        vikingHeader.setHeight(200);
        stopHeader = new Image(new Texture("img/stopHeader.png"));
        stopHeader.setPosition(VikingGame.SCREEN_WIDTH/2,VikingGame.SCREEN_HEIGHT/2-130);
        stopHeader.setWidth(430);
        stopHeader.setHeight(300);
        stage.addActor(vikingHeader);
        stage.addActor(stopHeader);

        vikingHeader.addAction(sequence(alpha(0.0f), fadeIn(.3f)));
        stopHeader.addAction(sequence(alpha(0.0f),delay(.1f), fadeIn(0.01f)));

        this.progress = 0f;
        queueAssets();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        //calls draw for every actor it contains
        stage.draw();


        //shapeRenderer (use it like a batch)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(32,VikingGame.SCREEN_HEIGHT/4-8,
                VikingGame.SCREEN_WIDTH-64,16);
        shapeRenderer.setColor(Color.GRAY);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(32,VikingGame.SCREEN_HEIGHT/4-8,
                progress*(VikingGame.SCREEN_WIDTH-64),16);


        shapeRenderer.end();



        //run batch
        vikingGame.batch.begin();
        vikingGame.batch.end();
    }

    public void update(float delta){

        stage.act(delta);

        //TODO Needs to make progressbar smooth (lerp function does NOT work somehow)
        progress= MathUtils.lerp(progress, vikingGame.assets.getProgress(), 0.1f);

        //animate upwards motion of logo to match main menu position
        vikingHeader.addAction(moveTo(VikingGame.SCREEN_WIDTH/2-430,VikingGame.SCREEN_HEIGHT -250,.3f));
        stopHeader.addAction(moveTo(VikingGame.SCREEN_WIDTH/2,VikingGame.SCREEN_HEIGHT -380,.3f));

        //once done loading all assets, go to menu screen
        if(vikingGame.assets.update() && progress <= vikingGame.assets.getProgress()-.001f){
            vikingGame.setScreen(vikingGame.menuState);
        }


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
        shapeRenderer.dispose();
        stage.dispose();
    }



}
