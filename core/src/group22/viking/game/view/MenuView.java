package group22.viking.game.view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.VikingGame;
import group22.viking.game.models.Assets;

public class MenuView extends View {

    private TextButton tutorialButton;
    private TextButton hostButton;
    private TextButton joinButton;
    private TextButton exitButton;
    private TextButton leaderboardButton;
    private TextButton muteButton;
    private TextButton practiceButton;

    private ImageButton profileButton;

    private TextField joinTextField;

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

    //TODO: PUT ALL THIS INTO ANIMATION CLASS OR SIMILAR; BEGIN OF COPIED PART
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
    //TODO: END OF COPIED PART

    TextureRegion profileTextureRegion;
    TextureRegionDrawable profileTextureRegionDrawable;


    public MenuView(SpriteBatch batch, Camera camera) {
        super(batch);

        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH, VikingGame.SCREEN_HEIGHT, camera));

        this.init();
    }

    @Override
    public void init() {
        // stage clear to make sure there aren't any further animations
        stage.clear();

        createBackground();
        createHeader();
        createButtons();
        createTextField();

        // TODO continue...

        runInitialAnimations();

        stage.act(0);
    }

    private void createButtons() {
        Vector2 verySmallButtonSize = new Vector2(120, 120);
        Vector2 smallButtonSize = new Vector2(150, 150);
        Vector2 bigButtonSize =  new Vector2(700, 150);
        Vector2 profileImageSize = new Vector2(500, 500);

        //todo set the texture to the user specific profile image
        profileTextureRegion = new TextureRegion(Assets.getTexture("img/WizardSpriteHead.png"));
        profileTextureRegionDrawable = new TextureRegionDrawable(profileTextureRegion);

        tutorialButton = factory.createTextButton(
                "Tutorial",
                new Vector2(150, VikingGame.SCREEN_HEIGHT/2+80-50),
                bigButtonSize
        );

        practiceButton = factory.createTextButton(
                "Practice",
                new Vector2(150, VikingGame.SCREEN_HEIGHT / 2 - 80 - 150 - 50),
                bigButtonSize);


        hostButton = factory.createTextButton(
                "Host",
                new Vector2(VikingGame.SCREEN_WIDTH/2+(VikingGame.SCREEN_WIDTH/2-700-150),VikingGame.SCREEN_HEIGHT/2-80-150-50),
                bigButtonSize
        );

        joinButton = factory.createTextButton(
                "Join",
                new Vector2(VikingGame.SCREEN_WIDTH/2+(VikingGame.SCREEN_WIDTH/2-700-150)+ 530 +20,
                        VikingGame.SCREEN_HEIGHT/2+80-50),
                smallButtonSize);

        exitButton = factory.createTextButton(
                "Exit",
                new Vector2(150, VikingGame.SCREEN_HEIGHT - 200),
                verySmallButtonSize);

        muteButton = factory.createTextButton(
                "U",
                new Vector2(VikingGame.SCREEN_WIDTH - 120 - 60, 50),
                verySmallButtonSize);

        leaderboardButton = factory.createTextButton(
                "L",
                new Vector2(VikingGame.SCREEN_WIDTH - 120 - 60 - 120 - 60, 50),
                verySmallButtonSize);

        profileButton = factory.createImageButton(
                profileTextureRegionDrawable,
                // new Vector2(VikingGame.SCREEN_WIDTH/2-profileButton.getWidth()/2,
                new Vector2(VikingGame.SCREEN_WIDTH/2-500/2,
                        //VikingGame.SCREEN_HEIGHT/2-profileButton.getHeight()/2-80),
                        VikingGame.SCREEN_HEIGHT/2-500/2-80),
                profileImageSize);


        stage.addActor(tutorialButton);
        stage.addActor(practiceButton);
        stage.addActor(hostButton);
        stage.addActor(profileButton);
        stage.addActor(joinButton);
        stage.addActor(leaderboardButton);
        stage.addActor(exitButton);
        stage.addActor(muteButton);
    }

    private void createTextField() {

        joinTextField = factory.createTextField(
                "Enter PIN",
                new Vector2(VikingGame.SCREEN_WIDTH / 2 + (VikingGame.SCREEN_WIDTH / 2 - 700 - 150),
                        VikingGame.SCREEN_HEIGHT / 2 + 80 - 50),
                new Vector2(530, 150)
        );

        stage.addActor(joinTextField);
    }

    private void createBackground() {
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
    }

    private void createHeader() {
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
    }

    @Override
    public void runInitialAnimations() {
        tutorialButton.addAction(factory.FADE_IN_ANIMATION);
        practiceButton.addAction(factory.FADE_IN_ANIMATION);
        hostButton.addAction(factory.FADE_IN_ANIMATION);
        joinTextField.addAction(factory.FADE_IN_ANIMATION);
        profileButton.addAction(factory.FADE_IN_ANIMATION);
        joinButton.addAction(factory.FADE_IN_ANIMATION);
        leaderboardButton.addAction(factory.FADE_IN_ANIMATION);
        exitButton.addAction(factory.FADE_IN_ANIMATION);

    }

    /*
    @Override
    public void dispose() {
        super.dispose();
        //stage.dispose();
    }
    */

    void drawElements(float deltaTime) {

        //TODO: COPIED FROM MENUSCREEN

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
        //TODO: END OF COPIED PART

        stage.act(deltaTime);
        stage.draw();
    }

    public TextButton getTutorialButton() {
        return tutorialButton;
    }

    public TextButton getHostButton() {
        return hostButton;
    }

    public TextButton getJoinButton() {
        return joinButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public TextButton getLeaderboardButton() {
        return leaderboardButton;
    }

    public TextButton getMuteButton() {
        return muteButton;
    }

    public TextButton getPracticeButton() {
        return practiceButton;
    }

    public TextField getJoinTextField() {
        return joinTextField;
    }

    public ImageButton getProfileButton() {
        return profileButton;
    }
}