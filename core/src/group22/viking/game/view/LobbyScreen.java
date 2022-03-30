package group22.viking.game.view;

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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.states.MenuState;
import group22.viking.game.controller.states.PlayState;
import group22.viking.game.models.Assets;
import group22.viking.game.view.components.CustomTextButton;

public class LobbyScreen implements Screen {


    private Stage stage;
    private VikingGame game;

    private Skin skin;

    private Image player1;
    private Image player2;

    private TextButton playButton;
    private TextButton exitButton;

    private ShapeRenderer shapeRenderer;

    /*
    constructor, do not load any actual files like pngs here. Instead do it in the show method
    */
    public LobbyScreen(VikingGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT,game.getCamera()));
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        System.out.println("PLAY");

        //delegate input Events to all Actors
        Gdx.input.setInputProcessor(stage);

        //skin
        this.skin = new Skin();
        this.skin.addRegions(Assets.getTextureAtlas("ui/uiskin.atlas"));
        this.skin.add("default-font", Assets.FONT48); //add font as default-font in json file
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));

        //todo get profile sprite information from user
        //todo set player2 once the second player has joined
        player1 = new Image(Assets.getTexture("img/WizardSprite.png"));
        player1.setPosition(0,0);
        player1.setWidth(VikingGame.SCREEN_WIDTH/2);
        player1.setHeight(VikingGame.SCREEN_HEIGHT);
        player2 = new Image(Assets.getTexture("img/KnightSprite.png"));
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
        game.getBatch().begin();
        Assets.FONT48.draw(game.getBatch(), "Lobby State", 20,80);
        Assets.FONT100.draw(game.getBatch(), "0", VikingGame.SCREEN_WIDTH/2-220,200);
        Assets.FONT100.draw(game.getBatch(), "0", VikingGame.SCREEN_WIDTH/2+150,200);
        game.getBatch().end();
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
        exitButton = new CustomTextButton("Exit", new Vector2(150,VikingGame.SCREEN_HEIGHT-200), new Vector2(120, 120));
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance(game).push(new MenuState(game));
            }
        });

        //PLAY button
        playButton = new CustomTextButton(
                "PLAY",
                new Vector2(VikingGame.SCREEN_WIDTH/2-playButton.getWidth()/2,VikingGame.SCREEN_HEIGHT/2-playButton.getHeight()/2),
                new Vector2(600, 150)
        );
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance(game).push(new PlayState(game, PlayState.Type.ONLINE));
            }
        });

        //add all buttons to stage
        stage.addActor(exitButton);
        stage.addActor(playButton);
    }
}
