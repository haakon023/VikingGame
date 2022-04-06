package group22.viking.game.view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.ECS.EntityFactory;
import group22.viking.game.ECS.RenderingSystem;
import group22.viking.game.ECS.components.PlayerComponent;
import group22.viking.game.ECS.components.StateComponent;
import group22.viking.game.ECS.components.TextureComponent;
import group22.viking.game.ECS.components.TransformComponent;
import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.states.MenuState;
import group22.viking.game.controller.states.PlayState;
import group22.viking.game.controller.states.State;
import group22.viking.game.models.Assets;
import group22.viking.game.view.components.CustomTextButton;

public class PlayScreen implements Screen {

    private Stage stage;

    private PlayState.Type type;

    //game images
    private Image islandImage;
    private Image monasteryImage;
    private Image waveBottomImage;
    private Image waveTopImage;
    private Image oceanBackImage;
    private Image oceanTopImage;

    private Skin skin;

    private CustomTextButton exitButton;

    private VikingGame game;

    ///private PlayState state;
    ///private RenderingSystem renderingSystem;

    /*
    constructor, do not load any actual files like pngs here. Instead do it in the show method
    */
    public PlayScreen(VikingGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH, VikingGame.SCREEN_HEIGHT, game.getCamera()));
        //this.state = state;
        //this.renderingSystem = renderingSystem;
    }

    public void buildBackground(EntityFactory factory) {
        factory.createTexture(
                Assets.OCEANBACK,
                new Vector3(0,0,-1),
                new Vector2(VikingGame.SCREEN_HEIGHT, VikingGame.SCREEN_WIDTH)
        );
        // ...


    }

    // depreciated:
    public void show() {
        System.out.println("PLAY");


        //delegate input Events to all Actors
        //Gdx.input.setInputProcessor(stage);         //TODO: this should probably also be our ECS InputController, right?


        
        /*oceanBackImage = new Image(Assets.getTexture("img/OceanBack.png"));
        oceanBackImage.setPosition(0,0);
        oceanBackImage.setWidth(VikingGame.SCREEN_WIDTH);
        oceanBackImage.setHeight(VikingGame.SCREEN_HEIGHT);

        oceanTopImage = new Image(Assets.getTexture("img/OceanTop.png"));
        oceanTopImage.setPosition(0,0);
        oceanTopImage.setWidth(VikingGame.SCREEN_WIDTH);
        oceanTopImage.setHeight(VikingGame.SCREEN_HEIGHT);

        waveBottomImage = new Image(Assets.getTexture("img/WaveBottom.png"));
        waveBottomImage.setWidth(VikingGame.SCREEN_WIDTH/4+80);
        waveBottomImage.setHeight(VikingGame.SCREEN_HEIGHT/4+80);
        waveBottomImage.setPosition(VikingGame.SCREEN_WIDTH/2-waveBottomImage.getWidth()/2,
                VikingGame.SCREEN_HEIGHT/2-waveBottomImage.getHeight()/2-35);

        islandImage = new Image(Assets.getTexture("img/Island.png"));
        islandImage.setWidth(VikingGame.SCREEN_WIDTH/4);
        islandImage.setHeight(VikingGame.SCREEN_HEIGHT/4);
        islandImage.setPosition(VikingGame.SCREEN_WIDTH/2-islandImage.getWidth()/2,
                VikingGame.SCREEN_HEIGHT/2-islandImage.getHeight()/2);

        waveTopImage = new Image(Assets.getTexture("img/WaveTop.png"));
        waveTopImage.setWidth(VikingGame.SCREEN_WIDTH/4);
        waveTopImage.setHeight(VikingGame.SCREEN_HEIGHT/4-90);
        waveTopImage.setPosition(VikingGame.SCREEN_WIDTH/2-waveTopImage.getWidth()/2,
                VikingGame.SCREEN_HEIGHT/2-waveTopImage.getHeight()/2-50);

        monasteryImage = new Image(Assets.getTexture("img/Monastery.png"));
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
        this.skin.addRegions(Assets.getTextureAtlas("ui/uiskin.atlas"));
        this.skin.add("default-font", Assets.FONT48); //add font as default-font in json file
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));

        initButtons();
*/
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
        exitButton = new CustomTextButton(
                "Exit",
                new Vector2(150,VikingGame.SCREEN_HEIGHT-200),
                new Vector2(120, 120)
        );
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //TODO: this should be in the state
                GameStateManager.getInstance(game).push(new MenuState(game));
            }
        });


        stage.addActor(exitButton);
    }
}
