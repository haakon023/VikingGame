package group22.viking.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.states.LeaderboardState;
import group22.viking.game.controller.states.LobbyState;
import group22.viking.game.controller.states.PlayState;
import group22.viking.game.controller.states.ProfileSettingsState;
import group22.viking.game.models.Assets;
import group22.viking.game.view.components.CustomImageButton;
import group22.viking.game.view.components.CustomTextButton;
import group22.viking.game.view.components.CustomTextField;

public class MenuScreen implements Screen {

    private VikingGame game;

    // stage
    private Stage stage;

    private Texture title;

    // header
    private Image vikingHeader;
    private Image stopHeader;

    // for background scene
    private Image waveDark;
    private Image waveMedium;
    private Image waveLight;
    private Image waveVeryLight;
    private Image vikingShip;
    private Image castle;

    //TODO: PUT ALL THIS INTO ANIMATION CLASS OR SIMILAR
    // for wave movement
    private float x1 = 0;
    private float x2 = 0;
    // for boat movement
    private float x3 = 0;
    private float y3 = 0;
    boolean movingRight = true;
    // for castle movement
    private float y4 = 0;
    private boolean movingUp = true;

    /*private CustomTextButton tutorialButton;
    private CustomTextButton practiceButton;
    private CustomTextButton joinButton;
    private CustomTextButton hostButton;
    private CustomTextButton leaderboardButton;
    private CustomTextButton unmuteButton;
    private CustomTextButton exitButton;

    private TextField joinTextField;

    private CustomImageButton profileButton;*/

    TextureRegion profileTextureRegion;
    TextureRegionDrawable profileTextureRegionDrawable;


