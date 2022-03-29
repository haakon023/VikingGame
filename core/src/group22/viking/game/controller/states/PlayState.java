package group22.viking.game.controller.states;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.VikingGame;

public class PlayState implements Screen {

    private Stage stage;
    private final VikingGame vikingGame;

    //game images
    private Image islandImage;
    private Image monasteryImage;
    private Image waveBottomImage;
    private Image waveTopImage;
    private Image oceanBackImage;
    private Image oceanTopImage;

    private Skin skin;

    private TextButton exitBtn;



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

        oceanBackImage = new Image(vikingGame.assets.get("img/OceanBack.png", Texture.class));
        oceanBackImage.setPosition(0,0);
        oceanBackImage.setWidth(VikingGame.SCREEN_WIDTH);
        oceanBackImage.setHeight(VikingGame.SCREEN_HEIGHT);

        oceanTopImage = new Image(vikingGame.assets.get("img/OceanTop.png", Texture.class));
        oceanTopImage.setPosition(0,0);
        oceanTopImage.setWidth(VikingGame.SCREEN_WIDTH);
        oceanTopImage.setHeight(VikingGame.SCREEN_HEIGHT);

        waveBottomImage = new Image(vikingGame.assets.get("img/WaveBottom.png", Texture.class));
        waveBottomImage.setWidth(VikingGame.SCREEN_WIDTH/4+80);
        waveBottomImage.setHeight(VikingGame.SCREEN_HEIGHT/4+80);
        waveBottomImage.setPosition(VikingGame.SCREEN_WIDTH/2-waveBottomImage.getWidth()/2,
                VikingGame.SCREEN_HEIGHT/2-waveBottomImage.getHeight()/2-35);

        islandImage = new Image(vikingGame.assets.get("img/Island.png", Texture.class));
        islandImage.setWidth(VikingGame.SCREEN_WIDTH/4);
        islandImage.setHeight(VikingGame.SCREEN_HEIGHT/4);
        islandImage.setPosition(VikingGame.SCREEN_WIDTH/2-islandImage.getWidth()/2,
                VikingGame.SCREEN_HEIGHT/2-islandImage.getHeight()/2);

        waveTopImage = new Image(vikingGame.assets.get("img/WaveTop.png", Texture.class));
        waveTopImage.setWidth(VikingGame.SCREEN_WIDTH/4);
        waveTopImage.setHeight(VikingGame.SCREEN_HEIGHT/4-90);
        waveTopImage.setPosition(VikingGame.SCREEN_WIDTH/2-waveTopImage.getWidth()/2,
                VikingGame.SCREEN_HEIGHT/2-waveTopImage.getHeight()/2-50);

        monasteryImage = new Image(vikingGame.assets.get("img/Monastery.png", Texture.class));
        monasteryImage.setWidth(VikingGame.SCREEN_WIDTH/4);
        monasteryImage.setHeight(VikingGame.SCREEN_HEIGHT/4+80);
        monasteryImage.setPosition(VikingGame.SCREEN_WIDTH/2-monasteryImage.getWidth()/2,
                VikingGame.SCREEN_HEIGHT/2-monasteryImage.getHeight()/2+80);

        stage.addActor(oceanBackImage);
        stage.addActor(oceanTopImage);
        stage.addActor(waveBottomImage);
        stage.addActor(islandImage);
        stage.addActor(waveTopImage);
        stage.addActor(monasteryImage);




        //skin
        this.skin = new Skin();
        this.skin.addRegions(vikingGame.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font",vikingGame.font48); //add font as default-font in json file
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

    private void initButtons() {
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

        stage.addActor(exitBtn);
    }

}
