package group22.viking.game.view;

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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.states.MenuState;
import group22.viking.game.models.Assets;
import group22.viking.game.view.components.CustomTextButton;
import group22.viking.game.view.components.CustomTextField;

public class ProfileScreen implements Screen {

    private Stage stage;

    //Images
    private Image profileImage;

    //Text Fields
    private CustomTextField nameField;

    //buttons
    private CustomTextButton exitButton;
    private CustomTextButton leftButton;
    private CustomTextButton rightButton;
    private CustomTextButton changeNameButton;

    private Skin skin;


    private VikingGame game;

    /*
    constructor, do not load any actual files like pngs here. Instead do it in the show method
    */
    public ProfileScreen(VikingGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH, VikingGame.SCREEN_HEIGHT, game.getCamera()));
    }

    @Override
    public void show() {
        System.out.println("Profile Settings");

        //delegate input Events to all Actors
        Gdx.input.setInputProcessor(stage);

        //image
        //todo set head according to users selection in database
        profileImage = new Image(Assets.getTexture("img/WizardSpriteHead.png"));
        profileImage.setWidth(400);
        profileImage.setHeight(400);
        profileImage.setPosition(VikingGame.SCREEN_WIDTH/4,
                VikingGame.SCREEN_HEIGHT-profileImage.getHeight()-150);
        profileImage.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        stage.addActor(profileImage);

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

        Assets.FONT48.draw(game.getBatch(), "Profile Settings State", 20,80);
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

        exitButton = new CustomTextButton("<", new Vector2(50, 150),
                new Vector2(150,VikingGame.SCREEN_HEIGHT-300));
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance(game).push(new MenuState(game));
            }
        });

        leftButton = new CustomTextButton(
                "<",
                new Vector2(profileImage.getX()-leftButton.getWidth(),
                VikingGame.SCREEN_HEIGHT-profileImage.getHeight()-150),
                new Vector2(80,profileImage.getHeight())
        );
        leftButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //todo cycle through images and save new icon to databse
            }
        });

        rightButton = new CustomTextButton(
                ">",
                new Vector2(profileImage.getX()+profileImage.getWidth(),
                VikingGame.SCREEN_HEIGHT-profileImage.getHeight()-150),
                new Vector2(80,profileImage.getHeight())
                );
        rightButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //todo cycle through images and save new icon to database
            }
        });

        nameField = new CustomTextField(
                "",
                new Vector2(profileImage.getX()+profileImage.getWidth()+rightButton.getWidth()+100,
                VikingGame.SCREEN_HEIGHT-profileImage.getHeight()-150),
                new Vector2(600, 150)
                );
        //todo set text to username
        nameField.setText("Caio");

        changeNameButton = new CustomTextButton(
                "Submit",
                new Vector2(profileImage.getX()+profileImage.getWidth()+rightButton.getWidth()+100+nameField.getWidth()+50,
                VikingGame.SCREEN_HEIGHT-profileImage.getHeight()-150),
                new Vector2(nameField.getWidth()/3,nameField.getHeight())
        );
        changeNameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //todo take name and save it to username in database
            }
        });


        stage.addActor(exitButton);
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(nameField);
        stage.addActor(changeNameButton);

    }
}