    /*
    constructor, do not load any actual files like pngs here. Instead do it in the show method
    */
    public MenuScreen(VikingGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH, VikingGame.SCREEN_HEIGHT, game.getCamera()));
    }

    @Override
    public void show() {
        //delegate input Events to all Actors
        Gdx.input.setInputProcessor(stage);

        //stage clear to make sure there aren't any further animations
        stage.clear();

        //header
        vikingHeader = new Image(Assets.getTexture("img/vikingHeader.png"));
        vikingHeader.setPosition(VikingGame.SCREEN_WIDTH/2-430,VikingGame.SCREEN_HEIGHT -250);
        vikingHeader.setWidth(660);
        vikingHeader.setHeight(200);
        stopHeader = new Image(Assets.getTexture("img/stopHeader.png"));
        stopHeader.setPosition(VikingGame.SCREEN_WIDTH/2,VikingGame.SCREEN_HEIGHT -380);
        stopHeader.setWidth(430);
        stopHeader.setHeight(300);
        stage.addActor(vikingHeader);
        stage.addActor(stopHeader);

        //background
        waveDark = new Image(Assets.getTexture("img/waveDark.png"));
        castle = new Image(Assets.getTexture("img/castle.png"));
        castle.setWidth(600);
        castle.setHeight(500);
        waveMedium = new Image(Assets.getTexture("img/waveMedium.png"));
        vikingShip = new Image(Assets.getTexture("img/vikingShip.png"));
        vikingShip.setWidth(600);
        vikingShip.setHeight(500);
        waveLight = new Image(Assets.getTexture("img/waveLight.png"));
        waveVeryLight = new Image(Assets.getTexture("img/waveVeryLight.png"));
        stage.addActor(waveDark);
        stage.addActor(castle);
        stage.addActor(waveMedium);
        stage.addActor(vikingShip);
        stage.addActor(waveLight);
        stage.addActor(waveVeryLight);



        initButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        //calls draw for every actor it contains
        stage.draw();




        //BEGIN
        game.getBatch().begin();

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
        game.getBatch().end();
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
        //title.dispose();
        stage.dispose();
    }

    private void initButtons() {

        Vector2 verySmallButtonSize = new Vector2(120, 120);
        Vector2 smallButtonSize = new Vector2(150, 150);
        Vector2 bigButtonSize =  new Vector2(700, 150);

        Vector2 profileImageSize = new Vector2(500, 500);
        /*
        tutorialButton = new CustomTextButton(
                "Tutorial",
                new Vector2(150, VikingGame.SCREEN_HEIGHT/2+80-50),
                bigButtonSize);
        tutorialButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                GameStateManager.getInstance(game).push(new PlayState(game, PlayState.Type.TUTORIAL));
            }
        });

        practiceButton = new CustomTextButton(
                "Practice",
                new Vector2(150, VikingGame.SCREEN_HEIGHT/2-80-150-50),
                bigButtonSize);
        practiceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance(game).push(new PlayState(game, PlayState.Type.PRACTICE));
            }
        });

        hostButton = new CustomTextButton(
                "Host",
                new Vector2(VikingGame.SCREEN_WIDTH/2+(VikingGame.SCREEN_WIDTH/2-700-150),VikingGame.SCREEN_HEIGHT/2-80-150-50),
                bigButtonSize);
        hostButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance(game).push(new LobbyState(game));
            }
        });


        unmuteButton = new CustomTextButton(
                "U",
                new Vector2(VikingGame.SCREEN_WIDTH-120-60,50),
                verySmallButtonSize);
        unmuteButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //todo
            }
        });


        exitButton = new CustomTextButton(
                "Exit",
                new Vector2(150,VikingGame.SCREEN_HEIGHT-200),
                verySmallButtonSize);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });


        leaderboardButton = new CustomTextButton(
                "L",
                new Vector2(VikingGame.SCREEN_WIDTH-120-60-120-60,50),
                verySmallButtonSize);
        leaderboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance(game).push(new LeaderboardState(game));
            }
        });

        joinTextField = new CustomTextField(
                "Enter PIN",
                new Vector2(VikingGame.SCREEN_WIDTH/2+(VikingGame.SCREEN_WIDTH/2-700-150),
                VikingGame.SCREEN_HEIGHT/2+80-50),
                new Vector2(530, 150)
                );



        joinButton = new CustomTextButton(
                "Join",
                // new Vector2(VikingGame.SCREEN_WIDTH/2+(VikingGame.SCREEN_WIDTH/2-700-150)+joinTextField.getWidth() +20,
                new Vector2(VikingGame.SCREEN_WIDTH/2+(VikingGame.SCREEN_WIDTH/2-700-150)+ 530 +20,
                        VikingGame.SCREEN_HEIGHT/2+80-50),
                smallButtonSize);
        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance(game).push(new LobbyState(game));
            }
        });


        //todo set the texture to the user specific profile image
        profileTextureRegion = new TextureRegion(Assets.getTexture("img/WizardSpriteHead.png"));
        profileTextureRegionDrawable = new TextureRegionDrawable(profileTextureRegion);

        profileButton = new CustomImageButton(
                profileTextureRegionDrawable,
                // new Vector2(VikingGame.SCREEN_WIDTH/2-profileButton.getWidth()/2,
                new Vector2(VikingGame.SCREEN_WIDTH/2-500/2,
                //VikingGame.SCREEN_HEIGHT/2-profileButton.getHeight()/2-80),
                VikingGame.SCREEN_HEIGHT/2-500/2-80),
                profileImageSize);
        profileButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameStateManager.getInstance(game).push(new ProfileSettingsState(game));
            }
        });





        stage.addActor(tutorialButton);
        stage.addActor(practiceButton);
        stage.addActor(joinTextField);
        stage.addActor(joinButton);
        stage.addActor(hostButton);
        stage.addActor(profileButton);
        stage.addActor(leaderboardButton);
        stage.addActor(unmuteButton);
        stage.addActor(exitButton);

        */


    }
/*
    public CustomTextButton getTutorialButton() {
        return tutorialButton;
    }

    public CustomTextButton getPracticeButton() {
        return practiceButton;
    }

    public CustomTextButton getJoinButton() {
        return joinButton;
    }

    public CustomTextButton getHostButton() {
        return hostButton;
    }

    public CustomTextButton getLeaderboardButton() {
        return leaderboardButton;
    }

    public CustomTextButton getUnmuteButton() {
        return unmuteButton;
    }

    public CustomTextButton getExitButton() {
        return exitButton;
    }

    public TextField getJoinTextField() {
        return joinTextField;
    }

    public CustomImageButton getProfileButton() {
        return profileButton;
    }
*/
}
