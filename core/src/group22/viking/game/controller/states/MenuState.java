package group22.viking.game.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.JViewport;
import javax.swing.Scrollable;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import org.w3c.dom.Text;

public class MenuState implements Screen {

    private final VikingGame vikingGame;

    //private Window leaderboardPopUp = new Window();

    //stage
    private Stage stage;
    //skin
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
    private Texture tutorialBtn;
    private Texture practiceBtn;
    private Texture joinBtn;
    private Texture hostBtn;
    private Texture profileBtn;

    //small buttons
    private Texture leaderboardBtn;
    private Texture unmutedBtn;
    private Texture mutedBtn;


    /*
    constructor, do not load any actual files like pngs here. Instead do it in the show method
    */
    public MenuState(final VikingGame vikingGame) {
        this.vikingGame = vikingGame;
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH,VikingGame.SCREEN_HEIGHT,vikingGame.camera));
        this.skin = new Skin(Gdx.files.internal(""));
    }




    @Override
    public void show() {
        //delegate input Events to all Actors
        Gdx.input.setInputProcessor(stage);


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

        //main buttons
        tutorialBtn = new Texture("img/button.png");
        practiceBtn = new Texture("img/button.png");
        hostBtn = new Texture("img/button.png");
        joinBtn = new Texture("img/button.png");
        profileBtn = new Texture("img/button.png");

        //small buttons
        leaderboardBtn = new Texture("img/leaderboardButton.png");
        unmutedBtn = new Texture("img/unmutedButton.png");
        mutedBtn = new Texture("img/mutedButton.png");

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

        tutorialBtn.dispose();
        practiceBtn.dispose();
        joinBtn.dispose();
        hostBtn.dispose();
        profileBtn.dispose();

        leaderboardBtn.dispose();
        unmutedBtn.dispose();
        mutedBtn.dispose();
        stage.dispose();
    }
}
