package group22.viking.game.controller.states;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.VikingGame;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class ProfileSettingsState implements Screen {

    private Stage stage;
    private final VikingGame vikingGame;

    //Images
    private Image profileImage;

    //Text Fields
    private TextField nameField;

    //buttons
    private TextButton exitBtn;
    private TextButton leftBtn;
    private TextButton rightBtn;
    private TextButton changeNameButton;

    private Skin skin;





    /*
    constructor, do not load any actual files like pngs here. Instead do it in the show method
    */
    public ProfileSettingsState(final VikingGame vikingGame) {
        this.vikingGame = vikingGame;
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT,vikingGame.camera));
    }



    @Override
    public void show() {

        System.out.println("Profile Settings");

        //delegate input Events to all Actors
        Gdx.input.setInputProcessor(stage);

        //image
        //todo set head according to users selection in database
        profileImage = new Image(vikingGame.assets.get("img/WizardSpriteHead.png", Texture.class));
        profileImage.setWidth(400);
        profileImage.setHeight(400);
        profileImage.setPosition(VikingGame.SCREEN_WIDTH/4,
                VikingGame.SCREEN_HEIGHT-profileImage.getHeight()-150);
        profileImage.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        stage.addActor(profileImage);

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

        vikingGame.font48.draw(vikingGame.batch, "Profile Settings State", 20,80);
        vikingGame.batch.end();
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
        exitBtn = new TextButton("<", skin, "default");
        exitBtn.setPosition(50,150);
        exitBtn.setSize(150,VikingGame.SCREEN_HEIGHT-300);
        exitBtn.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        exitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                vikingGame.setScreen(vikingGame.menuState);
            }
        });

        leftBtn = new TextButton("<", skin, "default");
        leftBtn.setSize(80,profileImage.getHeight());
        leftBtn.setPosition(profileImage.getX()-leftBtn.getWidth(),
                VikingGame.SCREEN_HEIGHT-profileImage.getHeight()-150);
        leftBtn.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        leftBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //todo cycle through images and save new icon to databse
            }
        });

        rightBtn = new TextButton(">", skin, "default");
        rightBtn.setSize(80,profileImage.getHeight());
        rightBtn.setPosition(profileImage.getX()+profileImage.getWidth(),
                VikingGame.SCREEN_HEIGHT-profileImage.getHeight()-150);
        rightBtn.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        rightBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //todo cycle through images and save new icon to database
            }
        });

        nameField = new TextField("", skin);
        //todo set text to username
        nameField.setText("Caio");
        nameField.setHeight(150);
        nameField.setWidth(600);
        nameField.setPosition(profileImage.getX()+profileImage.getWidth()+rightBtn.getWidth()+100,
                VikingGame.SCREEN_HEIGHT-profileImage.getHeight()-150);
        nameField.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));


        changeNameButton = new TextButton("Submit", skin, "default");
        changeNameButton.setSize(nameField.getWidth()/3,nameField.getHeight());
        changeNameButton.setPosition(
                profileImage.getX()+profileImage.getWidth()+rightBtn.getWidth()+100+nameField.getWidth()+50,
                VikingGame.SCREEN_HEIGHT-profileImage.getHeight()-150);
        changeNameButton.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        changeNameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //todo take name and save it to username in database
            }
        });


        stage.addActor(exitBtn);
        stage.addActor(leftBtn);
        stage.addActor(rightBtn);
        stage.addActor(nameField);
        stage.addActor(changeNameButton);

    }

}
