package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.VikingGame;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MenuState implements Screen {

    private final VikingGame vikingGame;

    //private Window leaderboardPopUp = new Window();

    //stage
    private Stage stage;
    
    //Skin
    private Skin skin;


    //header
    private Image vikingHeader;
    private Image stopHeader;


    //for background scene
    private Image waveDark;
    private Image waveMedium;
    private Image waveLight;
    private Image waveVeryLight;
    private Image vikingShip;
    private Image castle;

    //for wave movement
    private float x1 = 0;
    private float x2 = 0;

    //for boat movement
    private float x3 = 0;
    private float y3 = 0;
    boolean movingRight = true;

    //for castle movement
    private float y4 = 0;
    private boolean movingUp = true;




    private Texture title;

    //main Buttons
    private TextButton tutorialBtn;
    private TextButton practiceBtn;
    private TextField joinTextField;
    private TextButton joinBtn;
    private TextButton hostBtn;
    //private TextButton profileBtn;

    //small buttons
    private TextButton leaderboardBtn;
    private TextButton unmutedBtn;
    private TextButton exitBtn;
    //private TextButton mutedBtn;

    //for profile Icon
    private ShapeRenderer shapeRenderer;
    private ImageButton profileBtn;
    TextureRegion profileTextureRegion;
    TextureRegionDrawable profileTextureRegionDrawable;

    /*
    constructor, do not load any actual files like pngs here. Instead do it in the show method
    */
    public MenuState(final VikingGame vikingGame) {
        profileTextureRegion = new TextureRegion();
        profileTextureRegionDrawable = new TextureRegionDrawable();
        this.vikingGame = vikingGame;
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT,vikingGame.camera));
        Gdx.gl.glClearColor(0.34f, 0.44f, 0.53f, 1);
        this.shapeRenderer = new ShapeRenderer();


    }




    @Override
    public void show() {
        //delegate input Events to all Actors
        Gdx.input.setInputProcessor(stage);

        //stage clear to make sure there aren't any further animations
        stage.clear();
        
        
        //header
        vikingHeader = new Image(vikingGame.assets.get("img/vikingHeader.png",Texture.class));
        vikingHeader.setPosition(VikingGame.SCREEN_WIDTH/2-430,VikingGame.SCREEN_HEIGHT -250);
        vikingHeader.setWidth(660);
        vikingHeader.setHeight(200);
        stopHeader = new Image(vikingGame.assets.get("img/stopHeader.png",Texture.class));
        stopHeader.setPosition(VikingGame.SCREEN_WIDTH/2,VikingGame.SCREEN_HEIGHT -380);
        stopHeader.setWidth(430);
        stopHeader.setHeight(300);
        stage.addActor(vikingHeader);
        stage.addActor(stopHeader);



        //background
        waveDark = new Image(vikingGame.assets.get("img/waveDark.png",Texture.class));
        castle = new Image(vikingGame.assets.get("img/castle.png",Texture.class));
        castle.setWidth(600);
        castle.setHeight(500);
        waveMedium = new Image(vikingGame.assets.get("img/waveMedium.png",Texture.class));
        vikingShip = new Image(vikingGame.assets.get("img/vikingShip.png",Texture.class));
        vikingShip.setWidth(600);
        vikingShip.setHeight(500);
        waveLight = new Image(vikingGame.assets.get("img/waveLight.png",Texture.class));
        waveVeryLight = new Image(vikingGame.assets.get("img/waveVeryLight.png",Texture.class));
        stage.addActor(waveDark);
        stage.addActor(castle);
        stage.addActor(waveMedium);
        stage.addActor(vikingShip);
        stage.addActor(waveLight);
        stage.addActor(waveVeryLight);





        //skin
        this.skin = new Skin();
        this.skin.addRegions(vikingGame.assets.get("ui/uiskin.atlas",TextureAtlas.class));
        this.skin.add("default-font",vikingGame.font48); //add font as default-font in json file
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));
        
        initButtons();



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        //calls draw for every actor it contains
        stage.draw();

        //shapeRenderer (use it like a batch)
        //Gdx.gl.glEnable(GL20.GL_BLEND);
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //shapeRenderer.circle(VikingGame.SCREEN_WIDTH/2,VikingGame.SCREEN_HEIGHT/2-80,
        //        180);
        //shapeRenderer.setColor(new Color(0.25f, 0.33f, 0.41f, 0.8f));

        //shapeRenderer.end();
        //Gdx.gl.glDisable(GL20.GL_BLEND);





        //BEGIN
        vikingGame.batch.begin();

        //background
        waveDark.setPosition(-600 +(x1/3),0);
        //waveDark.addAction(sequence(moveTo(-500,0,3f), Animation.PlayMode.LOOP);
        castle.setPosition(1500,290+(y4/10));
        waveMedium.setPosition(-300+(x2/3),0);
        vikingShip.setPosition(200+(x3/3),200+(y3/6));
        waveLight.setPosition(-600+(x1/3),0);
        waveVeryLight.setPosition(-400+(x2/3),0);


        x1++;
        x2 += 2;
        if((x1/3) % 296 == 0){x1 = 0;}
        if((x2/3) % 296 == 0){x2 = 0;}
        //rock ship
        if(movingRight){
            x3++;
            y3++;
            if(x3 == 15){
                movingRight = false;
            }
        }else{
            x3--;
            y3--;
            if(x3 == -15){
                movingRight = true;
            }
        }
        //move island
        if(movingUp){

            y4++;
            if(x3 == 7){
                movingUp = false;
            }
        }else{
            y4--;
            if(x3 == -7){
                movingUp = true;
            }
        }
        vikingGame.batch.end();

    }

    public void update(float delta){

        //calls the act Method of any actor that is added to the stage
        stage.act(delta);

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,false);
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

        //background.dispose();
        title.dispose();
        stage.dispose();
    }

    private void initButtons() {
        tutorialBtn = new TextButton("Tutorial", skin, "default");
        tutorialBtn.setPosition(150,VikingGame.SCREEN_HEIGHT/2+80-50);
        tutorialBtn.setSize(700,150);
        tutorialBtn.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        tutorialBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                vikingGame.setScreen(vikingGame.playState);
            }
        });

        practiceBtn = new TextButton("Practice", skin, "default");
        practiceBtn.setPosition(150,VikingGame.SCREEN_HEIGHT/2-80-150-50);
        practiceBtn.setSize(700,150);
        practiceBtn.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        practiceBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                vikingGame.setScreen(vikingGame.playState);
            }
        });

        joinTextField = new TextField("Enter Pin", skin);
        joinTextField.setHeight(150);
        joinTextField.setWidth(530);
        joinTextField.setPosition(VikingGame.SCREEN_WIDTH/2+(VikingGame.SCREEN_WIDTH/2-700-150),
                VikingGame.SCREEN_HEIGHT/2+80-50);
        joinTextField.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));


        joinBtn = new TextButton("Join", skin, "default");
        joinBtn.setPosition(VikingGame.SCREEN_WIDTH/2+(VikingGame.SCREEN_WIDTH/2-700-150)+joinTextField.getWidth() +20,
                VikingGame.SCREEN_HEIGHT/2+80-50);
        joinBtn.setSize(150,150);
        joinBtn.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        joinBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                vikingGame.setScreen(vikingGame.lobbyState);
            }
        });

        hostBtn = new TextButton("Host", skin, "default");
        hostBtn.setPosition(VikingGame.SCREEN_WIDTH/2+(VikingGame.SCREEN_WIDTH/2-700-150),VikingGame.SCREEN_HEIGHT/2-80-150-50);
        hostBtn.setSize(700,150);
        hostBtn.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        hostBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                vikingGame.setScreen(vikingGame.lobbyState);
            }
        });

        //todo set the texture to the user specific profile image
        profileTextureRegion= new TextureRegion(vikingGame.assets.get("img/WizardSpriteHead.png",Texture.class));
        profileTextureRegionDrawable = new TextureRegionDrawable(profileTextureRegion);
        profileBtn = new ImageButton(profileTextureRegionDrawable);
        profileBtn.setSize(500,500);
        profileBtn.setPosition(VikingGame.SCREEN_WIDTH/2-profileBtn.getWidth()/2,
                VikingGame.SCREEN_HEIGHT/2-profileBtn.getHeight()/2-80);

        profileBtn.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        profileBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                vikingGame.setScreen(vikingGame.profileSettingsState);
            }
        });

        leaderboardBtn = new TextButton("L", skin, "default");
        leaderboardBtn.setPosition(VikingGame.SCREEN_WIDTH-120-60-120-60,50);
        leaderboardBtn.setSize(120,120);
        leaderboardBtn.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        leaderboardBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                vikingGame.setScreen(vikingGame.leaderboardState);
            }
        });

        unmutedBtn = new TextButton("U", skin, "default");
        unmutedBtn.setPosition(VikingGame.SCREEN_WIDTH-120-60,50);
        unmutedBtn.setSize(120,120);
        unmutedBtn.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        unmutedBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //todo
            }
        });

        exitBtn = new TextButton("Exit", skin, "default");
        exitBtn.setPosition(150,VikingGame.SCREEN_HEIGHT-200);
        exitBtn.setSize(120,120);
        exitBtn.addAction(sequence(alpha(0),parallel(fadeIn(0.5f),moveBy(0,-20,.5f, Interpolation.pow5Out))));
        exitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

        //add all buttons as actors
        stage.addActor(tutorialBtn);
        stage.addActor(practiceBtn);
        stage.addActor(joinTextField);
        stage.addActor(joinBtn);
        stage.addActor(hostBtn);
        stage.addActor(profileBtn);
        stage.addActor(leaderboardBtn);
        stage.addActor(unmutedBtn);
        stage.addActor(exitBtn);
        stage.addActor(profileBtn);

    }

}
