package group22.viking.game.controller.states;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.graalvm.compiler.nodes.virtual.VirtualInstanceNode;

import group22.viking.game.controller.VikingGame;

public class LobbyState implements Screen {

    private Stage stage;
    private final VikingGame vikingGame;

    private Skin skin;

    private Image player1;
    private Image player2;

    private TextButton playBtn;
    private TextButton exitBtn;

    private ShapeRenderer shapeRenderer;





    /*
    constructor, do not load any actual files like pngs here. Instead do it in the show method
    */
    public LobbyState(final VikingGame vikingGame) {
        this.vikingGame = vikingGame;
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT,vikingGame.camera));
        this.shapeRenderer = new ShapeRenderer();
    }



    @Override
    public void show() {

        System.out.println("PLAY");

        //delegate input Events to all Actors
        Gdx.input.setInputProcessor(stage);

        //skin
        this.skin = new Skin();
        this.skin.addRegions(vikingGame.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font",vikingGame.font48); //add font as default-font in json file
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));

        //todo get profile sprite information from user
        //todo set player2 once the second player has joined
        player1 = new Image(vikingGame.assets.get("img/WizardSprite.png", Texture.class));
        player1.setPosition(0,0);
        player1.setWidth(VikingGame.SCREEN_WIDTH/2);
        player1.setHeight(VikingGame.SCREEN_HEIGHT);
        player2 = new Image(vikingGame.assets.get("img/KnightSprite.png", Texture.class));
        player2.setPosition(VikingGame.SCREEN_WIDTH/2,0);
        player2.setWidth(VikingGame.SCREEN_WIDTH/2);
        player2.setHeight(VikingGame.SCREEN_HEIGHT);

        stage.addActor(player1);
        stage.addActor(player2);


        initButtons();



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.34f, 0.44f, 0.53f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);



        //shapeRenderer (use it like a batch)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(VikingGame.SCREEN_WIDTH/2-15,0,
                30,VikingGame.SCREEN_HEIGHT);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.end();

        //calls draw for every actor it contains
        stage.draw();


        //BEGIN
        vikingGame.batch.begin();
        vikingGame.font48.draw(vikingGame.batch, "Lobby State", 20,80);
        vikingGame.font100.draw(vikingGame.batch, "0", VikingGame.SCREEN_WIDTH/2-220,200);
        vikingGame.font100.draw(vikingGame.batch, "0", VikingGame.SCREEN_WIDTH/2+150,200);
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

    private void initButtons(){

        //EXIT Button
        exitBtn = new TextButton("Exit", skin, "default");
        exitBtn.setPosition(150,VikingGame.SCREEN_HEIGHT-200);
        exitBtn.setSize(120,120);
        exitBtn.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        exitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                vikingGame.setScreen(vikingGame.menuState);
            }
        });

        //PLAY button
        playBtn = new TextButton("PLAY", skin, "default");
        playBtn.setSize(600,150);
        playBtn.setPosition(VikingGame.SCREEN_WIDTH/2-playBtn.getWidth()/2,VikingGame.SCREEN_HEIGHT/2-playBtn.getHeight()/2);
        playBtn.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        playBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                vikingGame.setScreen(vikingGame.playState);
            }
        });

        //add all buttons to stage
        stage.addActor(exitBtn);
        stage.addActor(playBtn);
    }
}
