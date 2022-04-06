package group22.viking.game.view;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import group22.viking.game.controller.GameStateManager;
import group22.viking.game.controller.VikingGame;
import group22.viking.game.controller.states.LeaderboardState;
import group22.viking.game.controller.states.PlayState;
import group22.viking.game.controller.states.ProfileSettingsState;
import group22.viking.game.models.Assets;
import group22.viking.game.view.components.CustomImageButton;
import group22.viking.game.view.components.CustomTextButton;
import group22.viking.game.view.components.CustomTextField;

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

    TextureRegion profileTextureRegion;
    TextureRegionDrawable profileTextureRegionDrawable;

    private final ViewComponentFactory viewComponentFactory;

    public MenuView(SpriteBatch batch, Camera camera) {
        super(batch);
        this.viewComponentFactory = new ViewComponentFactory();

        this.stage = new Stage(new FitViewport(VikingGame.SCREEN_WIDTH, VikingGame.SCREEN_HEIGHT, camera));

        this.init();
    }

    @Override
    public void init() {
        // stage clear to make sure there aren't any further animations
        stage.clear();

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

        tutorialButton = viewComponentFactory.createTextButton(
                "Tutorial",
                new Vector2(150, VikingGame.SCREEN_HEIGHT/2+80-50),
                bigButtonSize
        );

        practiceButton = viewComponentFactory.createTextButton(
                "Practice",
                new Vector2(150, VikingGame.SCREEN_HEIGHT / 2 - 80 - 150 - 50),
                bigButtonSize);


        hostButton = viewComponentFactory.createTextButton(
                "Host",
                new Vector2(VikingGame.SCREEN_WIDTH/2+(VikingGame.SCREEN_WIDTH/2-700-150),VikingGame.SCREEN_HEIGHT/2-80-150-50),
                bigButtonSize
        );

        joinButton = viewComponentFactory.createTextButton(
                "Join",
                new Vector2(VikingGame.SCREEN_WIDTH/2+(VikingGame.SCREEN_WIDTH/2-700-150)+ 530 +20,
                        VikingGame.SCREEN_HEIGHT/2+80-50),
                smallButtonSize);

        exitButton = viewComponentFactory.createTextButton(
                "Exit",
                new Vector2(150, VikingGame.SCREEN_HEIGHT - 200),
                verySmallButtonSize);

        muteButton = viewComponentFactory.createTextButton(
                "U",
                new Vector2(VikingGame.SCREEN_WIDTH - 120 - 60, 50),
                verySmallButtonSize);

        leaderboardButton = viewComponentFactory.createTextButton(
                "L",
                new Vector2(VikingGame.SCREEN_WIDTH - 120 - 60 - 120 - 60, 50),
                verySmallButtonSize);

        profileButton = viewComponentFactory.createImageButton(
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

        joinTextField = viewComponentFactory.createTextField(
                "Enter PIN",
                new Vector2(VikingGame.SCREEN_WIDTH / 2 + (VikingGame.SCREEN_WIDTH / 2 - 700 - 150),
                        VikingGame.SCREEN_HEIGHT / 2 + 80 - 50),
                new Vector2(530, 150)
        );

        stage.addActor(joinTextField);

    }

    @Override
    public void runInitialAnimations() {
        // TODO
        Action fadeInAnimation = sequence(alpha(0),
                parallel(fadeIn(0.5f),
                        moveBy(0,-20,.5f, Interpolation.pow5Out)
                ));

        tutorialButton.addAction(fadeInAnimation);
        practiceButton.addAction(fadeInAnimation);
        hostButton.addAction(fadeInAnimation);
        joinTextField.addAction(fadeInAnimation);
        profileButton.addAction(fadeInAnimation);
        joinButton.addAction(fadeInAnimation);
        leaderboardButton.addAction(fadeInAnimation);
        exitButton.addAction(fadeInAnimation);

    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    void drawElements(float deltaTime) {
        System.out.println("DRAW ELEMENTS");
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
